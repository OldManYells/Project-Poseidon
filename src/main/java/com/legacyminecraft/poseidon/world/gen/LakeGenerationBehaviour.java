package com.legacyminecraft.poseidon.world.gen;


import java.util.Random;

public final class LakeGenerationBehaviour {
    private static final LakeGenerationBehaviour INSTANCE = new LakeGenerationBehaviour();

    private LakeGenerationBehaviour() {
    }

    public static LakeGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generate(World world, Random random, int x, int y, int z, int fluidBlockId) {
        x -= 8;

        for (z -= 8; y > 0 && world.isEmpty(x, y, z); --y) {
            // Intentionally empty: search downward for terrain.
        }

        y -= 4;
        boolean[] carvedMask = new boolean[2048];
        int ellipsoidCount = random.nextInt(4) + 4;

        for (int ellipsoid = 0; ellipsoid < ellipsoidCount; ++ellipsoid) {
            double sizeX = random.nextDouble() * 6.0D + 3.0D;
            double sizeY = random.nextDouble() * 4.0D + 2.0D;
            double sizeZ = random.nextDouble() * 6.0D + 3.0D;
            double centerX = random.nextDouble() * (16.0D - sizeX - 2.0D) + 1.0D + sizeX / 2.0D;
            double centerY = random.nextDouble() * (8.0D - sizeY - 4.0D) + 2.0D + sizeY / 2.0D;
            double centerZ = random.nextDouble() * (16.0D - sizeZ - 2.0D) + 1.0D + sizeZ / 2.0D;

            for (int localX = 1; localX < 15; ++localX) {
                for (int localZ = 1; localZ < 15; ++localZ) {
                    for (int localY = 1; localY < 7; ++localY) {
                        double nx = ((double) localX - centerX) / (sizeX / 2.0D);
                        double ny = ((double) localY - centerY) / (sizeY / 2.0D);
                        double nz = ((double) localZ - centerZ) / (sizeZ / 2.0D);
                        double distanceSquared = nx * nx + ny * ny + nz * nz;

                        if (distanceSquared < 1.0D) {
                            carvedMask[(localX * 16 + localZ) * 8 + localY] = true;
                        }
                    }
                }
            }
        }

        for (int localX = 0; localX < 16; ++localX) {
            for (int localZ = 0; localZ < 16; ++localZ) {
                for (int localY = 0; localY < 8; ++localY) {
                    boolean boundary = isBoundaryCell(carvedMask, localX, localY, localZ);
                    if (!boundary) {
                        continue;
                    }

                    Material material = world.getMaterial(x + localX, y + localY, z + localZ);
                    if (localY >= 4 && material.isLiquid()) {
                        return false;
                    }

                    if (localY < 4 && !material.isBuildable() && world.getTypeId(x + localX, y + localY, z + localZ) != fluidBlockId) {
                        return false;
                    }
                }
            }
        }

        for (int localX = 0; localX < 16; ++localX) {
            for (int localZ = 0; localZ < 16; ++localZ) {
                for (int localY = 0; localY < 8; ++localY) {
                    if (carvedMask[(localX * 16 + localZ) * 8 + localY]) {
                        world.setRawTypeId(x + localX, y + localY, z + localZ, localY >= 4 ? 0 : fluidBlockId);
                    }
                }
            }
        }

        for (int localX = 0; localX < 16; ++localX) {
            for (int localZ = 0; localZ < 16; ++localZ) {
                for (int localY = 4; localY < 8; ++localY) {
                    if (carvedMask[(localX * 16 + localZ) * 8 + localY]
                            && world.getTypeId(x + localX, y + localY - 1, z + localZ) == Block.DIRT.id
                            && world.a(EnumSkyBlock.SKY, x + localX, y + localY, z + localZ) > 0) {
                        world.setRawTypeId(x + localX, y + localY - 1, z + localZ, Block.GRASS.id);
                    }
                }
            }
        }

        if (Block.byId[fluidBlockId].material == Material.LAVA) {
            for (int localX = 0; localX < 16; ++localX) {
                for (int localZ = 0; localZ < 16; ++localZ) {
                    for (int localY = 0; localY < 8; ++localY) {
                        boolean boundary = isBoundaryCell(carvedMask, localX, localY, localZ);
                        if (boundary && (localY < 4 || random.nextInt(2) != 0)
                                && world.getMaterial(x + localX, y + localY, z + localZ).isBuildable()) {
                            world.setRawTypeId(x + localX, y + localY, z + localZ, Block.STONE.id);
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean isBoundaryCell(boolean[] carvedMask, int localX, int localY, int localZ) {
        return !carvedMask[(localX * 16 + localZ) * 8 + localY]
                && (localX < 15 && carvedMask[((localX + 1) * 16 + localZ) * 8 + localY]
                || localX > 0 && carvedMask[((localX - 1) * 16 + localZ) * 8 + localY]
                || localZ < 15 && carvedMask[(localX * 16 + localZ + 1) * 8 + localY]
                || localZ > 0 && carvedMask[(localX * 16 + (localZ - 1)) * 8 + localY]
                || localY < 7 && carvedMask[(localX * 16 + localZ) * 8 + localY + 1]
                || localY > 0 && carvedMask[(localX * 16 + localZ) * 8 + (localY - 1)]);
    }
}
