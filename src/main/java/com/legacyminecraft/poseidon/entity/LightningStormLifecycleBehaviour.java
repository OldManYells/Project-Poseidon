package com.legacyminecraft.poseidon.entity;


import java.util.List;
/**
 * Canonical lifecycle behaviour for lightning-storm entities.
 */
public final class LightningStormLifecycleBehaviour {
    private static final LightningStormLifecycleBehaviour INSTANCE = new LightningStormLifecycleBehaviour();

    private LightningStormLifecycleBehaviour() {
    }

    public static LightningStormLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public void initialize(EntityWeatherStorm storm, World world, double x, double y, double z, boolean isEffect, com.legacyminecraft.compat.bukkit.World bukkitWorld) {
        storm.setPositionRotation(x, y, z, 0.0F, 0.0F);
        storm.poseidonSetLifeTicks(2);
        storm.a = storm.poseidonNextRandomLong();
        storm.poseidonSetFlashCount(storm.poseidonNextRandomInt(3) + 1);
        if (!isEffect && world.spawnMonsters >= 2 && world.areChunksLoaded(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), 10)) {
            igniteIfPossible(world, bukkitWorld, MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));

            for (int attempt = 0; attempt < 4; ++attempt) {
                int candidateX = MathHelper.floor(x) + storm.poseidonNextRandomInt(3) - 1;
                int candidateY = MathHelper.floor(y) + storm.poseidonNextRandomInt(3) - 1;
                int candidateZ = MathHelper.floor(z) + storm.poseidonNextRandomInt(3) - 1;

                igniteIfPossible(world, bukkitWorld, candidateX, candidateY, candidateZ);
            }
        }
    }

    public void tick(EntityWeatherStorm storm) {
        if (storm.poseidonGetLifeTicks() == 2) {
            storm.world.makeSound(storm.locX, storm.locY, storm.locZ, "ambient.weather.thunder", 10000.0F, 0.8F + storm.poseidonNextRandomFloat() * 0.2F);
            storm.world.makeSound(storm.locX, storm.locY, storm.locZ, "random.explode", 2.0F, 0.5F + storm.poseidonNextRandomFloat() * 0.2F);
        }

        storm.poseidonSetLifeTicks(storm.poseidonGetLifeTicks() - 1);
        if (storm.poseidonGetLifeTicks() < 0) {
            if (storm.poseidonGetFlashCount() == 0) {
                storm.die();
            } else if (storm.poseidonGetLifeTicks() < -storm.poseidonNextRandomInt(10)) {
                storm.poseidonSetFlashCount(storm.poseidonGetFlashCount() - 1);
                storm.poseidonSetLifeTicks(1);
                storm.a = storm.poseidonNextRandomLong();
                if (!storm.isEffect && storm.world.areChunksLoaded(MathHelper.floor(storm.locX), MathHelper.floor(storm.locY), MathHelper.floor(storm.locZ), 10)) {
                    igniteIfPossible(storm.world, storm.poseidonGetCraftWorld(), MathHelper.floor(storm.locX), MathHelper.floor(storm.locY), MathHelper.floor(storm.locZ));
                }
            }
        }

        if (storm.poseidonGetLifeTicks() >= 0 && !storm.isEffect) {
            double strikeRadius = 3.0D;
            List nearbyEntities = storm.world.b((Entity) storm,
                    AxisAlignedBB.b(storm.locX - strikeRadius, storm.locY - strikeRadius, storm.locZ - strikeRadius,
                            storm.locX + strikeRadius, storm.locY + 6.0D + strikeRadius, storm.locZ + strikeRadius));

            for (int index = 0; index < nearbyEntities.size(); ++index) {
                Entity entity = (Entity) nearbyEntities.get(index);
                entity.a(storm);
            }

            storm.world.n = 2;
        }
    }

    public void readFromNbt(EntityWeatherStorm storm, NBTTagCompound nbtTag) {
        // Vanilla lightning carries no extra serialized payload.
    }

    public void writeToNbt(EntityWeatherStorm storm, NBTTagCompound nbtTag) {
        // Vanilla lightning carries no extra serialized payload.
    }

    private void igniteIfPossible(World world, com.legacyminecraft.compat.bukkit.World bukkitWorld, int x, int y, int z) {
        if (world.getTypeId(x, y, z) == 0 && Block.FIRE.canPlace(world, x, y, z)) {
            com.legacyminecraft.compat.bukkit.BlockIgniteEvent event =
                    new com.legacyminecraft.compat.bukkit.BlockIgniteEvent(
                            bukkitWorld.getBlockAt(x, y, z),
                            com.legacyminecraft.compat.bukkit.BlockIgniteEvent.IgniteCause.LIGHTNING,
                            null
                    );
            world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                world.setTypeId(x, y, z, Block.FIRE.id);
            }
        }
    }
}
