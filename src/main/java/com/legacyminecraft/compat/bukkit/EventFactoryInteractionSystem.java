package com.legacyminecraft.compat.bukkit;


/**
 * Canonical orchestration system for CraftEventFactory interaction/block-placement flows.
 */
public final class EventFactoryInteractionSystem {
    private static final EventFactoryInteractionSystem INSTANCE = new EventFactoryInteractionSystem();

    private static final SpawnBuildPermissionBehaviour SPAWN_BUILD_PERMISSION_BEHAVIOUR =
            SpawnBuildPermissionBehaviour.getInstance();
    private static final BlockPlaceEventConstructionBehaviour BLOCK_PLACE_EVENT_CONSTRUCTION_BEHAVIOUR =
            BlockPlaceEventConstructionBehaviour.getInstance();
    private static final BucketEventConstructionBehaviour BUCKET_EVENT_CONSTRUCTION_BEHAVIOUR =
            BucketEventConstructionBehaviour.getInstance();
    private static final PlayerInteractPreparationBehaviour PLAYER_INTERACT_PREPARATION_BEHAVIOUR =
            PlayerInteractPreparationBehaviour.getInstance();
    private static final PlayerInteractActionValidationPolicy PLAYER_INTERACT_ACTION_VALIDATION_POLICY =
            PlayerInteractActionValidationPolicy.getInstance();
    private static final PlayerInteractEventConstructionBehaviour PLAYER_INTERACT_EVENT_CONSTRUCTION_BEHAVIOUR =
            PlayerInteractEventConstructionBehaviour.getInstance();
    private static final BlockDamageEventConstructionBehaviour BLOCK_DAMAGE_EVENT_CONSTRUCTION_BEHAVIOUR =
            BlockDamageEventConstructionBehaviour.getInstance();
    private static final EventDispatchBridgeBehaviour EVENT_DISPATCH_BRIDGE_BEHAVIOUR =
            EventDispatchBridgeBehaviour.getInstance();
    private static final EventFactoryContextBridgeBehaviour EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR =
            EventFactoryContextBridgeBehaviour.getInstance();
    private static final EventFactoryBlockSelectionBehaviour EVENT_FACTORY_BLOCK_SELECTION_BEHAVIOUR =
            EventFactoryBlockSelectionBehaviour.getInstance();

    private EventFactoryInteractionSystem() {
    }

    public static EventFactoryInteractionSystem getInstance() {
        return INSTANCE;
    }

    public BlockPlaceEvent callBlockPlaceEvent(
            World world,
            EntityHuman who,
            BlockState replacedBlockState,
            int clickedX,
            int clickedY,
            int clickedZ,
            int type
    ) {
        return callBlockPlaceEvent(
                world,
                who,
                replacedBlockState,
                clickedX,
                clickedY,
                clickedZ,
                new com.legacyminecraft.compat.bukkit.inventory.ItemStack(
                        com.legacyminecraft.compat.bukkit.Material.getMaterial(type),
                        1
                )
        );
    }

    public BlockPlaceEvent callBlockPlaceEvent(
            World world,
            EntityHuman who,
            BlockState replacedBlockState,
            int clickedX,
            int clickedY,
            int clickedZ,
            com.legacyminecraft.compat.bukkit.Block block
    ) {
        return callBlockPlaceEvent(
                world,
                who,
                replacedBlockState,
                clickedX,
                clickedY,
                clickedZ,
                new com.legacyminecraft.compat.bukkit.inventory.ItemStack(block.getType(), 1)
        );
    }

    public BlockPlaceEvent callBlockPlaceEvent(
            World world,
            EntityHuman who,
            BlockState replacedBlockState,
            int clickedX,
            int clickedY,
            int clickedZ,
            ItemStack itemStack
    ) {
        CraftWorld craftWorld = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftWorld(world);
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(world);
        Player player = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolvePlayer(who);
        CraftItemStack itemInHand = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.toCraftItemStack(itemStack);

        Block blockClicked = EVENT_FACTORY_BLOCK_SELECTION_BEHAVIOUR.resolveClickedBlock(
                craftWorld, clickedX, clickedY, clickedZ
        );
        Block placedBlock = replacedBlockState.getBlock();
        boolean canBuild = SPAWN_BUILD_PERMISSION_BEHAVIOUR.canBuild(
                craftWorld, player, placedBlock.getX(), placedBlock.getZ());

        BlockPlaceEvent event = BLOCK_PLACE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockPlaceEvent(
                placedBlock, replacedBlockState, blockClicked, itemInHand, player, canBuild);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }

