package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

/**
 * Canonical handler for entity interaction packet flow.
 */
public final class EntityInteractionSystem {
    private static final EntityInteractionSystem INSTANCE = new EntityInteractionSystem();
    private final EntityInteractionPolicy interactionPolicy = EntityInteractionPolicy.getInstance();

    private EntityInteractionSystem() {
    }

    public static EntityInteractionSystem getInstance() {
        return INSTANCE;
    }

    public void handleUseEntityPacket(Object minecraftServer, Object server, Object player, Object packet7useentity) {
        if (Boolean.TRUE.equals(getField(player, "dead"))) {
            return;
        }

        int dimension = ((Number) getField(player, "dimension")).intValue();
        Object worldserver = invoke(minecraftServer, "getWorldServer", dimension);
        int target = ((Number) getField(packet7useentity, "target")).intValue();
        Object entity = invoke(worldserver, "getEntity", target);
        Object inventory = getField(player, "inventory");
        Object itemInHand = invoke(inventory, "getItemInHand");

        if (interactionPolicy.canProcessInteraction(cast(player), cast(entity))) {
            int action = ((Number) getField(packet7useentity, "c")).intValue();
            EntityInteractionMode interactionMode = interactionPolicy.resolveInteractionMode(action);
            if (interactionMode == EntityInteractionMode.INTERACT) {
                Object bukkitPlayer = invoke(player, "getBukkitEntity");
                Object bukkitEntity = invoke(entity, "getBukkitEntity");

                boolean insideVehicle = Boolean.TRUE.equals(invoke(bukkitPlayer, "isInsideVehicle"));
                boolean storageMinecart = bukkitEntity != null && bukkitEntity.getClass().getName().endsWith("StorageMinecart");
                if (interactionPolicy.shouldCancelStorageMinecartInteraction(insideVehicle, storageMinecart)) {
                    return;
                }

                Object event = LegacyCompatGatewayRegistry.gateway().createPlayerInteractEntityEvent(bukkitPlayer, bukkitEntity);
                Object pluginManager = invoke(server, "getPluginManager");
                invoke(pluginManager, "callEvent", event);

                if (Boolean.TRUE.equals(invoke(event, "isCancelled"))) {
                    return;
                }

                invoke(player, "c", entity);
                synchronizeInfiniteItemStack(player, itemInHand);
            } else if (interactionMode == EntityInteractionMode.ATTACK) {
                invoke(player, "d", entity);
                synchronizeInfiniteItemStack(player, itemInHand);
            }
        }
    }

    public boolean shouldCancelStorageMinecartInteraction(boolean playerInsideVehicle, boolean targetIsStorageMinecart) {
        return interactionPolicy.shouldCancelStorageMinecartInteraction(playerInsideVehicle, targetIsStorageMinecart);
    }

    private void synchronizeInfiniteItemStack(Object player, Object itemInHand) {
        if (itemInHand != null && ((Number) getField(itemInHand, "count")).intValue() <= -1) {
            Object activeContainer = getField(player, "activeContainer");
            invoke(player, "updateInventory", activeContainer);
        }
    }

    private Object getField(Object target, String name) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private Object invoke(Object target, String methodName, Object... args) {
        try {
            for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T cast(Object value) {
        return (T) value;
    }
}
