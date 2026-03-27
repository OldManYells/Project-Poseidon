package com.legacyminecraft.poseidon.network;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.List;

/**
 * Canonical bootstrap helpers for legacy network listen wrappers.
 */
public final class NetworkListenBootstrap {
    private static final NetworkListenBootstrap INSTANCE = new NetworkListenBootstrap();
    private final NetworkServerSocketPreferencePolicy networkServerSocketPreferencePolicy =
            NetworkServerSocketPreferencePolicy.getInstance();

    private NetworkListenBootstrap() {
    }

    public static NetworkListenBootstrap getInstance() {
        return INSTANCE;
    }

    public ServerSocket openServerSocket(int port, InetAddress bindAddress) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port, 0, bindAddress);
        serverSocket.setPerformancePreferences(
                networkServerSocketPreferencePolicy.connectionTimeWeight(),
                networkServerSocketPreferencePolicy.latencyWeight(),
                networkServerSocketPreferencePolicy.bandwidthWeight()
        );
        return serverSocket;
    }

    public Thread startAcceptThread(Thread acceptThread) {
        acceptThread.start();
        return acceptThread;
    }

    public void addPendingLogin(List pendingLoginHandlers, Object netloginhandler) {
        if (netloginhandler == null) {
            throw new IllegalArgumentException("Got null pendingconnection!");
        }

        pendingLoginHandlers.add(netloginhandler);
    }

    public void addServerHandler(List serverHandlers, Object netserverhandler) {
        serverHandlers.add(netserverhandler);
    }
}
