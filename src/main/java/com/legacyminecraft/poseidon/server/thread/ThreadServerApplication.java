package com.legacyminecraft.poseidon.server.thread;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

public final class ThreadServerApplication extends Thread {

    final MinecraftServer a;

    public ThreadServerApplication(String s, MinecraftServer minecraftserver) {
        super(s);
        this.a = minecraftserver;
    }

    public void run() {
        this.a.run();
    }
}
