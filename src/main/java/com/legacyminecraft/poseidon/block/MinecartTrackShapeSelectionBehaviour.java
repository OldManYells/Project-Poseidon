package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical behaviour for selecting minecart track shape from neighbors.
 */
public final class MinecartTrackShapeSelectionBehaviour {
    private static final MinecartTrackShapeSelectionBehaviour INSTANCE = new MinecartTrackShapeSelectionBehaviour();

    private MinecartTrackShapeSelectionBehaviour() {
    }

    public static MinecartTrackShapeSelectionBehaviour getInstance() {
        return INSTANCE;
    }

    public byte resolvePropagationShape(boolean hasNorth, boolean hasSouth, boolean hasWest, boolean hasEast, boolean poweredRail) {
        byte shape = -1;

        if (hasNorth || hasSouth) {
            shape = 0;
        }

        if (hasWest || hasEast) {
            shape = 1;
        }

        if (!poweredRail) {
            if (hasSouth && hasEast && !hasNorth && !hasWest) {
                shape = 6;
            }

            if (hasSouth && hasWest && !hasNorth && !hasEast) {
                shape = 7;
            }

            if (hasNorth && hasWest && !hasSouth && !hasEast) {
                shape = 8;
            }

            if (hasNorth && hasEast && !hasSouth && !hasWest) {
                shape = 9;
            }
        }

        if (shape < 0) {
            shape = 0;
        }

        return shape;
    }

    public byte resolvePlacementShape(boolean hasNorth,
                                      boolean hasSouth,
                                      boolean hasWest,
                                      boolean hasEast,
                                      boolean poweredRail,
                                      boolean preferSouthEastTurns) {
        byte shape = -1;

        if ((hasNorth || hasSouth) && !hasWest && !hasEast) {
            shape = 0;
        }

        if ((hasWest || hasEast) && !hasNorth && !hasSouth) {
            shape = 1;
        }

        if (!poweredRail) {
            if (hasSouth && hasEast && !hasNorth && !hasWest) {
                shape = 6;
            }

            if (hasSouth && hasWest && !hasNorth && !hasEast) {
                shape = 7;
            }

            if (hasNorth && hasWest && !hasSouth && !hasEast) {
                shape = 8;
            }

            if (hasNorth && hasEast && !hasSouth && !hasWest) {
                shape = 9;
            }
        }

        if (shape == -1) {
            if (hasNorth || hasSouth) {
                shape = 0;
            }

            if (hasWest || hasEast) {
                shape = 1;
            }

            if (!poweredRail) {
                if (preferSouthEastTurns) {
                    if (hasSouth && hasEast) {
                        shape = 6;
                    }

                    if (hasWest && hasSouth) {
                        shape = 7;
                    }

                    if (hasEast && hasNorth) {
                        shape = 9;
                    }

                    if (hasNorth && hasWest) {
                        shape = 8;
                    }
                } else {
                    if (hasNorth && hasWest) {
                        shape = 8;
                    }

                    if (hasEast && hasNorth) {
                        shape = 9;
                    }

                    if (hasWest && hasSouth) {
                        shape = 7;
                    }

                    if (hasSouth && hasEast) {
                        shape = 6;
                    }
                }
            }
        }

        if (shape < 0) {
            shape = 0;
        }

        return shape;
    }

    public byte applySlopeAdjustments(World world, int x, int y, int z, byte shape) {
        if (shape == 0) {
            if (BlockMinecartTrack.g(world, x, y + 1, z - 1)) {
                shape = 4;
            }

            if (BlockMinecartTrack.g(world, x, y + 1, z + 1)) {
                shape = 5;
            }
        }

        if (shape == 1) {
            if (BlockMinecartTrack.g(world, x + 1, y + 1, z)) {
                shape = 2;
            }

            if (BlockMinecartTrack.g(world, x - 1, y + 1, z)) {
                shape = 3;
            }
        }

        return shape;
    }
}
