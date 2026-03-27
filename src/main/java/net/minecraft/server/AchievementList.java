package net.minecraft.server;

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
    public AchievementList() {}

    public static void a() {}

    static {
        f = (new Achievement(0, "openInventory", 0, 0, Item.BOOK, (Achievement) null)).a().c();
        g = (new Achievement(1, "mineWood", 2, 1, Block.LOG, f)).c();
        h = (new Achievement(2, "buildWorkBench", 4, -1, Block.WORKBENCH, g)).c();
        i = (new Achievement(3, "buildPickaxe", 4, 2, Item.WOOD_PICKAXE, h)).c();
        j = (new Achievement(4, "buildFurnace", 3, 4, Block.BURNING_FURNACE, i)).c();
        k = (new Achievement(5, "acquireIron", 1, 4, Item.IRON_INGOT, j)).c();
        l = (new Achievement(6, "buildHoe", 2, -3, Item.WOOD_HOE, h)).c();
        m = (new Achievement(7, "makeBread", -1, -3, Item.BREAD, l)).c();
        n = (new Achievement(8, "bakeCake", 0, -5, Item.CAKE, l)).c();
        o = (new Achievement(9, "buildBetterPickaxe", 6, 2, Item.STONE_PICKAXE, i)).c();
        p = (new Achievement(10, "cookFish", 2, 6, Item.COOKED_FISH, j)).c();
        q = (new Achievement(11, "onARail", 2, 3, Block.RAILS, k)).b().c();
        r = (new Achievement(12, "buildSword", 6, -1, Item.WOOD_SWORD, h)).c();
        s = (new Achievement(13, "killEnemy", 8, -1, Item.BONE, r)).c();
        t = (new Achievement(14, "killCow", 7, -3, Item.LEATHER, r)).c();
        u = (new Achievement(15, "flyPig", 8, -4, Item.SADDLE, t)).b().c();
        System.out.println(e.size() + " achievements");
    }
}
