package com.legacyminecraft.poseidon.packet;

import net.minecraft.server.ChunkPosition;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Canonical codec helpers for legacy packet data layouts.
 */
public final class PacketDataCodec {
    private static final PacketDataCodec INSTANCE = new PacketDataCodec();

    private PacketDataCodec() {
    }

    public static PacketDataCodec getInstance() {
        return INSTANCE;
    }

    public void readPacket0(DataInputStream input) {
    }

    public void writePacket0(DataOutputStream output) {
    }

    public int packet0Length() {
        return 0;
    }

    public Packet20Data packet20FromNamedEntity(net.minecraft.server.EntityHuman entityHuman) {
        net.minecraft.server.ItemStack itemStack = entityHuman.inventory.getItemInHand();
        int heldItemId = itemStack == null ? 0 : itemStack.id;
        return new Packet20Data(
                entityHuman.id,
                entityHuman.name,
                toPacketCoordinate(entityHuman.locX),
                toPacketCoordinate(entityHuman.locY),
                toPacketCoordinate(entityHuman.locZ),
                toPackedAngle(entityHuman.yaw),
                toPackedAngle(entityHuman.pitch),
                heldItemId
        );
    }

    public Packet21Data packet21FromPickupEntity(net.minecraft.server.EntityItem entityItem) {
        return new Packet21Data(
                entityItem.id,
                entityItem.itemStack.id,
                entityItem.itemStack.count,
                entityItem.itemStack.getData(),
                toPacketCoordinate(entityItem.locX),
                toPacketCoordinate(entityItem.locY),
                toPacketCoordinate(entityItem.locZ),
                toPackedMotionByte(entityItem.motX),
                toPackedMotionByte(entityItem.motY),
                toPackedMotionByte(entityItem.motZ)
        );
    }

    public Packet23Data packet23FromVehicleEntity(net.minecraft.server.Entity entity, int vehicleType, int throwerId) {
        int velocityX = 0;
        int velocityY = 0;
        int velocityZ = 0;
        if (throwerId > 0) {
            velocityX = toPacketVelocityShort(entity.motX);
            velocityY = toPacketVelocityShort(entity.motY);
            velocityZ = toPacketVelocityShort(entity.motZ);
        }

        return new Packet23Data(
                entity.id,
                vehicleType,
                toPacketCoordinate(entity.locX),
                toPacketCoordinate(entity.locY),
                toPacketCoordinate(entity.locZ),
                throwerId,
                velocityX,
                velocityY,
                velocityZ
        );
    }

    public Packet24Data packet24FromMobEntity(net.minecraft.server.EntityLiving entityLiving) {
        return new Packet24Data(
                entityLiving.id,
                net.minecraft.server.EntityTypes.a(entityLiving),
                toPacketCoordinate(entityLiving.locX),
                toPacketCoordinate(entityLiving.locY),
                toPacketCoordinate(entityLiving.locZ),
                toPackedAngle(entityLiving.yaw),
                toPackedAngle(entityLiving.pitch),
                null
        );
    }

    public Packet25Data packet25FromPainting(net.minecraft.server.EntityPainting entityPainting) {
        return new Packet25Data(
                entityPainting.id,
                entityPainting.e.A,
                entityPainting.b,
                entityPainting.c,
                entityPainting.d,
                entityPainting.a
        );
    }

    public Packet28Data packet28FromEntity(net.minecraft.server.Entity entity) {
        return packet28FromMotion(entity.id, entity.motX, entity.motY, entity.motZ);
    }

    public Packet28Data packet28FromMotion(int entityId, double motionX, double motionY, double motionZ) {
        return new Packet28Data(
                entityId,
                toPacketVelocityShort(motionX),
                toPacketVelocityShort(motionY),
                toPacketVelocityShort(motionZ)
        );
    }

    public Packet34Data packet34FromEntity(net.minecraft.server.Entity entity) {
        return new Packet34Data(
                entity.id,
                toPacketCoordinate(entity.locX),
                toPacketCoordinate(entity.locY),
                toPacketCoordinate(entity.locZ),
                toPackedAngle(entity.yaw),
                toPackedAngle(entity.pitch)
        );
    }

    public Packet71WeatherData packet71FromWeatherEntity(net.minecraft.server.Entity entity) {
        int weatherType = entity instanceof net.minecraft.server.EntityWeatherStorm ? 1 : 0;
        return new Packet71WeatherData(
                entity.id,
                weatherType,
                toPacketCoordinate(entity.locX),
                toPacketCoordinate(entity.locY),
                toPacketCoordinate(entity.locZ)
        );
    }

    public Packet17Data readPacket17(DataInputStream input) throws IOException {
        return new Packet17Data(
                input.readInt(),
                input.readByte(),
                input.readInt(),
                input.readByte(),
                input.readInt()
        );
    }

