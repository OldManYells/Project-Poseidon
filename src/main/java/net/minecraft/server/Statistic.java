package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.StatisticRegistry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Statistic {

    public final int e;
    public final String f;
    public boolean g;
    public String h;
    private final Counter a;
    private static NumberFormat b = NumberFormat.getIntegerInstance(Locale.US);
    public static Counter i = new UnknownCounter();
    private static DecimalFormat c = new DecimalFormat("########0.00");
    public static Counter j = new TimeCounter();
    public static Counter k = new DistancesCounter();

    public Statistic(int i, String s, Counter counter) {
        this.g = false;
        this.e = i;
        this.f = s;
        this.a = counter;
    }

    public Statistic(int i, String s) {
        this(i, s, Statistic.i);
    }

    public Statistic e() {
        this.g = true;
        return this;
    }

    public Statistic d() {
        return (Statistic) StatisticRegistry.getInstance().register(this, StatisticList.a, StatisticList.b);
    }

    public String toString() {
        return this.f;
    }
}
