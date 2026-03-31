package net.minecraft.server;

public class MathHelper {

    private static float[] SIN_TABLE = new float[65536];

    public MathHelper() {}

    public static final float sin(float value) {
        return SIN_TABLE[(int) (value * 10430.378F) & '\uffff'];
    }

    public static final float cos(float value) {
        return SIN_TABLE[(int) (value * 10430.378F + 16384.0F) & '\uffff'];
    }

    public static final float sqrt(float value) {
        return (float) Math.sqrt((double) value);
    }

    public static final float sqrt(double value) {
        return (float) Math.sqrt(value);
    }

    public static int floor(float value) {
        int truncated = (int) value;
        return value < (float) truncated ? truncated - 1 : truncated;
    }

    public static int floor(double value) {
        int truncated = (int) value;
        return value < (double) truncated ? truncated - 1 : truncated;
    }

    public static float abs(float value) {
        return value >= 0.0F ? value : -value;
    }

    public static double absMax(double a, double b) {
        if (a < 0.0D) {
            a = -a;
        }

        if (b < 0.0D) {
            b = -b;
        }

        return a > b ? a : b;
    }

    @Deprecated
    public static final float c(float value) {
        return sqrt(value);
    }

    @Deprecated
    public static final float a(double value) {
        return sqrt(value);
    }

    @Deprecated
    public static int d(float value) {
        return floor(value);
    }

    @Deprecated
    public static double a(double a, double b) {
        return absMax(a, b);
    }

    static {
        for (int i = 0; i < 65536; ++i) {
            SIN_TABLE[i] = (float) Math.sin((double) i * Math.PI * 2.0D / 65536.0D);
        }
    }
}
