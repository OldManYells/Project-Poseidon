package com.legacyminecraft.poseidon.packets;
import com.legacyminecraft.poseidon.api.*;
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

public class Packet30Entity extends Packet {

    public int a;
    public byte b;
    public byte c;
    public byte d;
    public byte e;
    public byte f;
    public boolean g = false;

    public Packet30Entity() {}

    public Packet30Entity(int i) {
        this.a = i;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readInt();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.a);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return 4;
    }
}
