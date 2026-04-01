package com.legacyminecraft.poseidon.server.thread;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

import java.io.IOException;

public class ThreadCommandReader extends Thread {

    final MinecraftServer server;

    public ThreadCommandReader(MinecraftServer minecraftserver) {
        this.server = minecraftserver;
    }

    public void run() {
        jline.ConsoleReader bufferedreader = this.server.reader; // CraftBukkit
        String s = null;

        try {
            // CraftBukkit start - JLine disabling compatibility
            while (!this.server.isStopped && MinecraftServer.isRunning(this.server)) {
                if (org.bukkit.craftbukkit.Main.useJline) {
                    s = bufferedreader.readLine(">", null);
                } else {
                    s = bufferedreader.readLine();
                }
                if (s != null) {
                    this.server.issueCommand(s, this.server);
                }
                // CraftBukkit end
            }
        } catch (IOException ioexception) {
            // CraftBukkit
            java.util.logging.Logger.getLogger(ThreadCommandReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ioexception);
        }
    }
}
