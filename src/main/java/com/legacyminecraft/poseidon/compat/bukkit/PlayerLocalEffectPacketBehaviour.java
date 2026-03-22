package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Packet53BlockChange;
import net.minecraft.server.Packet54PlayNoteBlock;
import net.minecraft.server.Packet61;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;

/**
 * Canonical behaviour for CraftPlayer local effect/block-change packet construction.
 */
public final class PlayerLocalEffectPacketBehaviour {
    private static final PlayerLocalEffectPacketBehaviour INSTANCE = new PlayerLocalEffectPacketBehaviour();

    private PlayerLocalEffectPacketBehaviour() {
    }

    public static PlayerLocalEffectPacketBehaviour getInstance() {
        return INSTANCE;
    }

    public Packet54PlayNoteBlock createPlayNotePacket(Location location, byte instrumentType, byte noteId) {
        return new Packet54PlayNoteBlock(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                instrumentType,
                noteId
        );
    }

    public Packet61 createEffectPacket(Location location, Effect effect, int data) {
        return new Packet61(
                effect.getId(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                data
        );
    }

    public Packet53BlockChange createBlockChangePacket(Location location, int materialId, byte materialData) {
        Packet53BlockChange packet = new Packet53BlockChange(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                ((CraftWorld) location.getWorld()).getHandle()
        );
        packet.material = materialId;
        packet.data = materialData;
        return packet;
    }
}
