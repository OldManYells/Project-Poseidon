package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.network.NetworkTransportConfigPolicy;
import com.legacyminecraft.poseidon.network.NetworkWriterLoopSystem;
import java.io.IOException;

class NetworkWriterThread extends Thread {
    private boolean fast; // Poseidon
    final NetworkManager a;
    private final NetworkTransportConfigPolicy networkTransportConfigPolicy = NetworkTransportConfigPolicy.getInstance();
    private final NetworkWriterLoopSystem networkWriterLoopSystem = NetworkWriterLoopSystem.getInstance();
    private final NetworkWriterLoopSystem.WriterLoopOperations writerLoopOperations =
            new NetworkWriterLoopSystem.WriterLoopOperations() {
                @Override
                public void incrementWriterThreadCount() {
                    synchronized (NetworkManager.a) {
                        ++NetworkManager.c;
                    }
                }

                @Override
                public void decrementWriterThreadCount() {
                    synchronized (NetworkManager.a) {
                        --NetworkManager.c;
                    }
                }

                @Override
                public boolean isConnectionOpen() {
                    return NetworkManager.a(NetworkWriterThread.this.a);
                }

                @Override
                public boolean writeNextPacket() {
                    return NetworkManager.d(NetworkWriterThread.this.a);
                }

                @Override
                public void sleepQuietly(long millis) {
                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException interruptedexception) {
                        ;
                    }
                }

                @Override
                public void flushOutput() {
                    if (NetworkManager.e(NetworkWriterThread.this.a) != null) {
                        try {
                            NetworkManager.e(NetworkWriterThread.this.a).flush();
                        } catch (IOException ioexception) {
                            throw new RuntimeException(ioexception);
                        }
                    }
                }

                @Override
                public boolean isShuttingDown() {
                    return NetworkManager.f(NetworkWriterThread.this.a);
                }

                @Override
                public void handleException(Exception exception) {
                    NetworkManager.a(NetworkWriterThread.this.a, exception);
                }
            };

    NetworkWriterThread(NetworkManager networkmanager, String s) {
        super(s);
        this.a = networkmanager;
        this.fast = PoseidonConfig.getInstance().getBoolean(
                networkTransportConfigPolicy.fasterPacketsEnabledKey(),
                networkTransportConfigPolicy.fasterPacketsEnabledDefault()
        ); // Poseidon
    }

    public void run() {
        networkWriterLoopSystem.runLoop(this.fast, this.writerLoopOperations);
    }
}
