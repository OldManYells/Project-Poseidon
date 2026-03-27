package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.PoseidonPlugin;
import com.legacyminecraft.poseidon.auth.uuid.UUIDManager;
import com.legacyminecraft.compat.bukkit.Plugin;
import com.legacyminecraft.poseidon.uuid.ThreadUUIDFetcher;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.UUID;

public class LoginProcessHandler implements LoginProcessCallbacks, LoginPauseController {
    private final NetLoginHandler netLoginHandler;
    private final Packet1Login packet1Login;
    private final Server server;
    private final boolean onlineMode;
    private final Object legacyLoginProcessHandler;
    private final String msgKickAlreadyOnline;

    private final HashSet<ConnectionPause> connectionPauses = new HashSet<ConnectionPause>();

    private boolean loginCancelled = false;
    private boolean loginSuccessful = false;
    private long startTime;

    public LoginProcessHandler(NetLoginHandler netLoginHandler, Packet1Login packet1Login, Server server, boolean onlineMode, Object legacyLoginProcessHandler) {
        this.netLoginHandler = netLoginHandler;
        this.packet1Login = packet1Login;
        this.server = server;
        this.onlineMode = onlineMode;
        this.legacyLoginProcessHandler = legacyLoginProcessHandler;
        this.msgKickAlreadyOnline = PoseidonConfig.getInstance().getConfigString("message.kick.already-online");

        processAuthentication();

        long connectionStartTime = System.currentTimeMillis() / 1000L;
        runLoginTimer(connectionStartTime);
    }

