package com.legacyminecraft.compat.bukkit;


import java.util.Map;

/**
 * Canonical behaviour for CraftServer world-unload wrapper glue.
 */
public final class CraftServerWorldUnloadBehaviour {
    private static final CraftServerWorldUnloadBehaviour INSTANCE =
            new CraftServerWorldUnloadBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();

    private CraftServerWorldUnloadBehaviour() {
    }

    public static CraftServerWorldUnloadBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean unloadWorld(String name, boolean save, Map<String, World> worlds, MinecraftServer console, PluginManager pluginManager) {
        return unloadWorld(this.getWorld(name, worlds), save, worlds, console, pluginManager);
    }

    public boolean unloadWorld(World world, boolean save, Map<String, World> worlds, MinecraftServer console, PluginManager pluginManager) {
        if (world == null) {
            return false;
        }

        WorldServer handle = WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world);

        if (!(console.worlds.contains(handle))) {
            return false;
        }

        if (!(handle.dimension > 1)) {
            return false;
        }

        if (handle.players.size() > 0) {
            return false;
        }

        WorldUnloadEvent worldUnloadEvent = new WorldUnloadEvent(handle.getWorld());

        if (worldUnloadEvent.isCancelled()) {
            return false;
        }

        if (save) {
            handle.save(true, (IProgressUpdate) null);
            handle.saveLevel();
            WorldSaveEvent worldSaveEvent = new WorldSaveEvent(handle.getWorld());
            pluginManager.callEvent(worldSaveEvent);
        }

        worlds.remove(world.getName().toLowerCase());
        console.worlds.remove(console.worlds.indexOf(handle));

        return true;
    }

    private World getWorld(String name, Map<String, World> worlds) {
        if (name == null) {
            return null;
        }
        return worlds.get(name.toLowerCase());
    }
}
