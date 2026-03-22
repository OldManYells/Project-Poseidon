package net.minecraft.server;

import com.legacyminecraft.poseidon.compat.bukkit.PortalCreateEventBridgeBehaviour;
import com.legacyminecraft.poseidon.world.PortalTravelSearchBehaviour;

import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class PortalTravelAgent {
    private static final PortalCreateEventBridgeBehaviour PORTAL_CREATE_EVENT_BRIDGE_BEHAVIOUR = PortalCreateEventBridgeBehaviour.getInstance();
    private static final PortalTravelSearchBehaviour PORTAL_TRAVEL_SEARCH_BEHAVIOUR = PortalTravelSearchBehaviour.getInstance();

    private Random a = new Random();

    public PortalTravelAgent() {}

    public void a(World world, Entity entity) {
        if (!this.b(world, entity)) {
            this.c(world, entity);
            this.b(world, entity);
        }
    }

    public boolean b(World world, Entity entity) {
        int short1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.defaultSearchRadius();
        double d0 = -1.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = MathHelper.floor(entity.locX);
        int i1 = MathHelper.floor(entity.locZ);

        double d1;

        for (int j1 = l - short1; j1 <= l + short1; ++j1) {
            double d2 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(j1), entity.locX);

            for (int k1 = i1 - short1; k1 <= i1 + short1; ++k1) {
                double d3 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(k1), entity.locZ);

                for (int l1 = 127; l1 >= 0; --l1) {
                    if (world.getTypeId(j1, l1, k1) == Block.PORTAL.id) {
                        l1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.descendToPortalBase(world, j1, l1, k1);

                        d1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(l1), entity.locY);
                        double d4 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.squaredDistance(d2, d1, d3);

                        if (PORTAL_TRAVEL_SEARCH_BEHAVIOUR.isBetterDistance(d0, d4)) {
                            d0 = d4;
                            i = j1;
                            j = l1;
                            k = k1;
                        }
                    }
                }
            }
        }

        if (d0 >= 0.0D) {
            double d5 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(i);
            double d6 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(j);
            d1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(k);
            d5 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.adjustPortalCenterX(world, i, j, k, d5);
            d1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.adjustPortalCenterZ(world, i, j, k, d1);

            entity.setPositionRotation(d5, d6, d1, entity.yaw, 0.0F);
            entity.motX = entity.motY = entity.motZ = 0.0D;
            return true;
        } else {
            return false;
        }
    }

    public boolean c(World world, Entity entity) {
        int b0 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.defaultCreateSearchRadius();
        double d0 = -1.0D;
        int i = MathHelper.floor(entity.locX);
        int j = MathHelper.floor(entity.locY);
        int k = MathHelper.floor(entity.locZ);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = this.a.nextInt(4);

        int i2;
        double d1;
        int j2;
        double d2;
        int k2;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        double d3;
        double d4;

        for (i2 = i - b0; i2 <= i + b0; ++i2) {
            d1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(i2), entity.locX);

            for (j2 = k - b0; j2 <= k + b0; ++j2) {
                d2 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(j2), entity.locZ);

                label271:
                for (l2 = 127; l2 >= 0; --l2) {
                    if (world.isEmpty(i2, l2, j2)) {
                        while (l2 > 0 && world.isEmpty(i2, l2 - 1, j2)) {
                            --l2;
                        }

                        for (k2 = l1; k2 < l1 + 4; ++k2) {
                            j3 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.orientationAxisX(k2);
                            i3 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.orientationAxisZ(k2);

                            for (l3 = 0; l3 < 3; ++l3) {
                                for (k3 = 0; k3 < 4; ++k3) {
                                    for (j4 = -1; j4 < 4; ++j4) {
                                        i4 = i2 + (k3 - 1) * j3 + l3 * i3;
                                        k4 = l2 + j4;
                                        int l4 = j2 + (k3 - 1) * i3 - l3 * j3;

                                        if (j4 < 0 && !world.getMaterial(i4, k4, l4).isBuildable() || j4 >= 0 && !world.isEmpty(i4, k4, l4)) {
                                            continue label271;
                                        }
                                    }
                                }
                            }

                            d3 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(l2), entity.locY);
                            d4 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.squaredDistance(d1, d3, d2);
                            if (PORTAL_TRAVEL_SEARCH_BEHAVIOUR.isBetterDistance(d0, d4)) {
                                d0 = d4;
                                l = i2;
                                i1 = l2;
                                j1 = j2;
                                k1 = k2 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D) {
            for (i2 = i - b0; i2 <= i + b0; ++i2) {
                d1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(i2), entity.locX);

                for (j2 = k - b0; j2 <= k + b0; ++j2) {
                    d2 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(j2), entity.locZ);

                    label219:
                    for (l2 = 127; l2 >= 0; --l2) {
                        if (world.isEmpty(i2, l2, j2)) {
                            while (world.isEmpty(i2, l2 - 1, j2)) {
                                --l2;
                            }

                            for (k2 = l1; k2 < l1 + 2; ++k2) {
                                j3 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.primaryAxisX(k2);
                                i3 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.primaryAxisZ(k2);

                                for (l3 = 0; l3 < 4; ++l3) {
                                    for (k3 = -1; k3 < 4; ++k3) {
                                        j4 = i2 + (l3 - 1) * j3;
                                        i4 = l2 + k3;
                                        k4 = j2 + (l3 - 1) * i3;
                                        if (k3 < 0 && !world.getMaterial(j4, i4, k4).isBuildable() || k3 >= 0 && !world.isEmpty(j4, i4, k4)) {
                                            continue label219;
                                        }
                                    }
                                }

                                d3 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance(PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate(l2), entity.locY);
                                d4 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.squaredDistance(d1, d3, d2);
                                if (PORTAL_TRAVEL_SEARCH_BEHAVIOUR.isBetterDistance(d0, d4)) {
                                    d0 = d4;
                                    l = i2;
                                    i1 = l2;
                                    j1 = j2;
                                    k1 = k2 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i5 = l;
        int j5 = i1;

        j2 = j1;
        int k5 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.orientationAxisX(k1);
        int l5 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.orientationAxisZ(k1);

        boolean flag;

        // CraftBukkit start - portal create event
        java.util.Collection<org.bukkit.block.Block> blocks = new java.util.HashSet<org.bukkit.block.Block>();
        // Find out what blocks the portal is going to modify, duplicated from below
        org.bukkit.World bworld = world.getWorld();

        if (d0 < 0.0D) {
            i1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.clampPortalBaseY(i1);

            j5 = i1;

            for (l2 = -1; l2 <= 1; ++l2) {
                for (k2 = 1; k2 < 3; ++k2) {
                    for (j3 = -1; j3 < 3; ++j3) {
                        i3 = i5 + (k2 - 1) * k5 + l2 * l5;
                        l3 = j5 + j3;
                        k3 = j2 + (k2 - 1) * l5 - l2 * k5;
                        blocks.add(bworld.getBlockAt(i3, l3, k3));
                    }
                }
            }
        }

        for (l2 = 0; l2 < 4; ++l2) {
            for (k2 = 0; k2 < 4; ++k2) {
                for (j3 = -1; j3 < 4; ++j3) {
                    i3 = i5 + (k2 - 1) * k5;
                    l3 = j5 + j3;
                    k3 = j2 + (k2 - 1) * l5;
                    blocks.add(bworld.getBlockAt(i3, l3, k3));
                }
            }
        }

        if (PORTAL_CREATE_EVENT_BRIDGE_BEHAVIOUR.shouldCancelPortalCreation(blocks, bworld)) {
            return true;
        }
        // CraftBukkit end

        if (d0 < 0.0D) {
            i1 = PORTAL_TRAVEL_SEARCH_BEHAVIOUR.clampPortalBaseY(i1);

            j5 = i1;

            for (l2 = -1; l2 <= 1; ++l2) {
                for (k2 = 1; k2 < 3; ++k2) {
                    for (j3 = -1; j3 < 3; ++j3) {
                        i3 = i5 + (k2 - 1) * k5 + l2 * l5;
                        l3 = j5 + j3;
                        k3 = j2 + (k2 - 1) * l5 - l2 * k5;
                        flag = j3 < 0;
                        world.setTypeId(i3, l3, k3, flag ? Block.OBSIDIAN.id : 0);
                    }
                }
            }
        }

        for (l2 = 0; l2 < 4; ++l2) {
            world.suppressPhysics = true;

            for (k2 = 0; k2 < 4; ++k2) {
                for (j3 = -1; j3 < 4; ++j3) {
                    i3 = i5 + (k2 - 1) * k5;
                    l3 = j5 + j3;
                    k3 = j2 + (k2 - 1) * l5;
                    flag = k2 == 0 || k2 == 3 || j3 == -1 || j3 == 3;
                    world.setTypeId(i3, l3, k3, flag ? Block.OBSIDIAN.id : Block.PORTAL.id);
                }
            }

            world.suppressPhysics = false;

            for (k2 = 0; k2 < 4; ++k2) {
                for (j3 = -1; j3 < 4; ++j3) {
                    i3 = i5 + (k2 - 1) * k5;
                    l3 = j5 + j3;
                    k3 = j2 + (k2 - 1) * l5;
                    world.applyPhysics(i3, l3, k3, world.getTypeId(i3, l3, k3));
                }
            }
        }

        return true;
    }
}
