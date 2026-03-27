package com.legacyminecraft.poseidon.runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Runtime-local server player-list and permission state.
 */
public class ServerConfigurationManager {
    public final List<EntityPlayer> players = new ArrayList<EntityPlayer>();
    private final Set<String> operators = new HashSet<String>();
    private final Set<String> whitelist = new HashSet<String>();
    private final Set<String> bannedByName = new HashSet<String>();
    private final Set<String> bannedByIp = new HashSet<String>();

    public String c() {
        return Integer.toString(players.size());
    }

    public void savePlayers() {
    }

    public void b() {
    }

    public Set<String> e() {
        return new HashSet<String>(whitelist);
    }

    public void k(String playerName) {
        whitelist.add(playerName);
    }

    public void l(String playerName) {
        whitelist.remove(playerName);
    }

    public void f() {
    }

    public boolean isOp(String playerName) {
        return operators.contains(playerName);
    }

    public void e(String playerName) {
        operators.add(playerName);
    }

    public void f(String playerName) {
        operators.remove(playerName);
    }

    public void a(String playerName) {
        bannedByName.add(playerName);
    }

    public void b(String playerName) {
        bannedByName.remove(playerName);
    }

    public void c(String address) {
        bannedByIp.add(address);
    }

    public void d(String address) {
        bannedByIp.remove(address);
    }

    public void sendPacketNearby(double x, double y, double z, double radius, int dimension, Object packet) {
    }

    public void sendAll(Packet3Chat packet) {
    }

    public boolean a(String playerName, Packet packet) {
        return i(playerName) != null;
    }

    public void a(String playerName, String message) {
    }

    public EntityPlayer i(String playerName) {
        for (EntityPlayer player : players) {
            if (player != null && playerName.equalsIgnoreCase(player.name)) {
                return player;
            }
        }
        return null;
    }

    public EntityPlayer a(Object loginHandler, String username) {
        EntityPlayer player = new EntityPlayer();
        player.name = username;
        return player;
    }

    public void b(EntityPlayer player) {
        if (player != null && !players.contains(player)) {
            players.add(player);
        }
    }

    public void c(EntityPlayer player) {
    }

    public void d(EntityPlayer player) {
    }

    public String disconnect(EntityPlayer player) {
        players.remove(player);
        return player == null ? null : player.name + " left the game";
    }

    public EntityPlayer moveToWorld(EntityPlayer player, int dimension) {
        if (player != null) {
            player.dimension = dimension;
        }
        return player;
    }

    public void setPlayerFileData(WorldServer[] worlds) {
    }

    public void setPlayerFileData(com.legacyminecraft.poseidon.world.WorldServer[] worlds) {
    }

    public com.legacyminecraft.poseidon.world.EntityTracker getTracker(int dimension) {
        return new com.legacyminecraft.poseidon.world.EntityTracker();
    }
}
