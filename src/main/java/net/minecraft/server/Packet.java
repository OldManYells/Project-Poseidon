package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {

    private static final PacketProtocol packetProtocolService = PacketProtocol.getInstance();
    public final long timestamp = System.currentTimeMillis();
    public boolean k = false;

    public Packet() {}
    
    /**
     * Register a packet
     * @author moderator_Man
     * @param id
     * @param clientSide
     * @param serverSide
     * @param oclass
     */
    public static void registerPacket(int id, boolean clientSide, boolean serverSide, Class oclass)
    {
        packetProtocolService.registerPacket(id, clientSide, serverSide, oclass);
    }
    
    static void a(int i, boolean flag, boolean flag1, Class oclass) {
        packetProtocolService.registerPacket(i, flag, flag1, oclass);
    }

    public static Packet a(int i) {
        return packetProtocolService.createPacket(i);
    }

    public final int b() {
        return packetProtocolService.getPacketId(this);
    }

    // CraftBukkit - throws IOException
    public static Packet a(DataInputStream datainputstream, boolean flag) throws IOException {
        return packetProtocolService.readPacket(datainputstream, flag);
    }

    // CraftBukkit - throws IOException
    public static void a(Packet packet, DataOutputStream dataoutputstream) throws IOException {
        packetProtocolService.writePacket(packet, dataoutputstream);
    }

    // CraftBukkit - throws IOException
    public static void a(String s, DataOutputStream dataoutputstream)  throws IOException {
        packetProtocolService.writeString(s, dataoutputstream);
    }

    // CraftBukkit - throws IOException
    public static String a(DataInputStream datainputstream, int i)  throws IOException {
        return packetProtocolService.readString(datainputstream, i);
    }

    public abstract void a(DataInputStream datainputstream) throws IOException; // CraftBukkit

    public abstract void a(DataOutputStream dataoutputstream) throws IOException; // CraftBukkit

    public abstract void a(NetHandler nethandler);

    public abstract int a();
}