    public void writePacket17(Packet17Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getType());
        output.writeInt(data.getPrimaryValue());
        output.writeByte(data.getAuxiliaryByteValue());
        output.writeInt(data.getSecondaryValue());
    }

    public Packet61Data readPacket61(DataInputStream input) throws IOException {
        return new Packet61Data(
                input.readInt(),
                input.readInt(),
                input.readByte(),
                input.readInt(),
                input.readInt()
        );
    }

    public void writePacket61(Packet61Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getPrimaryId());
        output.writeInt(data.getX());
        output.writeByte(data.getY());
        output.writeInt(data.getZ());
        output.writeInt(data.getValue());
    }

    public Packet53Data readPacket53(DataInputStream input) throws IOException {
        return new Packet53Data(
                input.readInt(),
                input.read(),
                input.readInt(),
                input.read(),
                input.read()
        );
    }

    public void writePacket53(Packet53Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getX());
        output.write(data.getY());
        output.writeInt(data.getZ());
        output.write(data.getMaterialId());
        output.write(data.getDataValue());
    }

    public Packet54Data readPacket54(DataInputStream input) throws IOException {
        return new Packet54Data(
                input.readInt(),
                input.readShort(),
                input.readInt(),
                input.read(),
                input.read()
        );
    }

    public void writePacket54(Packet54Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getX());
        output.writeShort(data.getY());
        output.writeInt(data.getZ());
        output.write(data.getInstrument());
        output.write(data.getPitch());
    }

    public Packet29Data readPacket29(DataInputStream input) throws IOException {
        return new Packet29Data(input.readInt());
    }

    public void writePacket29(Packet29Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
    }

    public Packet38Data readPacket38(DataInputStream input) throws IOException {
        return new Packet38Data(input.readInt(), input.readByte());
    }

    public void writePacket38(Packet38Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getStatusByte());
    }

    public Packet39Data readPacket39(DataInputStream input) throws IOException {
        return new Packet39Data(input.readInt(), input.readInt());
    }

    public void writePacket39(Packet39Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeInt(data.getVehicleId());
    }

    public Packet50Data readPacket50(DataInputStream input) throws IOException {
        return new Packet50Data(input.readInt(), input.readInt(), input.read() != 0);
    }

    public void writePacket50(Packet50Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getChunkX());
        output.writeInt(data.getChunkZ());
        output.write(data.isMode() ? 1 : 0);
    }

    public Packet6Data readPacket6(DataInputStream input) throws IOException {
        return new Packet6Data(input.readInt(), input.readInt(), input.readInt());
    }

    public void writePacket6(Packet6Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getX());
        output.writeInt(data.getY());
        output.writeInt(data.getZ());
    }

    public Packet8Data readPacket8(DataInputStream input) throws IOException {
        return new Packet8Data(input.readShort());
    }

    public void writePacket8(Packet8Data data, DataOutputStream output) throws IOException {
        output.writeShort(data.getHealth());
    }

    public Packet200Data readPacket200(DataInputStream input) throws IOException {
        return new Packet200Data(input.readInt(), input.readByte());
    }

    public void writePacket200(Packet200Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getStatisticId());
        output.writeByte(data.getAmount());
    }

    public Packet4Data readPacket4(DataInputStream input) throws IOException {
        return new Packet4Data(input.readLong());
    }

    public void writePacket4(Packet4Data data, DataOutputStream output) throws IOException {
        output.writeLong(data.getWorldTime());
    }

    public Packet5Data readPacket5(DataInputStream input) throws IOException {
        return new Packet5Data(input.readInt(), input.readShort(), input.readShort(), input.readShort());
    }

    public void writePacket5(Packet5Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeShort(data.getSlot());
        output.writeShort(data.getItemId());
        output.writeShort(data.getDataValue());
    }

    public Packet7Data readPacket7(DataInputStream input) throws IOException {
        return new Packet7Data(input.readInt(), input.readInt(), input.readByte());
    }

    public void writePacket7(Packet7Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getUserEntityId());
        output.writeInt(data.getTargetEntityId());
        output.writeByte(data.getInteractionType());
    }

    public Packet9Data readPacket9(DataInputStream input) throws IOException {
        return new Packet9Data(input.readByte());
    }

    public void writePacket9(Packet9Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getDimension());
    }

    public Packet16Data readPacket16(DataInputStream input) throws IOException {
        return new Packet16Data(input.readShort());
    }

    public void writePacket16(Packet16Data data, DataOutputStream output) throws IOException {
        output.writeShort(data.getItemInHandIndex());
    }

    public Packet18Data readPacket18(DataInputStream input) throws IOException {
        return new Packet18Data(input.readInt(), input.readByte());
    }

    public void writePacket18(Packet18Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getAnimation());
    }

    public Packet19Data readPacket19(DataInputStream input) throws IOException {
        return new Packet19Data(input.readInt(), input.readByte());
    }

    public void writePacket19(Packet19Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getAnimation());
    }

    public Packet20Data readPacket20(DataInputStream input) throws IOException {
        return new Packet20Data(
                input.readInt(),
                Packet.a(input, 16),
                input.readInt(),
                input.readInt(),
                input.readInt(),
                input.readByte(),
                input.readByte(),
                input.readShort()
        );
    }

    public void writePacket20(Packet20Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        Packet.a(data.getPlayerName(), output);
        output.writeInt(data.getX());
        output.writeInt(data.getY());
        output.writeInt(data.getZ());
        output.writeByte(data.getYaw());
        output.writeByte(data.getPitch());
        output.writeShort(data.getHeldItemId());
    }

    public int packet20Length() {
        return 28;
    }

    public Packet21Data readPacket21(DataInputStream input) throws IOException {
        return new Packet21Data(
                input.readInt(),
                input.readShort(),
                input.readByte(),
                input.readShort(),
                input.readInt(),
                input.readInt(),
                input.readInt(),
                input.readByte(),
                input.readByte(),
                input.readByte()
        );
    }

    public void writePacket21(Packet21Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeShort(data.getItemId());
        output.writeByte(data.getItemCount());
        output.writeShort(data.getItemData());
        output.writeInt(data.getX());
        output.writeInt(data.getY());
        output.writeInt(data.getZ());
        output.writeByte(data.getVelocityX());
        output.writeByte(data.getVelocityY());
        output.writeByte(data.getVelocityZ());
    }

    public int packet21Length() {
        return 24;
    }

    public Packet24Data readPacket24(DataInputStream input) throws IOException {
        return new Packet24Data(
                input.readInt(),
                input.readByte(),
                input.readInt(),
                input.readInt(),
                input.readInt(),
                input.readByte(),
                input.readByte(),
                DataWatcher.a(input)
        );
    }

    public void writePacket24(Packet24Data data, DataWatcher watcher, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getEntityType());
        output.writeInt(data.getX());
        output.writeInt(data.getY());
        output.writeInt(data.getZ());
        output.writeByte(data.getYaw());
        output.writeByte(data.getPitch());
        if (watcher != null) {
            watcher.a(output);
        } else {
            DataWatcher.a(data.getMetadata(), output);
        }
    }

    public int packet24Length() {
        return 20;
    }

    public Packet40Data readPacket40(DataInputStream input) throws IOException {
        return new Packet40Data(input.readInt(), DataWatcher.a(input));
    }

    public void writePacket40(Packet40Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        DataWatcher.a(data.getMetadata(), output);
    }

    public int packet40Length() {
        return 5;
    }

    public Packet14Data readPacket14(DataInputStream input) throws IOException {
        return new Packet14Data(
                input.read(),
                input.readInt(),
                input.read(),
                input.readInt(),
                input.read()
        );
    }

    public void writePacket14(Packet14Data data, DataOutputStream output) throws IOException {
        output.write(data.getStatus());
        output.writeInt(data.getX());
        output.write(data.getY());
        output.writeInt(data.getZ());
        output.write(data.getFace());
    }

    public Packet15Data readPacket15(DataInputStream input) throws IOException {
        int x = input.readInt();
        int y = input.read();
        int z = input.readInt();
        int face = input.read();
        short itemId = input.readShort();
        ItemSlotData itemSlot = null;
        if (itemId >= 0) {
            itemSlot = new ItemSlotData(itemId, input.readByte(), input.readShort());
        }
        return new Packet15Data(x, y, z, face, itemSlot);
    }

    public void writePacket15(Packet15Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getX());
        output.write(data.getY());
        output.writeInt(data.getZ());
        output.write(data.getFace());
        ItemSlotData itemSlot = data.getItemSlot();
        if (itemSlot == null) {
            output.writeShort(-1);
        } else {
            output.writeShort(itemSlot.getItemId());
            output.writeByte(itemSlot.getCount());
            output.writeShort(itemSlot.getData());
        }
    }

    public Packet130Data readPacket130(DataInputStream input) throws IOException {
        int x = input.readInt();
        int y = input.readShort();
        int z = input.readInt();
        String[] lines = new String[4];
        for (int i = 0; i < 4; ++i) {
            lines[i] = Packet.a(input, 15);
        }
        return new Packet130Data(x, y, z, lines);
    }

    public void writePacket130(Packet130Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getX());
        output.writeShort(data.getY());
        output.writeInt(data.getZ());
        for (int i = 0; i < 4; ++i) {
            Packet.a(data.getLines()[i], output);
        }
    }

    public int packet130Length(String[] lines) {
        int total = 0;
        for (int i = 0; i < 4; ++i) {
            total += lines[i].length();
        }
        return total;
    }

    public Packet1Data readPacket1(DataInputStream input) throws IOException {
        return new Packet1Data(
                input.readInt(),
                Packet.a(input, 16),
                input.readLong(),
                input.readByte()
        );
    }

    public void writePacket1(Packet1Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getProtocolVersion());
        Packet.a(data.getUsername(), output);
        output.writeLong(data.getMapSeed());
        output.writeByte(data.getDimension());
    }

    public int packet1Length(String username) {
        return 4 + username.length() + 4 + 5;
    }

    public Packet2Data readPacket2(DataInputStream input) throws IOException {
        return new Packet2Data(Packet.a(input, 32));
    }

    public void writePacket2(Packet2Data data, DataOutputStream output) throws IOException {
        Packet.a(data.getHandshake(), output);
    }

    public int packet2Length(String handshake) {
        return 4 + handshake.length() + 4;
    }

    public Packet3Data readPacket3(DataInputStream input) throws IOException {
        return new Packet3Data(Packet.a(input, 119));
    }

    public void writePacket3(Packet3Data data, DataOutputStream output) throws IOException {
        Packet.a(data.getMessage(), output);
    }

    public int packet3Length(String message) {
        return message.length();
    }

    public Packet255Data readPacket255(DataInputStream input) throws IOException {
        return new Packet255Data(Packet.a(input, 100));
    }

    public void writePacket255(Packet255Data data, DataOutputStream output) throws IOException {
        Packet.a(data.getReason(), output);
    }

    public int packet255Length(String reason) {
        return reason.length();
    }

    public Packet30Data readPacket30(DataInputStream input) throws IOException {
        return new Packet30Data(input.readInt());
    }

    public void writePacket30(Packet30Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
    }

    public Packet31Data readPacket31(DataInputStream input) throws IOException {
        return new Packet31Data(
                input.readInt(),
                input.readByte(),
                input.readByte(),
                input.readByte()
        );
    }

    public void writePacket31(Packet31Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getDeltaX());
        output.writeByte(data.getDeltaY());
        output.writeByte(data.getDeltaZ());
    }

    public Packet32Data readPacket32(DataInputStream input) throws IOException {
        return new Packet32Data(
                input.readInt(),
                input.readByte(),
                input.readByte()
        );
    }

    public void writePacket32(Packet32Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getYaw());
        output.writeByte(data.getPitch());
    }

    public Packet33Data readPacket33(DataInputStream input) throws IOException {
        return new Packet33Data(
                input.readInt(),
                input.readByte(),
                input.readByte(),
                input.readByte(),
                input.readByte(),
                input.readByte()
        );
    }

    public void writePacket33(Packet33Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getDeltaX());
        output.writeByte(data.getDeltaY());
        output.writeByte(data.getDeltaZ());
        output.writeByte(data.getYaw());
        output.writeByte(data.getPitch());
    }

    public Packet34Data readPacket34(DataInputStream input) throws IOException {
        return new Packet34Data(
                input.readInt(),
                input.readInt(),
                input.readInt(),
                input.readInt(),
                (byte) input.read(),
                (byte) input.read()
        );
    }

    public void writePacket34(Packet34Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeInt(data.getX());
        output.writeInt(data.getY());
        output.writeInt(data.getZ());
        output.write(data.getYaw());
        output.write(data.getPitch());
    }

    public Packet10Data readPacket10(DataInputStream input) throws IOException {
        return new Packet10Data(input.read() != 0);
    }

    public void writePacket10(Packet10Data data, DataOutputStream output) throws IOException {
        output.write(data.isOnGround() ? 1 : 0);
    }

    public Packet11Data readPacket11(DataInputStream input) throws IOException {
        return new Packet11Data(
                input.readDouble(),
                input.readDouble(),
                input.readDouble(),
                input.readDouble(),
                input.read() != 0
        );
    }

    public void writePacket11(Packet11Data data, DataOutputStream output) throws IOException {
        output.writeDouble(data.getX());
        output.writeDouble(data.getY());
        output.writeDouble(data.getStance());
        output.writeDouble(data.getZ());
        output.write(data.isOnGround() ? 1 : 0);
    }

    public Packet12Data readPacket12(DataInputStream input) throws IOException {
        return new Packet12Data(input.readFloat(), input.readFloat(), input.read() != 0);
    }

    public void writePacket12(Packet12Data data, DataOutputStream output) throws IOException {
        output.writeFloat(data.getYaw());
        output.writeFloat(data.getPitch());
        output.write(data.isOnGround() ? 1 : 0);
    }

    public Packet13Data readPacket13(DataInputStream input) throws IOException {
        return new Packet13Data(
                input.readDouble(),
                input.readDouble(),
                input.readDouble(),
                input.readDouble(),
                input.readFloat(),
                input.readFloat(),
                input.read() != 0
        );
    }

    public void writePacket13(Packet13Data data, DataOutputStream output) throws IOException {
        output.writeDouble(data.getX());
        output.writeDouble(data.getY());
        output.writeDouble(data.getStance());
        output.writeDouble(data.getZ());
        output.writeFloat(data.getYaw());
        output.writeFloat(data.getPitch());
        output.write(data.isOnGround() ? 1 : 0);
    }

    public Packet22Data readPacket22(DataInputStream input) throws IOException {
        return new Packet22Data(input.readInt(), input.readInt());
    }

    public void writePacket22(Packet22Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getCollectedEntityId());
        output.writeInt(data.getCollectorEntityId());
    }

    public Packet23Data readPacket23(DataInputStream input) throws IOException {
        int entityId = input.readInt();
        int type = input.readByte();
        int x = input.readInt();
        int y = input.readInt();
        int z = input.readInt();
        int throwerId = input.readInt();
        int velocityX = 0;
        int velocityY = 0;
        int velocityZ = 0;
        if (throwerId > 0) {
            velocityX = input.readShort();
            velocityY = input.readShort();
            velocityZ = input.readShort();
        }
        return new Packet23Data(entityId, type, x, y, z, throwerId, velocityX, velocityY, velocityZ);
    }

    public void writePacket23(Packet23Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getType());
        output.writeInt(data.getX());
        output.writeInt(data.getY());
        output.writeInt(data.getZ());
        output.writeInt(data.getThrowerId());
        if (data.getThrowerId() > 0) {
            output.writeShort(data.getVelocityX());
            output.writeShort(data.getVelocityY());
            output.writeShort(data.getVelocityZ());
        }
    }

    public int packet23Length(int throwerId) {
        return 21 + throwerId > 0 ? 6 : 0;
    }

    public Packet25Data readPacket25(DataInputStream input) throws IOException {
        return new Packet25Data(
                input.readInt(),
                Packet.a(input, net.minecraft.server.EnumArt.z),
                input.readInt(),
                input.readInt(),
                input.readInt(),
                input.readInt()
        );
    }

    public void writePacket25(Packet25Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        Packet.a(data.getArtName(), output);
        output.writeInt(data.getTileX());
        output.writeInt(data.getTileY());
        output.writeInt(data.getTileZ());
        output.writeInt(data.getDirection());
    }

    public int packet25Length(String artName) {
        return 24 + artName.length();
    }

    public Packet28Data readPacket28(DataInputStream input) throws IOException {
        return new Packet28Data(input.readInt(), input.readShort(), input.readShort(), input.readShort());
    }

    public void writePacket28(Packet28Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeShort(data.getVelocityX());
        output.writeShort(data.getVelocityY());
        output.writeShort(data.getVelocityZ());
    }

    public int packet28Length() {
        return 10;
    }

    public Packet51ReadData readPacket51(DataInputStream input) throws IOException {
        int x = input.readInt();
        int y = input.readShort();
        int z = input.readInt();
        int sizeX = input.read() + 1;
        int sizeY = input.read() + 1;
        int sizeZ = input.read() + 1;
        int compressedLength = input.readInt();
        byte[] compressedData = new byte[compressedLength];
        input.readFully(compressedData);
        byte[] chunkData = new byte[sizeX * sizeY * sizeZ * 5 / 2];
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);

        try {
            inflater.inflate(chunkData);
        } catch (DataFormatException dataFormatException) {
            throw new IOException("Bad compressed data format");
        } finally {
            inflater.end();
        }

        return new Packet51ReadData(x, y, z, sizeX, sizeY, sizeZ, compressedLength, chunkData);
    }

    public void writePacket51(Packet51WriteData data, DataOutputStream output) throws IOException {
        output.writeInt(data.getX());
        output.writeShort(data.getY());
        output.writeInt(data.getZ());
        output.write(data.getSizeX() - 1);
        output.write(data.getSizeY() - 1);
        output.write(data.getSizeZ() - 1);
        output.writeInt(data.getCompressedLength());
        output.write(data.getCompressedData(), 0, data.getCompressedLength());
    }

    public int packet51Length(int compressedLength) {
        return 17 + compressedLength;
    }

    public Packet52Data readPacket52(DataInputStream input) throws IOException {
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        int recordCount = input.readShort() & 65535;
        short[] coordinates = new short[recordCount];
        byte[] typeIds = new byte[recordCount];
        byte[] metadata = new byte[recordCount];

        for (int i = 0; i < recordCount; ++i) {
            coordinates[i] = input.readShort();
        }

        input.readFully(typeIds);
        input.readFully(metadata);

        return new Packet52Data(chunkX, chunkZ, coordinates, typeIds, metadata, recordCount);
    }

    public void writePacket52(Packet52Data data, DataOutputStream output) throws IOException {
        output.writeInt(data.getChunkX());
        output.writeInt(data.getChunkZ());
        output.writeShort((short) data.getRecordCount());

        for (int i = 0; i < data.getRecordCount(); ++i) {
            output.writeShort(data.getCoordinates()[i]);
        }

        output.write(data.getTypeIds());
        output.write(data.getMetadata());
    }

    public int packet52Length(int recordCount) {
        return 10 + recordCount * 4;
    }

    public Packet60Data readPacket60(DataInputStream input) throws IOException {
        double x = input.readDouble();
        double y = input.readDouble();
        double z = input.readDouble();
        float radius = input.readFloat();
        int blockCount = input.readInt();

        Set explodedBlocks = new HashSet();
        int baseX = (int) x;
        int baseY = (int) y;
        int baseZ = (int) z;

        for (int i = 0; i < blockCount; ++i) {
            int blockX = input.readByte() + baseX;
            int blockY = input.readByte() + baseY;
            int blockZ = input.readByte() + baseZ;

            explodedBlocks.add(new ChunkPosition(blockX, blockY, blockZ));
        }

        return new Packet60Data(x, y, z, radius, explodedBlocks);
    }

    public void writePacket60(Packet60Data data, DataOutputStream output) throws IOException {
        output.writeDouble(data.getX());
        output.writeDouble(data.getY());
        output.writeDouble(data.getZ());
        output.writeFloat(data.getRadius());
        output.writeInt(data.getExplodedBlocks().size());
        int baseX = (int) data.getX();
        int baseY = (int) data.getY();
        int baseZ = (int) data.getZ();
        Iterator iterator = data.getExplodedBlocks().iterator();

        while (iterator.hasNext()) {
            ChunkPosition chunkPosition = (ChunkPosition) iterator.next();
            output.writeByte(chunkPosition.x - baseX);
            output.writeByte(chunkPosition.y - baseY);
            output.writeByte(chunkPosition.z - baseZ);
        }
    }

    public int packet60Length(int blockCount) {
        return 32 + blockCount * 3;
    }

    public Packet105Data readPacket105(DataInputStream input) throws IOException {
        return new Packet105Data(
                input.readByte(),
                input.readShort(),
                input.readShort()
        );
    }

    public void writePacket105(Packet105Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getWindowId());
        output.writeShort(data.getProperty());
        output.writeShort(data.getValue());
    }

    public Packet106Data readPacket106(DataInputStream input) throws IOException {
        return new Packet106Data(
                input.readByte(),
                input.readShort(),
                input.readByte() != 0
        );
    }

    public void writePacket106(Packet106Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getWindowId());
        output.writeShort(data.getActionNumber());
        output.writeByte(data.isAccepted() ? 1 : 0);
    }

    public Packet100Data readPacket100(DataInputStream input) throws IOException {
        return new Packet100Data(
                input.readByte(),
                input.readByte(),
                input.readUTF(),
                input.readByte()
        );
    }

    public void writePacket100(Packet100Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getWindowId());
        output.writeByte(data.getWindowType());
        output.writeUTF(data.getTitle());
        output.writeByte(data.getSlotCount());
    }

    public int packet100Length(String title) {
        return 3 + title.length();
    }

    public Packet101Data readPacket101(DataInputStream input) throws IOException {
        return new Packet101Data(input.readByte());
    }

    public void writePacket101(Packet101Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getWindowId());
    }

    public Packet103Data readPacket103(DataInputStream input) throws IOException {
        int windowId = input.readByte();
        int slot = input.readShort();
        short itemId = input.readShort();
        if (itemId < 0) {
            return new Packet103Data(windowId, slot, null);
        }

        ItemSlotData itemSlot = new ItemSlotData(itemId, input.readByte(), input.readShort());
        return new Packet103Data(windowId, slot, itemSlot);
    }

    public void writePacket103(Packet103Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getWindowId());
        output.writeShort(data.getSlot());
        ItemSlotData itemSlot = data.getItemSlot();
        if (itemSlot == null) {
            output.writeShort(-1);
        } else {
            output.writeShort(itemSlot.getItemId());
            output.writeByte(itemSlot.getCount());
            output.writeShort(itemSlot.getData());
        }
    }

    public Packet102Data readPacket102(DataInputStream input) throws IOException {
        int windowId = input.readByte();
        int slot = input.readShort();
        int button = input.readByte();
        short actionNumber = input.readShort();
        boolean shift = input.readBoolean();
        short itemId = input.readShort();
        ItemSlotData itemSlot = null;
        if (itemId >= 0) {
            itemSlot = new ItemSlotData(itemId, input.readByte(), input.readShort());
        }
        return new Packet102Data(windowId, slot, button, actionNumber, shift, itemSlot);
    }

    public void writePacket102(Packet102Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getWindowId());
        output.writeShort(data.getSlot());
        output.writeByte(data.getButton());
        output.writeShort(data.getActionNumber());
        output.writeBoolean(data.isShift());
        ItemSlotData itemSlot = data.getItemSlot();
        if (itemSlot == null) {
            output.writeShort(-1);
        } else {
            output.writeShort(itemSlot.getItemId());
            output.writeByte(itemSlot.getCount());
            output.writeShort(itemSlot.getData());
        }
    }

    public Packet104Data readPacket104(DataInputStream input) throws IOException {
        int windowId = input.readByte();
        short size = input.readShort();
        ItemSlotData[] slots = new ItemSlotData[size];
        for (int i = 0; i < size; ++i) {
            short itemId = input.readShort();
            if (itemId >= 0) {
                slots[i] = new ItemSlotData(itemId, input.readByte(), input.readShort());
            }
        }
        return new Packet104Data(windowId, slots);
    }

    public void writePacket104(Packet104Data data, DataOutputStream output) throws IOException {
        output.writeByte(data.getWindowId());
        output.writeShort(data.getSlots().length);
        for (int i = 0; i < data.getSlots().length; ++i) {
            ItemSlotData slot = data.getSlots()[i];
            if (slot == null) {
                output.writeShort(-1);
            } else {
                output.writeShort(slot.getItemId());
                output.writeByte(slot.getCount());
                output.writeShort(slot.getData());
            }
        }
    }

    public int packet104Length(int slotCount) {
        return 3 + slotCount * 5;
    }

    public Packet27Data readPacket27(DataInputStream input) throws IOException {
        return new Packet27Data(
                input.readFloat(),
                input.readFloat(),
                input.readFloat(),
                input.readFloat(),
                input.readBoolean(),
                input.readBoolean()
        );
    }

    public void writePacket27(Packet27Data data, DataOutputStream output) throws IOException {
        output.writeFloat(data.getPrimaryX());
        output.writeFloat(data.getPrimaryY());
        output.writeFloat(data.getSecondaryX());
        output.writeFloat(data.getSecondaryY());
        output.writeBoolean(data.isPrimaryFlag());
        output.writeBoolean(data.isSecondaryFlag());
    }

    public Packet70BedData readPacket70Bed(DataInputStream input) throws IOException {
        return new Packet70BedData(input.readByte());
    }

    public void writePacket70Bed(Packet70BedData data, DataOutputStream output) throws IOException {
        output.writeByte(data.getEventId());
    }

    public Packet71WeatherData readPacket71Weather(DataInputStream input) throws IOException {
        return new Packet71WeatherData(
                input.readInt(),
                input.readByte(),
                input.readInt(),
                input.readInt(),
                input.readInt()
        );
    }

    public void writePacket71Weather(Packet71WeatherData data, DataOutputStream output) throws IOException {
        output.writeInt(data.getEntityId());
        output.writeByte(data.getWeatherType());
        output.writeInt(data.getX());
        output.writeInt(data.getY());
        output.writeInt(data.getZ());
    }

    public int packet101Length() {
        return 1;
    }

    public int packet102Length() {
        return 11;
    }

    public int packet103Length() {
        return 8;
    }

    public int packet105Length() {
        return 5;
    }

    public int packet106Length() {
        return 4;
    }

    public int packet4Length() {
        return 8;
    }

    public int packet5Length() {
        return 8;
    }

    public int packet6Length() {
        return 12;
    }

    public int packet7Length() {
        return 9;
    }

    public int packet8Length() {
        return 2;
    }

    public int packet9Length() {
        return 1;
    }

    public int packet10Length() {
        return 1;
    }

    public int packet11Length() {
        return 33;
    }

    public int packet12Length() {
        return 9;
    }

    public int packet13Length() {
        return 41;
    }

    public int packet14Length() {
        return 11;
    }

    public int packet15Length() {
        return 15;
    }

    public int packet16Length() {
        return 2;
    }

    public int packet17Length() {
        return 14;
    }

    public int packet18Length() {
        return 5;
    }

    public int packet19Length() {
        return 5;
    }

    public int packet22Length() {
        return 8;
    }

    public int packet27Length() {
        return 18;
    }

    public int packet29Length() {
        return 4;
    }

    public int packet30Length() {
        return 4;
    }

    public int packet31Length() {
        return 7;
    }

    public int packet32Length() {
        return 6;
    }

    public int packet33Length() {
        return 9;
    }

    public int packet34Length() {
        return 34;
    }

    public int packet38Length() {
        return 5;
    }

    public int packet39Length() {
        return 8;
    }

    public int packet50Length() {
        return 9;
    }

    public int packet53Length() {
        return 11;
    }

    public int packet54Length() {
        return 12;
    }

    public int packet61Length() {
        return 20;
    }

    public int packet70Length() {
        return 1;
    }

    public int packet71Length() {
        return 17;
    }

    public int packet200Length() {
        return 6;
    }

    public Packet131Data readPacket131(DataInputStream input) throws IOException {
        short itemId = input.readShort();
        short damage = input.readShort();
        byte[] payload = new byte[input.readByte() & 255];
        input.readFully(payload);
        return new Packet131Data(itemId, damage, payload);
    }

    public void writePacket131(Packet131Data data, DataOutputStream output) throws IOException {
        output.writeShort(data.getItemId());
        output.writeShort(data.getDamage());
        output.writeByte(data.getPayload().length);
        output.write(data.getPayload());
    }

    public int packet131Length(byte[] payload) {
        return 4 + payload.length;
    }

    private int toPacketCoordinate(double coordinate) {
        return net.minecraft.server.MathHelper.floor(coordinate * 32.0D);
    }

    private byte toPackedAngle(float angle) {
        return (byte) ((int) (angle * 256.0F / 360.0F));
    }

    private byte toPackedMotionByte(double motion) {
        return (byte) ((int) (motion * 128.0D));
    }

    private int toPacketVelocityShort(double motion) {
        double clampedMotion = clampMotion(motion, 3.9D);
        return (int) (clampedMotion * 8000.0D);
    }

    private double clampMotion(double value, double maxAbs) {
        if (value < -maxAbs) {
            return -maxAbs;
        }
        if (value > maxAbs) {
            return maxAbs;
        }
        return value;
    }

    public static final class Packet17Data {
        private final int entityId;
        private final int type;
        private final int primaryValue;
        private final int auxiliaryByteValue;
        private final int secondaryValue;

        public Packet17Data(int entityId, int type, int primaryValue, int auxiliaryByteValue, int secondaryValue) {
            this.entityId = entityId;
            this.type = type;
            this.primaryValue = primaryValue;
            this.auxiliaryByteValue = auxiliaryByteValue;
            this.secondaryValue = secondaryValue;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getType() {
            return type;
        }

        public int getPrimaryValue() {
            return primaryValue;
        }

        public int getAuxiliaryByteValue() {
            return auxiliaryByteValue;
        }

        public int getSecondaryValue() {
            return secondaryValue;
        }
    }

    public static final class Packet61Data {
        private final int primaryId;
        private final int x;
        private final int y;
        private final int z;
        private final int value;

        public Packet61Data(int primaryId, int x, int y, int z, int value) {
            this.primaryId = primaryId;
            this.x = x;
            this.y = y;
            this.z = z;
            this.value = value;
        }

        public int getPrimaryId() {
            return primaryId;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getValue() {
            return value;
        }
    }

    public static final class Packet70BedData {
        private final int eventId;

        public Packet70BedData(int eventId) {
            this.eventId = eventId;
        }

        public int getEventId() {
            return eventId;
        }
    }

    public static final class Packet29Data {
        private final int entityId;

        public Packet29Data(int entityId) {
            this.entityId = entityId;
        }

        public int getEntityId() {
            return entityId;
        }
    }

    public static final class Packet38Data {
        private final int entityId;
        private final byte statusByte;

        public Packet38Data(int entityId, byte statusByte) {
            this.entityId = entityId;
            this.statusByte = statusByte;
        }

        public int getEntityId() {
            return entityId;
        }

        public byte getStatusByte() {
            return statusByte;
        }
    }

    public static final class Packet39Data {
        private final int entityId;
        private final int vehicleId;

        public Packet39Data(int entityId, int vehicleId) {
            this.entityId = entityId;
            this.vehicleId = vehicleId;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getVehicleId() {
            return vehicleId;
        }
    }

    public static final class Packet50Data {
        private final int chunkX;
        private final int chunkZ;
        private final boolean mode;

        public Packet50Data(int chunkX, int chunkZ, boolean mode) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.mode = mode;
        }

        public int getChunkX() {
            return chunkX;
        }

        public int getChunkZ() {
            return chunkZ;
        }

        public boolean isMode() {
            return mode;
        }
    }

    public static final class Packet6Data {
        private final int x;
        private final int y;
        private final int z;

        public Packet6Data(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

    public static final class Packet8Data {
        private final int health;

        public Packet8Data(int health) {
            this.health = health;
        }

        public int getHealth() {
            return health;
        }
    }

    public static final class Packet200Data {
        private final int statisticId;
        private final int amount;

        public Packet200Data(int statisticId, int amount) {
            this.statisticId = statisticId;
            this.amount = amount;
        }

        public int getStatisticId() {
            return statisticId;
        }

        public int getAmount() {
            return amount;
        }
    }

    public static final class Packet4Data {
        private final long worldTime;

        public Packet4Data(long worldTime) {
            this.worldTime = worldTime;
        }

        public long getWorldTime() {
            return worldTime;
        }
    }

    public static final class Packet5Data {
        private final int entityId;
        private final int slot;
        private final int itemId;
        private final int dataValue;

        public Packet5Data(int entityId, int slot, int itemId, int dataValue) {
            this.entityId = entityId;
            this.slot = slot;
            this.itemId = itemId;
            this.dataValue = dataValue;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getSlot() {
            return slot;
        }

        public int getItemId() {
            return itemId;
        }

        public int getDataValue() {
            return dataValue;
        }
    }

    public static final class Packet7Data {
        private final int userEntityId;
        private final int targetEntityId;
        private final int interactionType;

        public Packet7Data(int userEntityId, int targetEntityId, int interactionType) {
            this.userEntityId = userEntityId;
            this.targetEntityId = targetEntityId;
            this.interactionType = interactionType;
        }

        public int getUserEntityId() {
            return userEntityId;
        }

        public int getTargetEntityId() {
            return targetEntityId;
        }

        public int getInteractionType() {
            return interactionType;
        }
    }

    public static final class Packet9Data {
        private final byte dimension;

        public Packet9Data(byte dimension) {
            this.dimension = dimension;
        }

        public byte getDimension() {
            return dimension;
        }
    }

    public static final class Packet16Data {
        private final int itemInHandIndex;

        public Packet16Data(int itemInHandIndex) {
            this.itemInHandIndex = itemInHandIndex;
        }

        public int getItemInHandIndex() {
            return itemInHandIndex;
        }
    }

    public static final class Packet18Data {
        private final int entityId;
        private final int animation;

        public Packet18Data(int entityId, int animation) {
            this.entityId = entityId;
            this.animation = animation;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getAnimation() {
            return animation;
        }
    }

    public static final class Packet19Data {
        private final int entityId;
        private final int animation;

        public Packet19Data(int entityId, int animation) {
            this.entityId = entityId;
            this.animation = animation;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getAnimation() {
            return animation;
        }
    }

    public static final class Packet14Data {
        private final int status;
        private final int x;
        private final int y;
        private final int z;
        private final int face;

        public Packet14Data(int status, int x, int y, int z, int face) {
            this.status = status;
            this.x = x;
            this.y = y;
            this.z = z;
            this.face = face;
        }

        public int getStatus() {
            return status;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getFace() {
            return face;
        }
    }

    public static final class Packet15Data {
        private final int x;
        private final int y;
        private final int z;
        private final int face;
        private final ItemSlotData itemSlot;

        public Packet15Data(int x, int y, int z, int face, ItemSlotData itemSlot) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.face = face;
            this.itemSlot = itemSlot;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getFace() {
            return face;
        }

        public ItemSlotData getItemSlot() {
            return itemSlot;
        }
    }

    public static final class Packet130Data {
        private final int x;
        private final int y;
        private final int z;
        private final String[] lines;

        public Packet130Data(int x, int y, int z, String[] lines) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.lines = lines;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public String[] getLines() {
            return lines;
        }
    }

    public static final class Packet1Data {
        private final int protocolVersion;
        private final String username;
        private final long mapSeed;
        private final byte dimension;

        public Packet1Data(int protocolVersion, String username, long mapSeed, byte dimension) {
            this.protocolVersion = protocolVersion;
            this.username = username;
            this.mapSeed = mapSeed;
            this.dimension = dimension;
        }

        public int getProtocolVersion() {
            return protocolVersion;
        }

        public String getUsername() {
            return username;
        }

        public long getMapSeed() {
            return mapSeed;
        }

        public byte getDimension() {
            return dimension;
        }
    }

    public static final class Packet2Data {
        private final String handshake;

        public Packet2Data(String handshake) {
            this.handshake = handshake;
        }

        public String getHandshake() {
            return handshake;
        }
    }

    public static final class Packet3Data {
        private final String message;

        public Packet3Data(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static final class Packet255Data {
        private final String reason;

        public Packet255Data(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }

    public static final class Packet30Data {
        private final int entityId;

        public Packet30Data(int entityId) {
            this.entityId = entityId;
        }

        public int getEntityId() {
            return entityId;
        }
    }

    public static final class Packet31Data {
        private final int entityId;
        private final byte deltaX;
        private final byte deltaY;
        private final byte deltaZ;

        public Packet31Data(int entityId, byte deltaX, byte deltaY, byte deltaZ) {
            this.entityId = entityId;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.deltaZ = deltaZ;
        }

        public int getEntityId() {
            return entityId;
        }

        public byte getDeltaX() {
            return deltaX;
        }

        public byte getDeltaY() {
            return deltaY;
        }

        public byte getDeltaZ() {
            return deltaZ;
        }
    }

    public static final class Packet32Data {
        private final int entityId;
        private final byte yaw;
        private final byte pitch;

        public Packet32Data(int entityId, byte yaw, byte pitch) {
            this.entityId = entityId;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public int getEntityId() {
            return entityId;
        }

        public byte getYaw() {
            return yaw;
        }

        public byte getPitch() {
            return pitch;
        }
    }

    public static final class Packet33Data {
        private final int entityId;
        private final byte deltaX;
        private final byte deltaY;
        private final byte deltaZ;
        private final byte yaw;
        private final byte pitch;

        public Packet33Data(int entityId, byte deltaX, byte deltaY, byte deltaZ, byte yaw, byte pitch) {
            this.entityId = entityId;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.deltaZ = deltaZ;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public int getEntityId() {
            return entityId;
        }

        public byte getDeltaX() {
            return deltaX;
        }

        public byte getDeltaY() {
            return deltaY;
        }

        public byte getDeltaZ() {
            return deltaZ;
        }

        public byte getYaw() {
            return yaw;
        }

        public byte getPitch() {
            return pitch;
        }
    }

    public static final class Packet34Data {
        private final int entityId;
        private final int x;
        private final int y;
        private final int z;
        private final byte yaw;
        private final byte pitch;

        public Packet34Data(int entityId, int x, int y, int z, byte yaw, byte pitch) {
            this.entityId = entityId;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public byte getYaw() {
            return yaw;
        }

        public byte getPitch() {
            return pitch;
        }
    }

    public static final class Packet10Data {
        private final boolean onGround;

        public Packet10Data(boolean onGround) {
            this.onGround = onGround;
        }

        public boolean isOnGround() {
            return onGround;
        }
    }

    public static final class Packet11Data {
        private final double x;
        private final double y;
        private final double stance;
        private final double z;
        private final boolean onGround;

        public Packet11Data(double x, double y, double stance, double z, boolean onGround) {
            this.x = x;
            this.y = y;
            this.stance = stance;
            this.z = z;
            this.onGround = onGround;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getStance() {
            return stance;
        }

        public double getZ() {
            return z;
        }

        public boolean isOnGround() {
            return onGround;
        }
    }

    public static final class Packet12Data {
        private final float yaw;
        private final float pitch;
        private final boolean onGround;

        public Packet12Data(float yaw, float pitch, boolean onGround) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public boolean isOnGround() {
            return onGround;
        }
    }

    public static final class Packet13Data {
        private final double x;
        private final double y;
        private final double stance;
        private final double z;
        private final float yaw;
        private final float pitch;
        private final boolean onGround;

        public Packet13Data(double x, double y, double stance, double z, float yaw, float pitch, boolean onGround) {
            this.x = x;
            this.y = y;
            this.stance = stance;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getStance() {
            return stance;
        }

        public double getZ() {
            return z;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public boolean isOnGround() {
            return onGround;
        }
    }

    public static final class Packet20Data {
        private final int entityId;
        private final String playerName;
        private final int x;
        private final int y;
        private final int z;
        private final byte yaw;
        private final byte pitch;
        private final int heldItemId;

        public Packet20Data(int entityId, String playerName, int x, int y, int z, byte yaw, byte pitch, int heldItemId) {
            this.entityId = entityId;
            this.playerName = playerName;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.heldItemId = heldItemId;
        }

        public int getEntityId() {
            return entityId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public byte getYaw() {
            return yaw;
        }

        public byte getPitch() {
            return pitch;
        }

        public int getHeldItemId() {
            return heldItemId;
        }
    }

    public static final class Packet21Data {
        private final int entityId;
        private final int itemId;
        private final int itemCount;
        private final int itemData;
        private final int x;
        private final int y;
        private final int z;
        private final byte velocityX;
        private final byte velocityY;
        private final byte velocityZ;

        public Packet21Data(
                int entityId,
                int itemId,
                int itemCount,
                int itemData,
                int x,
                int y,
                int z,
                byte velocityX,
                byte velocityY,
                byte velocityZ
        ) {
            this.entityId = entityId;
            this.itemId = itemId;
            this.itemCount = itemCount;
            this.itemData = itemData;
            this.x = x;
            this.y = y;
            this.z = z;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.velocityZ = velocityZ;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getItemId() {
            return itemId;
        }

        public int getItemCount() {
            return itemCount;
        }

        public int getItemData() {
            return itemData;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public byte getVelocityX() {
            return velocityX;
        }

        public byte getVelocityY() {
            return velocityY;
        }

        public byte getVelocityZ() {
            return velocityZ;
        }
    }

    public static final class Packet22Data {
        private final int collectedEntityId;
        private final int collectorEntityId;

        public Packet22Data(int collectedEntityId, int collectorEntityId) {
            this.collectedEntityId = collectedEntityId;
            this.collectorEntityId = collectorEntityId;
        }

        public int getCollectedEntityId() {
            return collectedEntityId;
        }

        public int getCollectorEntityId() {
            return collectorEntityId;
        }
    }

    public static final class Packet23Data {
        private final int entityId;
        private final int type;
        private final int x;
        private final int y;
        private final int z;
        private final int throwerId;
        private final int velocityX;
        private final int velocityY;
        private final int velocityZ;

        public Packet23Data(
                int entityId,
                int type,
                int x,
                int y,
                int z,
                int throwerId,
                int velocityX,
                int velocityY,
                int velocityZ
        ) {
            this.entityId = entityId;
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
            this.throwerId = throwerId;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.velocityZ = velocityZ;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getType() {
            return type;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getThrowerId() {
            return throwerId;
        }

        public int getVelocityX() {
            return velocityX;
        }

        public int getVelocityY() {
            return velocityY;
        }

        public int getVelocityZ() {
            return velocityZ;
        }
    }

    public static final class Packet24Data {
        private final int entityId;
        private final int entityType;
        private final int x;
        private final int y;
        private final int z;
        private final byte yaw;
        private final byte pitch;
        private final List metadata;

        public Packet24Data(int entityId, int entityType, int x, int y, int z, byte yaw, byte pitch, List metadata) {
            this.entityId = entityId;
            this.entityType = entityType;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.metadata = metadata;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getEntityType() {
            return entityType;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public byte getYaw() {
            return yaw;
        }

        public byte getPitch() {
            return pitch;
        }

        public List getMetadata() {
            return metadata;
        }
    }

    public static final class Packet25Data {
        private final int entityId;
        private final String artName;
        private final int tileX;
        private final int tileY;
        private final int tileZ;
        private final int direction;

        public Packet25Data(int entityId, String artName, int tileX, int tileY, int tileZ, int direction) {
            this.entityId = entityId;
            this.artName = artName;
            this.tileX = tileX;
            this.tileY = tileY;
            this.tileZ = tileZ;
            this.direction = direction;
        }

        public int getEntityId() {
            return entityId;
        }

        public String getArtName() {
            return artName;
        }

        public int getTileX() {
            return tileX;
        }

        public int getTileY() {
            return tileY;
        }

        public int getTileZ() {
            return tileZ;
        }

        public int getDirection() {
            return direction;
        }
    }

    public static final class Packet28Data {
        private final int entityId;
        private final int velocityX;
        private final int velocityY;
        private final int velocityZ;

        public Packet28Data(int entityId, int velocityX, int velocityY, int velocityZ) {
            this.entityId = entityId;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.velocityZ = velocityZ;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getVelocityX() {
            return velocityX;
        }

        public int getVelocityY() {
            return velocityY;
        }

        public int getVelocityZ() {
            return velocityZ;
        }
    }

    public static final class Packet40Data {
        private final int entityId;
        private final List metadata;

        public Packet40Data(int entityId, List metadata) {
            this.entityId = entityId;
            this.metadata = metadata;
        }

        public int getEntityId() {
            return entityId;
        }

        public List getMetadata() {
            return metadata;
        }
    }

    public static final class Packet51ReadData {
        private final int x;
        private final int y;
        private final int z;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;
        private final int compressedLength;
        private final byte[] chunkData;

        public Packet51ReadData(
                int x,
                int y,
                int z,
                int sizeX,
                int sizeY,
                int sizeZ,
                int compressedLength,
                byte[] chunkData
        ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.sizeZ = sizeZ;
            this.compressedLength = compressedLength;
            this.chunkData = chunkData;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public int getSizeZ() {
            return sizeZ;
        }

        public int getCompressedLength() {
            return compressedLength;
        }

        public byte[] getChunkData() {
            return chunkData;
        }
    }

    public static final class Packet51WriteData {
        private final int x;
        private final int y;
        private final int z;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;
        private final int compressedLength;
        private final byte[] compressedData;

        public Packet51WriteData(
                int x,
                int y,
                int z,
                int sizeX,
                int sizeY,
                int sizeZ,
                int compressedLength,
                byte[] compressedData
        ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.sizeZ = sizeZ;
            this.compressedLength = compressedLength;
            this.compressedData = compressedData;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public int getSizeZ() {
            return sizeZ;
        }

        public int getCompressedLength() {
            return compressedLength;
        }

        public byte[] getCompressedData() {
            return compressedData;
        }
    }

    public static final class Packet52Data {
        private final int chunkX;
        private final int chunkZ;
        private final short[] coordinates;
        private final byte[] typeIds;
        private final byte[] metadata;
        private final int recordCount;

        public Packet52Data(int chunkX, int chunkZ, short[] coordinates, byte[] typeIds, byte[] metadata, int recordCount) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.coordinates = coordinates;
            this.typeIds = typeIds;
            this.metadata = metadata;
            this.recordCount = recordCount;
        }

        public int getChunkX() {
            return chunkX;
        }

        public int getChunkZ() {
            return chunkZ;
        }

        public short[] getCoordinates() {
            return coordinates;
        }

        public byte[] getTypeIds() {
            return typeIds;
        }

        public byte[] getMetadata() {
            return metadata;
        }

        public int getRecordCount() {
            return recordCount;
        }
    }

    public static final class Packet60Data {
        private final double x;
        private final double y;
        private final double z;
        private final float radius;
        private final Set explodedBlocks;

        public Packet60Data(double x, double y, double z, float radius, Set explodedBlocks) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.radius = radius;
            this.explodedBlocks = explodedBlocks;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getRadius() {
            return radius;
        }

        public Set getExplodedBlocks() {
            return explodedBlocks;
        }
    }

    public static final class Packet53Data {
        private final int x;
        private final int y;
        private final int z;
        private final int materialId;
        private final int dataValue;

        public Packet53Data(int x, int y, int z, int materialId, int dataValue) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.materialId = materialId;
            this.dataValue = dataValue;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getMaterialId() {
            return materialId;
        }

        public int getDataValue() {
            return dataValue;
        }
    }

    public static final class Packet54Data {
        private final int x;
        private final int y;
        private final int z;
        private final int instrument;
        private final int pitch;

        public Packet54Data(int x, int y, int z, int instrument, int pitch) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.instrument = instrument;
            this.pitch = pitch;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getInstrument() {
            return instrument;
        }

        public int getPitch() {
            return pitch;
        }
    }

    public static final class Packet105Data {
        private final int windowId;
        private final int property;
        private final int value;

        public Packet105Data(int windowId, int property, int value) {
            this.windowId = windowId;
            this.property = property;
            this.value = value;
        }

        public int getWindowId() {
            return windowId;
        }

        public int getProperty() {
            return property;
        }

        public int getValue() {
            return value;
        }
    }

    public static final class Packet106Data {
        private final int windowId;
        private final short actionNumber;
        private final boolean accepted;

        public Packet106Data(int windowId, short actionNumber, boolean accepted) {
            this.windowId = windowId;
            this.actionNumber = actionNumber;
            this.accepted = accepted;
        }

        public int getWindowId() {
            return windowId;
        }

        public short getActionNumber() {
            return actionNumber;
        }

        public boolean isAccepted() {
            return accepted;
        }
    }

    public static final class Packet100Data {
        private final int windowId;
        private final int windowType;
        private final String title;
        private final int slotCount;

        public Packet100Data(int windowId, int windowType, String title, int slotCount) {
            this.windowId = windowId;
            this.windowType = windowType;
            this.title = title;
            this.slotCount = slotCount;
        }

        public int getWindowId() {
            return windowId;
        }

        public int getWindowType() {
            return windowType;
        }

        public String getTitle() {
            return title;
        }

        public int getSlotCount() {
            return slotCount;
        }
    }

    public static final class Packet101Data {
        private final int windowId;

        public Packet101Data(int windowId) {
            this.windowId = windowId;
        }

        public int getWindowId() {
            return windowId;
        }
    }

    public static final class Packet103Data {
        private final int windowId;
        private final int slot;
        private final ItemSlotData itemSlot;

        public Packet103Data(int windowId, int slot, ItemSlotData itemSlot) {
            this.windowId = windowId;
            this.slot = slot;
            this.itemSlot = itemSlot;
        }

        public int getWindowId() {
            return windowId;
        }

        public int getSlot() {
            return slot;
        }

        public ItemSlotData getItemSlot() {
            return itemSlot;
        }
    }

    public static final class Packet102Data {
        private final int windowId;
        private final int slot;
        private final int button;
        private final short actionNumber;
        private final boolean shift;
        private final ItemSlotData itemSlot;

        public Packet102Data(int windowId, int slot, int button, short actionNumber, boolean shift, ItemSlotData itemSlot) {
            this.windowId = windowId;
            this.slot = slot;
            this.button = button;
            this.actionNumber = actionNumber;
            this.shift = shift;
            this.itemSlot = itemSlot;
        }

        public int getWindowId() {
            return windowId;
        }

        public int getSlot() {
            return slot;
        }

        public int getButton() {
            return button;
        }

        public short getActionNumber() {
            return actionNumber;
        }

        public boolean isShift() {
            return shift;
        }

        public ItemSlotData getItemSlot() {
            return itemSlot;
        }
    }

    public static final class Packet104Data {
        private final int windowId;
        private final ItemSlotData[] slots;

        public Packet104Data(int windowId, ItemSlotData[] slots) {
            this.windowId = windowId;
            this.slots = slots;
        }

        public int getWindowId() {
            return windowId;
        }

        public ItemSlotData[] getSlots() {
            return slots;
        }
    }

    public static final class ItemSlotData {
        private final short itemId;
        private final int count;
        private final short data;

        public ItemSlotData(short itemId, int count, short data) {
            this.itemId = itemId;
            this.count = count;
            this.data = data;
        }

        public short getItemId() {
            return itemId;
        }

        public int getCount() {
            return count;
        }

        public short getData() {
            return data;
        }
    }

    public static final class Packet27Data {
        private final float primaryX;
        private final float primaryY;
        private final float secondaryX;
        private final float secondaryY;
        private final boolean primaryFlag;
        private final boolean secondaryFlag;

        public Packet27Data(
                float primaryX,
                float primaryY,
                float secondaryX,
                float secondaryY,
                boolean primaryFlag,
                boolean secondaryFlag
        ) {
            this.primaryX = primaryX;
            this.primaryY = primaryY;
            this.secondaryX = secondaryX;
            this.secondaryY = secondaryY;
            this.primaryFlag = primaryFlag;
            this.secondaryFlag = secondaryFlag;
        }

        public float getPrimaryX() {
            return primaryX;
        }

        public float getPrimaryY() {
            return primaryY;
        }

        public float getSecondaryX() {
            return secondaryX;
        }

        public float getSecondaryY() {
            return secondaryY;
        }

        public boolean isPrimaryFlag() {
            return primaryFlag;
        }

        public boolean isSecondaryFlag() {
            return secondaryFlag;
        }
    }

    public static final class Packet71WeatherData {
        private final int entityId;
        private final int weatherType;
        private final int x;
        private final int y;
        private final int z;

        public Packet71WeatherData(int entityId, int weatherType, int x, int y, int z) {
            this.entityId = entityId;
            this.weatherType = weatherType;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getWeatherType() {
            return weatherType;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

    public static final class Packet131Data {
        private final short itemId;
        private final short damage;
        private final byte[] payload;

        public Packet131Data(short itemId, short damage, byte[] payload) {
            this.itemId = itemId;
            this.damage = damage;
            this.payload = payload;
        }

        public short getItemId() {
            return itemId;
        }

        public short getDamage() {
            return damage;
        }

        public byte[] getPayload() {
            return payload;
        }
    }
}
