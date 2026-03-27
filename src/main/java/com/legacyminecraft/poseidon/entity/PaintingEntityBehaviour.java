package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

public final class PaintingEntityBehaviour {
    private static final PaintingEntityBehaviour INSTANCE = new PaintingEntityBehaviour();
    private static final int STABILITY_CHECK_INTERVAL = 100;

    private PaintingEntityBehaviour() {
    }

    public static PaintingEntityBehaviour getInstance() {
        return INSTANCE;
    }

    public int tickAndMaybeResetCounter(int currentCounter) {
        int updatedCounter = currentCounter + 1;
        if (updatedCounter == STABILITY_CHECK_INTERVAL) {
            return 0;
        }
        return updatedCounter;
    }

    public boolean shouldRunStabilityCheck(int previousCounter, int updatedCounter, boolean worldStatic) {
        return previousCounter + 1 == STABILITY_CHECK_INTERVAL && !worldStatic;
    }

    public boolean breakFromWorldIfUnstable(Object painting) {
        if (isCancelledByPaintingEvent(painting, true, null)) {
            return false;
        }

        dropPaintingItem(painting);
        return true;
    }

    public boolean breakFromEntity(Object painting, Object attacker) {
        Object bukkitAttacker = invokeNoArg(attacker, "getBukkitEntity");
        if (isCancelledByPaintingEvent(painting, false, bukkitAttacker)) {
            return false;
        }

        dropPaintingItem(painting);
        return true;
    }

    public boolean shouldBreakFromMotion(boolean worldStatic, double deltaX, double deltaY, double deltaZ) {
        return !worldStatic && deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 0.0D;
    }

    public void dropPaintingItem(Object painting) {
        if (painting == null) {
            return;
        }
        invokeNoArg(painting, "die");
        try {
            Object world = getField(painting, "world");
            if (world == null) {
                return;
            }
            double locX = ((Number) getField(painting, "locX")).doubleValue();
            double locY = ((Number) getField(painting, "locY")).doubleValue();
            double locZ = ((Number) getField(painting, "locZ")).doubleValue();
            Object paintingItem = LegacyCompatGatewayRegistry.gateway().paintingItemSingleton();
            Object itemStack = LegacyCompatGatewayRegistry.gateway().createItemStackFromItem(paintingItem);
            Object entityItem = LegacyCompatGatewayRegistry.gateway().createEntityItem(world, locX, locY, locZ, itemStack);
            LegacyCompatGatewayRegistry.gateway().addEntityToWorld(world, entityItem);
        } catch (Exception ignored) {
        }
    }

    public void writeNbt(Object nbt, int direction, Object art, int tileX, int tileY, int tileZ) {
        invokeNbtSet(nbt, "a", Byte.TYPE, "Dir", Byte.valueOf((byte) direction));
        invokeNbtSet(nbt, "setString", String.class, "Motive", artName(art));
        invokeNbtSet(nbt, "a", Integer.TYPE, "TileX", Integer.valueOf(tileX));
        invokeNbtSet(nbt, "a", Integer.TYPE, "TileY", Integer.valueOf(tileY));
        invokeNbtSet(nbt, "a", Integer.TYPE, "TileZ", Integer.valueOf(tileZ));
    }

    public LoadedState readNbt(Object nbt) {
        int direction = readIntTag(nbt, "c", "Dir");
        int tileX = readIntTag(nbt, "e", "TileX");
        int tileY = readIntTag(nbt, "e", "TileY");
        int tileZ = readIntTag(nbt, "e", "TileZ");
        String motive = readStringTag(nbt, "Motive");
        Object art = resolveArt(motive);
        return new LoadedState(direction, tileX, tileY, tileZ, art);
    }

    private Object resolveArt(String motive) {
        return LegacyCompatGatewayRegistry.gateway().resolveArtByNameOrDefault(motive);
    }

    private boolean isCancelledByPaintingEvent(Object painting, boolean byWorld, Object attacker) {
        if (painting == null) {
            return false;
        }
        try {
            Object bukkitPainting = invokeNoArg(painting, "getBukkitEntity");
            Object event = byWorld
                    ? LegacyCompatGatewayRegistry.gateway().createPaintingBreakByWorldEvent(bukkitPainting)
                    : LegacyCompatGatewayRegistry.gateway().createPaintingBreakByEntityEvent(bukkitPainting, attacker);
            Object world = getField(painting, "world");
            Object server = world.getClass().getMethod("getServer").invoke(world);
            Object pluginManager = server.getClass().getMethod("getPluginManager").invoke(server);
            invokeCallEvent(pluginManager, event);
            return LegacyCompatGatewayRegistry.gateway().isEventCancelled(event);
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }

    private void invokeCallEvent(Object pluginManager, Object event) throws ReflectiveOperationException {
        for (java.lang.reflect.Method method : pluginManager.getClass().getMethods()) {
            if (!method.getName().equals("callEvent") || method.getParameterTypes().length != 1) {
                continue;
            }
            if (event == null || method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                method.invoke(pluginManager, event);
                return;
            }
        }
        throw new NoSuchMethodException("callEvent");
    }

    private void invokeNbtSet(Object nbt, String method, Class<?> valueType, String key, Object value) {
        if (nbt == null) {
            return;
        }
        try {
            nbt.getClass().getMethod(method, String.class, valueType).invoke(nbt, key, value);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private String readStringTag(Object nbt, String key) {
        if (nbt == null) {
            return "";
        }
        try {
            Object value = nbt.getClass().getMethod("getString", String.class).invoke(nbt, key);
            return value == null ? "" : value.toString();
        } catch (ReflectiveOperationException ignored) {
            return "";
        }
    }

    private int readIntTag(Object nbt, String method, String key) {
        if (nbt == null) {
            return 0;
        }
        try {
            Object value = nbt.getClass().getMethod(method, String.class).invoke(nbt, key);
            return ((Number) value).intValue();
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private String artName(Object art) {
        if (art == null) {
            return "";
        }
        try {
            Object value = art.getClass().getField("A").get(art);
            return value == null ? "" : value.toString();
        } catch (ReflectiveOperationException ignored) {
            return "";
        }
    }

    private Object invokeNoArg(Object target, String methodName) {
        if (target == null) {
            return null;
        }
        try {
            return target.getClass().getMethod(methodName).invoke(target);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private Object getField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }
        try {
            return target.getClass().getField(fieldName).get(target);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    public static final class LoadedState {
        public final int direction;
        public final int tileX;
        public final int tileY;
        public final int tileZ;
        public final Object art;

        public LoadedState(int direction, int tileX, int tileY, int tileZ, Object art) {
            this.direction = direction;
            this.tileX = tileX;
            this.tileY = tileY;
            this.tileZ = tileZ;
            this.art = art;
        }
    }
}
