package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behaviour for CraftServer player helper wrapper glue.
 */
public final class CraftServerPlayerHelperBehaviour {
    private static final CraftServerPlayerHelperBehaviour INSTANCE = new CraftServerPlayerHelperBehaviour();

    private CraftServerPlayerHelperBehaviour() {
    }

    public static CraftServerPlayerHelperBehaviour getInstance() {
        return INSTANCE;
    }

    public Player getPlayer(EntityPlayer entityPlayer) {
        return entityPlayer.netServerHandler.getPlayer();
    }

    public List<Player> matchPlayer(Player[] onlinePlayers, String partialName) {
        List<Player> matchedPlayers = new ArrayList<Player>();

        for (Player iterPlayer : onlinePlayers) {
            String iterPlayerName = iterPlayer.getName();

            if (partialName.equalsIgnoreCase(iterPlayerName)) {
                matchedPlayers.clear();
                matchedPlayers.add(iterPlayer);
                break;
            }

            if (iterPlayerName.toLowerCase().indexOf(partialName.toLowerCase()) != -1) {
                matchedPlayers.add(iterPlayer);
            }
        }

        return matchedPlayers;
    }

    public OfflinePlayer getOfflinePlayer(
            OfflinePlayer exactPlayer,
            String name,
            OfflinePlayerFactory offlinePlayerFactory
    ) {
        if (exactPlayer != null) {
            return exactPlayer;
        }
        return offlinePlayerFactory.create(name);
    }

    public interface OfflinePlayerFactory {
        OfflinePlayer create(String name);
    }
}
