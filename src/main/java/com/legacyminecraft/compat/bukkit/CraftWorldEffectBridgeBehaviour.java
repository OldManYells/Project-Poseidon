package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld play-effect overload forwarding.
 */
public final class CraftWorldEffectBridgeBehaviour {
    private static final CraftWorldEffectBridgeBehaviour INSTANCE = new CraftWorldEffectBridgeBehaviour();

    private CraftWorldEffectBridgeBehaviour() {
    }

    public static CraftWorldEffectBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void playEffect(CraftWorld craftWorld, Player player, Effect effect, int data) {
        craftWorld.playEffect(player.getLocation(), effect, data, 0);
    }

    public void playEffect(CraftWorld craftWorld, Location location, Effect effect, int data) {
        craftWorld.playEffect(location, effect, data, 64);
    }
}
