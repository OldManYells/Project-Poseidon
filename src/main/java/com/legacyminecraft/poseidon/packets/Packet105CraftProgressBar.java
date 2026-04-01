package com.legacyminecraft.poseidon.packets;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.server.network.*;
import com.legacyminecraft.poseidon.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet105CraftProgressBar extends Packet {

    public int a;
    public int b;
    public int c;

    public Packet105CraftProgressBar() {}

    public Packet105CraftProgressBar(int i, int j, int k) {
        this.a = i;
        this.b = j;
        this.c = k;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readByte();
        this.b = datainputstream.readShort();
        this.c = datainputstream.readShort();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeByte(this.a);
        dataoutputstream.writeShort(this.b);
        dataoutputstream.writeShort(this.c);
    }

    public int a() {
        return 5;
    }
}
