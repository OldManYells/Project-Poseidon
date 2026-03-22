package net.minecraft.server;

import com.legacyminecraft.poseidon.block.PortalFrameBehaviour;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.world.PortalCreateEvent;

import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class BlockPortal extends BlockBreakable {
    private final PortalFrameBehaviour portalFrameService = PortalFrameBehaviour.getInstance();

    public BlockPortal(int i, int j) {
        super(i, j, Material.PORTAL, false);
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public void a(IBlockAccess iblockaccess, int i, int j, int k) {
        PortalFrameBehaviour.Bounds bounds = portalFrameService.resolvePortalBounds(
                iblockaccess.getTypeId(i - 1, j, k) == this.id,
                iblockaccess.getTypeId(i + 1, j, k) == this.id
        );
        this.a(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean a_(World world, int i, int j, int k) {
        PortalFrameBehaviour.PortalAxis axis = portalFrameService.resolveCreationAxis(
                world.getTypeId(i - 1, j, k) == Block.OBSIDIAN.id,
                world.getTypeId(i + 1, j, k) == Block.OBSIDIAN.id,
                world.getTypeId(i, j, k - 1) == Block.OBSIDIAN.id,
                world.getTypeId(i, j, k + 1) == Block.OBSIDIAN.id
        );

        if (!axis.valid) {
            return false;
        } else {
            // CraftBukkit start
            java.util.Collection<org.bukkit.block.Block> blocks = new java.util.HashSet<org.bukkit.block.Block>();
            org.bukkit.World bworld = world.getWorld();
            // CraftBukkit end

            if (portalFrameService.shouldShiftOriginToLowerLeft(world.getTypeId(i - axis.axisX, j, k - axis.axisZ))) {
                i -= axis.axisX;
                k -= axis.axisZ;
            }

            for (int l = -1; l <= 2; ++l) {
                for (int i1 = -1; i1 <= 3; ++i1) {
                    if (!portalFrameService.shouldInspectFrameCoordinate(l, i1)) {
                        continue;
                    }

                    int j1 = world.getTypeId(i + axis.axisX * l, j + i1, k + axis.axisZ * l);
                    if (portalFrameService.isFrameBoundaryCoordinate(l, i1)) {
                        if (!portalFrameService.isValidFrameBoundaryBlock(j1, Block.OBSIDIAN.id)) {
                            return false;
                        }
                        blocks.add(bworld.getBlockAt(i + axis.axisX * l, j + i1, k + axis.axisZ * l)); // CraftBukkit
                    } else if (!portalFrameService.isValidPortalInteriorBlock(j1, Block.FIRE.id)) {
                        return false;
                    }
                }
            }

            // CraftBukkit start
            portalFrameService.forEachPortalInteriorCoordinate(i, j, k, axis.axisX, axis.axisZ, new PortalFrameBehaviour.CoordinateConsumer() {
                public void accept(int x, int y, int z) {
                    blocks.add(bworld.getBlockAt(x, y, z));
                }
            });

            PortalCreateEvent event = new PortalCreateEvent(blocks, bworld);
            world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }
            // CraftBukkit end

            world.suppressPhysics = true;

            portalFrameService.forEachPortalInteriorCoordinate(i, j, k, axis.axisX, axis.axisZ, new PortalFrameBehaviour.CoordinateConsumer() {
                public void accept(int x, int y, int z) {
                    world.setTypeId(x, y, z, Block.PORTAL.id);
                }
            });

            world.suppressPhysics = false;
            return true;
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        boolean hasPortalWest = world.getTypeId(i - 1, j, k) == this.id;
        boolean hasPortalEast = world.getTypeId(i + 1, j, k) == this.id;
        int b0 = portalFrameService.resolvePhysicsAxisX(hasPortalWest, hasPortalEast);
        int b1 = portalFrameService.resolvePhysicsAxisZ(hasPortalWest, hasPortalEast);
        int i1 = portalFrameService.findPortalBaseY(new PortalFrameBehaviour.TypeIdQuery() {
            public int getTypeId(int x, int y, int z) {
                return world.getTypeId(x, y, z);
            }
        }, i, j, k, this.id);

        if (!portalFrameService.hasValidPortalBase(world.getTypeId(i, i1 - 1, k), Block.OBSIDIAN.id)) {
            world.setTypeId(i, j, k, 0);
        } else {
            int j1 = portalFrameService.countVerticalPortalSpan(new PortalFrameBehaviour.TypeIdQuery() {
                public int getTypeId(int x, int y, int z) {
                    return world.getTypeId(x, y, z);
                }
            }, i, i1, k, this.id);

            if (portalFrameService.hasValidPortalCap(j1, world.getTypeId(i, i1 + j1, k), Block.OBSIDIAN.id)) {
                boolean flag = world.getTypeId(i - 1, j, k) == this.id || world.getTypeId(i + 1, j, k) == this.id;
                boolean flag1 = world.getTypeId(i, j, k - 1) == this.id || world.getTypeId(i, j, k + 1) == this.id;

                if (portalFrameService.hasCrossAxisPortalConflict(flag, flag1)) {
                    world.setTypeId(i, j, k, 0);
                } else if (portalFrameService.shouldDropPortalForInvalidSideSupport(portalFrameService.hasValidSideSupportPair(
                        world.getTypeId(i + b0, j, k + b1),
                        world.getTypeId(i - b0, j, k - b1),
                        Block.OBSIDIAN.id,
                        this.id
                ))) {
                    world.setTypeId(i, j, k, 0);
                }
            } else {
                world.setTypeId(i, j, k, 0);
            }
        }
    }

    public int a(Random random) {
        return portalFrameService.noDropCount();
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        if (portalFrameService.shouldTriggerEntityPortal(entity)) {
            // CraftBukkit start - Entity in portal
            EntityPortalEnterEvent event = new EntityPortalEnterEvent(entity.getBukkitEntity(), new org.bukkit.Location(world.getWorld(), i, j, k));
            world.getServer().getPluginManager().callEvent(event);
            // CraftBukkit end

            entity.P();
        }
    }
}
