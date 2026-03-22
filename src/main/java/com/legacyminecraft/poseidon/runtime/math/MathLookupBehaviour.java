package com.legacyminecraft.poseidon.runtime.math;

public final class MathLookupBehaviour {
    private static final MathLookupBehaviour INSTANCE = new MathLookupBehaviour();

    private MathLookupBehaviour() {
    }

    public static MathLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public float[] createSineLookupTable() {
        float[] values = new float[65536];

        for (int i = 0; i < 65536; ++i) {
            values[i] = (float) Math.sin((double) i * Math.PI * 2.0D / 65536.0D);
        }

        return values;
    }

    public float sin(float value, float[] lookupTable) {
        return lookupTable[(int) (value * 10430.378F) & '\uffff'];
    }

    public float cos(float value, float[] lookupTable) {
        return lookupTable[(int) (value * 10430.378F + 16384.0F) & '\uffff'];
    }

    public float squareRoot(float value) {
        return (float) Math.sqrt((double) value);
    }

    public float squareRoot(double value) {
        return (float) Math.sqrt(value);
    }

    public int floorFloat(float value) {
        int floored = (int) value;

        return value < (float) floored ? floored - 1 : floored;
    }

    public int floorDouble(double value) {
        int floored = (int) value;

        return value < (double) floored ? floored - 1 : floored;
    }

    public float absolute(float value) {
        return value >= 0.0F ? value : -value;
    }

    public double maxAbsolute(double left, double right) {
        if (left < 0.0D) {
            left = -left;
        }

        if (right < 0.0D) {
            right = -right;
        }

        return left > right ? left : right;
    }
}
