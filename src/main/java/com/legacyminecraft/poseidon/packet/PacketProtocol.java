package com.legacyminecraft.poseidon.packet;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.packets.ArtificialPacket53BlockChange;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Canonical protocol registry and packet stream codec for legacy packet wrappers.
 */
public final class PacketProtocol {
    private static final PacketProtocol INSTANCE = new PacketProtocol();

    private final Map packetIdToClassMap = new HashMap();
    private final Map packetClassToIdMap = new HashMap();
    private final Set clientPacketIdList = new HashSet();
    private final Set serverPacketIdList = new HashSet();
    private final PacketRegistrationBootstrap packetRegistrationBootstrapService =
            PacketRegistrationBootstrap.getInstance();
    private final PacketTrafficCounter packetTrafficCounterService = new PacketTrafficCounter();
    private final PacketStringCodec packetStringCodecService = new PacketStringCodec();
    private final PacketFactory packetFactoryService = new PacketFactory();

    private PacketProtocol() {
        registerDefaults();
        packetClassToIdMap.put(ArtificialPacket53BlockChange.class, Integer.valueOf(53));
    }

    public static PacketProtocol getInstance() {
        return INSTANCE;
    }

    public synchronized void registerPacket(int id, boolean clientSide, boolean serverSide, Class packetClass) {
        Integer packetId = Integer.valueOf(id);
        if (packetIdToClassMap.containsKey(packetId)) {
            throw new IllegalArgumentException("Duplicate packet id:" + id);
        }
        if (packetClassToIdMap.containsKey(packetClass)) {
            throw new IllegalArgumentException("Duplicate packet class:" + packetClass);
        }

        packetIdToClassMap.put(packetId, packetClass);
        packetClassToIdMap.put(packetClass, packetId);
        if (clientSide) {
            clientPacketIdList.add(packetId);
        }
        if (serverSide) {
            serverPacketIdList.add(packetId);
        }
    }

    public synchronized Packet createPacket(int id) {
        Class packetClass = (Class) packetIdToClassMap.get(Integer.valueOf(id));
        return packetFactoryService.createPacket(packetClass, id);
    }

    public synchronized int getPacketId(Packet packet) {
        return ((Integer) packetClassToIdMap.get(packet.getClass())).intValue();
    }

    public Packet readPacket(DataInputStream datainputstream, boolean serverSide) throws IOException {
        try {
            int packetId = datainputstream.read();
            if (packetId == -1) {
                return null;
            }

            Integer packetIdKey = Integer.valueOf(packetId);
            if (serverSide && !isServerPacket(packetIdKey) || !serverSide && !isClientPacket(packetIdKey)) {
                System.out.println("Bad packet id: " + packetId);
                return null;
            }

            Packet packet = createPacket(packetId);
            if (packet == null) {
                throw new IOException("Bad packet id " + packetId);
            }

            packet.a(datainputstream);
            packetTrafficCounterService.recordPacketStat(packetIdKey, packet.a());
            return packet;
        } catch (EOFException eofexception) {
            System.out.println("Reached end of stream");
            return null;
        } catch (SocketTimeoutException exception) {
            System.out.println("Read timed out");
            return null;
        } catch (SocketException exception) {
            if (!(boolean) PoseidonConfig.getInstance().getConfigOption("settings.remove-join-leave-debug", true)) {
                System.out.println("Connection reset");
            }
            return null;
        }
    }

    public void writePacket(Packet packet, DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.write(getPacketId(packet));
        packet.a(dataoutputstream);
    }

    public void writeString(String value, DataOutputStream dataoutputstream) throws IOException {
        packetStringCodecService.writeString(value, dataoutputstream);
    }

    public String readString(DataInputStream datainputstream, int maxLength) throws IOException {
        return packetStringCodecService.readString(datainputstream, maxLength);
    }

    private synchronized boolean isClientPacket(Integer packetId) {
        return clientPacketIdList.contains(packetId);
    }

    private synchronized boolean isServerPacket(Integer packetId) {
        return serverPacketIdList.contains(packetId);
    }

    private void registerDefaults() {
        packetRegistrationBootstrapService.registerDefaults(this);
    }
}
