package com.legacyminecraft.poseidon.world.player;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet3Chat;
import net.minecraft.server.Packet4UpdateTime;
import net.minecraft.server.Packet70Bed;
import net.minecraft.server.PlayerFileData;
import net.minecraft.server.WorldServer;

import java.util.List;
import java.util.Set;

/**
 * Canonical player-session list operations for legacy server wrappers.
 */
public final class PlayerSessionSystem {
    private static final PlayerSessionSystem INSTANCE = new PlayerSessionSystem();

    private PlayerSessionSystem() {
    }

    public static PlayerSessionSystem getInstance() {
        return INSTANCE;
    }

    public void sendPacketToAll(List players, Packet packet) {
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) players.get(i);
            entityplayer.netServerHandler.sendPacket(packet);
        }
    }

    public void sendPacketToDimension(List players, int dimension, Packet packet) {
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) players.get(i);
            if (entityplayer.dimension == dimension) {
                entityplayer.netServerHandler.sendPacket(packet);
            }
        }
    }

    public String buildPlayerNameList(List players) {
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < players.size(); ++i) {
            if (i > 0) {
                names.append(", ");
            }
            names.append(((EntityPlayer) players.get(i)).name);
        }
        return names.toString();
    }

    public EntityPlayer findPlayer(List players, String username) {
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) players.get(i);
            if (entityplayer.name.equalsIgnoreCase(username)) {
                return entityplayer;
            }
        }
        return null;
    }

    public void sendChatToPlayer(List players, String username, String message) {
        EntityPlayer entityplayer = findPlayer(players, username);
        if (entityplayer != null) {
            entityplayer.netServerHandler.sendPacket(new Packet3Chat(message));
        }
    }

    public void sendPacketNearby(List players, EntityHuman ignoredPlayer, double x, double y, double z, double radius, int dimension, Packet packet) {
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) players.get(i);

            if (entityplayer != ignoredPlayer && entityplayer.dimension == dimension) {
                double dx = x - entityplayer.locX;
                double dy = y - entityplayer.locY;
                double dz = z - entityplayer.locZ;

                if (dx * dx + dy * dy + dz * dz < radius * radius) {
                    entityplayer.netServerHandler.sendPacket(packet);
                }
            }
        }
    }

    public void sendPacketToOperators(List players, Set operators, String message) {
        Packet3Chat packet = new Packet3Chat(message);
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) players.get(i);
            if (operators.contains(entityplayer.name.trim().toLowerCase())) {
                entityplayer.netServerHandler.sendPacket(packet);
            }
        }
    }

    public boolean sendPacketToPlayer(List players, String username, Packet packet) {
        EntityPlayer entityplayer = findPlayer(players, username);
        if (entityplayer == null) {
            return false;
        }
        entityplayer.netServerHandler.sendPacket(packet);
        return true;
    }

    public void savePlayers(PlayerFileData playerFileData, List players) {
        for (int i = 0; i < players.size(); ++i) {
            playerFileData.a((EntityHuman) players.get(i));
        }
    }

    public void sendWorldState(EntityPlayer entityplayer, WorldServer worldserver) {
        entityplayer.netServerHandler.sendPacket(new Packet4UpdateTime(worldserver.getTime()));
        if (worldserver.v()) {
            entityplayer.netServerHandler.sendPacket(new Packet70Bed(1));
        }
    }

    public void refreshClient(EntityPlayer entityplayer) {
        entityplayer.updateInventory(entityplayer.defaultContainer);
        entityplayer.C();
    }
}
