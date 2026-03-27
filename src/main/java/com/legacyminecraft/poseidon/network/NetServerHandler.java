package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.api.network.ConnectionType;

/**
 * Network-local server-handler scaffold.
 */
public class NetServerHandler extends com.legacyminecraft.compat.bukkit.NetServerHandler {
    public final NetworkManager networkManager;
    public final EntityPlayer player;
    private boolean usingReleaseToBeta;
    private ConnectionType connectionType = ConnectionType.NORMAL;
    private int rawConnectionType = -1;
    private boolean receivedKeepAlive;

    public NetServerHandler() {
        this(new MinecraftServer(), new NetworkManager(), new EntityPlayer());
    }

    public NetServerHandler(MinecraftServer server, NetworkManager networkManager, EntityPlayer player) {
        this.networkManager = networkManager;
        this.player = player;
    }

    public void setUsingReleaseToBeta(boolean usingReleaseToBeta) {
        this.usingReleaseToBeta = usingReleaseToBeta;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public void setRawConnectionType(int rawConnectionType) {
        this.rawConnectionType = rawConnectionType;
    }

    public void setReceivedKeepAlive(boolean receivedKeepAlive) {
        this.receivedKeepAlive = receivedKeepAlive;
    }

    public void a() {
    }
}
