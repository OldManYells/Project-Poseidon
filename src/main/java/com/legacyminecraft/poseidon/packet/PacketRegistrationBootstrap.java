package com.legacyminecraft.poseidon.packet;

import net.minecraft.server.Packet0KeepAlive;
import net.minecraft.server.Packet100OpenWindow;
import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet102WindowClick;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.Packet104WindowItems;
import net.minecraft.server.Packet105CraftProgressBar;
import net.minecraft.server.Packet106Transaction;
import net.minecraft.server.Packet10Flying;
import net.minecraft.server.Packet11PlayerPosition;
import net.minecraft.server.Packet12PlayerLook;
import net.minecraft.server.Packet130UpdateSign;
import net.minecraft.server.Packet131;
import net.minecraft.server.Packet13PlayerLookMove;
import net.minecraft.server.Packet14BlockDig;
import net.minecraft.server.Packet15Place;
import net.minecraft.server.Packet16BlockItemSwitch;
import net.minecraft.server.Packet17;
import net.minecraft.server.Packet18ArmAnimation;
import net.minecraft.server.Packet19EntityAction;
import net.minecraft.server.Packet1Login;
import net.minecraft.server.Packet200Statistic;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet21PickupSpawn;
import net.minecraft.server.Packet22Collect;
import net.minecraft.server.Packet23VehicleSpawn;
import net.minecraft.server.Packet24MobSpawn;
import net.minecraft.server.Packet255KickDisconnect;
import net.minecraft.server.Packet25EntityPainting;
import net.minecraft.server.Packet27;
import net.minecraft.server.Packet28EntityVelocity;
import net.minecraft.server.Packet29DestroyEntity;
import net.minecraft.server.Packet2Handshake;
import net.minecraft.server.Packet30Entity;
import net.minecraft.server.Packet31RelEntityMove;
import net.minecraft.server.Packet32EntityLook;
import net.minecraft.server.Packet33RelEntityMoveLook;
import net.minecraft.server.Packet34EntityTeleport;
import net.minecraft.server.Packet38EntityStatus;
import net.minecraft.server.Packet39AttachEntity;
import net.minecraft.server.Packet3Chat;
import net.minecraft.server.Packet40EntityMetadata;
import net.minecraft.server.Packet4UpdateTime;
import net.minecraft.server.Packet50PreChunk;
import net.minecraft.server.Packet51MapChunk;
import net.minecraft.server.Packet52MultiBlockChange;
import net.minecraft.server.Packet53BlockChange;
import net.minecraft.server.Packet54PlayNoteBlock;
import net.minecraft.server.Packet5EntityEquipment;
import net.minecraft.server.Packet60Explosion;
import net.minecraft.server.Packet61;
import net.minecraft.server.Packet6SpawnPosition;
import net.minecraft.server.Packet70Bed;
import net.minecraft.server.Packet71Weather;
import net.minecraft.server.Packet7UseEntity;
import net.minecraft.server.Packet8UpdateHealth;
import net.minecraft.server.Packet9Respawn;

/**
 * Canonical default packet-id registration catalog.
 */
public final class PacketRegistrationBootstrap {
    private static final PacketRegistrationBootstrap INSTANCE = new PacketRegistrationBootstrap();

    private PacketRegistrationBootstrap() {
    }

    public static PacketRegistrationBootstrap getInstance() {
        return INSTANCE;
    }

