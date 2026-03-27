package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge behaviour for CraftEventFactory player/world/server/item context projection.
 */
public final class EventFactoryContextBridgeBehaviour {
    private static final EventFactoryContextBridgeBehaviour INSTANCE = new EventFactoryContextBridgeBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();
    private static final WorldServerProjectionBridgeBehaviour WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR =
            WorldServerProjectionBridgeBehaviour.getInstance();
    private static final ItemStackProjectionBridgeBehaviour ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR =
            ItemStackProjectionBridgeBehaviour.getInstance();

    private EventFactoryContextBridgeBehaviour() {
    }

    public static EventFactoryContextBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public Player resolvePlayer(EntityHuman who) {
        return who == null ? null : (Player) who.getBukkitEntity();
    }

    public AnimalTamer resolveAnimalTamer(EntityHuman who) {
        return who == null ? null : (AnimalTamer) who.getBukkitEntity();
    }

    public CraftWorld resolveCraftWorld(Player player) {
        return resolveCraftWorld((com.legacyminecraft.compat.bukkit.World) player.getWorld());
    }

    public CraftWorld resolveCraftWorld(com.legacyminecraft.compat.bukkit.World world) {
        return (CraftWorld) ((WorldServer) WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world)).getWorld();
    }

    public CraftWorld resolveCraftWorld(WorldServer world) {
        return (CraftWorld) WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftWorld(world);
    }

    public CraftServer resolveCraftServer(Player player) {
        return resolveCraftServer((com.legacyminecraft.compat.bukkit.World) player.getWorld());
    }

    public CraftServer resolveCraftServer(com.legacyminecraft.compat.bukkit.World world) {
        return (CraftServer) ((WorldServer) WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world)).getServer();
    }

    public CraftServer resolveCraftServer(WorldServer world) {
        return (CraftServer) WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftServer(world);
    }

    public CraftServer resolveCraftServer(com.legacyminecraft.compat.bukkit.Entity entity) {
        return resolveCraftServer((com.legacyminecraft.compat.bukkit.World) entity.getWorld());
    }

    public CraftItemStack toCraftItemStack(ItemStack itemStack) {
        return ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toCraftItemStack(itemStack);
    }

    public CraftItemStack toCraftItemStack(Item item) {
        return ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toCraftItemStack(item);
    }

    public Material resolveBucketMaterial(ItemStack itemStack) {
        return Material.getMaterial(itemStack.id);
    }
}
