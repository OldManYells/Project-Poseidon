package com.legacyminecraft.poseidon.auth.login;

public class ConnectionPause {
    private final String pluginName;
    private final String connectionPauseName;
    private final long creationTime;

    private long completionTime;
    private boolean active;

    public ConnectionPause(String pluginName, String connectionPauseName) {
        this.pluginName = pluginName;
        this.connectionPauseName = connectionPauseName;
        this.creationTime = System.currentTimeMillis();
        this.active = true;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getConnectionPauseName() {
        return connectionPauseName;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (!active) {
            this.completionTime = System.currentTimeMillis();
        }
        this.active = active;
    }

    public int getRunningTime() {
        long running = active ? (System.currentTimeMillis() - creationTime) : (completionTime - creationTime);
        return (int) (running / 1000L);
    }

    public long getCompletionTime() {
        return completionTime;
    }
}
