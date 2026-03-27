package org.bukkit.craftbukkit;

import com.legacyminecraft.compat.bukkit.OfflinePlayerIdentityBehaviour;
import com.legacyminecraft.compat.bukkit.OfflinePlayerModerationBehaviour;
import com.legacyminecraft.compat.bukkit.OfflinePlayerSnapshotCaptureBehaviour;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

public class CraftOfflinePlayer implements OfflinePlayer {
    private static final OfflinePlayerIdentityBehaviour OFFLINE_PLAYER_IDENTITY_BEHAVIOUR =
            OfflinePlayerIdentityBehaviour.getInstance();
    private static final OfflinePlayerModerationBehaviour OFFLINE_PLAYER_MODERATION_BEHAVIOUR =
            OfflinePlayerModerationBehaviour.getInstance();
    private static final OfflinePlayerSnapshotCaptureBehaviour OFFLINE_PLAYER_SNAPSHOT_CAPTURE_BEHAVIOUR =
            OfflinePlayerSnapshotCaptureBehaviour.getInstance();
    private final String name;
    private final CraftServer server;

    protected CraftOfflinePlayer(CraftServer server, String name) {
        OfflinePlayerSnapshotCaptureBehaviour.Snapshot snapshot =
                OFFLINE_PLAYER_SNAPSHOT_CAPTURE_BEHAVIOUR.capture(server, name);
        this.server = snapshot.getServer();
        this.name = snapshot.getName();
    }

    public boolean isOnline() {
        return OFFLINE_PLAYER_IDENTITY_BEHAVIOUR.isOnline();
    }

    public String getName() {
        return OFFLINE_PLAYER_IDENTITY_BEHAVIOUR.getName(name);
    }

    public Server getServer() {
        return OFFLINE_PLAYER_IDENTITY_BEHAVIOUR.getServer(server);
    }

    public boolean isOp() {
        return OFFLINE_PLAYER_MODERATION_BEHAVIOUR.isOp(server, name, getName());
    }

    public void setOp(boolean value) {
        OFFLINE_PLAYER_MODERATION_BEHAVIOUR.setOp(server, name, getName(), value);
    }

    public boolean isBanned() {
        return OFFLINE_PLAYER_MODERATION_BEHAVIOUR.isBanned(server, name, getName());
    }

    public void setBanned(boolean value) {
        OFFLINE_PLAYER_MODERATION_BEHAVIOUR.setBanned(server, name, getName(), value);
    }

    public boolean isWhitelisted() {
        return OFFLINE_PLAYER_MODERATION_BEHAVIOUR.isWhitelisted(server, name, getName());
    }

    public void setWhitelisted(boolean value) {
        OFFLINE_PLAYER_MODERATION_BEHAVIOUR.setWhitelisted(server, name, getName(), value);
    }
}
