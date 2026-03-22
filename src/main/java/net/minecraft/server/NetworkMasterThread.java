package net.minecraft.server;

import com.legacyminecraft.poseidon.network.NetworkMasterThreadSystem;

class NetworkMasterThread extends Thread {

    final NetworkManager a;
    private final NetworkMasterThreadSystem networkMasterThreadSystem = NetworkMasterThreadSystem.getInstance();

    NetworkMasterThread(NetworkManager networkmanager) {
        this.a = networkmanager;
    }

    public void run() {
        networkMasterThreadSystem.stopLingeringNetworkThreads(
                NetworkManager.g(this.a),
                NetworkManager.h(this.a)
        );
    }
}
