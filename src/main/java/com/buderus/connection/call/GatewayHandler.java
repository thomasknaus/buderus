package com.buderus.connection.call;

import com.buderus.connection.config.KM200Device;
import com.buderus.connection.config.KM200ServiceObject;
import com.buderus.connection.config.handler.KM200DataHandler;
import com.buderus.connection.config.handler.KM200ServiceHandler;
import com.buderus.connection.config.handler.KM200VirtualServiceHandler;
import com.buderus.connection.config.util.KM200ThingType;
import com.buderus.connection.config.util.KM200Utils;
import com.buderus.connection.config.util.ThingStatus;
import com.google.gson.JsonObject;
import org.apache.commons.httpclient.HttpClient;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class GatewayHandler {

    private static final Logger logger = LoggerFactory.getLogger(GatewayHandler.class);

    protected void initializeClass(){
        this.updateStatus(ThingStatus.UNINITIALIZED);
        this.remoteDevice = new KM200Device();
        this.dataHandler = new KM200DataHandler(this.remoteDevice);
    }

    private int readDelay = 100;
    private HttpClient client = null;
    private KM200Device device = null;
    private KM200Device remoteDevice;
    private KM200DataHandler dataHandler;
    private boolean initialized = false;
    private ThingStatus status;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    protected final Map<Channel, JsonObject> sendMap = Collections.synchronizedMap(new LinkedHashMap());

    public KM200Device getDevice() {
        return this.remoteDevice;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void prepareMessage(Thing thing, Channel channel, Command command) {
        if (this.getDevice() != null && this.getDevice().getInited() && channel != null) {
            JsonObject newObject = null;
            State state = null;
            String service = KM200Utils.checkParameterReplacement(channel, this.getDevice());
            this.logger.debug("handleCommand channel: {} service: {}", (Object) channel.getLabel(), (Object) service);
            newObject = this.dataHandler.sendProvidersState(service, command, channel.getAcceptedItemType(), KM200Utils.getChannelConfigurationStrings(channel));
            KM200Device kM200Device = this.getDevice();
            synchronized (kM200Device) {
                if (newObject != null) {
                    this.sendMap.put(channel, newObject);
                } else if (this.getDevice().containsService(service).booleanValue() && this.getDevice().getServiceObject(service).getVirtual() == 1) {
                    /*String parent = this.getDevice().getServiceObject(service).getParent();
                    for (Thing actThing : this.getThing().getThings()) {
                        this.logger.debug("Checking: {}", (Object) actThing.getUID().getAsString());
                        for (Channel tmpChannel : actThing.getChannels()) {
                            String actService = KM200Utils.checkParameterReplacement(tmpChannel, this.getDevice());
                            this.logger.debug("tmpService: {}", (Object) actService);
                            String actParent = this.getDevice().getServiceObject(actService).getParent();
                            if (actParent == null || !actParent.equals(parent) || (state = this.dataHandler.getProvidersState(actService, tmpChannel.getAcceptedItemType(), KM200Utils.getChannelConfigurationStrings(tmpChannel))) == null)
                                continue;
                            try {
                                this.updateState(tmpChannel.getUID(), state);
                            } catch (IllegalStateException e) {
                                this.logger.error("Could not get updated item state, Error: {}", (Throwable) e);
                            }
                        }
                    }*/
                } else {
                    this.logger.warn("Service is not availible: {}", (Object) service);
                }
            }
        }
    }

    protected void readCapabilities() {
        this.logger.debug("read Capabilities..");
        for (KM200ThingType thing : KM200ThingType.values()) {
            String rootPath = thing.getRootPath();
            this.logger.debug("Rootpath: {}", (Object) rootPath);
            if (rootPath.isEmpty() || rootPath.indexOf("/", 0) != rootPath.lastIndexOf("/", rootPath.length() - 1))
                continue;
            if (this.remoteDevice.getBlacklistMap().contains(thing.getRootPath())) {
                this.logger.debug("Service on blacklist: {}", (Object) thing.getRootPath());
                return;
            }
            KM200ServiceHandler serviceHandler = new KM200ServiceHandler(thing.getRootPath(), null, this.remoteDevice);
            serviceHandler.initObject();
        }
        this.logger.debug("init Virtual Objects");
        KM200VirtualServiceHandler virtualServiceHandler = new KM200VirtualServiceHandler(this.remoteDevice);
        virtualServiceHandler.initVirtualObjects();
        this.logger.debug("list All Services");
        this.getDevice().listAllServices();
        this.logger.debug("... Update of the KM200 Binding configuration completed");
        this.getDevice().setInited(true);
    }


    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispose() {
        this.logger.debug("Shutdown send executor");
        this.executor.shutdown();
        if (this.getDevice() != null) {
            KM200Device kM200Device = this.getDevice();
            synchronized (kM200Device) {
                this.getDevice().setInited(false);
                this.getDevice().setIP4Address("");
                this.getDevice().setCryptKeyPriv("");
                this.getDevice().setMD5Salt("");
                this.getDevice().setGatewayPassword("");
                this.getDevice().setPrivatePassword("");
                this.getDevice().serviceTreeMap.clear();
            }
        }
    }

    protected KM200Device getRemoteDevice() {
        return this.remoteDevice;
    }

    protected boolean isInitialized() {
        return initialized;
    }

    protected void updateStatus(ThingStatus status) {
        this.status = status;
    }

    protected class GetKM200Runnable
            implements Runnable {
        private final GatewayHandler gatewayHandler;
        private final KM200Device remoteDevice;
        private final Logger logger = LoggerFactory.getLogger(GetKM200Runnable.class);
        private final Map<Channel, JsonObject> sendMap;

        public GetKM200Runnable(Map<Channel, JsonObject> sendMap, GatewayHandler gatewayHandler, KM200Device remoteDevice) {
            this.sendMap = sendMap;
            this.gatewayHandler = gatewayHandler;
            this.remoteDevice = remoteDevice;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            this.logger.debug("GetKM200Runnable");
            KM200Device kM200Device = this.remoteDevice;
            synchronized (kM200Device) {
                if (this.remoteDevice.getInited()) {
                    this.remoteDevice.resetAllUpdates(this.remoteDevice.serviceTreeMap);
                }
            }
        }
    }

    protected class SendKM200Runnable
            implements Runnable {
        private final Logger logger = LoggerFactory.getLogger(SendKM200Runnable.class);
        private final Map<Channel, JsonObject> newObject;
        private final KM200Device remoteDevice;

        public SendKM200Runnable(Map<Channel, JsonObject> newObject, KM200Device remoteDevice) {
            this.newObject = newObject;
            this.remoteDevice = remoteDevice;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            Map.Entry<Channel, JsonObject> nextEntry;
            this.logger.debug("Send-Executor started");
            do {
                nextEntry = null;
                KM200Device kM200Device = this.remoteDevice;
                synchronized (kM200Device) {
                    Map<Channel, JsonObject> map = this.newObject;
                    synchronized (map) {
                        Iterator<Map.Entry<Channel, JsonObject>> i = this.newObject.entrySet().iterator();
                        if (i.hasNext()) {
                            this.logger.debug("Send-Thread, new entry");
                            nextEntry = i.next();
                            i.remove();
                        }
                    }
                    if (nextEntry != null) {
                        Channel channel = (Channel) nextEntry.getKey();
                        JsonObject newObject = nextEntry.getValue();
                        String service = KM200Utils.checkParameterReplacement(channel, this.remoteDevice);
                        KM200ServiceObject object = this.remoteDevice.getServiceObject(service);
                        this.logger.debug("Sending: {} to : {}", (Object) newObject, (Object) service);
                        if (object.getVirtual() == 1) {
                            this.remoteDevice.setServiceNode(object.getParent(), newObject);
                        } else {
                            this.remoteDevice.setServiceNode(service, newObject);
                        }
                    }
                }
            } while (nextEntry != null);
        }
    }

    private void updateBridgeProperties() {
        ArrayList<String> propertyServices = new ArrayList<String>();
        propertyServices.add(KM200ThingType.GATEWAY.getRootPath());
        propertyServices.add(KM200ThingType.SYSTEM.getRootPath());
        Map bridgeProperties = this.editProperties();
        for (KM200ThingType tType : KM200ThingType.values()) {
            List<String> asProperties = tType.asBridgeProperties();
            String rootPath = tType.getRootPath();
            if (rootPath.isEmpty()) continue;
            this.logger.debug("Add Property rootPath: {}", (Object)rootPath);
            KM200ServiceObject serObj = this.getDevice().getServiceObject(rootPath);
            for (String subKey : asProperties) {
                String subKeyType;
                if (!serObj.serviceTreeMap.containsKey(subKey) || !"stringValue".equals(subKeyType = serObj.serviceTreeMap.get(subKey).getServiceType()) && !"floatValue".equals(subKeyType)) continue;
                if (bridgeProperties.containsKey(subKey)) {
                    bridgeProperties.remove(subKey);
                }
                this.logger.debug("Add Property: {}  :{}", (Object)subKey, (Object)serObj.serviceTreeMap.get(subKey).getValue().toString());
                bridgeProperties.put(subKey, serObj.serviceTreeMap.get(subKey).getValue().toString());
            }
        }
        this.updateProperties(bridgeProperties);
    }
}
