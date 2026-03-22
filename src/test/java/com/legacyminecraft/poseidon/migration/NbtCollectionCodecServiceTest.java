package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.nbt.NbtCollectionCodecService;
import net.minecraft.server.NBTTagInt;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NbtCollectionCodecServiceTest {
    private final NbtCollectionCodecService service = NbtCollectionCodecService.getInstance();

    @Test
    public void compoundRoundTripPreservesNamedEntries() throws IOException {
        Map entries = new HashMap();
        entries.put("health", new NBTTagInt(20).a("health"));
        entries.put("score", new NBTTagInt(7).a("score"));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        service.writeCompound(entries, output);
        output.flush();

        Map decoded = new HashMap();
        service.readCompound(decoded, new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())));

        Assert.assertEquals(2, decoded.size());
        Assert.assertEquals(20, ((NBTTagInt) decoded.get("health")).a);
        Assert.assertEquals(7, ((NBTTagInt) decoded.get("score")).a);
    }

    @Test
    public void listRoundTripPreservesElementTypeAndPayload() throws IOException {
        List entries = new ArrayList();
        entries.add(new NBTTagInt(4));
        entries.add(new NBTTagInt(9));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);
        NbtCollectionCodecService.ListWriteResult writeResult = service.writeList(entries, output);
        output.flush();

        NbtCollectionCodecService.ListReadResult readResult =
                service.readList(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())));

        Assert.assertEquals(3, writeResult.getListType());
        Assert.assertEquals(3, readResult.getListType());
        Assert.assertEquals(2, readResult.getEntries().size());
        Assert.assertEquals(4, ((NBTTagInt) readResult.getEntries().get(0)).a);
        Assert.assertEquals(9, ((NBTTagInt) readResult.getEntries().get(1)).a);
    }

    @Test
    public void emptyListUsesLegacyDefaultElementType() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);

        NbtCollectionCodecService.ListWriteResult writeResult = service.writeList(new ArrayList(), output);
        output.flush();

        DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        byte encodedType = input.readByte();
        int encodedSize = input.readInt();

        Assert.assertEquals(1, writeResult.getListType());
        Assert.assertEquals(1, encodedType);
        Assert.assertEquals(0, encodedSize);
    }
}
