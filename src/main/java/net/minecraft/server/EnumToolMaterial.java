package net.minecraft.server;

public enum EnumToolMaterial {

    WOOD("WOOD", 0, 0, 59, 2.0F, 0),
    STONE("STONE", 1, 1, 131, 4.0F, 1),
    IRON("IRON", 2, 2, 250, 6.0F, 2),
    DIAMOND("EMERALD", 3, 3, 1561, 8.0F, 3),
    GOLD("GOLD", 4, 0, 32, 12.0F, 0);

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final int damageVsEntity;

    private static final EnumToolMaterial[] j = new EnumToolMaterial[] { WOOD, STONE, IRON, DIAMOND, GOLD};

    private EnumToolMaterial(String name, int ordinal, int harvestLevel, int maxUses, float efficiency, int damageVsEntity) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.damageVsEntity = damageVsEntity;
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public int getDamageVsEntity() {
        return this.damageVsEntity;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Deprecated
    public int a() {
        return this.getMaxUses();
    }

    @Deprecated
    public float b() {
        return this.getEfficiency();
    }

    @Deprecated
    public int c() {
        return this.getDamageVsEntity();
    }

    @Deprecated
    public int d() {
        return this.getHarvestLevel();
    }
}
