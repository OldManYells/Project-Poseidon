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

public class Packet101CloseWindow extends Packet {

    public int a;

    public Packet101CloseWindow() {}

    public Packet101CloseWindow(int i) {
        this.a = i;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readByte();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeByte(this.a);
    }

    public int a() {
        return 1;
    }
}
