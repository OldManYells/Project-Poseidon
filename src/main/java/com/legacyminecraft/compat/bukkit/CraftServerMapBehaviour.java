package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer map wrapper glue.
 */
public final class CraftServerMapBehaviour {
    private static final CraftServerMapBehaviour INSTANCE = new CraftServerMapBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();

    private CraftServerMapBehaviour() {
    }

    public static CraftServerMapBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftMapView getMap(MinecraftServer console, short id) {
        WorldMapCollection collection = console.worlds.get(0).worldMaps;
        WorldMap worldMap = (WorldMap) collection.a(WorldMap.class, "map_" + id);
        if (worldMap == null) {
            return null;
        }
        return worldMap.mapView;
    }

    public CraftMapView createMap(World world) {
        ItemStack stack = new ItemStack(Item.MAP, 1, -1);
        WorldMap worldMap = Item.MAP.a(stack, WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world));
        return worldMap.mapView;
    }
}
