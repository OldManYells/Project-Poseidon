package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.packets.ArtificialPacket53BlockChange;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class PacketProtocolServiceTest {
    @Test
    public void readsServerBoundKeepAlivePacket() throws Exception {
        byte[] payload = new byte[]{0};

        Packet packet = Packet.a(new DataInputStream(new ByteArrayInputStream(payload)), true);

        Assert.assertTrue(packet instanceof Packet0KeepAlive);
    }

    @Test
    public void rejectsPacketThatIsNotAllowedForServerSide() throws Exception {
        byte[] payload = new byte[]{4};

        Packet packet = Packet.a(new DataInputStream(new ByteArrayInputStream(payload)), true);

        Assert.assertNull(packet);
    }

    @Test
    public void preservesArtificialPacket53Alias() throws Exception {
        ArtificialPacket53BlockChange packet = new ArtificialPacket53BlockChange(1, 2, 3, 4, 5);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);

        Packet.a(packet, output);
        output.flush();

        Assert.assertEquals(53, bytes.toByteArray()[0] & 255);
        Assert.assertEquals(53, packet.b());
    }

    @Test
    public void roundTripsLegacyPacketStringEncoding() throws Exception {
        String message = "Poseidon";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(bytes);

        Packet.a(message, output);
        output.flush();

        String decoded = Packet.a(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())), 64);
        Assert.assertEquals(message, decoded);
    }
}
