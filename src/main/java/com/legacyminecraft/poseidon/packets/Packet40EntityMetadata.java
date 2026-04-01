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
import java.util.List;

public class Packet40EntityMetadata extends Packet {

    public int a;
    private List b;

    public Packet40EntityMetadata() {}

    public Packet40EntityMetadata(int i, DataWatcher datawatcher) {
        this.a = i;
        this.b = datawatcher.b();
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readInt();
        this.b = DataWatcher.a(datainputstream);
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.a);
        DataWatcher.a(this.b, dataoutputstream);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return 5;
    }
}
