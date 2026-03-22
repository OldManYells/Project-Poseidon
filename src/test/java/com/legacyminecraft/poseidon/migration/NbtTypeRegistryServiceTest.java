package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.nbt.NbtTypeRegistryService;
import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagEnd;
import net.minecraft.server.NBTTagInt;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NbtTypeRegistryServiceTest {
    @Test
    public void createResolvesKnownAndUnknownTypes() {
        NbtTypeRegistryService service = NbtTypeRegistryService.getInstance();

        Assert.assertTrue(service.create((byte) 0) instanceof NBTTagEnd);
        Assert.assertTrue(service.create((byte) 3) instanceof NBTTagInt);
        Assert.assertNull(service.create((byte) 120));
    }

    @Test
    public void typeNameMatchesLegacyMappings() {
        NbtTypeRegistryService service = NbtTypeRegistryService.getInstance();

        Assert.assertEquals("TAG_List", service.typeName((byte) 9));
        Assert.assertEquals("TAG_Compound", service.typeName((byte) 10));
        Assert.assertEquals("UNKNOWN", service.typeName((byte) 99));
    }

    @Test
    public void readWriteNamedTagRoundTripsPayloadAndName() throws IOException {
        NbtTypeRegistryService service = NbtTypeRegistryService.getInstance();
        NBTTagInt tag = (NBTTagInt) new NBTTagInt(42).a("score");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writeNamedTag(tag, output);
        output.flush();

        NBTBase decoded = service.readNamedTag(
                new DataInputStream(new ByteArrayInputStream(bytes.toByteArray()))
        );

        Assert.assertTrue(decoded instanceof NBTTagInt);
        Assert.assertEquals("score", decoded.b());
        Assert.assertEquals(42, ((NBTTagInt) decoded).a);
    }
}
