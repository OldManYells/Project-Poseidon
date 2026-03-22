package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * Canonical behaviour for CraftBlockState material-data validation and creation policy.
 */
public final class BlockStateDataBehaviour {
    private static final BlockStateDataBehaviour INSTANCE = new BlockStateDataBehaviour();

    private BlockStateDataBehaviour() {
    }

    public static BlockStateDataBehaviour getInstance() {
        return INSTANCE;
    }

    public MaterialData validateData(Material currentType, MaterialData newData) {
        if (currentType == null || currentType.getData() == null) {
            return newData;
        }

        if (newData.getClass() == currentType.getData() || newData.getClass() == MaterialData.class) {
            return newData;
        }

        throw new IllegalArgumentException("Provided data is not of type "
                + currentType.getData().getName() + ", found " + newData.getClass().getName());
    }

    public MaterialData createData(int typeId, byte rawData) {
        Material material = Material.getMaterial(typeId);
        if (material == null || material.getData() == null) {
            return new MaterialData(typeId, rawData);
        }

        return material.getNewData(rawData);
    }
}
