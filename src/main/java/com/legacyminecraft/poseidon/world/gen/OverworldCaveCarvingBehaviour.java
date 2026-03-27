package com.legacyminecraft.poseidon.world.gen;


import java.util.Random;

public final class OverworldCaveCarvingBehaviour {
    private static final OverworldCaveCarvingBehaviour INSTANCE = new OverworldCaveCarvingBehaviour();

    private OverworldCaveCarvingBehaviour() {
    }

    public static OverworldCaveCarvingBehaviour getInstance() {
        return INSTANCE;
    }

    public void generateLargeCaveNode(int originChunkX,
                                      int originChunkZ,
                                      byte[] blockData,
                                      double caveX,
                                      double caveY,
                                      double caveZ,
                                      int targetChunkX,
                                      int targetChunkZ,
                                      int caveRange,
                                      Random randomSource) {
        this.generateCaveNode(originChunkX, originChunkZ, blockData, caveX, caveY, caveZ,
                1.0F + randomSource.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D, caveRange, randomSource);
    }

    public void generateCaveNode(int originChunkX,
                                 int originChunkZ,
                                 byte[] blockData,
                                 double caveX,
                                 double caveY,
                                 double caveZ,
                                 float caveRadius,
                                 float yaw,
                                 float pitch,
                                 int currentStep,
                                 int totalSteps,
                                 double verticalScale,
                                 int caveRange,
                                 Random randomSource) {
        double centerChunkX = (double) (originChunkX * 16 + 8);
        double centerChunkZ = (double) (originChunkZ * 16 + 8);
        float yawDelta = 0.0F;
        float pitchDelta = 0.0F;
        Random tunnelRandom = new Random(randomSource.nextLong());

        if (totalSteps <= 0) {
            int maxTunnelLength = caveRange * 16 - 16;
            totalSteps = maxTunnelLength - tunnelRandom.nextInt(maxTunnelLength / 4);
        }

        boolean singleBranchMode = false;

        if (currentStep == -1) {
            currentStep = totalSteps / 2;
            singleBranchMode = true;
        }

        int splitStep = tunnelRandom.nextInt(totalSteps / 2) + totalSteps / 4;

        for (boolean gentlePitchMode = tunnelRandom.nextInt(6) == 0; currentStep < totalSteps; ++currentStep) {
            double horizontalRadius = 1.5D + (double) (MathHelper.sin((float) currentStep * 3.1415927F / (float) totalSteps) * caveRadius * 1.0F);
            double verticalRadius = horizontalRadius * verticalScale;
            float pitchCos = MathHelper.cos(pitch);
            float pitchSin = MathHelper.sin(pitch);

            caveX += (double) (MathHelper.cos(yaw) * pitchCos);
            caveY += (double) pitchSin;
            caveZ += (double) (MathHelper.sin(yaw) * pitchCos);
            if (gentlePitchMode) {
                pitch *= 0.92F;
            } else {
                pitch *= 0.7F;
            }

            pitch += pitchDelta * 0.1F;
            yaw += yawDelta * 0.1F;
            pitchDelta *= 0.9F;
            yawDelta *= 0.75F;
            pitchDelta += (tunnelRandom.nextFloat() - tunnelRandom.nextFloat()) * tunnelRandom.nextFloat() * 2.0F;
            yawDelta += (tunnelRandom.nextFloat() - tunnelRandom.nextFloat()) * tunnelRandom.nextFloat() * 4.0F;
            if (!singleBranchMode && currentStep == splitStep && caveRadius > 1.0F) {
                this.generateCaveNode(originChunkX, originChunkZ, blockData, caveX, caveY, caveZ,
                        tunnelRandom.nextFloat() * 0.5F + 0.5F, yaw - 1.5707964F, pitch / 3.0F,
                        currentStep, totalSteps, 1.0D, caveRange, randomSource);
                this.generateCaveNode(originChunkX, originChunkZ, blockData, caveX, caveY, caveZ,
                        tunnelRandom.nextFloat() * 0.5F + 0.5F, yaw + 1.5707964F, pitch / 3.0F,
                        currentStep, totalSteps, 1.0D, caveRange, randomSource);
                return;
            }

            if (singleBranchMode || tunnelRandom.nextInt(4) != 0) {
                double offsetX = caveX - centerChunkX;
                double offsetZ = caveZ - centerChunkZ;
                double remainingSteps = (double) (totalSteps - currentStep);
                double maxReach = (double) (caveRadius + 2.0F + 16.0F);

                if (offsetX * offsetX + offsetZ * offsetZ - remainingSteps * remainingSteps > maxReach * maxReach) {
                    return;
                }

                if (caveX >= centerChunkX - 16.0D - horizontalRadius * 2.0D
                        && caveZ >= centerChunkZ - 16.0D - horizontalRadius * 2.0D
                        && caveX <= centerChunkX + 16.0D + horizontalRadius * 2.0D
                        && caveZ <= centerChunkZ + 16.0D + horizontalRadius * 2.0D) {
                    int minBlockX = MathHelper.floor(caveX - horizontalRadius) - originChunkX * 16 - 1;
                    int maxBlockX = MathHelper.floor(caveX + horizontalRadius) - originChunkX * 16 + 1;
                    int minBlockY = MathHelper.floor(caveY - verticalRadius) - 1;
                    int maxBlockY = MathHelper.floor(caveY + verticalRadius) + 1;
                    int minBlockZ = MathHelper.floor(caveZ - horizontalRadius) - originChunkZ * 16 - 1;
                    int maxBlockZ = MathHelper.floor(caveZ + horizontalRadius) - originChunkZ * 16 + 1;

                    if (minBlockX < 0) {
                        minBlockX = 0;
                    }

                    if (maxBlockX > 16) {
                        maxBlockX = 16;
                    }

                    if (minBlockY < 1) {
                        minBlockY = 1;
                    }

                    if (maxBlockY > 120) {
                        maxBlockY = 120;
                    }

                    if (minBlockZ < 0) {
                        minBlockZ = 0;
                    }

                    if (maxBlockZ > 16) {
                        maxBlockZ = 16;
                    }

                    boolean encounteredWater = false;

                    for (int blockX = minBlockX; !encounteredWater && blockX < maxBlockX; ++blockX) {
                        for (int blockZ = minBlockZ; !encounteredWater && blockZ < maxBlockZ; ++blockZ) {
                            for (int blockY = maxBlockY + 1; !encounteredWater && blockY >= minBlockY - 1; --blockY) {
                                int index = (blockX * 16 + blockZ) * 128 + blockY;
                                if (blockY >= 0 && blockY < 128) {
                                    if (blockData[index] == Block.WATER.id || blockData[index] == Block.STATIONARY_WATER.id) {
                                        encounteredWater = true;
                                    }

                                    if (blockY != minBlockY - 1
                                            && blockX != minBlockX
                                            && blockX != maxBlockX - 1
                                            && blockZ != minBlockZ
                                            && blockZ != maxBlockZ - 1) {
                                        blockY = minBlockY;
                                    }
                                }
                            }
                        }
                    }

                    if (!encounteredWater) {
                        for (int blockX = minBlockX; blockX < maxBlockX; ++blockX) {
                            double normalizedX = ((double) (blockX + originChunkX * 16) + 0.5D - caveX) / horizontalRadius;

                            for (int blockZ = minBlockZ; blockZ < maxBlockZ; ++blockZ) {
                                double normalizedZ = ((double) (blockZ + originChunkZ * 16) + 0.5D - caveZ) / horizontalRadius;
                                int index = (blockX * 16 + blockZ) * 128 + maxBlockY;
                                boolean replacedSurfaceGrass = false;

                                if (normalizedX * normalizedX + normalizedZ * normalizedZ < 1.0D) {
                                    for (int blockY = maxBlockY - 1; blockY >= minBlockY; --blockY) {
                                        double normalizedY = ((double) blockY + 0.5D - caveY) / verticalRadius;

                                        if (normalizedY > -0.7D
                                                && normalizedX * normalizedX + normalizedY * normalizedY + normalizedZ * normalizedZ < 1.0D) {
                                            byte blockId = blockData[index];

                                            if (blockId == Block.GRASS.id) {
                                                replacedSurfaceGrass = true;
                                            }

                                            if (blockId == Block.STONE.id || blockId == Block.DIRT.id || blockId == Block.GRASS.id) {
                                                if (blockY < 10) {
                                                    blockData[index] = (byte) Block.LAVA.id;
                                                } else {
                                                    blockData[index] = 0;
                                                    if (replacedSurfaceGrass && blockData[index - 1] == Block.DIRT.id) {
                                                        blockData[index - 1] = (byte) Block.GRASS.id;
                                                    }
                                                }
                                            }
                                        }

                                        --index;
                                    }
                                }
                            }
                        }

                        if (singleBranchMode) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public void generateChunkCaves(World world,
                                   int chunkX,
                                   int chunkZ,
                                   int originChunkX,
                                   int originChunkZ,
                                   byte[] blockData,
                                   int caveRange,
                                   Random randomSource) {
        int caveCount = randomSource.nextInt(randomSource.nextInt(randomSource.nextInt(40) + 1) + 1);

        if (randomSource.nextInt(15) != 0) {
            caveCount = 0;
        }

        for (int caveIndex = 0; caveIndex < caveCount; ++caveIndex) {
            double startX = (double) (chunkX * 16 + randomSource.nextInt(16));
            double startY = (double) randomSource.nextInt(randomSource.nextInt(120) + 8);
            double startZ = (double) (chunkZ * 16 + randomSource.nextInt(16));
            int branchCount = 1;

            if (randomSource.nextInt(4) == 0) {
                this.generateLargeCaveNode(originChunkX, originChunkZ, blockData, startX, startY, startZ,
                        chunkX, chunkZ, caveRange, randomSource);
                branchCount += randomSource.nextInt(4);
            }

            for (int branchIndex = 0; branchIndex < branchCount; ++branchIndex) {
                float yaw = randomSource.nextFloat() * 3.1415927F * 2.0F;
                float pitch = (randomSource.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float radius = randomSource.nextFloat() * 2.0F + randomSource.nextFloat();

                this.generateCaveNode(originChunkX, originChunkZ, blockData, startX, startY, startZ,
                        radius, yaw, pitch, 0, 0, 1.0D, caveRange, randomSource);
            }
        }
    }
}
