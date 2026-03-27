package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftLivingEntity health validation/mutation and eye-view helpers.
 */
public final class LivingEntityHealthAndViewBehaviour {
    private static final int MIN_HEALTH = 0;
    private static final int MAX_HEALTH = 200;
    private static final double DEFAULT_EYE_HEIGHT = 1.0D;
    private static final LivingEntityHealthAndViewBehaviour INSTANCE = new LivingEntityHealthAndViewBehaviour();

    private LivingEntityHealthAndViewBehaviour() {
    }

    public static LivingEntityHealthAndViewBehaviour getInstance() {
        return INSTANCE;
    }

    public int getHealth(Object livingEntity) {
        return ((Number) BridgeReflection.getField(livingEntity, "health")).intValue();
    }

    public void setHealth(Object livingEntity, int health) {
        if (health < MIN_HEALTH || health > MAX_HEALTH) {
            throw new IllegalArgumentException("Health must be between 0 and 200");
        }

        if (health == 0 && hasField(livingEntity, "playerUUID")) {
            BridgeReflection.invoke(livingEntity, "die", null);
        }

        BridgeReflection.setField(livingEntity, "health", health);
    }

    public double getDefaultEyeHeight() {
        return DEFAULT_EYE_HEIGHT;
    }

    public <T> T computeEyeLocation(T baseLocation, double eyeHeight) {
        double y = ((Number) BridgeReflection.invoke(baseLocation, "getY")).doubleValue();
        BridgeReflection.invoke(baseLocation, "setY", y + eyeHeight);
        return baseLocation;
    }

    private boolean hasField(Object target, String fieldName) {
        try {
            BridgeReflection.getField(target, fieldName);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