    public PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(
            EntityHuman who,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            ItemStack itemStack
    ) {
        return (PlayerBucketEmptyEvent) getPlayerBucketEvent(
                Type.PLAYER_BUCKET_EMPTY, who, clickedX, clickedY, clickedZ, clickedFace, itemStack, Item.BUCKET
        );
    }

    public PlayerBucketFillEvent callPlayerBucketFillEvent(
            EntityHuman who,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            ItemStack itemStack,
            Item bucket
    ) {
        return (PlayerBucketFillEvent) getPlayerBucketEvent(
                Type.PLAYER_BUCKET_FILL, who, clickedX, clickedY, clickedZ, clickedFace, itemStack, bucket
        );
    }

    public PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, ItemStack itemStack) {
        PLAYER_INTERACT_ACTION_VALIDATION_POLICY.validateAirInteractionAction(action);
        return callPlayerInteractEvent(who, action, 0, 255, 0, 0, itemStack);
    }

    public PlayerInteractEvent callPlayerInteractEvent(
            EntityHuman who,
            Action action,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            ItemStack itemStack
    ) {
        Player player = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolvePlayer(who);
        CraftItemStack itemInHand = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.toCraftItemStack(itemStack);
        CraftWorld craftWorld = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftWorld(player);
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(player);

        Block blockClicked = EVENT_FACTORY_BLOCK_SELECTION_BEHAVIOUR.resolveClickedBlock(
                craftWorld, clickedX, clickedY, clickedZ
        );
        BlockFace blockFace = EVENT_FACTORY_BLOCK_SELECTION_BEHAVIOUR.resolveClickedFace(clickedFace);

        PlayerInteractPreparationBehaviour.PreparedInteraction preparedInteraction =
                PLAYER_INTERACT_PREPARATION_BEHAVIOUR.prepare(action, clickedY, itemInHand);
        Action resolvedAction = preparedInteraction.getAction();
        if (preparedInteraction.useNullClickedBlock()) {
            blockClicked = null;
        }
        CraftItemStack resolvedItemInHand = preparedInteraction.getItemInHand();

        PlayerInteractEvent event = PLAYER_INTERACT_EVENT_CONSTRUCTION_BEHAVIOUR.createPlayerInteractEvent(
                player, resolvedAction, resolvedItemInHand, blockClicked, blockFace);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }

    public BlockDamageEvent callBlockDamageEvent(
            EntityHuman who,
            int x,
            int y,
            int z,
            ItemStack itemStack,
            boolean instaBreak
    ) {
        Player player = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolvePlayer(who);
        CraftItemStack itemInHand = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.toCraftItemStack(itemStack);
        CraftWorld craftWorld = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftWorld(player);
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(player);
        Block blockClicked = EVENT_FACTORY_BLOCK_SELECTION_BEHAVIOUR.resolveClickedBlock(craftWorld, x, y, z);

        BlockDamageEvent event = BLOCK_DAMAGE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockDamageEvent(
                player, blockClicked, itemInHand, instaBreak);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }

    private PlayerEvent getPlayerBucketEvent(
            Type eventType,
            EntityHuman who,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            ItemStack itemStack,
            Item item
    ) {
        Player player = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolvePlayer(who);
        CraftItemStack itemInHand = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.toCraftItemStack(item);
        Material bucket = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveBucketMaterial(itemStack);
        CraftWorld craftWorld = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftWorld(player);
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(player);

        Block blockClicked = EVENT_FACTORY_BLOCK_SELECTION_BEHAVIOUR.resolveClickedBlock(
                craftWorld, clickedX, clickedY, clickedZ
        );
        BlockFace blockFace = EVENT_FACTORY_BLOCK_SELECTION_BEHAVIOUR.resolveClickedFace(clickedFace);
        boolean canBuild = SPAWN_BUILD_PERMISSION_BEHAVIOUR.canBuild(craftWorld, player, clickedX, clickedZ);

        PlayerEvent event = BUCKET_EVENT_CONSTRUCTION_BEHAVIOUR.createBucketEvent(
                eventType, player, blockClicked, blockFace, bucket, itemInHand, canBuild
        );
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }
}
