package org.bukkit.craftbukkit.network;

import net.minecraft.server.EnumArt;
import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;
import org.bukkit.craftbukkit.entity.EntityPainting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet25EntityPainting extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f;

    public Packet25EntityPainting() {}

    public Packet25EntityPainting(EntityPainting entitypainting) {
        this.a = entitypainting.id;
        this.b = entitypainting.hangingX;
        this.c = entitypainting.hangingY;
        this.d = entitypainting.hangingZ;
        this.e = entitypainting.hangingDirection;
        this.f = entitypainting.art.A;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readInt();
        this.f = a(datainputstream, EnumArt.z);
        this.b = datainputstream.readInt();
        this.c = datainputstream.readInt();
        this.d = datainputstream.readInt();
        this.e = datainputstream.readInt();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.a);
        a(this.f, dataoutputstream);
        dataoutputstream.writeInt(this.b);
        dataoutputstream.writeInt(this.c);
        dataoutputstream.writeInt(this.d);
        dataoutputstream.writeInt(this.e);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return 24;
    }
}
