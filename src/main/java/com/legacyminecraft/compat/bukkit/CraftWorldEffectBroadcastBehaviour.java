package com.legacyminecraft.compat.bukkit;


import java.util.List;

/**
 * Canonical behavior for CraftWorld effect packet broadcast orchestration.
 */
public final class CraftWorldEffectBroadcastBehaviour {
    private static final CraftWorldEffectBroadcastBehaviour INSTANCE = new CraftWorldEffectBroadcastBehaviour();
    private static final PlayerWrapperProjectionBridgeBehaviour PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            PlayerWrapperProjectionBridgeBehaviour.getInstance();

    private CraftWorldEffectBroadcastBehaviour() {
    }

    public static CraftWorldEffectBroadcastBehaviour getInstance() {
        return INSTANCE;
    }

    public void playEffect(Location location, Effect effect, int data, int radius, List<Player> players) {
        Packet61 packet = new Packet61(
                effect.getId(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                data
        );

        for (Player player : players) {
            int distance = (int) player.getLocation().distance(location);
            if (distance <= radius) {
                PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR
                        .requireCraftPlayer(player)
                        .getHandle()
                        .netServerHandler
                        .sendPacket(packet);
            }
        }
    }
}
