package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityCreeper;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.entity.CreeperPowerEvent;

/**
 * Canonical bridge behavior for CraftCreeper powered-state event orchestration.
 */
public final class CreeperPowerEventBridgeBehaviour {
    private static final CreeperPowerEventBridgeBehaviour INSTANCE = new CreeperPowerEventBridgeBehaviour();

    private CreeperPowerEventBridgeBehaviour() {
    }

    public static CreeperPowerEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void setPoweredWithEvent(CraftServer server, EntityCreeper creeper, boolean powered) {
        CreeperPowerEvent.PowerCause powerCause = powered
                ? CreeperPowerEvent.PowerCause.SET_ON
                : CreeperPowerEvent.PowerCause.SET_OFF;
        CreeperPowerEvent event = new CreeperPowerEvent(creeper.getBukkitEntity(), powerCause);
        server.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            creeper.setPowered(powered);
        }
    }
}