    public void registerDefaults(PacketProtocol packetProtocolService) {
        packetProtocolService.registerPacket(0, true, true, Packet0KeepAlive.class);
        packetProtocolService.registerPacket(1, true, true, Packet1Login.class);
        packetProtocolService.registerPacket(2, true, true, Packet2Handshake.class);
        packetProtocolService.registerPacket(3, true, true, Packet3Chat.class);
        packetProtocolService.registerPacket(4, true, false, Packet4UpdateTime.class);
        packetProtocolService.registerPacket(5, true, false, Packet5EntityEquipment.class);
        packetProtocolService.registerPacket(6, true, false, Packet6SpawnPosition.class);
        packetProtocolService.registerPacket(7, false, true, Packet7UseEntity.class);
        packetProtocolService.registerPacket(8, true, false, Packet8UpdateHealth.class);
        packetProtocolService.registerPacket(9, true, true, Packet9Respawn.class);
        packetProtocolService.registerPacket(10, true, true, Packet10Flying.class);
        packetProtocolService.registerPacket(11, true, true, Packet11PlayerPosition.class);
        packetProtocolService.registerPacket(12, true, true, Packet12PlayerLook.class);
        packetProtocolService.registerPacket(13, true, true, Packet13PlayerLookMove.class);
        packetProtocolService.registerPacket(14, false, true, Packet14BlockDig.class);
        packetProtocolService.registerPacket(15, false, true, Packet15Place.class);
        packetProtocolService.registerPacket(16, false, true, Packet16BlockItemSwitch.class);
        packetProtocolService.registerPacket(17, true, false, Packet17.class);
        packetProtocolService.registerPacket(18, true, true, Packet18ArmAnimation.class);
        packetProtocolService.registerPacket(19, false, true, Packet19EntityAction.class);
        packetProtocolService.registerPacket(20, true, false, Packet20NamedEntitySpawn.class);
        packetProtocolService.registerPacket(21, true, false, Packet21PickupSpawn.class);
        packetProtocolService.registerPacket(22, true, false, Packet22Collect.class);
        packetProtocolService.registerPacket(23, true, false, Packet23VehicleSpawn.class);
        packetProtocolService.registerPacket(24, true, false, Packet24MobSpawn.class);
        packetProtocolService.registerPacket(25, true, false, Packet25EntityPainting.class);
        packetProtocolService.registerPacket(27, false, false, Packet27.class);
        packetProtocolService.registerPacket(28, true, false, Packet28EntityVelocity.class);
        packetProtocolService.registerPacket(29, true, false, Packet29DestroyEntity.class);
        packetProtocolService.registerPacket(30, true, false, Packet30Entity.class);
        packetProtocolService.registerPacket(31, true, false, Packet31RelEntityMove.class);
        packetProtocolService.registerPacket(32, true, false, Packet32EntityLook.class);
        packetProtocolService.registerPacket(33, true, false, Packet33RelEntityMoveLook.class);
        packetProtocolService.registerPacket(34, true, false, Packet34EntityTeleport.class);
        packetProtocolService.registerPacket(38, true, false, Packet38EntityStatus.class);
        packetProtocolService.registerPacket(39, true, false, Packet39AttachEntity.class);
        packetProtocolService.registerPacket(40, true, false, Packet40EntityMetadata.class);
        packetProtocolService.registerPacket(50, true, false, Packet50PreChunk.class);
        packetProtocolService.registerPacket(51, true, false, Packet51MapChunk.class);
        packetProtocolService.registerPacket(52, true, false, Packet52MultiBlockChange.class);
        packetProtocolService.registerPacket(53, true, false, Packet53BlockChange.class);
        packetProtocolService.registerPacket(54, true, false, Packet54PlayNoteBlock.class);
        packetProtocolService.registerPacket(60, true, false, Packet60Explosion.class);
        packetProtocolService.registerPacket(61, true, false, Packet61.class);
        packetProtocolService.registerPacket(70, true, false, Packet70Bed.class);
        packetProtocolService.registerPacket(71, true, false, Packet71Weather.class);
        packetProtocolService.registerPacket(100, true, false, Packet100OpenWindow.class);
        packetProtocolService.registerPacket(101, true, true, Packet101CloseWindow.class);
        packetProtocolService.registerPacket(102, false, true, Packet102WindowClick.class);
        packetProtocolService.registerPacket(103, true, false, Packet103SetSlot.class);
        packetProtocolService.registerPacket(104, true, false, Packet104WindowItems.class);
        packetProtocolService.registerPacket(105, true, false, Packet105CraftProgressBar.class);
        packetProtocolService.registerPacket(106, true, true, Packet106Transaction.class);
        packetProtocolService.registerPacket(130, true, true, Packet130UpdateSign.class);
        packetProtocolService.registerPacket(131, true, false, Packet131.class);
        packetProtocolService.registerPacket(200, true, false, Packet200Statistic.class);
        packetProtocolService.registerPacket(255, true, true, Packet255KickDisconnect.class);
    }
}
