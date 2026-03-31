package net.minecraft.server;

import org.bukkit.craftbukkit.item.Item;
import org.bukkit.craftbukkit.item.ItemStack;
import org.bukkit.craftbukkit.server.StatisticCollector;

public class Achievement extends Statistic {

    public final int a; // displayColumn
    public final int b; // displayRow
    public final Achievement c; // parentAchievement
    private final String description;
    public final ItemStack d; // displayItem
    private boolean special;

    public Achievement(int id, String name, int displayColumn, int displayRow, Item item, Achievement parentAchievement) {
        this(id, name, displayColumn, displayRow, new ItemStack(item), parentAchievement);
    }

    public Achievement(int id, String name, int displayColumn, int displayRow, CraftBlock baseBlock, Achievement parentAchievement) {
        this(id, name, displayColumn, displayRow, new ItemStack(baseBlock), parentAchievement);
    }

    public Achievement(int id, String name, int displayColumn, int displayRow, ItemStack displayItem, Achievement parentAchievement) {
        super(5242880 + id, StatisticCollector.a("achievement." + name));
        this.d = displayItem;
        this.description = StatisticCollector.a("achievement." + name + ".desc");
        this.a = displayColumn;
        this.b = displayRow;
        if (displayColumn < AchievementList.a) {
            AchievementList.a = displayColumn;
        }

        if (displayRow < AchievementList.b) {
            AchievementList.b = displayRow;
        }

        if (displayColumn > AchievementList.c) {
            AchievementList.c = displayColumn;
        }

        if (displayRow > AchievementList.d) {
            AchievementList.d = displayRow;
        }

        this.c = parentAchievement;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isSpecial() {
        return this.special;
    }

    public Achievement setIndependent() {
        this.g = true;
        return this;
    }

    public Achievement setSpecial() {
        this.special = true;
        return this;
    }

    public Achievement register() {
        super.d();
        AchievementList.e.add(this);
        return this;
    }

    @Deprecated
    public Achievement a() {
        return this.setIndependent();
    }

    @Deprecated
    public Achievement b() {
        return this.setSpecial();
    }

    @Deprecated
    public Achievement c() {
        return this.register();
    }
}
