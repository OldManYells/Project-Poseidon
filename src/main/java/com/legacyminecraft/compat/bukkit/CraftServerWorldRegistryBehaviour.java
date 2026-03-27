package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Canonical behaviour for CraftServer world-registry wrapper glue.
 */
public final class CraftServerWorldRegistryBehaviour {
    private static final CraftServerWorldRegistryBehaviour INSTANCE = new CraftServerWorldRegistryBehaviour();

    private CraftServerWorldRegistryBehaviour() {
    }

    public static CraftServerWorldRegistryBehaviour getInstance() {
        return INSTANCE;
    }

    public List<World> getWorlds(Map<String, World> worldsByName) {
        return new ArrayList<World>(worldsByName.values());
    }

    public World getWorld(Map<String, World> worldsByName, String name) {
        return worldsByName.get(name.toLowerCase());
    }

    public World getWorld(Map<String, World> worldsByName, UUID worldUid) {
        for (World world : worldsByName.values()) {
            if (world.getUID().equals(worldUid)) {
                return world;
            }
        }
        return null;
    }

    public boolean addWorld(Map<String, World> worldsByName, World world) {
        if (getWorld(worldsByName, world.getUID()) != null) {
            return false;
        }
        worldsByName.put(world.getName().toLowerCase(), world);
        return true;
    }
}
