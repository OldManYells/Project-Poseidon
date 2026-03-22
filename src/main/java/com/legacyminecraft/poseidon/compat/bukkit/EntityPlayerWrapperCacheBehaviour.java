package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

import java.util.Map;

/**
 * Canonical behaviour for CraftEntity player-wrapper cache resolution.
 */
public final class EntityPlayerWrapperCacheBehaviour {
    private static final EntityPlayerWrapperCacheBehaviour INSTANCE = new EntityPlayerWrapperCacheBehaviour();

    private EntityPlayerWrapperCacheBehaviour() {
    }

    public static EntityPlayerWrapperCacheBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftPlayer resolvePlayer(Map<String, CraftPlayer> playerCache, EntityPlayer entityPlayer) {
        CraftPlayer wrapper = playerCache.get(entityPlayer.name);
        if (wrapper == null) {
            wrapper = new CraftPlayer((CraftServer) Bukkit.getServer(), entityPlayer);
            playerCache.put(entityPlayer.name, wrapper);
        } else {
            wrapper.setHandle(entityPlayer);
        }
        return wrapper;
    }
}
