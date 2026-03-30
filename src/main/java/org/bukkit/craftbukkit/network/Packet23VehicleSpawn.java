package org.bukkit.craftbukkit.network;

import net.minecraft.server.MathHelper;
import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;
import org.bukkit.craftbukkit.entity.Entity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet23VehicleSpawn extends Packet {

    public int entityId;
    public int encodedX;
    public int encodedY;
    public int encodedZ;
    public int velocityX;
    public int velocityY;
    public int velocityZ;
    public int type;
    public int ownerEntityId;

    public Packet23VehicleSpawn() {}

    public Packet23VehicleSpawn(Entity entity, int i) {
        this(entity, i, 0);
    }

    public Packet23VehicleSpawn(Entity entity, int type, int ownerEntityId) {
        this.entityId = entity.id;
        this.encodedX = MathHelper.floor(entity.locX * 32.0D);
        this.encodedY = MathHelper.floor(entity.locY * 32.0D);
        this.encodedZ = MathHelper.floor(entity.locZ * 32.0D);
        this.type = type;
        this.ownerEntityId = ownerEntityId;
        if (ownerEntityId > 0) {
            double d0 = entity.motX;
            double d1 = entity.motY;
            double d2 = entity.motZ;
            double d3 = 3.9D;

            if (d0 < -d3) {
                d0 = -d3;
            }

            if (d1 < -d3) {
                d1 = -d3;
            }

            if (d2 < -d3) {
                d2 = -d3;
            }

            if (d0 > d3) {
                d0 = d3;
            }

            if (d1 > d3) {
                d1 = d3;
            }

            if (d2 > d3) {
                d2 = d3;
            }

            this.velocityX = (int) (d0 * 8000.0D);
            this.velocityY = (int) (d1 * 8000.0D);
            this.velocityZ = (int) (d2 * 8000.0D);
        }
    }

    @Override
    public void a(DataInputStream datainputstream) throws IOException {
        this.readPacketData(datainputstream);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        this.entityId = datainputstream.readInt();
        this.type = datainputstream.readByte();
        this.encodedX = datainputstream.readInt();
        this.encodedY = datainputstream.readInt();
        this.encodedZ = datainputstream.readInt();
        this.ownerEntityId = datainputstream.readInt();
        if (this.ownerEntityId > 0) {
            this.velocityX = datainputstream.readShort();
            this.velocityY = datainputstream.readShort();
            this.velocityZ = datainputstream.readShort();
        }
    }

    @Override
    public void a(DataOutputStream dataoutputstream) throws IOException {
        this.writePacketData(dataoutputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.entityId);
        dataoutputstream.writeByte(this.type);
        dataoutputstream.writeInt(this.encodedX);
        dataoutputstream.writeInt(this.encodedY);
        dataoutputstream.writeInt(this.encodedZ);
        dataoutputstream.writeInt(this.ownerEntityId);
        if (this.ownerEntityId > 0) {
            dataoutputstream.writeShort(this.velocityX);
            dataoutputstream.writeShort(this.velocityY);
            dataoutputstream.writeShort(this.velocityZ);
        }
    }

    @Override
    public void a(NetHandler nethandler) {
        this.handlePacket(nethandler);
    }

    public void handlePacket(NetHandler nethandler) {
        nethandler.a(this);
    }

    @Override
    public int a() {
        return this.getPacketSize();
    }

    public int getPacketSize() {
        return 21 + this.ownerEntityId > 0 ? 6 : 0;
    }
}
