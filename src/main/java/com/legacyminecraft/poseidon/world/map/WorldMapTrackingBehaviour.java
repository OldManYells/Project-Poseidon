package com.legacyminecraft.poseidon.world.map;

import com.legacyminecraft.compat.bukkit.MapCursor;

import java.util.List;
import java.util.Map;

public final class WorldMapTrackingBehaviour {
    private static final WorldMapTrackingBehaviour INSTANCE = new WorldMapTrackingBehaviour();

    private WorldMapTrackingBehaviour() {
    }

    public static WorldMapTrackingBehaviour getInstance() {
        return INSTANCE;
    }

    public void updateTrackers(Object worldMap, Object entityhuman, Object itemstack, Map trackerByHuman, List trackers, List orienters,
                               int xCenter, int zCenter, byte scale, byte mapDimension, int pseudoRotationSeed) {
        if (!trackerByHuman.containsKey(entityhuman)) {
            WorldMapHumanTracker tracker = new WorldMapHumanTracker(worldMap, entityhuman);
            trackerByHuman.put(entityhuman, tracker);
            trackers.add(tracker);
        }

        orienters.clear();

        for (int index = 0; index < trackers.size(); ++index) {
            WorldMapHumanTracker tracker = (WorldMapHumanTracker) trackers.get(index);

            boolean dead = Boolean.TRUE.equals(getField(tracker.trackee, "dead"));
            Object inventory = getField(tracker.trackee, "inventory");
            boolean contains = (Boolean) invoke(inventory, "c", itemstack);
            if (!dead && contains) {
                float localX = ((Number) getField(tracker.trackee, "locX")).floatValue() - (float) xCenter;
                float localZ = ((Number) getField(tracker.trackee, "locZ")).floatValue() - (float) zCenter;
                localX = localX / (float) (1 << scale);
                localZ = localZ / (float) (1 << scale);
                byte boundsX = 64;
                byte boundsZ = 64;

                if (localX >= (float) (-boundsX) && localZ >= (float) (-boundsZ)
                        && localX <= (float) boundsX && localZ <= (float) boundsZ) {
                    byte iconType = 0;
                    byte iconX = (byte) ((int) ((double) (localX * 2.0F) + 0.5D));
                    byte iconZ = (byte) ((int) ((double) (localZ * 2.0F) + 0.5D));
                    float yaw = ((Number) getField(tracker.trackee, "yaw")).floatValue();
                    byte iconRotation = (byte) ((int) ((double) (yaw * 16.0F / 360.0F) + 0.5D));

                    if (mapDimension < 0) {
                        int pseudo = pseudoRotationSeed / 10;
                        iconRotation = (byte) (pseudo * pseudo * 34187121 + pseudo * 121 >> 15 & 15);
                    }

                    int dimension = ((Number) getField(tracker.trackee, "dimension")).intValue();
                    if (dimension == mapDimension) {
                        orienters.add(new WorldMapOrienter(worldMap, iconType, iconX, iconZ, iconRotation));
                    }
                }
            } else {
                trackerByHuman.remove(tracker.trackee);
                trackers.remove(tracker);
            }
        }
    }

    public byte[] createUpdatePacket(Map trackerByHuman, Object entityhuman, Object itemstack) {
        WorldMapHumanTracker tracker = (WorldMapHumanTracker) trackerByHuman.get(entityhuman);
        if (tracker == null) {
            return null;
        }
        return tracker.a(itemstack);
    }

    public void markDirty(Object worldMapBase, List trackers, int column, int minRow, int maxRow) {
        invokeNoArg(worldMapBase, "a");

        for (int index = 0; index < trackers.size(); ++index) {
            WorldMapHumanTracker tracker = (WorldMapHumanTracker) trackers.get(index);

            if (tracker.b[column] < 0 || tracker.b[column] > minRow) {
                tracker.b[column] = minRow;
            }

            if (tracker.c[column] < 0 || tracker.c[column] < maxRow) {
                tracker.c[column] = maxRow;
            }
        }
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

    private Object invoke(Object target, String methodName, Object argument) {
        try {
            java.lang.reflect.Method[] methods = target.getClass().getMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == 1) {
                    method.setAccessible(true);
                    return method.invoke(target, argument);
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke: " + methodName, exception);
        }
    }

    private void invokeNoArg(Object target, String methodName) {
        try {
            java.lang.reflect.Method method = target.getClass().getMethod(methodName);
            method.setAccessible(true);
            method.invoke(target);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke: " + methodName, exception);
        }
    }
}
