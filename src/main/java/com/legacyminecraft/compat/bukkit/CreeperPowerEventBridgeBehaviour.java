package com.legacyminecraft.compat.bukkit;


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

    public boolean isPowered(EntityCreeper creeper) {
        return creeper.isPowered();
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
