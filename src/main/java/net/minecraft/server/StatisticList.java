package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.StatisticTranslationBehaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    private static final StatisticTranslationBehaviour TRANSLATION = StatisticTranslationBehaviour.getInstance();

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
        if (!G || !H) {
            return;
        }

        HashSet craftableItemIds = new HashSet();

        for (int itemId = 0; itemId < Item.byId.length; ++itemId) {
            if (Item.byId[itemId] != null) {
                craftableItemIds.add(Integer.valueOf(itemId));
            }
        }

        D = new Statistic[32000];
        Iterator iterator = craftableItemIds.iterator();

        while (iterator.hasNext()) {
            Integer itemId = (Integer) iterator.next();
            int id = itemId.intValue();
            if (Item.byId[id] != null) {
                String translated = TRANSLATION.format("stat.craftItem", Item.byId[id].j());
                D[id] = (new CraftingStatistic(16842752 + id, translated, id)).d();
            }
        }

        a(D);
    }

    private static Statistic[] a(String key, int statisticBaseId) {
        Statistic[] statistics = new Statistic[256];

        for (int blockId = 0; blockId < 256; ++blockId) {
            if (Block.byId[blockId] != null && Block.byId[blockId].m()) {
                String translated = TRANSLATION.format(key, Block.byId[blockId].k());
                statistics[blockId] = (new CraftingStatistic(statisticBaseId + blockId, translated, blockId)).d();
                e.add((CraftingStatistic) statistics[blockId]);
            }
        }

        a(statistics);
        return statistics;
    }

    private static Statistic[] a(Statistic[] statistics, String key, int statisticBaseId, int startId, int endId) {
        if (statistics == null) {
            statistics = new Statistic[32000];
        }

        for (int id = startId; id < endId; ++id) {
            if (Item.byId[id] != null) {
                String translated = TRANSLATION.format(key, Item.byId[id].j());
                statistics[id] = (new CraftingStatistic(statisticBaseId + id, translated, id)).d();
                if (id >= Block.byId.length) {
                    d.add((CraftingStatistic) statistics[id]);
                }
            }
        }

        a(statistics);
        return statistics;
    }

    private static Statistic[] b(Statistic[] statistics, String key, int statisticBaseId, int startId, int endId) {
        if (statistics == null) {
            statistics = new Statistic[32000];
        }

        for (int id = startId; id < endId; ++id) {
            if (Item.byId[id] != null && Item.byId[id].f()) {
                String translated = TRANSLATION.format(key, Item.byId[id].j());
                statistics[id] = (new CraftingStatistic(statisticBaseId + id, translated, id)).d();
            }
        }

        a(statistics);
        return statistics;
    }

    private static void a(Statistic[] statistics) {
        a(statistics, Block.STATIONARY_WATER.id, Block.WATER.id);
        a(statistics, Block.STATIONARY_LAVA.id, Block.LAVA.id);
        a(statistics, Block.JACK_O_LANTERN.id, Block.PUMPKIN.id);
        a(statistics, Block.BURNING_FURNACE.id, Block.FURNACE.id);
        a(statistics, Block.GLOWING_REDSTONE_ORE.id, Block.REDSTONE_ORE.id);
        a(statistics, Block.DIODE_ON.id, Block.DIODE_OFF.id);
        a(statistics, Block.REDSTONE_TORCH_ON.id, Block.REDSTONE_TORCH_OFF.id);
        a(statistics, Block.RED_MUSHROOM.id, Block.BROWN_MUSHROOM.id);
        a(statistics, Block.DOUBLE_STEP.id, Block.STEP.id);
        a(statistics, Block.GRASS.id, Block.DIRT.id);
        a(statistics, Block.SOIL.id, Block.DIRT.id);
    }

    private static void a(Statistic[] statistics, int sourceId, int targetId) {
        if (statistics[sourceId] != null && statistics[targetId] == null) {
            statistics[targetId] = statistics[sourceId];
        } else {
            b.remove(statistics[sourceId]);
            e.remove(statistics[sourceId]);
            c.remove(statistics[sourceId]);
            statistics[sourceId] = statistics[targetId];
        }
    }

    static {
        AchievementList.a();
        f = (new CounterStatistic(1000, TRANSLATION.translate("stat.startGame"))).d();
        g = (new CounterStatistic(1001, TRANSLATION.translate("stat.createWorld"))).d();
        h = (new CounterStatistic(1002, TRANSLATION.translate("stat.loadWorld"))).d();
        i = (new CounterStatistic(1003, TRANSLATION.translate("stat.joinMultiplayer"))).d();
        j = (new CounterStatistic(1004, TRANSLATION.translate("stat.leaveGame"))).d();
        k = (new CounterStatistic(1100, TRANSLATION.translate("stat.playOneMinute"), Statistic.j)).d();
        l = (new CounterStatistic(2000, TRANSLATION.translate("stat.walkOneCm"), Statistic.k)).d();
        m = (new CounterStatistic(2001, TRANSLATION.translate("stat.swimOneCm"), Statistic.k)).d();
        n = (new CounterStatistic(2002, TRANSLATION.translate("stat.fallOneCm"), Statistic.k)).d();
        o = (new CounterStatistic(2003, TRANSLATION.translate("stat.climbOneCm"), Statistic.k)).d();
        p = (new CounterStatistic(2004, TRANSLATION.translate("stat.flyOneCm"), Statistic.k)).d();
        q = (new CounterStatistic(2005, TRANSLATION.translate("stat.diveOneCm"), Statistic.k)).d();
        r = (new CounterStatistic(2006, TRANSLATION.translate("stat.minecartOneCm"), Statistic.k)).d();
        s = (new CounterStatistic(2007, TRANSLATION.translate("stat.boatOneCm"), Statistic.k)).d();
        t = (new CounterStatistic(2008, TRANSLATION.translate("stat.pigOneCm"), Statistic.k)).d();
        u = (new CounterStatistic(2010, TRANSLATION.translate("stat.jump"))).d();
        v = (new CounterStatistic(2011, TRANSLATION.translate("stat.drop"))).d();
        w = (new CounterStatistic(2020, TRANSLATION.translate("stat.damageDealt"))).e().d();
        x = (new CounterStatistic(2021, TRANSLATION.translate("stat.damageTaken"))).e().d();
        y = (new CounterStatistic(2022, TRANSLATION.translate("stat.deaths"))).e().d();
        z = (new CounterStatistic(2023, TRANSLATION.translate("stat.mobKills"))).e().d();
        A = (new CounterStatistic(2024, TRANSLATION.translate("stat.playerKills"))).e().d();
        B = (new CounterStatistic(2025, TRANSLATION.translate("stat.fishCaught"))).e().d();
        C = a("stat.mineBlock", 16777216);
        G = false;
        H = false;
    }
}
