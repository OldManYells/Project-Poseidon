package com.legacyminecraft.poseidon.inventory;

import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;

/**
 * Canonical handler for hotbar item selection packets.
 */
public final class HotbarSelectionBehaviour {
    private static final HotbarSelectionBehaviour INSTANCE = new HotbarSelectionBehaviour();

    private HotbarSelectionBehaviour() {
    }

    public static HotbarSelectionBehaviour getInstance() {
        return INSTANCE;
    }

    public SwitchResult handleSwitch(Object server, Object player, Object packet16blockitemswitch) {
        boolean dead = Boolean.TRUE.equals(getField(player, "dead"));
        if (dead) {
            return SwitchResult.IGNORED_PLAYER_DEAD;
        }

        int itemInHandIndex = ((Number) getField(packet16blockitemswitch, "itemInHandIndex")).intValue();
        if (!isValidSelectionIndex(itemInHandIndex)) {
            return SwitchResult.INVALID_SELECTION;
        }

        Object bukkitPlayer = invoke(player, "getBukkitEntity");
        Object inventory = getField(player, "inventory");
        int previousIndex = ((Number) getField(inventory, "itemInHandIndex")).intValue();
        Object event = NetworkCompatGatewayRegistry.gateway()
                .createPlayerItemHeldEvent(bukkitPlayer, previousIndex, itemInHandIndex);
        Object pluginManager = invoke(server, "getPluginManager");
        invoke(pluginManager, "callEvent", event);
        setField(inventory, "itemInHandIndex", itemInHandIndex);
        return SwitchResult.APPLIED;
    }

    public boolean isValidSelectionIndex(int index) {
        return index >= 0 && index <= InventoryPlayer.e();
    }

    public enum SwitchResult {
        APPLIED,
        INVALID_SELECTION,
        IGNORED_PLAYER_DEAD
    }

    private Object getField(Object target, String fieldName) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read field: " + fieldName, exception);
        }
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to write field: " + fieldName, exception);
        }
    }

    private Object invoke(Object target, String methodName, Object... args) {
        try {
            java.lang.reflect.Method[] methods = target.getClass().getMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke: " + methodName, exception);
        }
    }

}
