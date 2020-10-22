/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.eclipse.smarthome.core.types.StateOption
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config.handler;

import com.buderus.connection.config.KM200Device;
import com.buderus.connection.config.KM200ServiceObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.smarthome.core.types.StateOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class KM200SwitchProgramServiceHandler {
    private final Logger logger = LoggerFactory.getLogger(KM200SwitchProgramServiceHandler.class);
    private int maxNbOfSwitchPoints = 8;
    private int maxNbOfSwitchPointsPerDay = 8;
    private int switchPointTimeRaster = 10;
    private String setpointProperty;
    private String positiveSwitch;
    private String negativeSwitch;
    protected final Integer MIN_TIME = 0;
    protected final Integer MAX_TIME = 1430;
    protected final String TYPE_MONDAY = "Mo";
    protected final String TYPE_TUESDAY = "Tu";
    protected final String TYPE_WEDNESDAY = "We";
    protected final String TYPE_THURSDAY = "Th";
    protected final String TYPE_FRIDAY = "Fr";
    protected final String TYPE_SATURDAY = "Sa";
    protected final String TYPE_SUNDAY = "Su";
    private String activeDay = "Mo";
    private Integer activeCycle = 1;
    public Map<String, HashMap<String, ArrayList<Integer>>> switchMap = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
    private List<String> days = new ArrayList<String>();
    public static List<StateOption> daysList;
    ArrayList<String> setpoints;

    public KM200SwitchProgramServiceHandler() {
        this.days.add("Mo");
        this.days.add("Tu");
        this.days.add("We");
        this.days.add("Th");
        this.days.add("Fr");
        this.days.add("Sa");
        this.days.add("Su");
        daysList = new ArrayList<StateOption>();
        daysList.add(new StateOption("Mo", "Monday"));
        daysList.add(new StateOption("Tu", "Tuesday"));
        daysList.add(new StateOption("We", "Wednesday"));
        daysList.add(new StateOption("Th", "Thursday"));
        daysList.add(new StateOption("Fr", "Friday"));
        daysList.add(new StateOption("Sa", "Saturday"));
        daysList.add(new StateOption("Su", "Sunday"));
        this.setpoints = new ArrayList();
    }

    void initWeeklist(String setpoint) {
        HashMap<String, ArrayList<Integer>> weekMap = this.switchMap.get(setpoint);
        if (weekMap == null) {
            weekMap = new HashMap();
            for (String day : this.days) {
                weekMap.put(day, new ArrayList());
            }
            this.switchMap.put(setpoint, weekMap);
        }
    }

    void addSwitch(String day, String setpoint, int time) {
        HashMap<String, ArrayList<Integer>> weekMap;
        this.logger.debug("Adding day: {} setpoint: {} time: {}", new Object[]{day, setpoint, time});
        if (!this.days.contains(day)) {
            this.logger.error("This type of weekday is not supported, get day: {}", (Object)day);
            throw new IllegalArgumentException("This type of weekday is not supported, get day: " + day);
        }
        if (!this.setpoints.contains(setpoint) && this.setpoints.size() == 2 && "on".compareTo(setpoint) == 0 && "high".compareTo(this.setpoints.get(0)) == 0 && "off".compareTo(this.setpoints.get(1)) == 0) {
            if ("on".compareTo(this.positiveSwitch) == 0 && "off".compareTo(this.negativeSwitch) == 0) {
                this.logger.info("!!! Wrong configuration on device. 'on' instead of 'high' in switch program. It seems that's a firmware problem-> ignoring it !!!");
            } else {
                throw new IllegalArgumentException("This type of setpoint is not supported, get setpoint: " + setpoint);
            }
        }
        if ((weekMap = this.switchMap.get(setpoint)) == null) {
            this.initWeeklist(setpoint);
            weekMap = this.switchMap.get(setpoint);
        }
        ArrayList<Integer> dayList = weekMap.get(day);
        dayList.add(time);
        Collections.sort(dayList);
    }

    void removeAllSwitches() {
        if (this.switchMap != null) {
            this.switchMap.clear();
        }
    }

    public void setMaxNbOfSwitchPoints(Integer nbr) {
        if (nbr != null) {
            this.maxNbOfSwitchPoints = nbr;
        }
    }

    public void setMaxNbOfSwitchPointsPerDay(Integer nbr) {
        if (nbr != null) {
            this.maxNbOfSwitchPointsPerDay = nbr;
        }
    }

    public void setSwitchPointTimeRaster(Integer raster) {
        if (raster != null) {
            this.switchPointTimeRaster = raster;
        }
    }

    public void setSetpointProperty(String property) {
        this.setpointProperty = property;
    }

    public void setActiveDay(String day) {
        if (!this.days.contains(day)) {
            this.logger.error("This type of weekday is not supported, get day: {}", (Object)day);
            throw new IllegalArgumentException("This type of weekday is not supported, get day: " + day);
        }
        this.activeDay = day;
    }

    public void setActiveCycle(Integer cycle) {
        if (cycle > this.getMaxNbOfSwitchPoints() / 2 || cycle > this.getMaxNbOfSwitchPointsPerDay() / 2 || cycle < 1) {
            this.logger.error("The value of cycle is not valid, get cycle: {}", (Object)cycle);
            throw new IllegalArgumentException("The value of cycle is not valid, get cycle: " + cycle.toString());
        }
        this.activeCycle = cycle > this.getNbrCycles() + 1 || this.getNbrCycles() == 0 ? Integer.valueOf(this.getNbrCycles() + 1) : cycle;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setActivePositiveSwitch(Integer time) {
        Integer actTime = time < this.MIN_TIME ? this.MIN_TIME : (time > this.MAX_TIME ? this.MAX_TIME : time);
        Map<String, HashMap<String, ArrayList<Integer>>> map = this.switchMap;
        synchronized (map) {
            ArrayList<Integer> daysList;
            HashMap<String, ArrayList<Integer>> week = this.switchMap.get(this.getPositiveSwitch());
            if (week != null && (daysList = week.get(this.getActiveDay())) != null) {
                Integer actC = this.getActiveCycle();
                Integer nbrC = this.getNbrCycles();
                Integer nSwitch = null;
                boolean newS = false;
                if (nbrC < actC) {
                    newS = true;
                }
                nSwitch = this.switchMap.get(this.getNegativeSwitch()).get(this.getActiveDay()).size() < actC ? Integer.valueOf(0) : this.switchMap.get(this.getNegativeSwitch()).get(this.getActiveDay()).get(actC - 1);
                if (actTime > nSwitch - this.getSwitchPointTimeRaster() && nSwitch > 0) {
                    actTime = nSwitch;
                    if (nSwitch < this.MAX_TIME) {
                        actTime = actTime - this.getSwitchPointTimeRaster();
                    }
                }
                if (actC > 1) {
                    Integer nPrevSwitch = this.switchMap.get(this.getNegativeSwitch()).get(this.getActiveDay()).get(actC - 2);
                    if (actTime < nPrevSwitch + this.getSwitchPointTimeRaster()) {
                        actTime = nPrevSwitch + this.getSwitchPointTimeRaster();
                    }
                }
                if (newS) {
                    daysList.add(actTime);
                } else {
                    daysList.set(actC - 1, actTime);
                }
                this.checkRemovement();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setActiveNegativeSwitch(Integer time) {
        Integer actTime = time < this.MIN_TIME ? this.MIN_TIME : (time > this.MAX_TIME ? this.MAX_TIME : time);
        Map<String, HashMap<String, ArrayList<Integer>>> map = this.switchMap;
        synchronized (map) {
            ArrayList<Integer> daysList;
            HashMap<String, ArrayList<Integer>> week = this.switchMap.get(this.getNegativeSwitch());
            if (week != null && (daysList = week.get(this.getActiveDay())) != null) {
                Integer nbrC = this.getNbrCycles();
                Integer actC = this.getActiveCycle();
                Integer pSwitch = null;
                boolean newS = false;
                if (nbrC < actC) {
                    newS = true;
                }
                pSwitch = this.switchMap.get(this.getPositiveSwitch()).get(this.getActiveDay()).size() < actC ? Integer.valueOf(0) : this.switchMap.get(this.getPositiveSwitch()).get(this.getActiveDay()).get(actC - 1);
                if (actTime < pSwitch + this.getSwitchPointTimeRaster()) {
                    actTime = pSwitch + this.getSwitchPointTimeRaster();
                }
                if (nbrC > actC) {
                    Integer pNextSwitch = this.switchMap.get(this.getPositiveSwitch()).get(this.getActiveDay()).get(actC);
                    if (actTime > pNextSwitch - this.getSwitchPointTimeRaster() && pNextSwitch > 0) {
                        actTime = pNextSwitch - this.getSwitchPointTimeRaster();
                    }
                }
                if (newS) {
                    daysList.add(actTime);
                } else {
                    daysList.set(actC - 1, actTime);
                }
                this.checkRemovement();
            }
        }
    }

    void checkRemovement() {
        if (this.getActiveNegativeSwitch().equals(this.MAX_TIME) && this.getActivePositiveSwitch().equals(this.MAX_TIME) && this.getNbrCycles() > 0) {
            this.switchMap.get(this.getNegativeSwitch()).get(this.getActiveDay()).remove(this.getActiveCycle() - 1);
            this.switchMap.get(this.getPositiveSwitch()).get(this.getActiveDay()).remove(this.getActiveCycle() - 1);
        }
    }

    public boolean determineSwitchNames(KM200Device device) {
        if (this.setpointProperty != null) {
            this.logger.debug("Determine switch names..");
            KM200ServiceObject setpObject = device.getServiceObject(this.setpointProperty);
            if (setpObject.serviceTreeMap.keySet().isEmpty()) {
                return false;
            }
            for (String key : setpObject.serviceTreeMap.keySet()) {
                this.logger.debug("Key: {}", (Object)key);
                this.setpoints.add(key);
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateSwitches(JsonObject nodeRoot, KM200Device device) {
        Map<String, HashMap<String, ArrayList<Integer>>> map = this.switchMap;
        synchronized (map) {
            this.removeAllSwitches();
            JsonArray sPoints = nodeRoot.get("switchPoints").getAsJsonArray();
            this.logger.debug("sPoints: {}", (Object)nodeRoot);
            if (this.positiveSwitch == null || this.negativeSwitch == null) {
                if (sPoints.size() > 0) {
                    for (int i = 0; i < sPoints.size(); ++i) {
                        JsonObject subJSON = sPoints.get(i).getAsJsonObject();
                        String setpoint = subJSON.get("setpoint").getAsString();
                        if (this.positiveSwitch == null || this.negativeSwitch == null) {
                            this.positiveSwitch = setpoint;
                            this.negativeSwitch = setpoint;
                        } else {
                            this.negativeSwitch = setpoint;
                        }
                        if (this.positiveSwitch.equals(this.negativeSwitch)) {
                            continue;
                        }
                        break;
                    }
                } else if (this.setpointProperty != null) {
                    BigDecimal firstVal = null;
                    KM200ServiceObject setpObject = device.getServiceObject(this.setpointProperty);
                    this.logger.debug("No switch points set. Use alternative way. {}", (Object)nodeRoot);
                    for (String key : this.setpoints) {
                        if (this.positiveSwitch == null || this.negativeSwitch == null) {
                            this.positiveSwitch = key;
                            this.negativeSwitch = key;
                            firstVal = (BigDecimal)setpObject.serviceTreeMap.get(key).getValue();
                        } else {
                            BigDecimal nextVal = (BigDecimal)setpObject.serviceTreeMap.get(key).getValue();
                            if (nextVal.compareTo(firstVal) > 0) {
                                this.positiveSwitch = key;
                            } else {
                                this.negativeSwitch = key;
                            }
                        }
                        if (this.positiveSwitch.equalsIgnoreCase(this.negativeSwitch)) {
                            continue;
                        }
                        break;
                    }
                }
            }
            this.logger.debug("Positive switch: {}", (Object)this.positiveSwitch);
            this.logger.debug("Negative switch: {}", (Object)this.negativeSwitch);
            HashMap<String, ArrayList<Integer>> weekMap = null;
            weekMap = this.switchMap.get(this.positiveSwitch);
            if (weekMap == null) {
                this.initWeeklist(this.positiveSwitch);
            }
            if ((weekMap = this.switchMap.get(this.negativeSwitch)) == null) {
                this.initWeeklist(this.negativeSwitch);
            }
            for (int i = 0; i < sPoints.size(); ++i) {
                JsonObject subJSON = sPoints.get(i).getAsJsonObject();
                String day = subJSON.get("dayOfWeek").getAsString();
                String setpoint = subJSON.get("setpoint").getAsString();
                Integer time = subJSON.get("time").getAsInt();
                this.addSwitch(day, setpoint, time);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public JsonObject getUpdatedJSONData(KM200ServiceObject parObject) {
        Map<String, HashMap<String, ArrayList<Integer>>> map = this.switchMap;
        synchronized (map) {
            JsonObject switchRoot;
            block7: {
                boolean prepareNewOnly = false;
                JsonArray sPoints = new JsonArray();
                for (String day : this.days) {
                    JsonObject tmpObj;
                    if (!this.switchMap.get(this.getPositiveSwitch()).containsKey(day) || !this.switchMap.get(this.getNegativeSwitch()).containsKey(day)) continue;
                    Integer minDays = Math.min(this.switchMap.get(this.getPositiveSwitch()).get(day).size(), this.switchMap.get(this.getNegativeSwitch()).get(day).size());
                    Integer j = 0;
                    while (j < minDays) {
                        tmpObj = new JsonObject();
                        tmpObj.addProperty("dayOfWeek", day);
                        tmpObj.addProperty("setpoint", this.getPositiveSwitch());
                        tmpObj.addProperty("time", (Number)this.switchMap.get(this.getPositiveSwitch()).get(day).get(j));
                        sPoints.add((JsonElement)tmpObj);
                        tmpObj = new JsonObject();
                        tmpObj.addProperty("dayOfWeek", day);
                        tmpObj.addProperty("setpoint", this.getNegativeSwitch());
                        tmpObj.addProperty("time", (Number)this.switchMap.get(this.getNegativeSwitch()).get(day).get(j));
                        sPoints.add((JsonElement)tmpObj);
                        j = j + 1;
                    }
                    if (this.switchMap.get(this.getPositiveSwitch()).get(day).size() > minDays) {
                        tmpObj = new JsonObject();
                        tmpObj.addProperty("dayOfWeek", day);
                        tmpObj.addProperty("setpoint", this.getPositiveSwitch());
                        tmpObj.addProperty("time", (Number)this.switchMap.get(this.getPositiveSwitch()).get(day).get(j));
                        sPoints.add((JsonElement)tmpObj);
                        prepareNewOnly = true;
                        continue;
                    }
                    if (this.switchMap.get(this.getNegativeSwitch()).get(day).size() <= minDays) continue;
                    tmpObj = new JsonObject();
                    tmpObj.addProperty("dayOfWeek", day);
                    tmpObj.addProperty("setpoint", this.getNegativeSwitch());
                    tmpObj.addProperty("time", (Number)this.switchMap.get(this.getNegativeSwitch()).get(day).get(j));
                    sPoints.add((JsonElement)tmpObj);
                    prepareNewOnly = true;
                }
                this.logger.debug("New switching points: {}", (Object)sPoints);
                switchRoot = parObject.getJSONData();
                switchRoot.remove("switchPoints");
                switchRoot.add("switchPoints", (JsonElement)sPoints);
                parObject.setJSONData(switchRoot);
                if (!prepareNewOnly) break block7;
                return null;
            }
            return switchRoot;
        }
    }

    int getMaxNbOfSwitchPoints() {
        return this.maxNbOfSwitchPoints;
    }

    int getMaxNbOfSwitchPointsPerDay() {
        return this.maxNbOfSwitchPointsPerDay;
    }

    public int getSwitchPointTimeRaster() {
        return this.switchPointTimeRaster;
    }

    public String getSetpointProperty() {
        return this.setpointProperty;
    }

    public String getPositiveSwitch() {
        return this.positiveSwitch;
    }

    public String getNegativeSwitch() {
        return this.negativeSwitch;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Integer getNbrCycles() {
        Map<String, HashMap<String, ArrayList<Integer>>> map = this.switchMap;
        synchronized (map) {
            HashMap<String, ArrayList<Integer>> weekP = this.switchMap.get(this.getPositiveSwitch());
            HashMap<String, ArrayList<Integer>> weekN = this.switchMap.get(this.getNegativeSwitch());
            if (weekP != null && weekN != null) {
                if (weekP.isEmpty() && weekN.isEmpty()) {
                    return 0;
                }
                ArrayList<Integer> daysListP = weekP.get(this.getActiveDay());
                ArrayList<Integer> daysListN = weekN.get(this.getActiveDay());
                return Math.min(daysListP.size(), daysListN.size());
            }
        }
        return null;
    }

    public String getActiveDay() {
        return this.activeDay;
    }

    public Integer getActiveCycle() {
        return this.activeCycle;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Integer getActivePositiveSwitch() {
        Map<String, HashMap<String, ArrayList<Integer>>> map = this.switchMap;
        synchronized (map) {
            Integer cycl;
            ArrayList<Integer> daysList;
            HashMap<String, ArrayList<Integer>> week = this.switchMap.get(this.getPositiveSwitch());
            if (week != null && (daysList = week.get(this.getActiveDay())).size() > 0 && (cycl = this.getActiveCycle()) <= daysList.size()) {
                return daysList.get(this.getActiveCycle() - 1);
            }
        }
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Integer getActiveNegativeSwitch() {
        Map<String, HashMap<String, ArrayList<Integer>>> map = this.switchMap;
        synchronized (map) {
            Integer cycl;
            ArrayList<Integer> daysList;
            HashMap<String, ArrayList<Integer>> week = this.switchMap.get(this.getNegativeSwitch());
            if (week != null && (daysList = week.get(this.getActiveDay())).size() > 0 && (cycl = this.getActiveCycle()) <= daysList.size()) {
                return daysList.get(this.getActiveCycle() - 1);
            }
        }
        return 0;
    }
}

