package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Canonical compat server-configuration scaffold.
 */
public class ServerConfigurationManager {
    public final Set<String> banByName = new HashSet<String>();
    public final Set<String> banByIP = new HashSet<String>();
    public final List<EntityPlayer> players = new ArrayList<EntityPlayer>();
    public final PlayerFileData playerFileData = new PlayerFileData();
    public boolean o;

    public void a(String playerName) {
        banByName.add(playerName);
    }

    public void b(String playerName) {
        banByName.remove(playerName);
    }

    public Set<String> e() {
        return new HashSet<String>();
    }

    public void c(String address) {
        banByIP.add(address);
    }

    public void d(String address) {
        banByIP.remove(address);
    }

    public void k(String playerName) {
    }

    public void l(String playerName) {
    }

    public boolean isOp(String playerName) {
        return false;
    }

    public void e(String playerName) {
    }

    public void f(String playerName) {
    }

    public void f() {
    }

    public void sendAll(Packet packet) {
    }

    public String disconnect(EntityPlayer player) {
        players.remove(player);
        return player == null ? null : player.name + " left the game";
    }

    public EntityPlayer moveToWorld(EntityPlayer player, int dimension) {
        player.dimension = dimension;
        return player;
    }

    public void d(EntityPlayer player) {
    }

    public EntityPlayer a(Object netLoginHandler, String username) {
        EntityPlayer player = new EntityPlayer();
        player.name = username;
        return player;
    }

    public void b(EntityPlayer player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void a(EntityPlayer player, WorldServer worldServer) {
        player.world = worldServer;
    }

    public void c(EntityPlayer player) {
    }

    public static class PlayerFileData {
        public void a(EntityPlayer player) {
        }

        public void b(EntityPlayer player) {
        }
    }
}
