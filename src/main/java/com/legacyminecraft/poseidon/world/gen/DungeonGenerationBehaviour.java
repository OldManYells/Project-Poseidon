package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.TileEntityMobSpawner;
import net.minecraft.server.World;

import java.util.Random;

public final class DungeonGenerationBehaviour {
    private static final DungeonGenerationBehaviour INSTANCE = new DungeonGenerationBehaviour();

    private DungeonGenerationBehaviour() {
    }

    public static DungeonGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        byte roomHeight = 3;
        int roomHalfWidth = random.nextInt(2) + 2;
        int roomHalfDepth = random.nextInt(2) + 2;
        int openingCount = 0;

        for (int blockX = x - roomHalfWidth - 1; blockX <= x + roomHalfWidth + 1; ++blockX) {
            for (int blockY = y - 1; blockY <= y + roomHeight + 1; ++blockY) {
                for (int blockZ = z - roomHalfDepth - 1; blockZ <= z + roomHalfDepth + 1; ++blockZ) {
                    Material material = world.getMaterial(blockX, blockY, blockZ);

                    if (blockY == y - 1 && !material.isBuildable()) {
                        return false;
                    }

                    if (blockY == y + roomHeight + 1 && !material.isBuildable()) {
                        return false;
                    }

                    if ((blockX == x - roomHalfWidth - 1 || blockX == x + roomHalfWidth + 1 || blockZ == z - roomHalfDepth - 1 || blockZ == z + roomHalfDepth + 1)
                            && blockY == y
                            && world.isEmpty(blockX, blockY, blockZ)
                            && world.isEmpty(blockX, blockY + 1, blockZ)) {
                        ++openingCount;
                    }
                }
            }
        }

        if (openingCount < 1 || openingCount > 5) {
            return false;
        }

        for (int blockX = x - roomHalfWidth - 1; blockX <= x + roomHalfWidth + 1; ++blockX) {
            for (int blockY = y + roomHeight; blockY >= y - 1; --blockY) {
                for (int blockZ = z - roomHalfDepth - 1; blockZ <= z + roomHalfDepth + 1; ++blockZ) {
                    if (blockX != x - roomHalfWidth - 1 && blockY != y - 1 && blockZ != z - roomHalfDepth - 1
                            && blockX != x + roomHalfWidth + 1 && blockY != y + roomHeight + 1 && blockZ != z + roomHalfDepth + 1) {
                        world.setTypeId(blockX, blockY, blockZ, 0);
                    } else if (blockY >= 0 && !world.getMaterial(blockX, blockY - 1, blockZ).isBuildable()) {
                        world.setTypeId(blockX, blockY, blockZ, 0);
                    } else if (world.getMaterial(blockX, blockY, blockZ).isBuildable()) {
                        if (blockY == y - 1 && random.nextInt(4) != 0) {
                            world.setTypeId(blockX, blockY, blockZ, Block.MOSSY_COBBLESTONE.id);
                        } else {
                            world.setTypeId(blockX, blockY, blockZ, Block.COBBLESTONE.id);
                        }
                    }
                }
            }
        }

        for (int chestIndex = 0; chestIndex < 2; ++chestIndex) {
            for (int chestAttempt = 0; chestAttempt < 3; ++chestAttempt) {
                int chestX = x + random.nextInt(roomHalfWidth * 2 + 1) - roomHalfWidth;
                int chestZ = z + random.nextInt(roomHalfDepth * 2 + 1) - roomHalfDepth;

                if (!world.isEmpty(chestX, y, chestZ)) {
                    continue;
                }

                int nearbyWalls = 0;
                if (world.getMaterial(chestX - 1, y, chestZ).isBuildable()) {
                    ++nearbyWalls;
                }
                if (world.getMaterial(chestX + 1, y, chestZ).isBuildable()) {
                    ++nearbyWalls;
                }
                if (world.getMaterial(chestX, y, chestZ - 1).isBuildable()) {
                    ++nearbyWalls;
                }
                if (world.getMaterial(chestX, y, chestZ + 1).isBuildable()) {
                    ++nearbyWalls;
                }

                if (nearbyWalls == 1) {
                    world.setTypeId(chestX, y, chestZ, Block.CHEST.id);
                    TileEntityChest chest = (TileEntityChest) world.getTileEntity(chestX, y, chestZ);
                    for (int slotPick = 0; slotPick < 8; ++slotPick) {
                        ItemStack loot = selectChestLoot(random);
                        if (loot != null) {
                            chest.setItem(random.nextInt(chest.getSize()), loot);
                        }
                    }
                    break;
                }
            }
        }

        world.setTypeId(x, y, z, Block.MOB_SPAWNER.id);
        TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
        spawner.a(selectSpawnerMob(random));
        return true;
    }

    private ItemStack selectChestLoot(Random random) {
        int pick = random.nextInt(11);

        return pick == 0 ? new ItemStack(Item.SADDLE)
                : (pick == 1 ? new ItemStack(Item.IRON_INGOT, random.nextInt(4) + 1)
                : (pick == 2 ? new ItemStack(Item.BREAD)
                : (pick == 3 ? new ItemStack(Item.WHEAT, random.nextInt(4) + 1)
                : (pick == 4 ? new ItemStack(Item.SULPHUR, random.nextInt(4) + 1)
                : (pick == 5 ? new ItemStack(Item.STRING, random.nextInt(4) + 1)
                : (pick == 6 ? new ItemStack(Item.BUCKET)
                : (pick == 7 && random.nextInt(100) == 0 ? new ItemStack(Item.GOLDEN_APPLE)
                : (pick == 8 && random.nextInt(2) == 0 ? new ItemStack(Item.REDSTONE, random.nextInt(4) + 1)
                : (pick == 9 && random.nextInt(10) == 0 ? new ItemStack(Item.byId[Item.GOLD_RECORD.id + random.nextInt(2)])
                : (pick == 10 ? new ItemStack(Item.INK_SACK, 1, 3) : null))))))))));
    }

    private String selectSpawnerMob(Random random) {
        int pick = random.nextInt(4);
        return pick == 0 ? "Skeleton" : (pick == 1 ? "Zombie" : (pick == 2 ? "Zombie" : (pick == 3 ? "Spider" : "")));
    }
}
