package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.runtime.math.TrigAtanBehaviour;
/**
 * Credits for this class goes to user aioobe on stackoverflow.com
 * Source: http://stackoverflow.com/questions/4454630/j2me-calculate-the-the-distance-between-2-latitude-and-longitude
 *
 */
public class TrigMath {
    private static final TrigAtanBehaviour TRIG_ATAN_BEHAVIOUR = TrigAtanBehaviour.getInstance();

    public static double atan(double arg) {
        return TRIG_ATAN_BEHAVIOUR.atan(arg);
    }

    public static double atan2(double arg1, double arg2) {
        return TRIG_ATAN_BEHAVIOUR.atan2(arg1, arg2);
    }
}
