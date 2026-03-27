package com.legacyminecraft.compat.bukkit;


import java.util.List;
import java.util.UUID;

/**
 * Canonical behaviour for CraftServer player lookup and online-player projection.
 */
public final class CraftServerPlayerLookupBehaviour {
    private static final CraftServerPlayerLookupBehaviour INSTANCE = new CraftServerPlayerLookupBehaviour();
    private static final NmsEntityProjectionBridgeBehaviour NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR =
            NmsEntityProjectionBridgeBehaviour.getInstance();

    private CraftServerPlayerLookupBehaviour() {
    }

    public static CraftServerPlayerLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public Object[] getOnlinePlayers(List players) {
        Object[] onlinePlayers = new Object[players.size()];
        for (int playerIndex = 0; playerIndex < onlinePlayers.length; playerIndex++) {
            onlinePlayers[playerIndex] = resolveOnlinePlayer(players.get(playerIndex));
        }
        return onlinePlayers;
    }

    public Object getPlayer(Object[] onlinePlayers, String name) {
        Object bestMatch = null;
        String normalizedName = name.toLowerCase();
        int bestDelta = Integer.MAX_VALUE;

        for (int index = 0; index < onlinePlayers.length; index++) {
            Object player = onlinePlayers[index];
            String playerName = stringValue(invoke(player, "getName")).toLowerCase();
            if (playerName.startsWith(normalizedName)) {
                int currentDelta = playerName.length() - normalizedName.length();
                if (currentDelta < bestDelta) {
                    bestMatch = player;
                    bestDelta = currentDelta;
                }
                if (currentDelta == 0) {
                    break;
                }
            }
        }

        return bestMatch;
    }

    public Object getPlayer(Object[] onlinePlayers, UUID uuid) {
        for (int index = 0; index < onlinePlayers.length; index++) {
            Object player = onlinePlayers[index];
            Object uniqueId = invoke(player, "getUniqueId");
            if (uuid.equals(uniqueId)) {
                return player;
            }
        }
        return null;
    }

    public Object getPlayerExact(Object[] onlinePlayers, String name) {
        String normalizedName = name.toLowerCase();

        for (int index = 0; index < onlinePlayers.length; index++) {
            Object player = onlinePlayers[index];
            String playerName = stringValue(invoke(player, "getName"));
            if (playerName.equalsIgnoreCase(normalizedName)) {
                return player;
            }
        }

        return null;
    }

    private static Object resolveOnlinePlayer(Object value) {
        Object player = NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR.resolveOnlinePlayer(value);
        if (player != null) {
            return player;
        }
        if (value == null) {
            return null;
        }
        try {
            Object netHandler = value.getClass().getField("netServerHandler").get(value);
            return invoke(netHandler, "getPlayer");
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static Object invoke(Object target, String methodName) {
        if (target == null) {
            return null;
        }
        try {
            return target.getClass().getMethod(methodName).invoke(target);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
