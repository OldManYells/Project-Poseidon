package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.ServerRunInvocationSystem;

public final class ThreadServerApplication extends Thread {

    final MinecraftServer a;
    private final ServerRunInvocationSystem serverRunInvocationSystem = ServerRunInvocationSystem.getInstance();

    public ThreadServerApplication(String s, MinecraftServer minecraftserver) {
        super(s);
        this.a = minecraftserver;
    }

    public void run() {
        serverRunInvocationSystem.runServer(this.a);
    }
}
