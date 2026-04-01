
package org.bukkit.craftbukkit.util;

import com.legacy.minecraft.poseidon.MinecraftServer;

public class ServerShutdownThread extends Thread {
    private final MinecraftServer server;

    public ServerShutdownThread(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        server.stop();
    }
}
