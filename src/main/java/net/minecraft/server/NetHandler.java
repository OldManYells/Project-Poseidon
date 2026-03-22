package net.minecraft.server;

import com.legacyminecraft.poseidon.network.NetHandlerPacketForwardingBehaviour;

public abstract class NetHandler {
    private static final NetHandlerPacketForwardingBehaviour NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR = NetHandlerPacketForwardingBehaviour.getInstance();

    public NetHandler() {}

    public abstract boolean c();

    public void a(Packet51MapChunk packet51mapchunk) {}

    public void a(Packet packet) {}

    public void a(String s, Object[] aobject) {}

    public void a(Packet0KeepAlive packet0KeepAlive) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet0KeepAlive);
    }

    public void a(Packet255KickDisconnect packet255kickdisconnect) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet255kickdisconnect);
    }

    public void a(Packet1Login packet1login) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet1login);
    }

    public void a(Packet10Flying packet10flying) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet10flying);
    }

    public void a(Packet52MultiBlockChange packet52multiblockchange) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet52multiblockchange);
    }

    public void a(Packet14BlockDig packet14blockdig) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet14blockdig);
    }

    public void a(Packet53BlockChange packet53blockchange) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet53blockchange);
    }

    public void a(Packet50PreChunk packet50prechunk) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet50prechunk);
    }

    public void a(Packet20NamedEntitySpawn packet20namedentityspawn) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet20namedentityspawn);
    }

    public void a(Packet30Entity packet30entity) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet30entity);
    }

    public void a(Packet34EntityTeleport packet34entityteleport) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet34entityteleport);
    }

    public void a(Packet15Place packet15place) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet15place);
    }

    public void a(Packet16BlockItemSwitch packet16blockitemswitch) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet16blockitemswitch);
    }

    public void a(Packet29DestroyEntity packet29destroyentity) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet29destroyentity);
    }

    public void a(Packet21PickupSpawn packet21pickupspawn) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet21pickupspawn);
    }

    public void a(Packet22Collect packet22collect) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet22collect);
    }

    public void a(Packet3Chat packet3chat) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet3chat);
    }

    public void a(Packet23VehicleSpawn packet23vehiclespawn) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet23vehiclespawn);
    }

    public void a(Packet18ArmAnimation packet18armanimation) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet18armanimation);
    }

    public void a(Packet19EntityAction packet19entityaction) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet19entityaction);
    }

    public void a(Packet2Handshake packet2handshake) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet2handshake);
    }

    public void a(Packet24MobSpawn packet24mobspawn) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet24mobspawn);
    }

    public void a(Packet4UpdateTime packet4updatetime) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet4updatetime);
    }

    public void a(Packet6SpawnPosition packet6spawnposition) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet6spawnposition);
    }

    public void a(Packet28EntityVelocity packet28entityvelocity) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet28entityvelocity);
    }

    public void a(Packet40EntityMetadata packet40entitymetadata) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet40entitymetadata);
    }

    public void a(Packet39AttachEntity packet39attachentity) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet39attachentity);
    }

    public void a(Packet7UseEntity packet7useentity) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet7useentity);
    }

    public void a(Packet38EntityStatus packet38entitystatus) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet38entitystatus);
    }

    public void a(Packet8UpdateHealth packet8updatehealth) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet8updatehealth);
    }

    public void a(Packet9Respawn packet9respawn) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet9respawn);
    }

    public void a(Packet60Explosion packet60explosion) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet60explosion);
    }

    public void a(Packet100OpenWindow packet100openwindow) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet100openwindow);
    }

    public void a(Packet101CloseWindow packet101closewindow) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet101closewindow);
    }

    public void a(Packet102WindowClick packet102windowclick) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet102windowclick);
    }

    public void a(Packet103SetSlot packet103setslot) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet103setslot);
    }

    public void a(Packet104WindowItems packet104windowitems) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet104windowitems);
    }

    public void a(Packet130UpdateSign packet130updatesign) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet130updatesign);
    }

    public void a(Packet105CraftProgressBar packet105craftprogressbar) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet105craftprogressbar);
    }

    public void a(Packet5EntityEquipment packet5entityequipment) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet5entityequipment);
    }

    public void a(Packet106Transaction packet106transaction) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet106transaction);
    }

    public void a(Packet25EntityPainting packet25entitypainting) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet25entitypainting);
    }

    public void a(Packet54PlayNoteBlock packet54playnoteblock) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet54playnoteblock);
    }

    public void a(Packet200Statistic packet200statistic) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet200statistic);
    }

    public void a(Packet17 packet17) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet17);
    }

    public void a(Packet27 packet27) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet27);
    }

    public void a(Packet70Bed packet70bed) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet70bed);
    }

    public void a(Packet71Weather packet71weather) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet71weather);
    }

    public void a(Packet131 packet131) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet131);
    }

    public void a(Packet61 packet61) {
        NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet61);
    }
}
