package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.SleepForeverSystem;

public class ThreadSleepForever extends Thread {

    final MinecraftServer a;
    private final SleepForeverSystem sleepForeverSystem = SleepForeverSystem.getInstance();

    public ThreadSleepForever(MinecraftServer minecraftserver) {
        this.a = minecraftserver;
        this.setDaemon(true);
        this.start();
    }

    public void run() {
        sleepForeverSystem.sleepForever();
    }
}
