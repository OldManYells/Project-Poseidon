package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class NbtPrimitiveCodecServiceTest {
    private final NbtPrimitiveCodecService service = NbtPrimitiveCodecService.getInstance();

    @Test
    public void byteShortIntLongRoundTrip() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writeByte(output, (byte) -5);
        service.writeShort(output, (short) 32000);
        service.writeInt(output, 1234567);
        service.writeLong(output, 9876543210L);
        output.flush();

        DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        Assert.assertEquals(-5, service.readByte(input));
        Assert.assertEquals(32000, service.readShort(input));
        Assert.assertEquals(1234567, service.readInt(input));
        Assert.assertEquals(9876543210L, service.readLong(input));
    }

    @Test
    public void floatDoubleAndStringRoundTrip() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writeFloat(output, 1.25F);
        service.writeDouble(output, -9.5D);
        service.writeString(output, "poseidon");
        output.flush();

        DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        Assert.assertEquals(1.25F, service.readFloat(input), 0.0F);
        Assert.assertEquals(-9.5D, service.readDouble(input), 0.0D);
        Assert.assertEquals("poseidon", service.readString(input));
    }

    @Test
    public void byteArrayRoundTripIncludesLengthPrefix() throws IOException {
        byte[] expected = new byte[]{1, 2, 3, 4};
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writeByteArray(output, expected);
        output.flush();

        byte[] actual = service.readByteArray(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())));

        Assert.assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void endTagCodecIsNoOpAndHasStableTypeId() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writeEnd(output);
        output.flush();

        DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        service.readEnd(input);

        Assert.assertEquals(0, service.endTypeId());
        Assert.assertEquals(0, bytes.toByteArray().length);
    }
}
