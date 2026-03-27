package com.legacyminecraft.compat.bukkit;


import java.util.List;

/**
 * Canonical behavior for CraftWorld full-time update and player sync packet fan-out.
 */
public final class CraftWorldTimeUpdateBehaviour {
    private static final CraftWorldTimeUpdateBehaviour INSTANCE = new CraftWorldTimeUpdateBehaviour();
    private static final PlayerWrapperProjectionBridgeBehaviour PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            PlayerWrapperProjectionBridgeBehaviour.getInstance();

    private CraftWorldTimeUpdateBehaviour() {
    }

    public static CraftWorldTimeUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public void setFullTime(WorldServer worldServer, List<Player> players, long time) {
        worldServer.setTime(time);
        for (Player player : players) {
            CraftPlayer craftPlayer =
                    PLAYER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.requireCraftPlayer(player);
            craftPlayer.getHandle().netServerHandler
                    .sendPacket(new Packet4UpdateTime(craftPlayer.getHandle().getPlayerTime()));
        }
    }
}
