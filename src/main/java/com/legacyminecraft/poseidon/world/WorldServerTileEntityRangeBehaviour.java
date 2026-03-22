package com.legacyminecraft.poseidon.world;

import net.minecraft.server.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behaviour for world-server tile-entity range collection.
 */
public final class WorldServerTileEntityRangeBehaviour {
    private static final WorldServerTileEntityRangeBehaviour INSTANCE = new WorldServerTileEntityRangeBehaviour();

    private WorldServerTileEntityRangeBehaviour() {
    }

    public static WorldServerTileEntityRangeBehaviour getInstance() {
        return INSTANCE;
    }

    public List collectInRange(
            List tileEntities,
            int minX,
            int minY,
            int minZ,
            int maxX,
            int maxY,
            int maxZ
    ) {
        ArrayList entitiesInRange = new ArrayList();

        for (int index = 0; index < tileEntities.size(); ++index) {
            TileEntity tileEntity = (TileEntity) tileEntities.get(index);

            if (this.isWithinRange(tileEntity, minX, minY, minZ, maxX, maxY, maxZ)) {
                entitiesInRange.add(tileEntity);
            }
        }

        return entitiesInRange;
    }

    private boolean isWithinRange(TileEntity tileEntity, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return tileEntity.x >= minX
                && tileEntity.y >= minY
                && tileEntity.z >= minZ
                && tileEntity.x < maxX
                && tileEntity.y < maxY
                && tileEntity.z < maxZ;
    }
}
