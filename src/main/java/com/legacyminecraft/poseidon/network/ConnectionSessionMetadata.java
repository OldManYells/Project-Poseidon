package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.api.network.ConnectionType;

/**
 * Canonical per-connection metadata shared across login and play handlers.
 */
public final class ConnectionSessionMetadata {
    private boolean usingReleaseToBeta;
    private ConnectionType connectionType = ConnectionType.NORMAL;
    private int rawConnectionType;
    private boolean receivedKeepAlive;

    public boolean isUsingReleaseToBeta() {
        return usingReleaseToBeta;
    }

    public void setUsingReleaseToBeta(boolean usingReleaseToBeta) {
        this.usingReleaseToBeta = usingReleaseToBeta;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public int getRawConnectionType() {
        return rawConnectionType;
    }

    public void setRawConnectionType(int rawConnectionType) {
        this.rawConnectionType = rawConnectionType;
    }

    public boolean isReceivedKeepAlive() {
        return receivedKeepAlive;
    }

    public void setReceivedKeepAlive(boolean receivedKeepAlive) {
        this.receivedKeepAlive = receivedKeepAlive;
    }
}
