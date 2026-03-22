package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;

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

    public void sendToTrackedPlayers(Set trackedPlayers, Packet packet) {
        Iterator iterator = trackedPlayers.iterator();
        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();
            entityplayer.netServerHandler.sendPacket(packet);
        }
    }

    public void sendToTrackedPlayersAndSelf(Set trackedPlayers, Entity tracker, Packet packet) {
        sendToTrackedPlayers(trackedPlayers, packet);
        if (tracker instanceof EntityPlayer) {
            ((EntityPlayer) tracker).netServerHandler.sendPacket(packet);
        }
    }

    public void queueDestroyForTrackedPlayers(Set trackedPlayers, int trackerId) {
        Iterator iterator = trackedPlayers.iterator();
        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();
            entityplayer.removeQueue.add(Integer.valueOf(trackerId));
        }
    }

    public void removeTrackedPlayer(Set trackedPlayers, EntityPlayer entityplayer, int trackerId) {
        if (trackedPlayers.contains(entityplayer)) {
            entityplayer.removeQueue.add(Integer.valueOf(trackerId));
            trackedPlayers.remove(entityplayer);
        }
    }
}
