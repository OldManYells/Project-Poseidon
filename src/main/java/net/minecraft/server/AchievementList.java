package net.minecraft.server;


import org.bukkit.craftbukkit.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AchievementList {

    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static List e = new ArrayList();
    public static Achievement f = (new Achievement(0, "openInventory", 0, 0, org.bukkit.craftbukkit.item.Item.BOOK, (Achievement) null)).a().c();
    public static Achievement g = (new Achievement(1, "mineWood", 2, 1, CraftBlock.LOG, f)).c();
    public static Achievement h = (new Achievement(2, "buildWorkBench", 4, -1, CraftBlock.WORKBENCH, g)).c();
    public static Achievement i = (new Achievement(3, "buildPickaxe", 4, 2, org.bukkit.craftbukkit.item.Item.WOOD_PICKAXE, h)).c();
    public static Achievement j = (new Achievement(4, "buildFurnace", 3, 4, CraftBlock.BURNING_FURNACE, i)).c();
    public static Achievement k = (new Achievement(5, "acquireIron", 1, 4, org.bukkit.craftbukkit.item.Item.IRON_INGOT, j)).c();
    public static Achievement l = (new Achievement(6, "buildHoe", 2, -3, org.bukkit.craftbukkit.item.Item.WOOD_HOE, h)).c();
    public static Achievement m = (new Achievement(7, "makeBread", -1, -3, org.bukkit.craftbukkit.item.Item.BREAD, l)).c();
    public static Achievement n = (new Achievement(8, "bakeCake", 0, -5, org.bukkit.craftbukkit.item.Item.CAKE, l)).c();
    public static Achievement o = (new Achievement(9, "buildBetterPickaxe", 6, 2, org.bukkit.craftbukkit.item.Item.STONE_PICKAXE, i)).c();
    public static Achievement p = (new Achievement(10, "cookFish", 2, 6, org.bukkit.craftbukkit.item.Item.COOKED_FISH, j)).c();
    public static Achievement q = (new Achievement(11, "onARail", 2, 3, CraftBlock.RAILS, k)).b().c();
    public static Achievement r = (new Achievement(12, "buildSword", 6, -1, org.bukkit.craftbukkit.item.Item.WOOD_SWORD, h)).c();
    public static Achievement s = (new Achievement(13, "killEnemy", 8, -1, org.bukkit.craftbukkit.item.Item.BONE, r)).c();
    public static Achievement t = (new Achievement(14, "killCow", 7, -3, org.bukkit.craftbukkit.item.Item.LEATHER, r)).c();
    public static Achievement u = (new Achievement(15, "flyPig", 8, -4, Item.SADDLE, t)).b().c();

    public AchievementList() {}

    public static void a() {}

    static {
        System.out.println(e.size() + " achievements");
    }
}
