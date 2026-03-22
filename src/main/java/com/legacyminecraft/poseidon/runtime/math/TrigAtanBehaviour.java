package com.legacyminecraft.poseidon.runtime.math;

/**
 * Canonical arctangent approximation behavior used by legacy trig wrappers.
 */
public final class TrigAtanBehaviour {
    private static final TrigAtanBehaviour INSTANCE = new TrigAtanBehaviour();

    private static final double SQUARE_ROOT_TWO_PLUS_ONE = 2.414213562373095048802e0;
    private static final double SQUARE_ROOT_TWO_MINUS_ONE = .414213562373095048802e0;
    private static final double P4 = .161536412982230228262e2;
    private static final double P3 = .26842548195503973794141e3;
    private static final double P2 = .11530293515404850115428136e4;
    private static final double P1 = .178040631643319697105464587e4;
    private static final double P0 = .89678597403663861959987488e3;
    private static final double Q4 = .5895697050844462222791e2;
    private static final double Q3 = .536265374031215315104235e3;
    private static final double Q2 = .16667838148816337184521798e4;
    private static final double Q1 = .207933497444540981287275926e4;
    private static final double Q0 = .89678597403663861962481162e3;
    private static final double HALF_PI = 1.5707963267948966135E0;

    private TrigAtanBehaviour() {
    }

    public static TrigAtanBehaviour getInstance() {
        return INSTANCE;
    }

    private double mxatan(double argument) {
        double argumentSquared = argument * argument;
        double value = ((((P4 * argumentSquared + P3) * argumentSquared + P2) * argumentSquared + P1) * argumentSquared + P0);
        value = value / (((((argumentSquared + Q4) * argumentSquared + Q3) * argumentSquared + Q2) * argumentSquared + Q1) * argumentSquared + Q0);
        return value * argument;
    }

    private double msatan(double argument) {
        return argument < SQUARE_ROOT_TWO_MINUS_ONE
                ? mxatan(argument)
                : argument > SQUARE_ROOT_TWO_PLUS_ONE
                ? HALF_PI - mxatan(1 / argument)
                : HALF_PI / 2 + mxatan((argument - 1) / (argument + 1));
    }

    public double atan(double argument) {
        return argument > 0 ? msatan(argument) : -msatan(-argument);
    }

    public double atan2(double y, double x) {
        if (y + x == y) {
            return y >= 0 ? HALF_PI : -HALF_PI;
        }
        double result = atan(y / x);
        return x < 0 ? result <= 0 ? result + Math.PI : result - Math.PI : result;
    }
}

