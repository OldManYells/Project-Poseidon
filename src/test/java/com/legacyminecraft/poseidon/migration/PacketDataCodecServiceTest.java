package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;
import net.minecraft.server.ChunkPosition;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.Deflater;

public class PacketDataCodecServiceTest {
    private final PacketDataCodec service = PacketDataCodec.getInstance();

    @Test
    public void packet17RoundTripPreservesLegacyFieldOrder() throws IOException {
        PacketDataCodec.Packet17Data expected =
                new PacketDataCodec.Packet17Data(12345, -12, 777, -5, 9001);

        PacketDataCodec.Packet17Data actual = readPacket17(writePacket17(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getType(), actual.getType());
        Assert.assertEquals(expected.getPrimaryValue(), actual.getPrimaryValue());
        Assert.assertEquals(expected.getAuxiliaryByteValue(), actual.getAuxiliaryByteValue());
        Assert.assertEquals(expected.getSecondaryValue(), actual.getSecondaryValue());
    }

    @Test
    public void packet61RoundTripPreservesLegacyFieldOrder() throws IOException {
        PacketDataCodec.Packet61Data expected =
                new PacketDataCodec.Packet61Data(77, 1024, -7, 2048, 4096);

        PacketDataCodec.Packet61Data actual = readPacket61(writePacket61(expected));

        Assert.assertEquals(expected.getPrimaryId(), actual.getPrimaryId());
        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getValue(), actual.getValue());
    }

    @Test
    public void packet70BedRoundTripPreservesEventByte() throws IOException {
        PacketDataCodec.Packet70BedData expected = new PacketDataCodec.Packet70BedData(-1);

        PacketDataCodec.Packet70BedData actual = readPacket70Bed(writePacket70Bed(expected));

        Assert.assertEquals(expected.getEventId(), actual.getEventId());
    }

    @Test
    public void packet27RoundTripPreservesFloatAndBooleanPayload() throws IOException {
        PacketDataCodec.Packet27Data expected =
                new PacketDataCodec.Packet27Data(1.5F, -2.25F, 7.0F, 9.5F, true, false);

        PacketDataCodec.Packet27Data actual = readPacket27(writePacket27(expected));

        Assert.assertEquals(expected.getPrimaryX(), actual.getPrimaryX(), 0.0F);
        Assert.assertEquals(expected.getPrimaryY(), actual.getPrimaryY(), 0.0F);
        Assert.assertEquals(expected.getSecondaryX(), actual.getSecondaryX(), 0.0F);
        Assert.assertEquals(expected.getSecondaryY(), actual.getSecondaryY(), 0.0F);
        Assert.assertEquals(expected.isPrimaryFlag(), actual.isPrimaryFlag());
        Assert.assertEquals(expected.isSecondaryFlag(), actual.isSecondaryFlag());
    }

    @Test
    public void packet71WeatherRoundTripPreservesCoordinatesAndType() throws IOException {
        PacketDataCodec.Packet71WeatherData expected =
                new PacketDataCodec.Packet71WeatherData(900, 1, -320, 64, 450);

        PacketDataCodec.Packet71WeatherData actual = readPacket71Weather(writePacket71Weather(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getWeatherType(), actual.getWeatherType());
        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
    }

    @Test
    public void packet53RoundTripPreservesUnsignedByteFields() throws IOException {
        PacketDataCodec.Packet53Data expected =
                new PacketDataCodec.Packet53Data(100, 255, -50, 200, 128);

        PacketDataCodec.Packet53Data actual = readPacket53(writePacket53(expected));

        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getMaterialId(), actual.getMaterialId());
        Assert.assertEquals(expected.getDataValue(), actual.getDataValue());
    }

