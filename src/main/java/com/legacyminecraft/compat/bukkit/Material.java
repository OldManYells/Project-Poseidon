package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat material scaffold.
 */
public enum Material {
    AIR(0, null),
    MAP(358, MaterialData.class),
    NOTE_BLOCK(25, MaterialData.class),
    REDSTONE_WIRE(55, MaterialData.class);

    private final int id;
    private final Class<? extends MaterialData> data;

    Material(int id, Class<? extends MaterialData> data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Class<? extends MaterialData> getData() {
        return data;
    }

    public MaterialData getNewData(byte rawData) {
        return new MaterialData(id, rawData);
    }

    public static Material getMaterial(int id) {
        for (Material material : values()) {
            if (material.id == id) {
                return material;
            }
        }
        return null;
    }
}
