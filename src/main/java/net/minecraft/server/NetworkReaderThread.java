package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.network.NetworkTransportConfigPolicy;
import com.legacyminecraft.poseidon.network.NetworkReaderLoopSystem;

class NetworkReaderThread extends Thread {
    private boolean fast; // Poseidon
    final NetworkManager a;
    private final NetworkTransportConfigPolicy networkTransportConfigPolicy = NetworkTransportConfigPolicy.getInstance();
    private final NetworkReaderLoopSystem networkReaderLoopSystem = NetworkReaderLoopSystem.getInstance();
    private final NetworkReaderLoopSystem.ReaderLoopOperations readerLoopOperations =
            new NetworkReaderLoopSystem.ReaderLoopOperations() {
                @Override
                public void incrementReaderThreadCount() {
                    synchronized (NetworkManager.a) {
                        ++NetworkManager.b;
                    }
                }

                @Override
                public void decrementReaderThreadCount() {
                    synchronized (NetworkManager.a) {
                        --NetworkManager.b;
                    }
                }

                @Override
                public boolean isConnectionOpen() {
                    return NetworkManager.a(NetworkReaderThread.this.a);
                }

                @Override
                public boolean isShuttingDown() {
                    return NetworkManager.b(NetworkReaderThread.this.a);
                }

                @Override
                public boolean readNextPacket() {
                    return NetworkManager.c(NetworkReaderThread.this.a);
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
                public void handleException(Exception exception) {
                    NetworkManager.a(NetworkReaderThread.this.a, exception);
                }
            };

    NetworkReaderThread(NetworkManager networkmanager, String s) {
        super(s);
        this.a = networkmanager;
        this.fast = PoseidonConfig.getInstance().getBoolean(
                networkTransportConfigPolicy.fasterPacketsEnabledKey(),
                networkTransportConfigPolicy.fasterPacketsEnabledDefault()
        ); // Poseidon
    }

    public void run() {
        networkReaderLoopSystem.runLoop(this.fast, this.readerLoopOperations);
    }
}
