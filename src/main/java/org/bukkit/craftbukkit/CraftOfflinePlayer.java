package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.compat.bukkit.OfflinePlayerAccessBehaviour;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

public class CraftOfflinePlayer implements OfflinePlayer {
    private static final OfflinePlayerAccessBehaviour OFFLINE_PLAYER_ACCESS_BEHAVIOUR =
            OfflinePlayerAccessBehaviour.getInstance();
    private final String name;
    private final CraftServer server;

    protected CraftOfflinePlayer(CraftServer server, String name) {
        this.server = server;
        this.name = name;
    }

    public boolean isOnline() {
        return false;
    }

    public String getName() {
        return name;
    }

    public Server getServer() {
        return server;
    }

    public boolean isOp() {
        return OFFLINE_PLAYER_ACCESS_BEHAVIOUR.isOperator(server, getName());
    }

    public void setOp(boolean value) {
        if (value == isOp()) return;
        OFFLINE_PLAYER_ACCESS_BEHAVIOUR.setOperator(server, getName(), value);
    }

    public boolean isBanned() {
        return OFFLINE_PLAYER_ACCESS_BEHAVIOUR.isBanned(server, name);
    }

    public void setBanned(boolean value) {
        OFFLINE_PLAYER_ACCESS_BEHAVIOUR.setBanned(server, name, value);
    }

    public boolean isWhitelisted() {
        return OFFLINE_PLAYER_ACCESS_BEHAVIOUR.isWhitelisted(server, name);
    }

    public void setWhitelisted(boolean value) {
        OFFLINE_PLAYER_ACCESS_BEHAVIOUR.setWhitelisted(server, name, value);
    }
}
