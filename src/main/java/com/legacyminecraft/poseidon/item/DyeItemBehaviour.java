package com.legacyminecraft.poseidon.item;


public final class DyeItemBehaviour {
    private static final DyeItemBehaviour INSTANCE = new DyeItemBehaviour();

    private DyeItemBehaviour() {
    }

    public static DyeItemBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean applyToBlock(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, java.util.Random random) {
        if (itemstack.getData() != 15) {
            return false;
        }

        int blockId = world.getTypeId(i, j, k);
        if (blockId == Block.SAPLING.id) {
            if (!world.isStatic) {
                ((BlockSapling) Block.SAPLING).b(world, i, j, k, world.random);
                --itemstack.count;
            }
            return true;
        }

        if (blockId == Block.CROPS.id) {
            if (!world.isStatic) {
                ((BlockCrops) Block.CROPS).d_(world, i, j, k);
                --itemstack.count;
            }
            return true;
        }

        if (blockId == Block.GRASS.id) {
            if (!world.isStatic) {
                --itemstack.count;
                spreadBonemealFlora(world, i, j, k, random);
            }
            return true;
        }

        return false;
    }

    private void spreadBonemealFlora(World world, int i, int j, int k, java.util.Random random) {
        for (int j1 = 0; j1 < 128; ++j1) {
            int k1 = i;
            int l1 = j + 1;
            int i2 = k;
            boolean blocked = false;

            for (int j2 = 0; j2 < j1 / 16; ++j2) {
                k1 += random.nextInt(3) - 1;
                l1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2;
                i2 += random.nextInt(3) - 1;
                if (world.getTypeId(k1, l1 - 1, i2) != Block.GRASS.id || world.e(k1, l1, i2)) {
                    blocked = true;
                    break;
                }
            }

            if (blocked || world.getTypeId(k1, l1, i2) != 0) {
                continue;
            }

            if (random.nextInt(10) != 0) {
                world.setTypeIdAndData(k1, l1, i2, Block.LONG_GRASS.id, 1);
            } else if (random.nextInt(3) != 0) {
                world.setTypeId(k1, l1, i2, Block.YELLOW_FLOWER.id);
            } else {
                world.setTypeId(k1, l1, i2, Block.RED_ROSE.id);
            }
        }
    }

    public void applyToEntity(ItemStack itemstack, EntityLiving entityliving) {
        if (!(entityliving instanceof EntitySheep)) {
            return;
        }

        EntitySheep entitysheep = (EntitySheep) entityliving;
        int color = BlockCloth.c(itemstack.getData());
        if (!entitysheep.isSheared() && entitysheep.getColor() != color) {
            entitysheep.setColor(color);
            --itemstack.count;
        }
    }
}
