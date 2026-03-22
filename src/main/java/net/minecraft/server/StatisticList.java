package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.StatisticArrayBuilder;
import com.legacyminecraft.poseidon.world.stats.CraftingStatisticBootstrap;
import com.legacyminecraft.poseidon.world.stats.StatisticBootstrap;

import java.util.*;

public class StatisticList {

    protected static Map a = new HashMap();
    public static List b = new ArrayList();
    public static List c = new ArrayList();
    public static List d = new ArrayList();
    public static List e = new ArrayList();
    public static Statistic f;
    public static Statistic g;
    public static Statistic h;
    public static Statistic i;
    public static Statistic j;
    public static Statistic k;
    public static Statistic l;
    public static Statistic m;
    public static Statistic n;
    public static Statistic o;
    public static Statistic p;
    public static Statistic q;
    public static Statistic r;
    public static Statistic s;
    public static Statistic t;
    public static Statistic u;
    public static Statistic v;
    public static Statistic w;
    public static Statistic x;
    public static Statistic y;
    public static Statistic z;
    public static Statistic A;
    public static Statistic B;
    public static Statistic[] C;
    public static Statistic[] D;
    public static Statistic[] E;
    public static Statistic[] F;
    private static boolean G;
    private static boolean H;
    private static final StatisticBootstrap statisticBootstrapService = StatisticBootstrap.getInstance();

    public StatisticList() {}

    public static void a() {}

    public static void b() {
        E = a(E, "stat.useItem", 16908288, 0, Block.byId.length);
        F = b(F, "stat.breakItem", 16973824, 0, Block.byId.length);
        G = true;
        d();
    }

    public static void c() {
        E = a(E, "stat.useItem", 16908288, Block.byId.length, 32000);
        F = b(F, "stat.breakItem", 16973824, Block.byId.length, 32000);
        H = true;
        d();
    }

    public static void d() {
        Statistic[] initialized = CraftingStatisticBootstrap.getInstance().initializeCraftingStatistics(G, H, b, e, c);
        if (initialized != null) {
            D = initialized;
        }
    }

    private static Statistic[] a(String s, int i) {
        return StatisticArrayBuilder.getInstance().createMineBlockStats(s, i, b, e, c);
    }

    private static Statistic[] a(Statistic[] astatistic, String s, int i, int j, int k) {
        return StatisticArrayBuilder.getInstance().createUseStats(astatistic, s, i, j, k, b, e, c, d);
    }

    private static Statistic[] b(Statistic[] astatistic, String s, int i, int j, int k) {
        return StatisticArrayBuilder.getInstance().createBreakStats(astatistic, s, i, j, k, b, e, c);
    }

    private static void a(Statistic[] astatistic) {
        StatisticArrayBuilder.getInstance().normalizeEquivalentBlockIds(astatistic, b, e, c);
    }

    private static void a(Statistic[] astatistic, int i, int j) {
        StatisticArrayBuilder.getInstance().mergeEquivalentBlockIdPair(astatistic, i, j, b, e, c);
    }

    static {
        AchievementList.a();
        StatisticBootstrap.StatisticSet statisticSet = statisticBootstrapService.bootstrapCoreStatistics();
        f = statisticSet.getStartGame();
        g = statisticSet.getCreateWorld();
        h = statisticSet.getLoadWorld();
        i = statisticSet.getJoinMultiplayer();
        j = statisticSet.getLeaveGame();
        k = statisticSet.getPlayOneMinute();
        l = statisticSet.getWalkOneCm();
        m = statisticSet.getSwimOneCm();
        n = statisticSet.getFallOneCm();
        o = statisticSet.getClimbOneCm();
        p = statisticSet.getFlyOneCm();
        q = statisticSet.getDiveOneCm();
        r = statisticSet.getMinecartOneCm();
        s = statisticSet.getBoatOneCm();
        t = statisticSet.getPigOneCm();
        u = statisticSet.getJump();
        v = statisticSet.getDrop();
        w = statisticSet.getDamageDealt();
        x = statisticSet.getDamageTaken();
        y = statisticSet.getDeaths();
        z = statisticSet.getMobKills();
        A = statisticSet.getPlayerKills();
        B = statisticSet.getFishCaught();
        C = a("stat.mineBlock", 16777216);
        G = false;
        H = false;
    }
}
