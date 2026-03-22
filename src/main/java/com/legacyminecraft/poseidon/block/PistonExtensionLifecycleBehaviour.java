package com.legacyminecraft.poseidon.block;

import net.minecraft.server.Block;
import net.minecraft.server.BlockPiston;
import net.minecraft.server.PistonBlockTextures;
import net.minecraft.server.World;

/**
 * Canonical lifecycle policy for piston extension blocks.
 */
public final class PistonExtensionLifecycleBehaviour {
    private static final PistonExtensionLifecycleBehaviour INSTANCE = new PistonExtensionLifecycleBehaviour();

    private final PistonExtensionGeometryBehaviour pistonExtensionGeometryService = PistonExtensionGeometryBehaviour.getInstance();

    private PistonExtensionLifecycleBehaviour() {
    }

    public static PistonExtensionLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isValidExtensionDataForRemoval(int blockData) {
        return blockData >= 0 && blockData <= 13 && blockData != 6 && blockData != 7;
    }

    public boolean isValidFacingForPhysics(int facing) {
        return facing >= 0 && facing <= 5;
    }

    public BasePosition resolvePistonBaseForRemoval(int x, int y, int z, int extensionData) {
        if (!isValidExtensionDataForRemoval(extensionData)) {
            return null;
        }

        int facing = pistonExtensionGeometryService.extractFacing(extensionData);
        int reverseFacing = PistonBlockTextures.a[facing];
        return new BasePosition(
                x + PistonBlockTextures.b[reverseFacing],
                y + PistonBlockTextures.c[reverseFacing],
                z + PistonBlockTextures.d[reverseFacing]
        );
    }

    public BasePosition resolvePistonBaseForPhysics(int x, int y, int z, int extensionData) {
        int facing = pistonExtensionGeometryService.extractFacing(extensionData);
        if (!isValidFacingForPhysics(facing)) {
            return null;
        }

        return new BasePosition(
                x - PistonBlockTextures.b[facing],
                y - PistonBlockTextures.c[facing],
                z - PistonBlockTextures.d[facing]
        );
    }

    public boolean isPistonBaseType(int typeId) {
        return typeId == Block.PISTON.id || typeId == Block.PISTON_STICKY.id;
    }

    public void removeAttachedPistonBaseIfNecessary(World world, int x, int y, int z, int extensionData) {
        BasePosition basePosition = resolvePistonBaseForRemoval(x, y, z, extensionData);
        if (basePosition == null) {
            return;
        }

        int baseType = world.getTypeId(basePosition.getX(), basePosition.getY(), basePosition.getZ());
        if (!isPistonBaseType(baseType)) {
            return;
        }

        int baseData = world.getData(basePosition.getX(), basePosition.getY(), basePosition.getZ());
        if (BlockPiston.d(baseData)) {
            Block.byId[baseType].g(world, basePosition.getX(), basePosition.getY(), basePosition.getZ(), baseData);
            world.setTypeId(basePosition.getX(), basePosition.getY(), basePosition.getZ(), 0);
        }
    }

    public void validateOrBreakExtension(World world, int x, int y, int z, int sourceTypeId, int extensionData) {
        BasePosition basePosition = resolvePistonBaseForPhysics(x, y, z, extensionData);
        if (basePosition == null) {
            return;
        }

        int baseType = world.getTypeId(basePosition.getX(), basePosition.getY(), basePosition.getZ());
        if (!isPistonBaseType(baseType)) {
            world.setTypeId(x, y, z, 0);
            return;
        }

        Block.byId[baseType].doPhysics(world, basePosition.getX(), basePosition.getY(), basePosition.getZ(), sourceTypeId);
    }

    public static final class BasePosition {
        private final int x;
        private final int y;
        private final int z;

        public BasePosition(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
