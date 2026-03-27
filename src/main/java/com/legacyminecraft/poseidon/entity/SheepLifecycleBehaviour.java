package com.legacyminecraft.poseidon.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SheepLifecycleBehaviour {
    private static final SheepLifecycleBehaviour INSTANCE = new SheepLifecycleBehaviour();
    private static final int SHEEP_FLAGS_WATCHER_INDEX = 16;
    private static final int COLOR_MASK = 15;
    private static final int SHEARED_MASK = 16;

    public static final float[][] WOOL_COLORS = new float[][]{
            {1.0F, 1.0F, 1.0F},
            {0.95F, 0.7F, 0.2F},
            {0.9F, 0.5F, 0.85F},
            {0.6F, 0.7F, 0.95F},
            {0.9F, 0.9F, 0.2F},
            {0.5F, 0.8F, 0.1F},
            {0.95F, 0.7F, 0.8F},
            {0.3F, 0.3F, 0.3F},
            {0.6F, 0.6F, 0.6F},
            {0.3F, 0.6F, 0.7F},
            {0.7F, 0.4F, 0.9F},
            {0.2F, 0.4F, 0.8F},
            {0.5F, 0.4F, 0.3F},
            {0.4F, 0.5F, 0.2F},
            {0.8F, 0.3F, 0.3F},
            {0.1F, 0.1F, 0.1F}
    };

    private SheepLifecycleBehaviour() {
    }

    public static SheepLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public int getFlagsWatcherIndex() {
        return SHEEP_FLAGS_WATCHER_INDEX;
    }

    public byte createInitialFlags() {
        return (byte) 0;
    }

    public List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> createDeathDrops(boolean sheared, int colorId) {
        List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> drops = new ArrayList<com.legacyminecraft.compat.bukkit.inventory.ItemStack>();
        if (!sheared) {
            drops.add(new com.legacyminecraft.compat.bukkit.inventory.ItemStack(com.legacyminecraft.compat.bukkit.Material.WOOL, 1, (short) 0, (byte) colorId));
        }
        return drops;
    }

    public void dropDeathLoot(EntitySheep sheep) {
        List<com.legacyminecraft.compat.bukkit.inventory.ItemStack> drops = createDeathDrops(sheep.isSheared(), sheep.getColor());
        com.legacyminecraft.compat.bukkit.World bukkitWorld = sheep.world.getWorld();
        com.legacyminecraft.compat.bukkit.entity.Entity bukkitEntity = sheep.getBukkitEntity();
        com.legacyminecraft.compat.bukkit.event.entity.EntityDeathEvent event = new com.legacyminecraft.compat.bukkit.event.entity.EntityDeathEvent(bukkitEntity, drops);
        sheep.world.getServer().getPluginManager().callEvent(event);

        for (com.legacyminecraft.compat.bukkit.inventory.ItemStack stack : event.getDrops()) {
            bukkitWorld.dropItemNaturally(bukkitEntity.getLocation(), stack);
        }
    }

    public int getDropBlockId() {
        return Block.WOOL.id;
    }

    public boolean tryShear(EntitySheep sheep, EntityHuman player, ItemStack itemInHand, Random random) {
        if (itemInHand == null || itemInHand.id != Item.SHEARS.id || sheep.isSheared()) {
            return false;
        }

        if (!sheep.world.isStatic) {
            sheep.setSheared(true);
            int woolDropCount = 2 + random.nextInt(3);

            for (int dropIndex = 0; dropIndex < woolDropCount; ++dropIndex) {
                EntityItem droppedWool = sheep.a(new ItemStack(Block.WOOL.id, 1, sheep.getColor()), 1.0F);
                droppedWool.motY += (double) (random.nextFloat() * 0.05F);
                droppedWool.motX += (double) ((random.nextFloat() - random.nextFloat()) * 0.1F);
                droppedWool.motZ += (double) ((random.nextFloat() - random.nextFloat()) * 0.1F);
            }
        }

        itemInHand.damage(1, player);
        return false;
    }

    public String getAmbientSound() {
        return "mob.sheep";
    }

    public String getHurtSound() {
        return "mob.sheep";
    }

    public String getDeathSound() {
        return "mob.sheep";
    }

    public int getColor(byte flags) {
        return flags & COLOR_MASK;
    }

    public byte withColor(byte flags, int colorId) {
        return (byte) (flags & 240 | colorId & COLOR_MASK);
    }

    public boolean isSheared(byte flags) {
        return (flags & SHEARED_MASK) != 0;
    }

    public byte withSheared(byte flags, boolean sheared) {
        return sheared ? (byte) (flags | SHEARED_MASK) : (byte) (flags & ~SHEARED_MASK);
    }

    public int chooseSpawnColor(Random random) {
        int roll = random.nextInt(100);
        if (roll < 5) {
            return 15;
        }
        if (roll < 10) {
            return 7;
        }
        if (roll < 15) {
            return 8;
        }
        if (roll < 18) {
            return 12;
        }
        return random.nextInt(500) == 0 ? 6 : 0;
    }
}
