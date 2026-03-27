package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftTNTPrimed property access/mutation.
 */
public final class PrimedTntPropertyBehaviour {
    private static final PrimedTntPropertyBehaviour INSTANCE = new PrimedTntPropertyBehaviour();

    private PrimedTntPropertyBehaviour() {
    }

    public static PrimedTntPropertyBehaviour getInstance() {
        return INSTANCE;
    }

    public float getYield(EntityTNTPrimed tnt) {
        return tnt.yield;
    }

    public void setYield(EntityTNTPrimed tnt, float yield) {
        tnt.yield = yield;
    }

    public boolean isIncendiary(EntityTNTPrimed tnt) {
        return tnt.isIncendiary;
    }

    public void setIncendiary(EntityTNTPrimed tnt, boolean incendiary) {
        tnt.isIncendiary = incendiary;
    }

    public int getFuseTicks(EntityTNTPrimed tnt) {
        return tnt.fuseTicks;
    }

    public void setFuseTicks(EntityTNTPrimed tnt, int fuseTicks) {
        tnt.fuseTicks = fuseTicks;
    }
}

