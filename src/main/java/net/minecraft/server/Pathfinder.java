package net.minecraft.server;

import org.bukkit.craftbukkit.block.BlockDoor;
import org.bukkit.craftbukkit.entity.Entity;
import org.bukkit.craftbukkit.entity.EntityList;

public class Pathfinder {

    private IBlockAccess worldMap;
    private Path path = new Path();
    private EntityList pointMap = new EntityList();
    private PathPoint[] pathOptions = new PathPoint[32];

    public Pathfinder(IBlockAccess blockAccess) {
        this.worldMap = blockAccess;
    }

    public PathEntity createEntityPathTo(Entity entity, Entity target, float maxDistance) {
        return this.createPathTo(entity, target.locX, target.boundingBox.b, target.locZ, maxDistance);
    }

    public PathEntity createPathTo(Entity entity, int x, int y, int z, float maxDistance) {
        return this.createPathTo(entity, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), maxDistance);
    }

    private PathEntity createPathTo(Entity entity, double x, double y, double z, float maxDistance) {
        this.path.clearPath();
        this.pointMap.a();
        PathPoint start = this.openPoint(MathHelper.floor(entity.boundingBox.a), MathHelper.floor(entity.boundingBox.b), MathHelper.floor(entity.boundingBox.c));
        PathPoint target = this.openPoint(MathHelper.floor(x - (double) (entity.length / 2.0F)), MathHelper.floor(y), MathHelper.floor(z - (double) (entity.length / 2.0F)));
        PathPoint size = new PathPoint(MathHelper.floor(entity.length + 1.0F), MathHelper.floor(entity.width + 1.0F), MathHelper.floor(entity.length + 1.0F));
        return this.findPath(entity, start, target, size, maxDistance);
    }

    private PathEntity findPath(Entity entity, PathPoint start, PathPoint target, PathPoint size, float maxDistance) {
        start.e = 0.0F;
        start.f = start.distanceTo(target);
        start.g = start.f;
        this.path.clearPath();
        this.path.addPoint(start);
        PathPoint closestPoint = start;

        while (!this.path.isEmpty()) {
            PathPoint currentPoint = this.path.dequeue();

            if (currentPoint.equals(target)) {
                return this.createEntityPath(start, target);
            }

            if (currentPoint.distanceTo(target) < closestPoint.distanceTo(target)) {
                closestPoint = currentPoint;
            }

            currentPoint.i = true;
            int optionCount = this.findPathOptions(entity, currentPoint, size, target, maxDistance);

            for (int optionIndex = 0; optionIndex < optionCount; ++optionIndex) {
                PathPoint nextPoint = this.pathOptions[optionIndex];
                float nextCost = currentPoint.e + currentPoint.distanceTo(nextPoint);

                if (!nextPoint.isAssigned() || nextCost < nextPoint.e) {
                    nextPoint.h = currentPoint;
                    nextPoint.e = nextCost;
                    nextPoint.f = nextPoint.distanceTo(target);
                    if (nextPoint.isAssigned()) {
                        this.path.changeDistance(nextPoint, nextPoint.e + nextPoint.f);
                    } else {
                        nextPoint.g = nextPoint.e + nextPoint.f;
                        this.path.addPoint(nextPoint);
                    }
                }
            }
        }

        return closestPoint == start ? null : this.createEntityPath(start, closestPoint);
    }

    private int findPathOptions(Entity entity, PathPoint currentPoint, PathPoint size, PathPoint target, float maxDistance) {
        int optionCount = 0;
        byte stepHeight = 0;

        if (this.getVerticalOffset(entity, currentPoint.a, currentPoint.b + 1, currentPoint.c, size) == 1) {
            stepHeight = 1;
        }

        PathPoint northPoint = this.getSafePoint(entity, currentPoint.a, currentPoint.b, currentPoint.c + 1, size, stepHeight);
        PathPoint westPoint = this.getSafePoint(entity, currentPoint.a - 1, currentPoint.b, currentPoint.c, size, stepHeight);
        PathPoint eastPoint = this.getSafePoint(entity, currentPoint.a + 1, currentPoint.b, currentPoint.c, size, stepHeight);
        PathPoint southPoint = this.getSafePoint(entity, currentPoint.a, currentPoint.b, currentPoint.c - 1, size, stepHeight);

        if (northPoint != null && !northPoint.i && northPoint.distanceTo(target) < maxDistance) {
            this.pathOptions[optionCount++] = northPoint;
        }

        if (westPoint != null && !westPoint.i && westPoint.distanceTo(target) < maxDistance) {
            this.pathOptions[optionCount++] = westPoint;
        }

        if (eastPoint != null && !eastPoint.i && eastPoint.distanceTo(target) < maxDistance) {
            this.pathOptions[optionCount++] = eastPoint;
        }

        if (southPoint != null && !southPoint.i && southPoint.distanceTo(target) < maxDistance) {
            this.pathOptions[optionCount++] = southPoint;
        }

        return optionCount;
    }

    private PathPoint getSafePoint(Entity entity, int x, int y, int z, PathPoint size, int stepHeight) {
        PathPoint safePoint = null;

        if (this.getVerticalOffset(entity, x, y, z, size) == 1) {
            safePoint = this.openPoint(x, y, z);
        }

        if (safePoint == null && stepHeight > 0 && this.getVerticalOffset(entity, x, y + stepHeight, z, size) == 1) {
            safePoint = this.openPoint(x, y + stepHeight, z);
            y += stepHeight;
        }

        if (safePoint != null) {
            int fallDistance = 0;
            int verticalOffset = 0;

            while (y > 0 && (verticalOffset = this.getVerticalOffset(entity, x, y - 1, z, size)) == 1) {
                ++fallDistance;
                if (fallDistance >= 4) {
                    return null;
                }

                --y;
                if (y > 0) {
                    safePoint = this.openPoint(x, y, z);
                }
            }

            if (verticalOffset == -2) {
                return null;
            }
        }

        return safePoint;
    }

    private final PathPoint openPoint(int x, int y, int z) {
        int hash = PathPoint.makeHash(x, y, z);
        PathPoint pathPoint = (PathPoint) this.pointMap.a(hash);

        if (pathPoint == null) {
            pathPoint = new PathPoint(x, y, z);
            this.pointMap.a(hash, pathPoint);
        }

        return pathPoint;
    }

    private int getVerticalOffset(Entity entity, int x, int y, int z, PathPoint size) {
        for (int blockX = x; blockX < x + size.a; ++blockX) {
            for (int blockY = y; blockY < y + size.b; ++blockY) {
                for (int blockZ = z; blockZ < z + size.c; ++blockZ) {
                    int typeId = this.worldMap.getTypeId(blockX, blockY, blockZ);

                    if (typeId > 0) {
                        if (typeId != CraftBlock.IRON_DOOR_BLOCK.id && typeId != CraftBlock.WOODEN_DOOR.id) {
                            Material material = CraftBlock.byId[typeId].material;

                            if (material.isSolid()) {
                                return 0;
                            }

                            if (material == Material.WATER) {
                                return -1;
                            }

                            if (material == Material.LAVA) {
                                return -2;
                            }
                        } else {
                            int blockData = this.worldMap.getData(blockX, blockY, blockZ);

                            if (!BlockDoor.e(blockData)) {
                                return 0;
                            }
                        }
                    }
                }
            }
        }

        return 1;
    }

    private PathEntity createEntityPath(PathPoint start, PathPoint end) {
        int pointCount = 1;

        PathPoint current;
        for (current = end; current.h != null; current = current.h) {
            ++pointCount;
        }

        PathPoint[] points = new PathPoint[pointCount];
        current = end;
        --pointCount;

        for (points[pointCount] = end; current.h != null; points[pointCount] = current) {
            current = current.h;
            --pointCount;
        }

        return new PathEntity(points);
    }

    @Deprecated
    public PathEntity a(Entity entity, Entity target, float maxDistance) {
        return this.createEntityPathTo(entity, target, maxDistance);
    }

    @Deprecated
    public PathEntity a(Entity entity, int x, int y, int z, float maxDistance) {
        return this.createPathTo(entity, x, y, z, maxDistance);
    }

    @Deprecated
    private PathEntity a(Entity entity, double x, double y, double z, float maxDistance) {
        return this.createPathTo(entity, x, y, z, maxDistance);
    }

    @Deprecated
    private PathEntity a(Entity entity, PathPoint start, PathPoint target, PathPoint size, float maxDistance) {
        return this.findPath(entity, start, target, size, maxDistance);
    }

    @Deprecated
    private int b(Entity entity, PathPoint currentPoint, PathPoint size, PathPoint target, float maxDistance) {
        return this.findPathOptions(entity, currentPoint, size, target, maxDistance);
    }

    @Deprecated
    private PathPoint a(Entity entity, int x, int y, int z, PathPoint size, int stepHeight) {
        return this.getSafePoint(entity, x, y, z, size, stepHeight);
    }

    @Deprecated
    private final PathPoint a(int x, int y, int z) {
        return this.openPoint(x, y, z);
    }

    @Deprecated
    private int a(Entity entity, int x, int y, int z, PathPoint size) {
        return this.getVerticalOffset(entity, x, y, z, size);
    }

    @Deprecated
    private PathEntity a(PathPoint start, PathPoint end) {
        return this.createEntityPath(start, end);
    }
}
