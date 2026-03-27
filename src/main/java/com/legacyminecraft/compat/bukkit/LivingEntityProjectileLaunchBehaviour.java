package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftLivingEntity projectile-launch bridge operations.
 */
public final class LivingEntityProjectileLaunchBehaviour {
    private static final LivingEntityProjectileLaunchBehaviour INSTANCE = new LivingEntityProjectileLaunchBehaviour();

    private LivingEntityProjectileLaunchBehaviour() {
    }

    public static LivingEntityProjectileLaunchBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T throwEgg(Object world, Object thrower) {
        Object egg;
        try {
            egg = BridgeReflection.invoke(world, "createEgg", thrower);
        } catch (Exception ignored) {
            egg = createViaCtor("net.minecraft.server.EntityEgg", world, thrower);
        }
        BridgeReflection.invoke(world, "addEntity", egg);
        return BridgeReflection.cast(BridgeReflection.invoke(egg, "getBukkitEntity"));
    }

    public <T> T throwSnowball(Object world, Object thrower) {
        Object snowball = createViaCtor("net.minecraft.server.EntitySnowball", world, thrower);
        BridgeReflection.invoke(world, "addEntity", snowball);
        return BridgeReflection.cast(BridgeReflection.invoke(snowball, "getBukkitEntity"));
    }

    public <T> T shootArrow(Object world, Object shooter) {
        Object arrow = createViaCtor("net.minecraft.server.EntityArrow", world, shooter);
        BridgeReflection.invoke(world, "addEntity", arrow);
        return BridgeReflection.cast(BridgeReflection.invoke(arrow, "getBukkitEntity"));
    }

    private Object createViaCtor(String className, Object arg0, Object arg1) {
        try {
            Class<?> type = Class.forName(className);
            java.lang.reflect.Constructor<?>[] constructors = type.getDeclaredConstructors();
            for (java.lang.reflect.Constructor<?> ctor : constructors) {
                if (ctor.getParameterTypes().length == 2) {
                    ctor.setAccessible(true);
                    return ctor.newInstance(arg0, arg1);
                }
            }
            throw new IllegalStateException("No compatible constructor found: " + className);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to construct projectile: " + className, exception);
        }
    }
}
