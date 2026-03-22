package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.CraftServer;

import java.util.UUID;

/**
 * Canonical Bukkit-compat behaviour for entity world binding fallback.
 */
public final class EntityWorldBindingBehaviour {
    private static final EntityWorldBindingBehaviour INSTANCE = new EntityWorldBindingBehaviour();

    private EntityWorldBindingBehaviour() {
    }

    public static EntityWorldBindingBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldUseFallbackWorld(net.minecraft.server.World world) {
        return world == null;
    }

    public net.minecraft.server.World resolveFallbackWorld() {
        return ((CraftWorld) Bukkit.getServer().getWorlds().get(0)).getHandle();
    }

    public net.minecraft.server.World resolveWorld(net.minecraft.server.World world) {
        return world;
    }

    public World resolvePlayerWorld(Server server, NBTTagCompound entityTag, EntityPlayer entityPlayer) {
        World bukkitWorld;
        String worldName = entityTag.getString("World");

        if (entityTag.hasKey("WorldUUIDMost") && entityTag.hasKey("WorldUUIDLeast")) {
            UUID worldUuid = new UUID(entityTag.getLong("WorldUUIDMost"), entityTag.getLong("WorldUUIDLeast"));
            bukkitWorld = server.getWorld(worldUuid);
        } else {
            bukkitWorld = server.getWorld(worldName);
        }

        if (bukkitWorld == null) {
            bukkitWorld = ((CraftServer) server).getServer().getWorldServer(entityPlayer.dimension).getWorld();
        }

        return bukkitWorld;
    }
}