    private void runLoginTimer(long connectionStartTime) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(PoseidonPlugin.getInstance(), () -> {
            int currentRunningTime = (int) (System.currentTimeMillis() / 1000L - connectionStartTime);
            if (!loginSuccessful && !loginCancelled) {
                System.out.println("[Poseidon] The login process for " + packet1Login.name + " is still running. It has been running for " + currentRunningTime + " seconds. The following plugins are still currently pausing the login process: " + getConnectionPauseNames(true));

                if (currentRunningTime >= 20) {
                    cancelLoginProcess("Login Process Handler Timeout");
                    System.out.println("[Poseidon] LoginProcessHandler for user " + packet1Login.name + " has failed to respond after 20 seconds. And future calls to this class will result in error");
                    System.out.println("[Poseidon] Plugin Pauses: " + getConnectionPauseNames(true));
                }

                if (currentRunningTime < 60) {
                    runLoginTimer(connectionStartTime);
                }
            }
        }, 20 * 5);
    }

    private void processAuthentication() {
        PlayerConnectionInitializationEvent event = new PlayerConnectionInitializationEvent(this.packet1Login.name, this.netLoginHandler.getSocket().getInetAddress(), this, legacyLoginProcessHandler);
        this.server.getPluginManager().callEvent(event);
        if (loginCancelled) {
            return;
        }

        if (onlineMode) {
            verifyMojangSession();
        } else {
            getUserUUID();
        }
    }

    private void getUserUUID() {
        long unixTime = (System.currentTimeMillis() / 1000L);
        UUID uuid = UUIDManager.getInstance().getUUIDFromUsername(packet1Login.name, true, unixTime);
        if (uuid == null) {
            boolean useGetMethod = PoseidonConfig.getInstance().getString("settings.uuid-fetcher.method.value", "POST").equalsIgnoreCase("GET");
            (new ThreadUUIDFetcher(packet1Login, this, useGetMethod)).start();
        } else {
            System.out.println("[Poseidon] Fetched UUID from Cache for " + packet1Login.name + " - " + uuid);
            connectPlayer(uuid);
        }
    }

    @Override
    public synchronized void userUUIDReceived(UUID uuid, boolean onlineMode) {
        if (!onlineMode) {
            if (Boolean.valueOf(String.valueOf(PoseidonConfig.getInstance().getConfigOption("settings.check-username-validity.enabled", true))) && !isUsernameValid()) {
                return;
            }
        }

        if (onlineMode) {
            long unixTime = (System.currentTimeMillis() / 1000L) + 1382400;
            UUIDManager.getInstance().receivedUUID(packet1Login.name, uuid, unixTime, true);
            connectPlayer(uuid);
        } else {
            connectPlayerCracked(uuid);
        }
    }

    private void connectPlayerCracked(UUID uuid) {
        String username = this.packet1Login.name;

        if (Boolean.valueOf(String.valueOf(PoseidonConfig.getInstance().getConfigOption("settings.check-username-validity.enabled", true))) && !isUsernameValid()) {
            return;
        }

        boolean prefixDot = PoseidonConfig.getInstance().getConfigBoolean("settings.cracked-username-prefix.enabled", false);

        if (prefixDot) {
            if (!username.startsWith(".")) {
                System.out.println("[Poseidon] Adding . prefix to cracked user " + username + "'s username");
                username = "." + username;
            }

            this.packet1Login.name = username;
            netLoginHandler.updateUsername(username);
        }

        UUID offlineUUID = UUIDManager.generateOfflineUUID(username);
        long expiresOn = (System.currentTimeMillis() / 1000L) + 1382400;
        UUIDManager.getInstance().receivedUUID(username, offlineUUID, expiresOn, false);

        connectPlayer(offlineUUID);
    }

    public boolean isUsernameValid() {
        String username = this.packet1Login.name;

        int minimumLength = Integer.valueOf(String.valueOf(PoseidonConfig.getInstance().getConfigOption("settings.check-username-validity.min-length", 3)));
        int maximumLength = Integer.valueOf(String.valueOf(PoseidonConfig.getInstance().getConfigOption("settings.check-username-validity.max-length", 16)));

        if (username.length() < minimumLength) {
            cancelLoginProcess("Sorry, your username is too short. The minimum length is: " + minimumLength);
            return false;
        }

        boolean prefixDot = PoseidonConfig.getInstance().getConfigBoolean("settings.cracked-username-prefix.enabled", false);
        boolean dotAdded = false;
        if (prefixDot && username.startsWith(".")) {
            username = username.substring(1);
            dotAdded = true;
        }

        if (username.length() > maximumLength) {
            cancelLoginProcess("Sorry, your username is too long. The maximum length is: " + (dotAdded ? (maximumLength - 1) : maximumLength));
            return false;
        }

        if (username.isEmpty()) {
            cancelLoginProcess("Sorry, you don't have a username, messing with MC?????");
            return false;
        }

        String regex = String.valueOf(PoseidonConfig.getInstance().getConfigOption("settings.check-username-validity.regex", "[a-zA-Z0-9_?]*"));

        if (!username.matches(regex)) {
            cancelLoginProcess("Sorry, your username is invalid, allowed characters: " + regex);
            return false;
        }

        return true;
    }

    private void verifyMojangSession() {
        if (!loginSuccessful && !loginCancelled) {
            (new ThreadLoginVerifier(this, netLoginHandler, this.packet1Login)).start();
        }
    }

    @Override
    public synchronized void userMojangSessionVerified() {
        if (!loginSuccessful && !loginCancelled) {
            getUserUUID();
        }
    }

    private void connectPlayer(UUID uuid) {
        String username = packet1Login.name;

        for (Player p : server.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(username) || p.getUniqueId().equals(uuid)) {
                cancelLoginProcess(this.msgKickAlreadyOnline);
                System.out.println("[Poseidon] User " + username + " has been blocked from connecting as they share a username or UUID with a user who is already online called " + p.getName() +
                        "\nMost likely the user has changed their UUID or the server is running in offline mode and someone has attempted to connect with their name");
            }
        }

        if (!loginSuccessful && !loginCancelled) {
            if (this.netLoginHandler.getSocket() == null) {
                return;
            }

            PlayerPreLoginEvent event = new PlayerPreLoginEvent(this.packet1Login.name, ((InetSocketAddress) netLoginHandler.networkManager.getSocketAddress()).getAddress(), this, legacyLoginProcessHandler);
            this.server.getPluginManager().callEvent(event);
            if (event.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
                cancelLoginProcess(event.getKickMessage());
                return;
            }

            if (hasActiveConnectionPause()) {
                startTime = System.currentTimeMillis() / 1000L;
            } else {
                loginSuccessful = true;
                netLoginHandler.setDeferredLoginPacket(packet1Login);
            }
        }
    }

    @Override
    public void cancelLoginProcess(String message) {
        if (!loginCancelled && !loginSuccessful) {
            loginCancelled = true;
            netLoginHandler.disconnect(message);
        }
    }

    @Override
    public ConnectionPause createConnectionPause(Plugin plugin, String connectionPauseName) {
        ConnectionPause connectionPause = new ConnectionPause(plugin.getDescription().getName(), connectionPauseName);
        connectionPauses.add(connectionPause);
        return connectionPause;
    }

    public ConnectionPause addConnectionInterrupt(Plugin plugin, String connectionPauseName) {
        return createConnectionPause(plugin, connectionPauseName);
    }

    @Override
    public void clearConnectionPause(ConnectionPause connectionPause) {
        if (!connectionPauses.contains(connectionPause)) {
            System.out.println("[Poseidon] A plugin has tried to remove a connection pause from the player " + packet1Login.name + " called " + connectionPause.getConnectionPauseName() +
                    " from the plugin " + connectionPause.getPluginName() + ". Please contact the plugin author and get them to check their logic as this is a duplicate remove, or a pause for another player.");
            return;
        }

        connectionPause.setActive(false);
        if (!hasActiveConnectionPause()) {
            long endTime = System.currentTimeMillis() / 1000L;
            int timeTaken = (int) (endTime - startTime);

            if (loginCancelled) {
                System.out.println("[Poseidon] Player " + packet1Login.name + " was not allowed to join after being on hold for " + timeTaken + " seconds by the following plugins: " + getConnectionPauseNames(false));
                return;
            }

            this.loginSuccessful = true;
            System.out.println("[Poseidon] Player " + packet1Login.name + " has been allowed to join after being on hold for " + timeTaken + " seconds by the following plugins: " + getConnectionPauseNames(false));
            netLoginHandler.setDeferredLoginPacket(packet1Login);
        }
    }

    public void removeConnectionInterrupt(ConnectionPause connectionPause) {
        clearConnectionPause(connectionPause);
    }

    public ConnectionPause[] getActiveConnectionPauses() {
        HashSet<ConnectionPause> activePauses = new HashSet<ConnectionPause>();
        for (ConnectionPause connectionPause : connectionPauses) {
            if (connectionPause.isActive()) {
                activePauses.add(connectionPause);
            }
        }
        return activePauses.toArray(new ConnectionPause[activePauses.size()]);
    }

    public String getConnectionPauseNames(boolean activeOnly) {
        StringBuilder pauseNames = new StringBuilder();
        for (ConnectionPause connectionPause : connectionPauses) {
            String pluginName = connectionPause.getPluginName();
            String pauseName = connectionPause.getConnectionPauseName();
            boolean isActive = connectionPause.isActive();
            int time = connectionPause.getRunningTime();
            if (!activeOnly || connectionPause.isActive()) {
                pauseNames.append(pluginName).append(":").append(pauseName).append(":").append(isActive ? "Running" : "Complete").append(":").append(time).append("-Seconds, ");
            }
        }
        return pauseNames.toString();
    }

    @Override
    public boolean hasActiveConnectionPause() {
        return getActiveConnectionPauses().length > 0;
    }
}
