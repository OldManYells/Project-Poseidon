package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.AchievementDefinitionBehaviour;
import com.legacyminecraft.poseidon.world.stats.AchievementRegistry;

public class Achievement extends Statistic {

    public final int a;
    public final int b;
    public final Achievement c;
    private final String l;
    public final ItemStack d;
    private boolean m;
    private static final AchievementDefinitionBehaviour achievementDefinitionService = AchievementDefinitionBehaviour.getInstance();

    public Achievement(int i, String s, int j, int k, Item item, Achievement achievement) {
        this(i, s, j, k, new ItemStack(item), achievement);
    }

    public Achievement(int i, String s, int j, int k, Block block, Achievement achievement) {
        this(i, s, j, k, new ItemStack(block), achievement);
    }

    public Achievement(int i, String s, int j, int k, ItemStack itemstack, Achievement achievement) {
        super(achievementDefinitionService.toStatisticId(i), achievementDefinitionService.resolveTitle(s));
        this.d = itemstack;
        this.l = achievementDefinitionService.resolveDescription(s);
        this.a = j;
        this.b = k;
        achievementDefinitionService.trackBounds(j, k);
        this.c = achievement;
    }

    public Achievement a() {
        this.g = true;
        return this;
    }

    public Achievement b() {
        this.m = true;
        return this;
    }

    public Achievement c() {
        return (Achievement) AchievementRegistry.getInstance().registerRaw(this);
    }
}
