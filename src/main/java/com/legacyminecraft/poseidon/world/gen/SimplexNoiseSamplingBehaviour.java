package com.legacyminecraft.poseidon.world.gen;

import java.util.Random;

/**
 * Canonical 2D simplex noise sampling behaviour used by legacy wrappers.
 */
public final class SimplexNoiseSamplingBehaviour {
    private static final SimplexNoiseSamplingBehaviour INSTANCE = new SimplexNoiseSamplingBehaviour();

    private static final int[][] GRADIENTS = new int[][]{
            {1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0},
            {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1},
            {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}
    };
    private static final double SIMPLEX_SKEW = 0.5D * (Math.sqrt(3.0D) - 1.0D);
    private static final double SIMPLEX_UNSKEW = (3.0D - Math.sqrt(3.0D)) / 6.0D;

    private SimplexNoiseSamplingBehaviour() {
    }

    public static SimplexNoiseSamplingBehaviour getInstance() {
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

    public void sample2d(double[] outputNoise,
                         double startX,
                         double startZ,
                         int sizeX,
                         int sizeZ,
                         double scaleX,
                         double scaleZ,
                         double amplitude,
                         int[] permutation,
                         double xOffset,
                         double zOffset) {
        int outputIndex = 0;

        for (int gridX = 0; gridX < sizeX; ++gridX) {
            double sampleX = (startX + (double) gridX) * scaleX + xOffset;

            for (int gridZ = 0; gridZ < sizeZ; ++gridZ) {
                double sampleZ = (startZ + (double) gridZ) * scaleZ + zOffset;
                double skewFactor = (sampleX + sampleZ) * SIMPLEX_SKEW;
                int simplexX = fastFloor(sampleX + skewFactor);
                int simplexZ = fastFloor(sampleZ + skewFactor);
                double unskewFactor = (double) (simplexX + simplexZ) * SIMPLEX_UNSKEW;
                double cellOriginX = (double) simplexX - unskewFactor;
                double cellOriginZ = (double) simplexZ - unskewFactor;
                double localX = sampleX - cellOriginX;
                double localZ = sampleZ - cellOriginZ;
                int cornerOffsetX;
                int cornerOffsetZ;

                if (localX > localZ) {
                    cornerOffsetX = 1;
                    cornerOffsetZ = 0;
                } else {
                    cornerOffsetX = 0;
                    cornerOffsetZ = 1;
                }

                double middleX = localX - (double) cornerOffsetX + SIMPLEX_UNSKEW;
                double middleZ = localZ - (double) cornerOffsetZ + SIMPLEX_UNSKEW;
                double farX = localX - 1.0D + 2.0D * SIMPLEX_UNSKEW;
                double farZ = localZ - 1.0D + 2.0D * SIMPLEX_UNSKEW;
                int permutationX = simplexX & 255;
                int permutationZ = simplexZ & 255;
                int gradientNear = permutation[permutationX + permutation[permutationZ]] % 12;
                int gradientMiddle = permutation[permutationX + cornerOffsetX + permutation[permutationZ + cornerOffsetZ]] % 12;
                int gradientFar = permutation[permutationX + 1 + permutation[permutationZ + 1]] % 12;
                double nearAttenuation = 0.5D - localX * localX - localZ * localZ;
                double nearContribution;

                if (nearAttenuation < 0.0D) {
                    nearContribution = 0.0D;
                } else {
                    nearAttenuation *= nearAttenuation;
                    nearContribution = nearAttenuation * nearAttenuation * dot2d(GRADIENTS[gradientNear], localX, localZ);
                }

                double middleAttenuation = 0.5D - middleX * middleX - middleZ * middleZ;
                double middleContribution;

                if (middleAttenuation < 0.0D) {
                    middleContribution = 0.0D;
                } else {
                    middleAttenuation *= middleAttenuation;
                    middleContribution = middleAttenuation * middleAttenuation * dot2d(GRADIENTS[gradientMiddle], middleX, middleZ);
                }

                double farAttenuation = 0.5D - farX * farX - farZ * farZ;
                double farContribution;

                if (farAttenuation < 0.0D) {
                    farContribution = 0.0D;
                } else {
                    farAttenuation *= farAttenuation;
                    farContribution = farAttenuation * farAttenuation * dot2d(GRADIENTS[gradientFar], farX, farZ);
                }

                outputNoise[outputIndex++] += 70.0D * (nearContribution + middleContribution + farContribution) * amplitude;
            }
        }
    }

    private static int fastFloor(double value) {
        return value > 0.0D ? (int) value : (int) value - 1;
    }

    private static double dot2d(int[] gradient, double x, double z) {
        return (double) gradient[0] * x + (double) gradient[1] * z;
    }
}
