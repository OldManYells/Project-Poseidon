package com.legacyminecraft.poseidon.network;

import java.net.Socket;
import java.util.logging.Logger;

/**
 * Network-local login-handler scaffold.
 */
public class NetLoginHandler {
    public static final Logger a = Logger.getLogger("NetLoginHandler");

    public final NetworkManager networkManager = new NetworkManager();
    public Object loginProcessHandler;
    private Socket socket = new Socket();
    private Packet1Login deferredLoginPacket;
    private String username = "player";
    private String serverId = "";

    public void disconnect(String message) {
    }

    public String b() {
        return username;
    }

    public void a() {
    }

    public Socket getSocket() {
        return socket;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void setDeferredLoginPacket(Packet1Login deferredLoginPacket) {
        this.deferredLoginPacket = deferredLoginPacket;
    }

    public Packet1Login getDeferredLoginPacket() {
        return deferredLoginPacket;
    }

    public String getServerID() {
        return serverId;
    }

    public void setServerID(String serverId) {
        this.serverId = serverId;
    }
}

