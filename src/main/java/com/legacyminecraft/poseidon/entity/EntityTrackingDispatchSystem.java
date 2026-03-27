package com.legacyminecraft.poseidon.entity;


import java.util.Iterator;
import java.util.Set;

/**
 * Canonical dispatch helper for tracker packet fan-out and remove-queue synchronization.
 */
public final class EntityTrackingDispatchSystem {
    private static final EntityTrackingDispatchSystem INSTANCE = new EntityTrackingDispatchSystem();

    private EntityTrackingDispatchSystem() {
    }

    public static EntityTrackingDispatchSystem getInstance() {
        return INSTANCE;
    }

    public void sendToTrackedPlayers(Set trackedPlayers, Object packet) {
        Iterator iterator = trackedPlayers.iterator();
        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();
            sendPacket(entityplayer, packet);
        }
    }

    public void sendToTrackedPlayersAndSelf(Set trackedPlayers, Entity tracker, Object packet) {
        sendToTrackedPlayers(trackedPlayers, packet);
        if (tracker instanceof EntityPlayer) {
            sendPacket((EntityPlayer) tracker, packet);
        }
    }

    public void queueDestroyForTrackedPlayers(Set trackedPlayers, int trackerId) {
        Iterator iterator = trackedPlayers.iterator();
        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();
            queueRemoveId(entityplayer, trackerId);
        }
    }

    public void removeTrackedPlayer(Set trackedPlayers, EntityPlayer entityplayer, int trackerId) {
        if (trackedPlayers.contains(entityplayer)) {
            queueRemoveId(entityplayer, trackerId);
            trackedPlayers.remove(entityplayer);
        }
    }

    private void sendPacket(EntityPlayer player, Object packet) {
        try {
            Object netServerHandler = player.getClass().getField("netServerHandler").get(player);
            if (netServerHandler != null) {
                java.lang.reflect.Method[] methods = netServerHandler.getClass().getMethods();
                for (int i = 0; i < methods.length; ++i) {
                    java.lang.reflect.Method method = methods[i];
                    if ("sendPacket".equals(method.getName()) && method.getParameterTypes().length == 1) {
                        method.invoke(netServerHandler, packet);
                        return;
                    }
                }
            }
        } catch (ReflectiveOperationException ignored) {
            // no-op in lean migration scaffold
        }
    }

    private void queueRemoveId(EntityPlayer player, int trackerId) {
        try {
            Object queue = player.getClass().getField("removeQueue").get(player);
            if (queue instanceof java.util.List) {
                ((java.util.List) queue).add(Integer.valueOf(trackerId));
            }
        } catch (ReflectiveOperationException ignored) {
            // no-op in lean migration scaffold
        }
    }
}
