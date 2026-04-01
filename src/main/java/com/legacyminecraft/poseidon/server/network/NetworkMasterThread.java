package com.legacyminecraft.poseidon.server.network;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

class NetworkMasterThread extends Thread {

    final NetworkManager a;

    NetworkMasterThread(NetworkManager networkmanager) {
        this.a = networkmanager;
    }

    public void run() {
        try {
            Thread.sleep(5000L);
            if (NetworkManager.g(this.a).isAlive()) {
                try {
                    NetworkManager.g(this.a).interrupt();
                } catch (Throwable throwable) {
                    ;
                }
            }

            if (NetworkManager.h(this.a).isAlive()) {
                try {
                    NetworkManager.h(this.a).interrupt();
                } catch (Throwable throwable1) {
                    ;
                }
            }
        } catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }
    }
}
