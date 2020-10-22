/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.eclipse.smarthome.core.thing.ThingUID
 *  org.eclipse.smarthome.core.thing.type.ChannelGroupType
 *  org.eclipse.smarthome.core.thing.type.ChannelGroupTypeUID
 *  org.eclipse.smarthome.core.thing.type.ChannelType
 *  org.eclipse.smarthome.core.thing.type.ChannelTypeProvider
 *  org.eclipse.smarthome.core.thing.type.ChannelTypeUID
 *  org.osgi.service.component.annotations.Component
 */
package com.buderus.connection.config;

import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.type.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KM200ChannelTypeProvider
implements ChannelTypeProvider {
    private List<ChannelType> channelTypes = new CopyOnWriteArrayList<ChannelType>();
    private List<ChannelGroupType> channelGroupTypes = new CopyOnWriteArrayList<ChannelGroupType>();

    public Collection<ChannelType> getChannelTypes(Locale locale) {
        return this.channelTypes;
    }

    public ChannelType getChannelType(ChannelTypeUID channelTypeUID, Locale locale) {
        for (ChannelType channelType : this.channelTypes) {
            if (!channelType.getUID().equals((Object)channelTypeUID)) continue;
            return channelType;
        }
        return null;
    }

    public ChannelGroupType getChannelGroupType(ChannelGroupTypeUID channelGroupTypeUID, Locale locale) {
        for (ChannelGroupType channelGroupType : this.channelGroupTypes) {
            if (!channelGroupType.getUID().equals((Object)channelGroupTypeUID)) continue;
            return channelGroupType;
        }
        return null;
    }

    public Collection<ChannelGroupType> getChannelGroupTypes(Locale locale) {
        return this.channelGroupTypes;
    }

    public void addChannelType(ChannelType type) {
        this.channelTypes.add(type);
    }

    public void removeChannelType(ChannelType type) {
        this.channelTypes.remove((Object)type);
    }

    public void removeChannelTypesForThing(ThingUID uid) {
        ArrayList<ChannelType> removes = new ArrayList<ChannelType>();
        for (ChannelType c : this.channelTypes) {
            if (!c.getUID().getAsString().startsWith(uid.getAsString())) continue;
            removes.add(c);
        }
        this.channelTypes.removeAll(removes);
    }
}

