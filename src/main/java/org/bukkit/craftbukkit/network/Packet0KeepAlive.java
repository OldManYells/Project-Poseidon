package org.bukkit.craftbukkit.network;

import net.minecraft.server.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Packet0KeepAlive extends Packet {

    public Packet0KeepAlive() {
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) {
    }

    public void a(DataOutputStream dataoutputstream) {
    }

    public int a() {
        return 0;
    }
}
