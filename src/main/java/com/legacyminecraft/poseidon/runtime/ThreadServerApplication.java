package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local server thread wrapper.
 */
public class ThreadServerApplication extends Thread {
    private final MinecraftServer server;

    public ThreadServerApplication(String name, MinecraftServer server) {
        super(name);
        this.server = server;
    }

    @Override
    public void run() {
        if (server != null) {
            server.run();
        }
    }
}
