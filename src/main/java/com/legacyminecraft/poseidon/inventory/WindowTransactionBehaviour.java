package com.legacyminecraft.poseidon.inventory;

import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;

import java.util.ArrayList;
import java.util.Map;

/**
 * Canonical handler for container window click/transaction packet flows.
 */
public final class WindowTransactionBehaviour {
    private static final WindowTransactionBehaviour INSTANCE = new WindowTransactionBehaviour();

    private WindowTransactionBehaviour() {
    }

    public static WindowTransactionBehaviour getInstance() {
        return INSTANCE;
    }

    public void handleCloseWindow(Object player) {
        if (Boolean.TRUE.equals(getField(player, "dead"))) {
            return;
        }
        invoke(player, "A");
    }

    public void handleWindowClick(Object player, Object packet102windowclick, Map pendingTransactions) {
        if (Boolean.TRUE.equals(getField(player, "dead"))) {
            return;
        }

        Object activeContainer = getField(player, "activeContainer");
        int windowId = ((Number) getField(activeContainer, "windowId")).intValue();
        int clickWindow = ((Number) getField(packet102windowclick, "a")).intValue();
        boolean canInteract = Boolean.TRUE.equals(invoke(activeContainer, "c", player));
        if (windowId == clickWindow && canInteract) {
            Object itemstack = invoke(
                    activeContainer,
                    "a",
                    getField(packet102windowclick, "b"),
                    getField(packet102windowclick, "c"),
                    getField(packet102windowclick, "f"),
                    player
            );

            Object expectedItem = getField(packet102windowclick, "e");
            boolean sameItem = Boolean.TRUE.equals(invokeStatic("com.legacyminecraft.poseidon.inventory.ItemStack", "equals", expectedItem, itemstack));
            Object netServerHandler = getField(player, "netServerHandler");
            if (sameItem) {
                Object txnPacket = NetworkCompatGatewayRegistry.gateway().createTransactionPacket(
                        ((Number) getField(packet102windowclick, "a")).intValue(),
                        ((Number) getField(packet102windowclick, "d")).shortValue(),
                        true
                );
                invoke(netServerHandler, "sendPacket", txnPacket);
                setField(player, "h", true);
                invoke(activeContainer, "a");
                invoke(player, "z");
                setField(player, "h", false);
            } else {
                pendingTransactions.put(Integer.valueOf(windowId), Short.valueOf(((Number) getField(packet102windowclick, "d")).shortValue()));
                Object txnPacket = NetworkCompatGatewayRegistry.gateway().createTransactionPacket(
                        ((Number) getField(packet102windowclick, "a")).intValue(),
                        ((Number) getField(packet102windowclick, "d")).shortValue(),
                        false
                );
                invoke(netServerHandler, "sendPacket", txnPacket);
                invoke(activeContainer, "a", player, false);
                ArrayList arraylist = new ArrayList();

                java.util.List slots = cast(getField(activeContainer, "e"));
                for (int i = 0; i < slots.size(); ++i) {
                    Object slot = slots.get(i);
                    arraylist.add(invoke(slot, "getItem"));
                }

                invoke(player, "a", activeContainer, arraylist);
            }
        }
    }

    public void handleTransactionConfirmation(Object player, Object packet106transaction, Map pendingTransactions) {
        if (Boolean.TRUE.equals(getField(player, "dead"))) {
            return;
        }

        Object activeContainer = getField(player, "activeContainer");
        int windowId = ((Number) getField(activeContainer, "windowId")).intValue();
        Short oshort = (Short) pendingTransactions.get(Integer.valueOf(windowId));

        short txId = ((Number) getField(packet106transaction, "b")).shortValue();
        int txWindow = ((Number) getField(packet106transaction, "a")).intValue();
        boolean canInteract = Boolean.TRUE.equals(invoke(activeContainer, "c", player));
        if (oshort != null && txId == oshort.shortValue() && windowId == txWindow && !canInteract) {
            invoke(activeContainer, "a", player, true);
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

    private void setField(Object target, String name, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            field.setAccessible(true);
            field.set(target, value);
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

    private Object invokeStatic(String className, String methodName, Object... args) {
        try {
            Class<?> type = Class.forName(className);
            for (java.lang.reflect.Method method : type.getMethods()) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                    method.setAccessible(true);
                    return method.invoke(null, args);
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
