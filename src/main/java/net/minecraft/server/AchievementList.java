package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.AchievementBootstrap;

import java.util.ArrayList;
import java.util.List;

public class AchievementList {

    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static List e = new ArrayList();
    public static Achievement f;
    public static Achievement g;
    public static Achievement h;
    public static Achievement i;
    public static Achievement j;
    public static Achievement k;
    public static Achievement l;
    public static Achievement m;
    public static Achievement n;
    public static Achievement o;
    public static Achievement p;
    public static Achievement q;
    public static Achievement r;
    public static Achievement s;
    public static Achievement t;
    public static Achievement u;
    private static final AchievementBootstrap achievementBootstrapService = AchievementBootstrap.getInstance();

    public AchievementList() {}

    public static void a() {}

    static {
        AchievementBootstrap.AchievementSet achievementSet = achievementBootstrapService.bootstrapDefaults();
        f = achievementSet.getOpenInventory();
        g = achievementSet.getMineWood();
        h = achievementSet.getBuildWorkBench();
        i = achievementSet.getBuildPickaxe();
        j = achievementSet.getBuildFurnace();
        k = achievementSet.getAcquireIron();
        l = achievementSet.getBuildHoe();
        m = achievementSet.getMakeBread();
        n = achievementSet.getBakeCake();
        o = achievementSet.getBuildBetterPickaxe();
        p = achievementSet.getCookFish();
        q = achievementSet.getOnARail();
        r = achievementSet.getBuildSword();
        s = achievementSet.getKillEnemy();
        t = achievementSet.getKillCow();
        u = achievementSet.getFlyPig();
        System.out.println(e.size() + " achievements");
    }
}
