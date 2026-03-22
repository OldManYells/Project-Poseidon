package com.legacyminecraft.poseidon.world.gen;

import java.util.Random;

/**
 * Canonical Perlin noise sampling behaviour for legacy wrappers.
 */
public final class PerlinNoiseSamplingBehaviour {
    private static final PerlinNoiseSamplingBehaviour INSTANCE = new PerlinNoiseSamplingBehaviour();

    private PerlinNoiseSamplingBehaviour() {
    }

    public static PerlinNoiseSamplingBehaviour getInstance() {
        return INSTANCE;
    }

    public int[] createPermutation(Random random) {
        int[] permutation = new int[512];

        for (int index = 0; index < 256; permutation[index] = index++) {
            // Keep vanilla initialization ordering.
        }

        for (int index = 0; index < 256; ++index) {
            int swapIndex = random.nextInt(256 - index) + index;
            int value = permutation[index];

            permutation[index] = permutation[swapIndex];
            permutation[swapIndex] = value;
            permutation[index + 256] = permutation[index];
        }

        return permutation;
    }

    public double randomCoordinateOffset(Random random) {
        return random.nextDouble() * 256.0D;
    }

    public double sampleSingle(double x,
                               double y,
                               double z,
                               int[] permutation,
                               double xOffset,
                               double yOffset,
                               double zOffset) {
        double shiftedX = x + xOffset;
        double shiftedY = y + yOffset;
        double shiftedZ = z + zOffset;
        int cellX = (int) shiftedX;
        int cellY = (int) shiftedY;
        int cellZ = (int) shiftedZ;

        if (shiftedX < (double) cellX) {
            --cellX;
        }

        if (shiftedY < (double) cellY) {
            --cellY;
        }

        if (shiftedZ < (double) cellZ) {
            --cellZ;
        }

        int wrappedX = cellX & 255;
        int wrappedY = cellY & 255;
        int wrappedZ = cellZ & 255;

        shiftedX -= (double) cellX;
        shiftedY -= (double) cellY;
        shiftedZ -= (double) cellZ;
        double fadeX = fade(shiftedX);
        double fadeY = fade(shiftedY);
        double fadeZ = fade(shiftedZ);
        int hashXY = permutation[wrappedX] + wrappedY;
        int hashXYZ = permutation[hashXY] + wrappedZ;
        int hashXYNextZ = permutation[hashXY + 1] + wrappedZ;
        int hashXNextY = permutation[wrappedX + 1] + wrappedY;
        int hashXNextYZ = permutation[hashXNextY] + wrappedZ;
        int hashXNextYNextZ = permutation[hashXNextY + 1] + wrappedZ;

        return lerp(
                fadeZ,
                lerp(
                        fadeY,
                        lerp(fadeX,
                                gradient3d(permutation[hashXYZ], shiftedX, shiftedY, shiftedZ),
                                gradient3d(permutation[hashXNextYZ], shiftedX - 1.0D, shiftedY, shiftedZ)),
                        lerp(fadeX,
                                gradient3d(permutation[hashXYNextZ], shiftedX, shiftedY - 1.0D, shiftedZ),
                                gradient3d(permutation[hashXNextYNextZ], shiftedX - 1.0D, shiftedY - 1.0D, shiftedZ))
                ),
                lerp(
                        fadeY,
                        lerp(fadeX,
                                gradient3d(permutation[hashXYZ + 1], shiftedX, shiftedY, shiftedZ - 1.0D),
                                gradient3d(permutation[hashXNextYZ + 1], shiftedX - 1.0D, shiftedY, shiftedZ - 1.0D)),
                        lerp(fadeX,
                                gradient3d(permutation[hashXYNextZ + 1], shiftedX, shiftedY - 1.0D, shiftedZ - 1.0D),
                                gradient3d(permutation[hashXNextYNextZ + 1], shiftedX - 1.0D, shiftedY - 1.0D, shiftedZ - 1.0D))
                )
        );
    }

