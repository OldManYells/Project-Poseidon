package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Block;
import net.minecraft.server.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;

import java.util.ArrayList;
import java.util.Random;

/**
 * Canonical behaviour for CraftBukkit portal creation search, event bridge, and frame placement.
 */
public final class PortalCreationBehaviour {
    private static final PortalCreationBehaviour INSTANCE = new PortalCreationBehaviour();
    private static final PortalCreateEventBridgeBehaviour PORTAL_CREATE_EVENT_BRIDGE_BEHAVIOUR =
            PortalCreateEventBridgeBehaviour.getInstance();

    private PortalCreationBehaviour() {
    }

    public static PortalCreationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean createPortal(CraftWorld craftWorld, Location location, Random random, int creationRadius) {
        World world = craftWorld.getHandle();

        double bestDistanceSquared = -1.0D;
        int originX = location.getBlockX();
        int originY = location.getBlockY();
        int originZ = location.getBlockZ();
        int bestX = originX;
        int bestY = originY;
        int bestZ = originZ;
        int bestOrientation = 0;
        int randomOrientationStart = random.nextInt(4);

        for (int searchX = originX - creationRadius; searchX <= originX + creationRadius; ++searchX) {
            double deltaX = (double) searchX + 0.5D - location.getX();

            for (int searchZ = originZ - creationRadius; searchZ <= originZ + creationRadius; ++searchZ) {
                double deltaZ = (double) searchZ + 0.5D - location.getZ();

                label271:
                for (int searchY = 127; searchY >= 0; --searchY) {
                    if (world.isEmpty(searchX, searchY, searchZ)) {
                        while (searchY > 0 && world.isEmpty(searchX, searchY - 1, searchZ)) {
                            --searchY;
                        }

                        for (int orientationIndex = randomOrientationStart; orientationIndex < randomOrientationStart + 4; ++orientationIndex) {
                            int axisX = orientationIndex % 2;
                            int axisZ = 1 - axisX;
                            if (orientationIndex % 4 >= 2) {
                                axisX = -axisX;
                                axisZ = -axisZ;
                            }

                            for (int widthOffset = 0; widthOffset < 3; ++widthOffset) {
                                for (int depthOffset = 0; depthOffset < 4; ++depthOffset) {
                                    for (int yOffset = -1; yOffset < 5; ++yOffset) {
                                        int frameX = searchX + (depthOffset - 1) * axisX + widthOffset * axisZ;
                                        int frameY = searchY + yOffset;
                                        int frameZ = searchZ + (depthOffset - 1) * axisZ - widthOffset * axisX;

                                        if (yOffset < 0 && !world.getMaterial(frameX, frameY, frameZ).isBuildable()
                                                || yOffset >= 0 && !world.isEmpty(frameX, frameY, frameZ)) {
                                            continue label271;
                                        }
                                    }
                                }
                            }

                            double deltaY = (double) searchY + 0.5D - location.getY();
                            double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
                            if (bestDistanceSquared < 0.0D || distanceSquared < bestDistanceSquared) {
                                bestDistanceSquared = distanceSquared;
                                bestX = searchX;
                                bestY = searchY + 1;
                                bestZ = searchZ;
                                bestOrientation = orientationIndex % 4;
                            }
                        }
                    }
                }
            }
        }

        if (bestDistanceSquared < 0.0D) {
            for (int searchX = originX - creationRadius; searchX <= originX + creationRadius; ++searchX) {
                double deltaX = (double) searchX + 0.5D - location.getX();

                for (int searchZ = originZ - creationRadius; searchZ <= originZ + creationRadius; ++searchZ) {
                    double deltaZ = (double) searchZ + 0.5D - location.getZ();

                    label219:
                    for (int searchY = 127; searchY >= 0; --searchY) {
                        if (world.isEmpty(searchX, searchY, searchZ)) {
                            while (searchY > 0 && world.isEmpty(searchX, searchY - 1, searchZ)) {
                                --searchY;
                            }

                            for (int orientationIndex = randomOrientationStart; orientationIndex < randomOrientationStart + 2; ++orientationIndex) {
                                int axisX = orientationIndex % 2;
                                int axisZ = 1 - axisX;

                                for (int depthOffset = 0; depthOffset < 4; ++depthOffset) {
                                    for (int yOffset = -1; yOffset < 5; ++yOffset) {
                                        int frameX = searchX + (depthOffset - 1) * axisX;
                                        int frameY = searchY + yOffset;
                                        int frameZ = searchZ + (depthOffset - 1) * axisZ;
                                        if (yOffset < 0 && !world.getMaterial(frameX, frameY, frameZ).isBuildable()
                                                || yOffset >= 0 && !world.isEmpty(frameX, frameY, frameZ)) {
                                            continue label219;
                                        }
                                    }
                                }

                                double deltaY = (double) searchY + 0.5D - location.getY();
                                double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
                                if (bestDistanceSquared < 0.0D || distanceSquared < bestDistanceSquared) {
                                    bestDistanceSquared = distanceSquared;
                                    bestX = searchX;
                                    bestY = searchY + 1;
                                    bestZ = searchZ;
                                    bestOrientation = orientationIndex % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int portalX = bestX;
        int portalY = bestY;
        int portalZ = bestZ;
        int frameAxisX = bestOrientation % 2;
        int frameAxisZ = 1 - frameAxisX;
        if (bestOrientation % 4 >= 2) {
            frameAxisX = -frameAxisX;
            frameAxisZ = -frameAxisZ;
        }

        ArrayList<org.bukkit.block.Block> changedBlocks = new ArrayList<org.bukkit.block.Block>();
        if (bestDistanceSquared < 0.0D) {
            if (bestY < 70) {
                bestY = 70;
            }
            if (bestY > 118) {
                bestY = 118;
            }
            portalY = bestY;

            for (int sideOffset = -1; sideOffset <= 1; ++sideOffset) {
                for (int depthOffset = 1; depthOffset < 3; ++depthOffset) {
                    for (int yOffset = -1; yOffset < 3; ++yOffset) {
                        int frameX = portalX + (depthOffset - 1) * frameAxisX + sideOffset * frameAxisZ;
                        int frameY = portalY + yOffset;
                        int frameZ = portalZ + (depthOffset - 1) * frameAxisZ - sideOffset * frameAxisX;
                        addUniqueBlock(changedBlocks, craftWorld.getBlockAt(frameX, frameY, frameZ));
                    }
                }
            }
        }

        for (int depthOffset = 0; depthOffset < 4; ++depthOffset) {
            for (int widthOffset = 0; widthOffset < 4; ++widthOffset) {
                for (int yOffset = -1; yOffset < 4; ++yOffset) {
                    int frameX = portalX + (widthOffset - 1) * frameAxisX;
                    int frameY = portalY + yOffset;
                    int frameZ = portalZ + (widthOffset - 1) * frameAxisZ;
                    addUniqueBlock(changedBlocks, craftWorld.getBlockAt(frameX, frameY, frameZ));
                }
            }
        }

        if (PORTAL_CREATE_EVENT_BRIDGE_BEHAVIOUR.shouldCancelPortalCreation(changedBlocks, craftWorld)) {
            return false;
        }

        if (bestDistanceSquared < 0.0D) {
            if (bestY < 70) {
                bestY = 70;
            }
            if (bestY > 118) {
                bestY = 118;
            }
            portalY = bestY;

            for (int sideOffset = -1; sideOffset <= 1; ++sideOffset) {
                for (int depthOffset = 1; depthOffset < 3; ++depthOffset) {
                    for (int yOffset = -1; yOffset < 3; ++yOffset) {
                        int frameX = portalX + (depthOffset - 1) * frameAxisX + sideOffset * frameAxisZ;
                        int frameY = portalY + yOffset;
                        int frameZ = portalZ + (depthOffset - 1) * frameAxisZ - sideOffset * frameAxisX;
                        boolean isFoundation = yOffset < 0;
                        world.setTypeId(frameX, frameY, frameZ, isFoundation ? Block.OBSIDIAN.id : 0);
                    }
                }
            }
        }

        for (int depthOffset = 0; depthOffset < 4; ++depthOffset) {
            world.suppressPhysics = true;

            for (int widthOffset = 0; widthOffset < 4; ++widthOffset) {
                for (int yOffset = -1; yOffset < 4; ++yOffset) {
                    int frameX = portalX + (widthOffset - 1) * frameAxisX;
                    int frameY = portalY + yOffset;
                    int frameZ = portalZ + (widthOffset - 1) * frameAxisZ;
                    boolean isFrame = widthOffset == 0 || widthOffset == 3 || yOffset == -1 || yOffset == 3;
                    world.setTypeId(frameX, frameY, frameZ, isFrame ? Block.OBSIDIAN.id : Block.PORTAL.id);
                }
            }

            world.suppressPhysics = false;

            for (int widthOffset = 0; widthOffset < 4; ++widthOffset) {
                for (int yOffset = -1; yOffset < 4; ++yOffset) {
                    int frameX = portalX + (widthOffset - 1) * frameAxisX;
                    int frameY = portalY + yOffset;
                    int frameZ = portalZ + (widthOffset - 1) * frameAxisZ;
                    world.applyPhysics(frameX, frameY, frameZ, world.getTypeId(frameX, frameY, frameZ));
                }
            }
        }

        return true;
    }

    private void addUniqueBlock(ArrayList<org.bukkit.block.Block> changedBlocks, org.bukkit.block.Block block) {
        if (!changedBlocks.contains(block)) {
            changedBlocks.add(block);
        }
    }
}
