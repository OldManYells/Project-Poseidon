package net.minecraft.server;

import com.legacyminecraft.poseidon.network.NetworkListenBootstrap;
import com.legacyminecraft.poseidon.network.NetworkConnectionPumpSystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkListenThread extends com.legacyminecraft.poseidon.runtime.NetworkListenThread {

    public static Logger a = Logger.getLogger("Minecraft");
    private ServerSocket d;
    private Thread e;
    public volatile boolean b = false;
    private int f = 0;
    private ArrayList g = new ArrayList();
    private ArrayList h = new ArrayList();
    public MinecraftServer c;
    private final NetworkListenBootstrap networkListenBootstrap = NetworkListenBootstrap.getInstance();
    private final NetworkConnectionPumpSystem networkConnectionPumpSystem = NetworkConnectionPumpSystem.getInstance();

    public NetworkListenThread(MinecraftServer minecraftserver, InetAddress inetaddress, int i) throws IOException {
        this.c = minecraftserver;
        this.d = networkListenBootstrap.openServerSocket(i, inetaddress);
        this.b = true;
        this.e = networkListenBootstrap.startAcceptThread(new NetworkAcceptThread(this, "Listen thread", minecraftserver));
    }

    public void a(NetServerHandler netserverhandler) {
        networkListenBootstrap.addServerHandler(this.h, netserverhandler);
    }

    private void a(NetLoginHandler netloginhandler) {
        networkListenBootstrap.addPendingLogin(this.g, netloginhandler);
    }

    public void a() {
        networkConnectionPumpSystem.pumpConnections(this.g, this.h, a);
    }

    static ServerSocket a(NetworkListenThread networklistenthread) {
        return networklistenthread.d;
    }

    static int b(NetworkListenThread networklistenthread) {
        return networklistenthread.f++;
    }

    static void a(NetworkListenThread networklistenthread, NetLoginHandler netloginhandler) {
        networklistenthread.a(netloginhandler);
    }
}
