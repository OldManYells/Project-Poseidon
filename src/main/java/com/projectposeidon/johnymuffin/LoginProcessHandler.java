package com.projectposeidon.johnymuffin;

import com.legacyminecraft.poseidon.auth.login.LoginPauseController;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.Packet1Login;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @deprecated Use canonical authentication classes under com.legacyminecraft.poseidon.auth.
 */
@Deprecated
public class LoginProcessHandler implements com.legacyminecraft.poseidon.auth.login.LoginProcessCallbacks, LoginPauseController {
    private final com.legacyminecraft.poseidon.auth.login.LoginProcessHandler delegate;
    private com.projectposeidon.johnymuffin.ConnectionPause legacyConnectionPause;

    public LoginProcessHandler(NetLoginHandler netLoginHandler, Packet1Login packet1Login, CraftServer server, boolean onlineMode) {
        this.delegate = new com.legacyminecraft.poseidon.auth.login.LoginProcessHandler(netLoginHandler, packet1Login, server, onlineMode, this);
    }

    com.legacyminecraft.poseidon.auth.login.LoginProcessHandler getDelegate() {
        return delegate;
    }

    @Override
    public synchronized void userUUIDReceived(UUID uuid, boolean onlineMode) {
        delegate.userUUIDReceived(uuid, onlineMode);
    }

    @Override
    public synchronized void userMojangSessionVerified() {
        delegate.userMojangSessionVerified();
    }

    @Override
    public void cancelLoginProcess(String message) {
        delegate.cancelLoginProcess(message);
    }

    public com.projectposeidon.johnymuffin.ConnectionPause addConnectionInterrupt(Plugin plugin, String connectionPauseName) {
        com.legacyminecraft.poseidon.auth.login.ConnectionPause pause = createConnectionPause(plugin, connectionPauseName);
        return com.projectposeidon.johnymuffin.ConnectionPause.fromCanonical(pause, this, this);
    }

    @Deprecated
    public void removeConnectionPause(com.projectposeidon.johnymuffin.ConnectionPause connectionPause) {
        removeConnectionInterrupt(connectionPause);
    }

    public void removeConnectionInterrupt(com.projectposeidon.johnymuffin.ConnectionPause connectionPause) {
        if (connectionPause == null) {
            return;
        }
        clearConnectionPause(connectionPause.getCanonicalConnectionPause());
    }

    public com.projectposeidon.johnymuffin.ConnectionPause[] getActiveConnectionPauses() {
        com.legacyminecraft.poseidon.auth.login.ConnectionPause[] pauses = delegate.getActiveConnectionPauses();
        List<com.projectposeidon.johnymuffin.ConnectionPause> wrappers = new ArrayList<com.projectposeidon.johnymuffin.ConnectionPause>(pauses.length);
        for (com.legacyminecraft.poseidon.auth.login.ConnectionPause pause : pauses) {
            wrappers.add(com.projectposeidon.johnymuffin.ConnectionPause.fromCanonical(pause, this, this));
        }
        return wrappers.toArray(new com.projectposeidon.johnymuffin.ConnectionPause[wrappers.size()]);
    }

    public String getConnectionPauseNames(boolean activeOnly) {
        return delegate.getConnectionPauseNames(activeOnly);
    }

    @Deprecated
    public void addConnectionPause(Plugin plugin) throws Exception {
        System.out.println("[Poseidon] " + plugin.getDescription().getName() + " is using the deprecated connection pause system which will be removed in the future. Contact the plugin author to get an updated version.");
        legacyConnectionPause = addConnectionInterrupt(plugin, "Legacy-Connection-Pause");
    }

    @Deprecated
    public void removeConnectionPause(Plugin plugin) {
        if (legacyConnectionPause != null) {
            removeConnectionInterrupt(legacyConnectionPause);
            return;
        }
        System.out.println("[Poseidon] " + plugin.getDescription().getName() + " Attempted to remove a legacy (deprecated) connection pause that was never added. Please contact the plugin author and get them to check their logic and update to the new connection pause system.");
    }

    public boolean isPlayerConnectionPaused() {
        return hasActiveConnectionPause();
    }

    @Override
    public com.legacyminecraft.poseidon.auth.login.ConnectionPause createConnectionPause(Plugin plugin, String connectionPauseName) {
        return delegate.createConnectionPause(plugin, connectionPauseName);
    }

    @Override
    public void clearConnectionPause(com.legacyminecraft.poseidon.auth.login.ConnectionPause connectionPause) {
        delegate.clearConnectionPause(connectionPause);
    }

    @Override
    public boolean hasActiveConnectionPause() {
        return delegate.hasActiveConnectionPause();
    }
}
