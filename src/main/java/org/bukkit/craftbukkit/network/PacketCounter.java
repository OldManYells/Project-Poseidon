package org.bukkit.craftbukkit.network;

import net.minecraft.server.EmptyClass1;

public class PacketCounter {

    private int a;
    private long b;

    private PacketCounter() {}

    public void a(int i) {
        ++this.a;
        this.b += (long) i;
    }

    public PacketCounter(EmptyClass1 emptyclass1) {
        this();
    }
}