    @Test
    public void packet54RoundTripPreservesNoteData() throws IOException {
        PacketDataCodec.Packet54Data expected =
                new PacketDataCodec.Packet54Data(15, 64, -20, 4, 24);

        PacketDataCodec.Packet54Data actual = readPacket54(writePacket54(expected));

        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getInstrument(), actual.getInstrument());
        Assert.assertEquals(expected.getPitch(), actual.getPitch());
    }

    @Test
    public void packet105RoundTripPreservesSignedWindowIdAndShorts() throws IOException {
        PacketDataCodec.Packet105Data expected =
                new PacketDataCodec.Packet105Data(-1, 32000, 1234);

        PacketDataCodec.Packet105Data actual = readPacket105(writePacket105(expected));

        Assert.assertEquals(expected.getWindowId(), actual.getWindowId());
        Assert.assertEquals(expected.getProperty(), actual.getProperty());
        Assert.assertEquals(expected.getValue(), actual.getValue());
    }

    @Test
    public void packet106RoundTripPreservesAcceptanceFlag() throws IOException {
        PacketDataCodec.Packet106Data expected =
                new PacketDataCodec.Packet106Data(2, (short) 42, true);

        PacketDataCodec.Packet106Data actual = readPacket106(writePacket106(expected));

        Assert.assertEquals(expected.getWindowId(), actual.getWindowId());
        Assert.assertEquals(expected.getActionNumber(), actual.getActionNumber());
        Assert.assertEquals(expected.isAccepted(), actual.isAccepted());
    }

    @Test
    public void packet29RoundTripPreservesEntityId() throws IOException {
        PacketDataCodec.Packet29Data expected = new PacketDataCodec.Packet29Data(123456);

        PacketDataCodec.Packet29Data actual = readPacket29(writePacket29(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
    }

    @Test
    public void packet38RoundTripPreservesSignedStatusByte() throws IOException {
        PacketDataCodec.Packet38Data expected = new PacketDataCodec.Packet38Data(77, (byte) -12);

        PacketDataCodec.Packet38Data actual = readPacket38(writePacket38(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getStatusByte(), actual.getStatusByte());
    }

    @Test
    public void packet39RoundTripPreservesAttachMapping() throws IOException {
        PacketDataCodec.Packet39Data expected = new PacketDataCodec.Packet39Data(10, -1);

        PacketDataCodec.Packet39Data actual = readPacket39(writePacket39(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getVehicleId(), actual.getVehicleId());
    }

    @Test
    public void packet50RoundTripPreservesPreChunkMode() throws IOException {
        PacketDataCodec.Packet50Data expected = new PacketDataCodec.Packet50Data(5, -9, true);

        PacketDataCodec.Packet50Data actual = readPacket50(writePacket50(expected));

        Assert.assertEquals(expected.getChunkX(), actual.getChunkX());
        Assert.assertEquals(expected.getChunkZ(), actual.getChunkZ());
        Assert.assertEquals(expected.isMode(), actual.isMode());
    }

    @Test
    public void packet6RoundTripPreservesSpawnCoordinates() throws IOException {
        PacketDataCodec.Packet6Data expected = new PacketDataCodec.Packet6Data(1, 64, 128);

        PacketDataCodec.Packet6Data actual = readPacket6(writePacket6(expected));

        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
    }

    @Test
    public void packet8RoundTripPreservesShortHealthField() throws IOException {
        PacketDataCodec.Packet8Data expected = new PacketDataCodec.Packet8Data(300);

        PacketDataCodec.Packet8Data actual = readPacket8(writePacket8(expected));

        Assert.assertEquals(expected.getHealth(), actual.getHealth());
    }

    @Test
    public void packet200RoundTripPreservesSignedAmountByte() throws IOException {
        PacketDataCodec.Packet200Data expected = new PacketDataCodec.Packet200Data(42, -5);

        PacketDataCodec.Packet200Data actual = readPacket200(writePacket200(expected));

        Assert.assertEquals(expected.getStatisticId(), actual.getStatisticId());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test
    public void packet100RoundTripPreservesWindowMetadata() throws IOException {
        PacketDataCodec.Packet100Data expected =
                new PacketDataCodec.Packet100Data(1, 2, "Workbench", 9);

        PacketDataCodec.Packet100Data actual = readPacket100(writePacket100(expected));

        Assert.assertEquals(expected.getWindowId(), actual.getWindowId());
        Assert.assertEquals(expected.getWindowType(), actual.getWindowType());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getSlotCount(), actual.getSlotCount());
        Assert.assertEquals(12, service.packet100Length("Workbench"));
    }

    @Test
    public void packet101RoundTripPreservesWindowId() throws IOException {
        PacketDataCodec.Packet101Data expected = new PacketDataCodec.Packet101Data(-1);

        PacketDataCodec.Packet101Data actual = readPacket101(writePacket101(expected));

        Assert.assertEquals(expected.getWindowId(), actual.getWindowId());
    }

    @Test
    public void packet103RoundTripPreservesOptionalItemSlotPayload() throws IOException {
        PacketDataCodec.Packet103Data withItem = new PacketDataCodec.Packet103Data(
                1,
                5,
                new PacketDataCodec.ItemSlotData((short) 3, 64, (short) 7)
        );
        PacketDataCodec.Packet103Data withItemDecoded = readPacket103(writePacket103(withItem));

        Assert.assertEquals(withItem.getWindowId(), withItemDecoded.getWindowId());
        Assert.assertEquals(withItem.getSlot(), withItemDecoded.getSlot());
        Assert.assertNotNull(withItemDecoded.getItemSlot());
        Assert.assertEquals(withItem.getItemSlot().getItemId(), withItemDecoded.getItemSlot().getItemId());
        Assert.assertEquals(withItem.getItemSlot().getCount(), withItemDecoded.getItemSlot().getCount());
        Assert.assertEquals(withItem.getItemSlot().getData(), withItemDecoded.getItemSlot().getData());

        PacketDataCodec.Packet103Data noItem = new PacketDataCodec.Packet103Data(2, 9, null);
        PacketDataCodec.Packet103Data noItemDecoded = readPacket103(writePacket103(noItem));
        Assert.assertNull(noItemDecoded.getItemSlot());
    }

    @Test
    public void packet102RoundTripPreservesClickPayloadAndOptionalItem() throws IOException {
        PacketDataCodec.Packet102Data withItem = new PacketDataCodec.Packet102Data(
                1,
                15,
                0,
                (short) 88,
                true,
                new PacketDataCodec.ItemSlotData((short) 4, 3, (short) 2)
        );
        PacketDataCodec.Packet102Data withItemDecoded = readPacket102(writePacket102(withItem));

        Assert.assertEquals(withItem.getWindowId(), withItemDecoded.getWindowId());
        Assert.assertEquals(withItem.getSlot(), withItemDecoded.getSlot());
        Assert.assertEquals(withItem.getButton(), withItemDecoded.getButton());
        Assert.assertEquals(withItem.getActionNumber(), withItemDecoded.getActionNumber());
        Assert.assertEquals(withItem.isShift(), withItemDecoded.isShift());
        Assert.assertNotNull(withItemDecoded.getItemSlot());
        Assert.assertEquals(withItem.getItemSlot().getItemId(), withItemDecoded.getItemSlot().getItemId());
        Assert.assertEquals(withItem.getItemSlot().getCount(), withItemDecoded.getItemSlot().getCount());
        Assert.assertEquals(withItem.getItemSlot().getData(), withItemDecoded.getItemSlot().getData());

        PacketDataCodec.Packet102Data noItem = new PacketDataCodec.Packet102Data(2, 3, 1, (short) 0, false, null);
        PacketDataCodec.Packet102Data noItemDecoded = readPacket102(writePacket102(noItem));
        Assert.assertNull(noItemDecoded.getItemSlot());
    }

    @Test
    public void packet104RoundTripPreservesSlotArrayAndLengthFormula() throws IOException {
        PacketDataCodec.ItemSlotData[] slots = new PacketDataCodec.ItemSlotData[]{
                new PacketDataCodec.ItemSlotData((short) 1, 1, (short) 0),
                null,
                new PacketDataCodec.ItemSlotData((short) 5, 64, (short) 9)
        };
        PacketDataCodec.Packet104Data expected = new PacketDataCodec.Packet104Data(3, slots);

        PacketDataCodec.Packet104Data actual = readPacket104(writePacket104(expected));

        Assert.assertEquals(expected.getWindowId(), actual.getWindowId());
        Assert.assertEquals(3, actual.getSlots().length);
        Assert.assertNotNull(actual.getSlots()[0]);
        Assert.assertNull(actual.getSlots()[1]);
        Assert.assertNotNull(actual.getSlots()[2]);
        Assert.assertEquals((short) 5, actual.getSlots()[2].getItemId());
        Assert.assertEquals(18, service.packet104Length(3));
    }

    @Test
    public void packet4RoundTripPreservesWorldTime() throws IOException {
        PacketDataCodec.Packet4Data expected = new PacketDataCodec.Packet4Data(123456789L);

        PacketDataCodec.Packet4Data actual = readPacket4(writePacket4(expected));

        Assert.assertEquals(expected.getWorldTime(), actual.getWorldTime());
    }

    @Test
    public void packet5RoundTripPreservesEquipmentTuple() throws IOException {
        PacketDataCodec.Packet5Data expected = new PacketDataCodec.Packet5Data(5, 1, 276, 10);

        PacketDataCodec.Packet5Data actual = readPacket5(writePacket5(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getSlot(), actual.getSlot());
        Assert.assertEquals(expected.getItemId(), actual.getItemId());
        Assert.assertEquals(expected.getDataValue(), actual.getDataValue());
    }

    @Test
    public void packet7RoundTripPreservesUseEntityFields() throws IOException {
        PacketDataCodec.Packet7Data expected = new PacketDataCodec.Packet7Data(1, 2, -1);

        PacketDataCodec.Packet7Data actual = readPacket7(writePacket7(expected));

        Assert.assertEquals(expected.getUserEntityId(), actual.getUserEntityId());
        Assert.assertEquals(expected.getTargetEntityId(), actual.getTargetEntityId());
        Assert.assertEquals(expected.getInteractionType(), actual.getInteractionType());
    }

    @Test
    public void packet9RoundTripPreservesDimensionByte() throws IOException {
        PacketDataCodec.Packet9Data expected = new PacketDataCodec.Packet9Data((byte) -1);

        PacketDataCodec.Packet9Data actual = readPacket9(writePacket9(expected));

        Assert.assertEquals(expected.getDimension(), actual.getDimension());
    }

    @Test
    public void packet16RoundTripPreservesSelectedHotbarSlot() throws IOException {
        PacketDataCodec.Packet16Data expected = new PacketDataCodec.Packet16Data(8);

        PacketDataCodec.Packet16Data actual = readPacket16(writePacket16(expected));

        Assert.assertEquals(expected.getItemInHandIndex(), actual.getItemInHandIndex());
    }

    @Test
    public void packet18RoundTripPreservesArmAnimationData() throws IOException {
        PacketDataCodec.Packet18Data expected = new PacketDataCodec.Packet18Data(123, 1);

        PacketDataCodec.Packet18Data actual = readPacket18(writePacket18(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getAnimation(), actual.getAnimation());
    }

    @Test
    public void packet19RoundTripPreservesEntityActionData() throws IOException {
        PacketDataCodec.Packet19Data expected = new PacketDataCodec.Packet19Data(456, 2);

        PacketDataCodec.Packet19Data actual = readPacket19(writePacket19(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getAnimation(), actual.getAnimation());
    }

    @Test
    public void packet20RoundTripPreservesNamedEntitySpawnPayload() throws IOException {
        PacketDataCodec.Packet20Data expected =
                new PacketDataCodec.Packet20Data(123, "PlayerOne", 100, 64, -20, (byte) 90, (byte) 45, 276);

        PacketDataCodec.Packet20Data actual = readPacket20(writePacket20(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getPlayerName(), actual.getPlayerName());
        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getYaw(), actual.getYaw());
        Assert.assertEquals(expected.getPitch(), actual.getPitch());
        Assert.assertEquals(expected.getHeldItemId(), actual.getHeldItemId());
        Assert.assertEquals(28, service.packet20Length());
    }

    @Test
    public void packet21RoundTripPreservesPickupSpawnPayload() throws IOException {
        PacketDataCodec.Packet21Data expected =
                new PacketDataCodec.Packet21Data(11, 264, 3, 0, 300, 65, -44, (byte) 1, (byte) -2, (byte) 3);

        PacketDataCodec.Packet21Data actual = readPacket21(writePacket21(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getItemId(), actual.getItemId());
        Assert.assertEquals(expected.getItemCount(), actual.getItemCount());
        Assert.assertEquals(expected.getItemData(), actual.getItemData());
        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getVelocityX(), actual.getVelocityX());
        Assert.assertEquals(expected.getVelocityY(), actual.getVelocityY());
        Assert.assertEquals(expected.getVelocityZ(), actual.getVelocityZ());
        Assert.assertEquals(24, service.packet21Length());
    }

    @Test
    public void packet24RoundTripPreservesMobSpawnFieldsAndMetadataSentinel() throws IOException {
        PacketDataCodec.Packet24Data expected =
                new PacketDataCodec.Packet24Data(22, 54, 10, 64, -30, (byte) 1, (byte) 2, null);

        PacketDataCodec.Packet24Data actual = readPacket24(writePacket24(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getEntityType(), actual.getEntityType());
        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getYaw(), actual.getYaw());
        Assert.assertEquals(expected.getPitch(), actual.getPitch());
        Assert.assertNull(actual.getMetadata());
        Assert.assertEquals(20, service.packet24Length());
    }

    @Test
    public void packet40RoundTripPreservesMetadataContainer() throws IOException {
        PacketDataCodec.Packet40Data expected = new PacketDataCodec.Packet40Data(99, null);

        PacketDataCodec.Packet40Data actual = readPacket40(writePacket40(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertNull(actual.getMetadata());
        Assert.assertEquals(5, service.packet40Length());
    }

    @Test
    public void packet51RoundTripInflatesMapChunkPayload() throws IOException {
        int sizeX = 2;
        int sizeY = 2;
        int sizeZ = 2;
        byte[] rawChunkData = new byte[sizeX * sizeY * sizeZ * 5 / 2];
        for (int i = 0; i < rawChunkData.length; ++i) {
            rawChunkData[i] = (byte) (i * 3);
        }
        byte[] compressed = compress(rawChunkData);
        PacketDataCodec.Packet51WriteData expected = new PacketDataCodec.Packet51WriteData(
                16,
                32,
                -48,
                sizeX,
                sizeY,
                sizeZ,
                compressed.length,
                compressed
        );

        PacketDataCodec.Packet51ReadData actual = readPacket51(writePacket51(expected));

        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getSizeX(), actual.getSizeX());
        Assert.assertEquals(expected.getSizeY(), actual.getSizeY());
        Assert.assertEquals(expected.getSizeZ(), actual.getSizeZ());
        Assert.assertEquals(expected.getCompressedLength(), actual.getCompressedLength());
        Assert.assertTrue(Arrays.equals(rawChunkData, actual.getChunkData()));
        Assert.assertEquals(17 + compressed.length, service.packet51Length(compressed.length));
    }

    @Test
    public void packet52RoundTripPreservesMultiBlockArrays() throws IOException {
        short[] coordinates = new short[]{(short) 0x1234, (short) 0x0F0F};
        byte[] typeIds = new byte[]{1, 54};
        byte[] metadata = new byte[]{0, 9};
        PacketDataCodec.Packet52Data expected =
                new PacketDataCodec.Packet52Data(7, -3, coordinates, typeIds, metadata, coordinates.length);

        PacketDataCodec.Packet52Data actual = readPacket52(writePacket52(expected));

        Assert.assertEquals(expected.getChunkX(), actual.getChunkX());
        Assert.assertEquals(expected.getChunkZ(), actual.getChunkZ());
        Assert.assertEquals(expected.getRecordCount(), actual.getRecordCount());
        Assert.assertTrue(Arrays.equals(expected.getCoordinates(), actual.getCoordinates()));
        Assert.assertTrue(Arrays.equals(expected.getTypeIds(), actual.getTypeIds()));
        Assert.assertTrue(Arrays.equals(expected.getMetadata(), actual.getMetadata()));
        Assert.assertEquals(18, service.packet52Length(2));
    }

    @Test
    public void packet60RoundTripPreservesRelativeExplosionBlocks() throws IOException {
        Set explodedBlocks = new HashSet();
        explodedBlocks.add(new ChunkPosition(10, 20, 30));
        explodedBlocks.add(new ChunkPosition(11, 21, 29));
        PacketDataCodec.Packet60Data expected =
                new PacketDataCodec.Packet60Data(10.5D, 20.5D, 30.5D, 4.0F, explodedBlocks);

        PacketDataCodec.Packet60Data actual = readPacket60(writePacket60(expected));

        Assert.assertEquals(expected.getX(), actual.getX(), 0.0D);
        Assert.assertEquals(expected.getY(), actual.getY(), 0.0D);
        Assert.assertEquals(expected.getZ(), actual.getZ(), 0.0D);
        Assert.assertEquals(expected.getRadius(), actual.getRadius(), 0.0F);
        Assert.assertEquals(expected.getExplodedBlocks().size(), actual.getExplodedBlocks().size());
        Assert.assertTrue(actual.getExplodedBlocks().contains(new ChunkPosition(10, 20, 30)));
        Assert.assertTrue(actual.getExplodedBlocks().contains(new ChunkPosition(11, 21, 29)));
        Assert.assertEquals(38, service.packet60Length(2));
    }

    @Test
    public void packet25LengthIncludesArtNameLength() {
        Assert.assertEquals(31, service.packet25Length("KebabXX"));
    }

    @Test
    public void packet28RoundTripPreservesVelocityTripletAndLength() throws IOException {
        PacketDataCodec.Packet28Data expected = new PacketDataCodec.Packet28Data(101, 1200, -800, 0);

        PacketDataCodec.Packet28Data actual = readPacket28(writePacket28(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getVelocityX(), actual.getVelocityX());
        Assert.assertEquals(expected.getVelocityY(), actual.getVelocityY());
        Assert.assertEquals(expected.getVelocityZ(), actual.getVelocityZ());
        Assert.assertEquals(10, service.packet28Length());
    }

    @Test
    public void packet28FromMotionClampsToLegacyVelocityBounds() {
        PacketDataCodec.Packet28Data actual = service.packet28FromMotion(9, 4.5D, -7.0D, 0.25D);

        Assert.assertEquals(9, actual.getEntityId());
        Assert.assertEquals(31200, actual.getVelocityX());
        Assert.assertEquals(-31200, actual.getVelocityY());
        Assert.assertEquals(2000, actual.getVelocityZ());
    }

    @Test
    public void packetConstantLengthContractsRemainCanonicalized() {
        Assert.assertEquals(8, service.packet4Length());
        Assert.assertEquals(8, service.packet5Length());
        Assert.assertEquals(12, service.packet6Length());
        Assert.assertEquals(9, service.packet7Length());
        Assert.assertEquals(2, service.packet8Length());
        Assert.assertEquals(1, service.packet9Length());
        Assert.assertEquals(1, service.packet10Length());
        Assert.assertEquals(33, service.packet11Length());
        Assert.assertEquals(9, service.packet12Length());
        Assert.assertEquals(41, service.packet13Length());
        Assert.assertEquals(11, service.packet14Length());
        Assert.assertEquals(15, service.packet15Length());
        Assert.assertEquals(2, service.packet16Length());
        Assert.assertEquals(14, service.packet17Length());
        Assert.assertEquals(5, service.packet18Length());
        Assert.assertEquals(5, service.packet19Length());
        Assert.assertEquals(8, service.packet22Length());
        Assert.assertEquals(18, service.packet27Length());
        Assert.assertEquals(4, service.packet29Length());
        Assert.assertEquals(4, service.packet30Length());
        Assert.assertEquals(7, service.packet31Length());
        Assert.assertEquals(6, service.packet32Length());
        Assert.assertEquals(9, service.packet33Length());
        Assert.assertEquals(34, service.packet34Length());
        Assert.assertEquals(5, service.packet38Length());
        Assert.assertEquals(8, service.packet39Length());
        Assert.assertEquals(9, service.packet50Length());
        Assert.assertEquals(11, service.packet53Length());
        Assert.assertEquals(12, service.packet54Length());
        Assert.assertEquals(20, service.packet61Length());
        Assert.assertEquals(1, service.packet70Length());
        Assert.assertEquals(17, service.packet71Length());
        Assert.assertEquals(1, service.packet101Length());
        Assert.assertEquals(11, service.packet102Length());
        Assert.assertEquals(8, service.packet103Length());
        Assert.assertEquals(5, service.packet105Length());
        Assert.assertEquals(4, service.packet106Length());
        Assert.assertEquals(6, service.packet200Length());
    }

    @Test
    public void packet14RoundTripPreservesDigCoordinatesAndFace() throws IOException {
        PacketDataCodec.Packet14Data expected = new PacketDataCodec.Packet14Data(2, 10, 64, -20, 1);

        PacketDataCodec.Packet14Data actual = readPacket14(writePacket14(expected));

        Assert.assertEquals(expected.getStatus(), actual.getStatus());
        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getFace(), actual.getFace());
    }

    @Test
    public void packet15RoundTripPreservesOptionalPlacedItem() throws IOException {
        PacketDataCodec.Packet15Data withItem = new PacketDataCodec.Packet15Data(
                1,
                2,
                3,
                4,
                new PacketDataCodec.ItemSlotData((short) 5, 1, (short) 0)
        );
        PacketDataCodec.Packet15Data withItemDecoded = readPacket15(writePacket15(withItem));

        Assert.assertEquals(withItem.getX(), withItemDecoded.getX());
        Assert.assertEquals(withItem.getY(), withItemDecoded.getY());
        Assert.assertEquals(withItem.getZ(), withItemDecoded.getZ());
        Assert.assertEquals(withItem.getFace(), withItemDecoded.getFace());
        Assert.assertNotNull(withItemDecoded.getItemSlot());
        Assert.assertEquals(withItem.getItemSlot().getItemId(), withItemDecoded.getItemSlot().getItemId());

        PacketDataCodec.Packet15Data noItem = new PacketDataCodec.Packet15Data(1, 2, 3, 4, null);
        PacketDataCodec.Packet15Data noItemDecoded = readPacket15(writePacket15(noItem));
        Assert.assertNull(noItemDecoded.getItemSlot());
    }

    @Test
    public void packet130RoundTripPreservesSignLinesAndLength() throws IOException {
        String[] lines = new String[]{"Line1", "Line2", "A", ""};
        PacketDataCodec.Packet130Data expected = new PacketDataCodec.Packet130Data(7, 64, 9, lines);

        PacketDataCodec.Packet130Data actual = readPacket130(writePacket130(expected));

        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals("Line1", actual.getLines()[0]);
        Assert.assertEquals("Line2", actual.getLines()[1]);
        Assert.assertEquals(11, service.packet130Length(lines));
    }

    @Test
    public void packet1RoundTripPreservesLoginFields() throws IOException {
        PacketDataCodec.Packet1Data expected =
                new PacketDataCodec.Packet1Data(14, "Steve", 123L, (byte) 0);

        PacketDataCodec.Packet1Data actual = readPacket1(writePacket1(expected));

        Assert.assertEquals(expected.getProtocolVersion(), actual.getProtocolVersion());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getMapSeed(), actual.getMapSeed());
        Assert.assertEquals(expected.getDimension(), actual.getDimension());
        Assert.assertEquals(18, service.packet1Length("Steve"));
    }

    @Test
    public void packet2RoundTripPreservesHandshakeAndLengthFormula() throws IOException {
        PacketDataCodec.Packet2Data expected = new PacketDataCodec.Packet2Data("token");

        PacketDataCodec.Packet2Data actual = readPacket2(writePacket2(expected));

        Assert.assertEquals(expected.getHandshake(), actual.getHandshake());
        Assert.assertEquals(13, service.packet2Length("token"));
    }

    @Test
    public void packet3RoundTripPreservesChatMessage() throws IOException {
        PacketDataCodec.Packet3Data expected = new PacketDataCodec.Packet3Data("hello world");

        PacketDataCodec.Packet3Data actual = readPacket3(writePacket3(expected));

        Assert.assertEquals(expected.getMessage(), actual.getMessage());
        Assert.assertEquals(11, service.packet3Length("hello world"));
    }

    @Test
    public void packet255RoundTripPreservesReasonMessage() throws IOException {
        PacketDataCodec.Packet255Data expected = new PacketDataCodec.Packet255Data("Goodbye");

        PacketDataCodec.Packet255Data actual = readPacket255(writePacket255(expected));

        Assert.assertEquals(expected.getReason(), actual.getReason());
        Assert.assertEquals(7, service.packet255Length("Goodbye"));
    }

    @Test
    public void packet30RoundTripPreservesEntityId() throws IOException {
        PacketDataCodec.Packet30Data expected = new PacketDataCodec.Packet30Data(123);

        PacketDataCodec.Packet30Data actual = readPacket30(writePacket30(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
    }

    @Test
    public void packet31RoundTripPreservesRelativeMovementDeltas() throws IOException {
        PacketDataCodec.Packet31Data expected =
                new PacketDataCodec.Packet31Data(1, (byte) 2, (byte) -3, (byte) 4);

        PacketDataCodec.Packet31Data actual = readPacket31(writePacket31(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getDeltaX(), actual.getDeltaX());
        Assert.assertEquals(expected.getDeltaY(), actual.getDeltaY());
        Assert.assertEquals(expected.getDeltaZ(), actual.getDeltaZ());
    }

    @Test
    public void packet32RoundTripPreservesLookAngles() throws IOException {
        PacketDataCodec.Packet32Data expected =
                new PacketDataCodec.Packet32Data(9, (byte) 90, (byte) 45);

        PacketDataCodec.Packet32Data actual = readPacket32(writePacket32(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getYaw(), actual.getYaw());
        Assert.assertEquals(expected.getPitch(), actual.getPitch());
    }

    @Test
    public void packet33RoundTripPreservesMoveLookPayload() throws IOException {
        PacketDataCodec.Packet33Data expected =
                new PacketDataCodec.Packet33Data(7, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);

        PacketDataCodec.Packet33Data actual = readPacket33(writePacket33(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getDeltaX(), actual.getDeltaX());
        Assert.assertEquals(expected.getDeltaY(), actual.getDeltaY());
        Assert.assertEquals(expected.getDeltaZ(), actual.getDeltaZ());
        Assert.assertEquals(expected.getYaw(), actual.getYaw());
        Assert.assertEquals(expected.getPitch(), actual.getPitch());
    }

    @Test
    public void packet34RoundTripPreservesTeleportPayload() throws IOException {
        PacketDataCodec.Packet34Data expected =
                new PacketDataCodec.Packet34Data(5, 100, 64, -20, (byte) 10, (byte) 20);

        PacketDataCodec.Packet34Data actual = readPacket34(writePacket34(expected));

        Assert.assertEquals(expected.getEntityId(), actual.getEntityId());
        Assert.assertEquals(expected.getX(), actual.getX());
        Assert.assertEquals(expected.getY(), actual.getY());
        Assert.assertEquals(expected.getZ(), actual.getZ());
        Assert.assertEquals(expected.getYaw(), actual.getYaw());
        Assert.assertEquals(expected.getPitch(), actual.getPitch());
    }

    @Test
    public void packet10RoundTripPreservesGroundFlag() throws IOException {
        PacketDataCodec.Packet10Data expected = new PacketDataCodec.Packet10Data(true);

        PacketDataCodec.Packet10Data actual = readPacket10(writePacket10(expected));

        Assert.assertEquals(expected.isOnGround(), actual.isOnGround());
    }

    @Test
    public void packet11RoundTripPreservesPositionPayload() throws IOException {
        PacketDataCodec.Packet11Data expected =
                new PacketDataCodec.Packet11Data(1.0D, 2.0D, 3.0D, 4.0D, true);

        PacketDataCodec.Packet11Data actual = readPacket11(writePacket11(expected));

        Assert.assertEquals(expected.getX(), actual.getX(), 0.0D);
        Assert.assertEquals(expected.getY(), actual.getY(), 0.0D);
        Assert.assertEquals(expected.getStance(), actual.getStance(), 0.0D);
        Assert.assertEquals(expected.getZ(), actual.getZ(), 0.0D);
        Assert.assertEquals(expected.isOnGround(), actual.isOnGround());
    }

    @Test
    public void packet12RoundTripPreservesLookPayload() throws IOException {
        PacketDataCodec.Packet12Data expected =
                new PacketDataCodec.Packet12Data(90.0F, 45.0F, false);

        PacketDataCodec.Packet12Data actual = readPacket12(writePacket12(expected));

        Assert.assertEquals(expected.getYaw(), actual.getYaw(), 0.0F);
        Assert.assertEquals(expected.getPitch(), actual.getPitch(), 0.0F);
        Assert.assertEquals(expected.isOnGround(), actual.isOnGround());
    }

    @Test
    public void packet13RoundTripPreservesLookMovePayload() throws IOException {
        PacketDataCodec.Packet13Data expected =
                new PacketDataCodec.Packet13Data(5.0D, 6.0D, 7.0D, 8.0D, 10.0F, 20.0F, true);

        PacketDataCodec.Packet13Data actual = readPacket13(writePacket13(expected));

        Assert.assertEquals(expected.getX(), actual.getX(), 0.0D);
        Assert.assertEquals(expected.getY(), actual.getY(), 0.0D);
        Assert.assertEquals(expected.getStance(), actual.getStance(), 0.0D);
        Assert.assertEquals(expected.getZ(), actual.getZ(), 0.0D);
        Assert.assertEquals(expected.getYaw(), actual.getYaw(), 0.0F);
        Assert.assertEquals(expected.getPitch(), actual.getPitch(), 0.0F);
        Assert.assertEquals(expected.isOnGround(), actual.isOnGround());
    }

    @Test
    public void packet131RoundTripPreservesPayloadAndLength() throws IOException {
        byte[] payload = new byte[]{1, 2, 3, 4, 5};
        PacketDataCodec.Packet131Data expected =
                new PacketDataCodec.Packet131Data((short) 17, (short) 2, payload);

        PacketDataCodec.Packet131Data actual = readPacket131(writePacket131(expected));

        Assert.assertEquals(expected.getItemId(), actual.getItemId());
        Assert.assertEquals(expected.getDamage(), actual.getDamage());
        Assert.assertTrue(Arrays.equals(expected.getPayload(), actual.getPayload()));
        Assert.assertEquals(9, service.packet131Length(payload));
    }

    private byte[] writePacket17(PacketDataCodec.Packet17Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket17(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet17Data readPacket17(byte[] data) throws IOException {
        return service.readPacket17(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket61(PacketDataCodec.Packet61Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket61(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet61Data readPacket61(byte[] data) throws IOException {
        return service.readPacket61(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket70Bed(PacketDataCodec.Packet70BedData data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket70Bed(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet70BedData readPacket70Bed(byte[] data) throws IOException {
        return service.readPacket70Bed(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket27(PacketDataCodec.Packet27Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket27(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet27Data readPacket27(byte[] data) throws IOException {
        return service.readPacket27(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket71Weather(PacketDataCodec.Packet71WeatherData data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket71Weather(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet71WeatherData readPacket71Weather(byte[] data) throws IOException {
        return service.readPacket71Weather(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket53(PacketDataCodec.Packet53Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket53(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet53Data readPacket53(byte[] data) throws IOException {
        return service.readPacket53(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket54(PacketDataCodec.Packet54Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket54(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet54Data readPacket54(byte[] data) throws IOException {
        return service.readPacket54(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket105(PacketDataCodec.Packet105Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket105(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet105Data readPacket105(byte[] data) throws IOException {
        return service.readPacket105(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket106(PacketDataCodec.Packet106Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket106(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet106Data readPacket106(byte[] data) throws IOException {
        return service.readPacket106(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket29(PacketDataCodec.Packet29Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket29(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet29Data readPacket29(byte[] data) throws IOException {
        return service.readPacket29(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket38(PacketDataCodec.Packet38Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket38(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet38Data readPacket38(byte[] data) throws IOException {
        return service.readPacket38(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket39(PacketDataCodec.Packet39Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket39(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet39Data readPacket39(byte[] data) throws IOException {
        return service.readPacket39(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket50(PacketDataCodec.Packet50Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket50(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet50Data readPacket50(byte[] data) throws IOException {
        return service.readPacket50(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket6(PacketDataCodec.Packet6Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket6(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet6Data readPacket6(byte[] data) throws IOException {
        return service.readPacket6(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket8(PacketDataCodec.Packet8Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket8(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet8Data readPacket8(byte[] data) throws IOException {
        return service.readPacket8(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket200(PacketDataCodec.Packet200Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket200(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet200Data readPacket200(byte[] data) throws IOException {
        return service.readPacket200(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket100(PacketDataCodec.Packet100Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket100(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet100Data readPacket100(byte[] data) throws IOException {
        return service.readPacket100(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket101(PacketDataCodec.Packet101Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket101(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet101Data readPacket101(byte[] data) throws IOException {
        return service.readPacket101(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket103(PacketDataCodec.Packet103Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket103(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet103Data readPacket103(byte[] data) throws IOException {
        return service.readPacket103(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket102(PacketDataCodec.Packet102Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket102(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet102Data readPacket102(byte[] data) throws IOException {
        return service.readPacket102(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket104(PacketDataCodec.Packet104Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket104(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet104Data readPacket104(byte[] data) throws IOException {
        return service.readPacket104(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket4(PacketDataCodec.Packet4Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket4(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet4Data readPacket4(byte[] data) throws IOException {
        return service.readPacket4(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket5(PacketDataCodec.Packet5Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket5(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet5Data readPacket5(byte[] data) throws IOException {
        return service.readPacket5(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket7(PacketDataCodec.Packet7Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket7(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet7Data readPacket7(byte[] data) throws IOException {
        return service.readPacket7(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket9(PacketDataCodec.Packet9Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket9(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet9Data readPacket9(byte[] data) throws IOException {
        return service.readPacket9(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket16(PacketDataCodec.Packet16Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket16(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet16Data readPacket16(byte[] data) throws IOException {
        return service.readPacket16(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket18(PacketDataCodec.Packet18Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket18(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet18Data readPacket18(byte[] data) throws IOException {
        return service.readPacket18(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket19(PacketDataCodec.Packet19Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket19(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet19Data readPacket19(byte[] data) throws IOException {
        return service.readPacket19(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket14(PacketDataCodec.Packet14Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket14(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet14Data readPacket14(byte[] data) throws IOException {
        return service.readPacket14(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket15(PacketDataCodec.Packet15Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket15(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet15Data readPacket15(byte[] data) throws IOException {
        return service.readPacket15(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket130(PacketDataCodec.Packet130Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket130(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet130Data readPacket130(byte[] data) throws IOException {
        return service.readPacket130(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket1(PacketDataCodec.Packet1Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket1(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet1Data readPacket1(byte[] data) throws IOException {
        return service.readPacket1(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket2(PacketDataCodec.Packet2Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket2(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet2Data readPacket2(byte[] data) throws IOException {
        return service.readPacket2(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket3(PacketDataCodec.Packet3Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket3(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet3Data readPacket3(byte[] data) throws IOException {
        return service.readPacket3(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket255(PacketDataCodec.Packet255Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket255(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet255Data readPacket255(byte[] data) throws IOException {
        return service.readPacket255(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket30(PacketDataCodec.Packet30Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket30(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet30Data readPacket30(byte[] data) throws IOException {
        return service.readPacket30(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket31(PacketDataCodec.Packet31Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket31(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet31Data readPacket31(byte[] data) throws IOException {
        return service.readPacket31(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket32(PacketDataCodec.Packet32Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket32(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet32Data readPacket32(byte[] data) throws IOException {
        return service.readPacket32(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket33(PacketDataCodec.Packet33Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket33(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet33Data readPacket33(byte[] data) throws IOException {
        return service.readPacket33(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket34(PacketDataCodec.Packet34Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket34(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet34Data readPacket34(byte[] data) throws IOException {
        return service.readPacket34(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket10(PacketDataCodec.Packet10Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket10(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet10Data readPacket10(byte[] data) throws IOException {
        return service.readPacket10(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket11(PacketDataCodec.Packet11Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket11(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet11Data readPacket11(byte[] data) throws IOException {
        return service.readPacket11(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket12(PacketDataCodec.Packet12Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket12(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet12Data readPacket12(byte[] data) throws IOException {
        return service.readPacket12(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket13(PacketDataCodec.Packet13Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket13(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet13Data readPacket13(byte[] data) throws IOException {
        return service.readPacket13(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket131(PacketDataCodec.Packet131Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket131(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet131Data readPacket131(byte[] data) throws IOException {
        return service.readPacket131(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket20(PacketDataCodec.Packet20Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket20(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet20Data readPacket20(byte[] data) throws IOException {
        return service.readPacket20(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket21(PacketDataCodec.Packet21Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket21(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet21Data readPacket21(byte[] data) throws IOException {
        return service.readPacket21(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket24(PacketDataCodec.Packet24Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket24(data, null, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet24Data readPacket24(byte[] data) throws IOException {
        return service.readPacket24(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket40(PacketDataCodec.Packet40Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket40(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet40Data readPacket40(byte[] data) throws IOException {
        return service.readPacket40(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket51(PacketDataCodec.Packet51WriteData data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket51(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet51ReadData readPacket51(byte[] data) throws IOException {
        return service.readPacket51(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket52(PacketDataCodec.Packet52Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket52(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet52Data readPacket52(byte[] data) throws IOException {
        return service.readPacket52(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket60(PacketDataCodec.Packet60Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket60(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet60Data readPacket60(byte[] data) throws IOException {
        return service.readPacket60(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] writePacket28(PacketDataCodec.Packet28Data data) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writePacket28(data, output);
        output.flush();
        return bytes.toByteArray();
    }

    private PacketDataCodec.Packet28Data readPacket28(byte[] data) throws IOException {
        return service.readPacket28(new DataInputStream(new ByteArrayInputStream(data)));
    }

    private byte[] compress(byte[] rawData) {
        Deflater deflater = new Deflater(-1);
        deflater.setInput(rawData);
        deflater.finish();
        byte[] buffer = new byte[rawData.length + 64];
        int compressedLength = deflater.deflate(buffer);
        deflater.end();
        return Arrays.copyOf(buffer, compressedLength);
    }
}
