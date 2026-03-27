package com.legacyminecraft.poseidon.item;


public final class FishingRodUseBehaviour {
    private static final FishingRodUseBehaviour INSTANCE = new FishingRodUseBehaviour();

    private FishingRodUseBehaviour() {
    }

    public static FishingRodUseBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack use(ItemStack itemstack, World world, EntityHuman entityhuman, java.util.Random random) {
        if (entityhuman.hookedFish != null) {
            int damage = entityhuman.hookedFish.h();
            itemstack.damage(damage, entityhuman);
            entityhuman.w();
            return itemstack;
        }

        PlayerFishEvent playerFishEvent = new PlayerFishEvent((com.legacyminecraft.compat.bukkit.entity.Player) entityhuman.getBukkitEntity(), null, PlayerFishEvent.State.FISHING);
        world.getServer().getPluginManager().callEvent(playerFishEvent);
        if (playerFishEvent.isCancelled()) {
            return itemstack;
        }

        world.makeSound(entityhuman, "random.bow", 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isStatic) {
            world.addEntity(new EntityFish(world, entityhuman));
        }
        entityhuman.w();
        return itemstack;
    }
}