    public void sampleVolume(double[] outputNoise,
                             double startX,
                             double startY,
                             double startZ,
                             int sizeX,
                             int sizeY,
                             int sizeZ,
                             double scaleX,
                             double scaleY,
                             double scaleZ,
                             double inverseAmplitude,
                             int[] permutation,
                             double xOffset,
                             double yOffset,
                             double zOffset) {
        int outputIndex;
        int permutationIndexY;
        double sampleX;
        double fadeX;
        double sampleZ;
        int cellZ;
        double fadeZ;
        int hashXZ0;
        int hashXZ1;
        int hashXZNext0;
        int hashXZNext1;
        int wrappedX;
        int wrappedZ;
        int cellX;

        if (sizeY == 1) {
            outputIndex = 0;
            double amplitude = 1.0D / inverseAmplitude;

            for (int gridX = 0; gridX < sizeX; ++gridX) {
                sampleX = (startX + (double) gridX) * scaleX + xOffset;
                cellX = floorForNoise(sampleX);
                wrappedX = cellX & 255;

                sampleX -= (double) cellX;
                fadeX = fade(sampleX);

                for (int gridZ = 0; gridZ < sizeZ; ++gridZ) {
                    sampleZ = (startZ + (double) gridZ) * scaleZ + zOffset;
                    cellZ = floorForNoise(sampleZ);
                    wrappedZ = cellZ & 255;
                    sampleZ -= (double) cellZ;
                    fadeZ = fade(sampleZ);
                    int hashX0 = permutation[wrappedX];
                    int hash0 = permutation[hashX0] + wrappedZ;
                    int hashX1 = permutation[wrappedX + 1];

                    permutationIndexY = permutation[hashX1] + wrappedZ;
                    double lowerEdge = lerp(fadeX,
                            gradient2d(permutation[hash0], sampleX, sampleZ),
                            gradient3d(permutation[permutationIndexY], sampleX - 1.0D, 0.0D, sampleZ));
                    double upperEdge = lerp(fadeX,
                            gradient3d(permutation[hash0 + 1], sampleX, 0.0D, sampleZ - 1.0D),
                            gradient3d(permutation[permutationIndexY + 1], sampleX - 1.0D, 0.0D, sampleZ - 1.0D));
                    double blended = lerp(fadeZ, lowerEdge, upperEdge);

                    outputNoise[outputIndex++] += blended * amplitude;
                }
            }

            return;
        }

        outputIndex = 0;
        double amplitude = 1.0D / inverseAmplitude;

        permutationIndexY = -1;
        double lowerNear = 0.0D;
        double upperNear = 0.0D;
        double lowerFar = 0.0D;
        double upperFar = 0.0D;

        for (int gridX = 0; gridX < sizeX; ++gridX) {
            sampleX = (startX + (double) gridX) * scaleX + xOffset;
            cellX = floorForNoise(sampleX);
            wrappedX = cellX & 255;
            sampleX -= (double) cellX;
            fadeX = fade(sampleX);

            for (int gridZ = 0; gridZ < sizeZ; ++gridZ) {
                sampleZ = (startZ + (double) gridZ) * scaleZ + zOffset;
                cellZ = floorForNoise(sampleZ);

                wrappedZ = cellZ & 255;
                sampleZ -= (double) cellZ;
                fadeZ = fade(sampleZ);

                for (int gridY = 0; gridY < sizeY; ++gridY) {
                    double sampleY = (startY + (double) gridY) * scaleY + yOffset;
                    int cellY = floorForNoise(sampleY);

                    int wrappedY = cellY & 255;
                    sampleY -= (double) cellY;
                    double fadeY = fade(sampleY);

                    if (gridY == 0 || wrappedY != permutationIndexY) {
                        permutationIndexY = wrappedY;
                        int hashX0 = permutation[wrappedX] + wrappedY;
                        int hash000 = permutation[hashX0] + wrappedZ;
                        int hash010 = permutation[hashX0 + 1] + wrappedZ;
                        int hashX1 = permutation[wrappedX + 1] + wrappedY;

                        hashXZ0 = permutation[hashX1] + wrappedZ;
                        hashXZ1 = permutation[hashX1 + 1] + wrappedZ;

                        lowerNear = lerp(fadeX,
                                gradient3d(permutation[hash000], sampleX, sampleY, sampleZ),
                                gradient3d(permutation[hashXZ0], sampleX - 1.0D, sampleY, sampleZ));
                        upperNear = lerp(fadeX,
                                gradient3d(permutation[hash010], sampleX, sampleY - 1.0D, sampleZ),
                                gradient3d(permutation[hashXZ1], sampleX - 1.0D, sampleY - 1.0D, sampleZ));
                        lowerFar = lerp(fadeX,
                                gradient3d(permutation[hash000 + 1], sampleX, sampleY, sampleZ - 1.0D),
                                gradient3d(permutation[hashXZ0 + 1], sampleX - 1.0D, sampleY, sampleZ - 1.0D));
                        upperFar = lerp(fadeX,
                                gradient3d(permutation[hash010 + 1], sampleX, sampleY - 1.0D, sampleZ - 1.0D),
                                gradient3d(permutation[hashXZ1 + 1], sampleX - 1.0D, sampleY - 1.0D, sampleZ - 1.0D));
                    }

                    double nearPlane = lerp(fadeY, lowerNear, upperNear);
                    double farPlane = lerp(fadeY, lowerFar, upperFar);
                    double noiseValue = lerp(fadeZ, nearPlane, farPlane);

                    outputNoise[outputIndex++] += noiseValue * amplitude;
                }
            }
        }
    }

    public double lerp(double alpha, double start, double end) {
        return start + alpha * (end - start);
    }

    public double gradient2d(int hash, double x, double y) {
        int gradientIndex = hash & 15;
        double xComponent = (double) (1 - ((gradientIndex & 8) >> 3)) * x;
        double yComponent = gradientIndex < 4 ? 0.0D : (gradientIndex != 12 && gradientIndex != 14 ? y : x);

        return ((gradientIndex & 1) == 0 ? xComponent : -xComponent)
                + ((gradientIndex & 2) == 0 ? yComponent : -yComponent);
    }

    public double gradient3d(int hash, double x, double y, double z) {
        int gradientIndex = hash & 15;
        double axisA = gradientIndex < 8 ? x : y;
        double axisB = gradientIndex < 4 ? y : (gradientIndex != 12 && gradientIndex != 14 ? z : x);

        return ((gradientIndex & 1) == 0 ? axisA : -axisA)
                + ((gradientIndex & 2) == 0 ? axisB : -axisB);
    }

    private static int floorForNoise(double value) {
        int floor = (int) value;

        if (value < (double) floor) {
            --floor;
        }

        return floor;
    }

    private static double fade(double value) {
        return value * value * value * (value * (value * 6.0D - 15.0D) + 10.0D);
    }
}
