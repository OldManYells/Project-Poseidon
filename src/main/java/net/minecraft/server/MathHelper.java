package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.math.MathLookupBehaviour;

public class MathHelper {
    private static final MathLookupBehaviour MATH_LOOKUP_BEHAVIOUR = MathLookupBehaviour.getInstance();

    private static float[] a = MATH_LOOKUP_BEHAVIOUR.createSineLookupTable();

    public MathHelper() {}

    public static final float sin(float f) {
        return MATH_LOOKUP_BEHAVIOUR.sin(f, a);
    }

    public static final float cos(float f) {
        return MATH_LOOKUP_BEHAVIOUR.cos(f, a);
    }

    public static final float c(float f) {
        return MATH_LOOKUP_BEHAVIOUR.squareRoot(f);
    }

    public static final float a(double d0) {
        return MATH_LOOKUP_BEHAVIOUR.squareRoot(d0);
    }

    public static int d(float f) {
        return MATH_LOOKUP_BEHAVIOUR.floorFloat(f);
    }

    public static int floor(double d0) {
        return MATH_LOOKUP_BEHAVIOUR.floorDouble(d0);
    }

    public static float abs(float f) {
        return MATH_LOOKUP_BEHAVIOUR.absolute(f);
    }

    public static double a(double d0, double d1) {
        return MATH_LOOKUP_BEHAVIOUR.maxAbsolute(d0, d1);
    }
}
