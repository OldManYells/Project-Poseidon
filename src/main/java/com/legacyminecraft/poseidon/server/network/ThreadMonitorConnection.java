package com.legacyminecraft.poseidon.server.network;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.server.network.*;

class ThreadMonitorConnection extends Thread {

    final NetworkManager a;

    ThreadMonitorConnection(NetworkManager networkmanager) {
        this.a = networkmanager;
    }

    public void run() {
        try {
            Thread.sleep(2000L);
            if (NetworkManager.a(this.a)) {
                NetworkManager.h(this.a).interrupt();
                this.a.a("disconnect.closed", new Object[0]);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
