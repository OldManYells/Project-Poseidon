package com.legacyminecraft.poseidon.world.stats;


/**
 * Canonical bootstrap for legacy default achievements.
 */
public final class AchievementBootstrap {
    private static final AchievementBootstrap INSTANCE = new AchievementBootstrap();

    private AchievementBootstrap() {
    }

    public static AchievementBootstrap getInstance() {
        return INSTANCE;
    }

    public AchievementSet bootstrapDefaults() {
        Achievement openInventory = (new Achievement(0, "openInventory", 0, 0, Item.BOOK, (Achievement) null)).a().c();
        Achievement mineWood = (new Achievement(1, "mineWood", 2, 1, Block.LOG, openInventory)).c();
        Achievement buildWorkBench = (new Achievement(2, "buildWorkBench", 4, -1, Block.WORKBENCH, mineWood)).c();
        Achievement buildPickaxe = (new Achievement(3, "buildPickaxe", 4, 2, Item.WOOD_PICKAXE, buildWorkBench)).c();
        Achievement buildFurnace = (new Achievement(4, "buildFurnace", 3, 4, Block.BURNING_FURNACE, buildPickaxe)).c();
        Achievement acquireIron = (new Achievement(5, "acquireIron", 1, 4, Item.IRON_INGOT, buildFurnace)).c();
        Achievement buildHoe = (new Achievement(6, "buildHoe", 2, -3, Item.WOOD_HOE, buildWorkBench)).c();
        Achievement makeBread = (new Achievement(7, "makeBread", -1, -3, Item.BREAD, buildHoe)).c();
        Achievement bakeCake = (new Achievement(8, "bakeCake", 0, -5, Item.CAKE, buildHoe)).c();
        Achievement buildBetterPickaxe = (new Achievement(9, "buildBetterPickaxe", 6, 2, Item.STONE_PICKAXE, buildPickaxe)).c();
        Achievement cookFish = (new Achievement(10, "cookFish", 2, 6, Item.COOKED_FISH, buildFurnace)).c();
        Achievement onARail = (new Achievement(11, "onARail", 2, 3, Block.RAILS, acquireIron)).b().c();
        Achievement buildSword = (new Achievement(12, "buildSword", 6, -1, Item.WOOD_SWORD, buildWorkBench)).c();
        Achievement killEnemy = (new Achievement(13, "killEnemy", 8, -1, Item.BONE, buildSword)).c();
        Achievement killCow = (new Achievement(14, "killCow", 7, -3, Item.LEATHER, buildSword)).c();
        Achievement flyPig = (new Achievement(15, "flyPig", 8, -4, Item.SADDLE, killCow)).b().c();

        return new AchievementSet(
                openInventory,
                mineWood,
                buildWorkBench,
                buildPickaxe,
                buildFurnace,
                acquireIron,
                buildHoe,
                makeBread,
                bakeCake,
                buildBetterPickaxe,
                cookFish,
                onARail,
                buildSword,
                killEnemy,
                killCow,
                flyPig
        );
    }

    public static final class AchievementSet {
        private final Achievement openInventory;
        private final Achievement mineWood;
        private final Achievement buildWorkBench;
        private final Achievement buildPickaxe;
        private final Achievement buildFurnace;
        private final Achievement acquireIron;
        private final Achievement buildHoe;
        private final Achievement makeBread;
        private final Achievement bakeCake;
        private final Achievement buildBetterPickaxe;
        private final Achievement cookFish;
        private final Achievement onARail;
        private final Achievement buildSword;
        private final Achievement killEnemy;
        private final Achievement killCow;
        private final Achievement flyPig;

        private AchievementSet(
                Achievement openInventory,
                Achievement mineWood,
                Achievement buildWorkBench,
                Achievement buildPickaxe,
                Achievement buildFurnace,
                Achievement acquireIron,
                Achievement buildHoe,
                Achievement makeBread,
                Achievement bakeCake,
                Achievement buildBetterPickaxe,
                Achievement cookFish,
                Achievement onARail,
                Achievement buildSword,
                Achievement killEnemy,
                Achievement killCow,
                Achievement flyPig
        ) {
            this.openInventory = openInventory;
            this.mineWood = mineWood;
            this.buildWorkBench = buildWorkBench;
            this.buildPickaxe = buildPickaxe;
            this.buildFurnace = buildFurnace;
            this.acquireIron = acquireIron;
            this.buildHoe = buildHoe;
            this.makeBread = makeBread;
            this.bakeCake = bakeCake;
            this.buildBetterPickaxe = buildBetterPickaxe;
            this.cookFish = cookFish;
            this.onARail = onARail;
            this.buildSword = buildSword;
            this.killEnemy = killEnemy;
            this.killCow = killCow;
            this.flyPig = flyPig;
        }

        public Achievement getOpenInventory() {
            return openInventory;
        }

        public Achievement getMineWood() {
            return mineWood;
        }

        public Achievement getBuildWorkBench() {
            return buildWorkBench;
        }

        public Achievement getBuildPickaxe() {
            return buildPickaxe;
        }

        public Achievement getBuildFurnace() {
            return buildFurnace;
        }

        public Achievement getAcquireIron() {
            return acquireIron;
        }

        public Achievement getBuildHoe() {
            return buildHoe;
        }

        public Achievement getMakeBread() {
            return makeBread;
        }

        public Achievement getBakeCake() {
            return bakeCake;
        }

        public Achievement getBuildBetterPickaxe() {
            return buildBetterPickaxe;
        }

        public Achievement getCookFish() {
            return cookFish;
        }

        public Achievement getOnARail() {
            return onARail;
        }

        public Achievement getBuildSword() {
            return buildSword;
        }

        public Achievement getKillEnemy() {
            return killEnemy;
        }

        public Achievement getKillCow() {
            return killCow;
        }

        public Achievement getFlyPig() {
            return flyPig;
        }
    }
}
