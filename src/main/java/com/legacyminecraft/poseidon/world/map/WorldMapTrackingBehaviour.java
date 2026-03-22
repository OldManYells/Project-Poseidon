package com.legacyminecraft.poseidon.world.map;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.WorldMap;
import net.minecraft.server.WorldMapBase;
import net.minecraft.server.WorldMapHumanTracker;
import net.minecraft.server.WorldMapOrienter;

import java.util.List;
import java.util.Map;

public final class WorldMapTrackingBehaviour {
    private static final WorldMapTrackingBehaviour INSTANCE = new WorldMapTrackingBehaviour();

    private WorldMapTrackingBehaviour() {
    }

    public static WorldMapTrackingBehaviour getInstance() {
        return INSTANCE;
    }

    public void updateTrackers(WorldMap worldMap, EntityHuman entityhuman, ItemStack itemstack, Map trackerByHuman, List trackers, List orienters,
                               int xCenter, int zCenter, byte scale, byte mapDimension, int pseudoRotationSeed) {
        if (!trackerByHuman.containsKey(entityhuman)) {
            WorldMapHumanTracker tracker = new WorldMapHumanTracker(worldMap, entityhuman);
            trackerByHuman.put(entityhuman, tracker);
            trackers.add(tracker);
        }

        orienters.clear();

        for (int index = 0; index < trackers.size(); ++index) {
            WorldMapHumanTracker tracker = (WorldMapHumanTracker) trackers.get(index);

            if (!tracker.trackee.dead && tracker.trackee.inventory.c(itemstack)) {
                float localX = (float) (tracker.trackee.locX - (double) xCenter) / (float) (1 << scale);
                float localZ = (float) (tracker.trackee.locZ - (double) zCenter) / (float) (1 << scale);
                byte boundsX = 64;
                byte boundsZ = 64;

                if (localX >= (float) (-boundsX) && localZ >= (float) (-boundsZ)
                        && localX <= (float) boundsX && localZ <= (float) boundsZ) {
                    byte iconType = 0;
                    byte iconX = (byte) ((int) ((double) (localX * 2.0F) + 0.5D));
                    byte iconZ = (byte) ((int) ((double) (localZ * 2.0F) + 0.5D));
                    byte iconRotation = (byte) ((int) ((double) (tracker.trackee.yaw * 16.0F / 360.0F) + 0.5D));

                    if (mapDimension < 0) {
                        int pseudo = pseudoRotationSeed / 10;
                        iconRotation = (byte) (pseudo * pseudo * 34187121 + pseudo * 121 >> 15 & 15);
                    }

                    if (tracker.trackee.dimension == mapDimension) {
                        orienters.add(new WorldMapOrienter(worldMap, iconType, iconX, iconZ, iconRotation));
                    }
                }
            } else {
                trackerByHuman.remove(tracker.trackee);
                trackers.remove(tracker);
            }
        }
    }

    public byte[] createUpdatePacket(Map trackerByHuman, EntityHuman entityhuman, ItemStack itemstack) {
        WorldMapHumanTracker tracker = (WorldMapHumanTracker) trackerByHuman.get(entityhuman);
        if (tracker == null) {
            return null;
        }
        return tracker.a(itemstack);
    }

    public void markDirty(WorldMapBase worldMapBase, List trackers, int column, int minRow, int maxRow) {
        worldMapBase.a();

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
}
