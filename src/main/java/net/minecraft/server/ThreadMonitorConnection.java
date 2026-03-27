package net.minecraft.server;

import com.legacyminecraft.poseidon.network.ConnectionMonitorSystem;
import com.legacyminecraft.poseidon.network.NetworkDisconnectArgumentPolicy;
import com.legacyminecraft.poseidon.network.NetworkDisconnectKeyPolicy;

class ThreadMonitorConnection extends Thread {

    final NetworkManager a;
    private final ConnectionMonitorSystem connectionMonitorSystem = ConnectionMonitorSystem.getInstance();
    private final NetworkDisconnectKeyPolicy networkDisconnectKeyPolicy = NetworkDisconnectKeyPolicy.getInstance();
    private final NetworkDisconnectArgumentPolicy networkDisconnectArgumentPolicy =
            NetworkDisconnectArgumentPolicy.getInstance();
    private final ConnectionMonitorSystem.ConnectionState connectionState =
            new ConnectionMonitorSystem.ConnectionState() {
                @Override
                public boolean isConnectionOpen() {
                    return NetworkManager.a(ThreadMonitorConnection.this.a);
                }
            };
    private final Runnable interruptWriter = new Runnable() {
        @Override
        public void run() {
            NetworkManager.h(ThreadMonitorConnection.this.a).interrupt();
        }
    };
    private final Runnable disconnectAction = new Runnable() {
        @Override
        public void run() {
            ThreadMonitorConnection.this.a.a(
                    networkDisconnectKeyPolicy.closed(),
                    networkDisconnectArgumentPolicy.emptyArgs()
            );
        }
    };

    ThreadMonitorConnection(NetworkManager networkmanager) {
        this.a = networkmanager;
    }

    public void run() {
        connectionMonitorSystem.monitorAndDisconnectIfOpen(
                this.connectionState,
                this.interruptWriter,
                this.disconnectAction
        );
    }
}
