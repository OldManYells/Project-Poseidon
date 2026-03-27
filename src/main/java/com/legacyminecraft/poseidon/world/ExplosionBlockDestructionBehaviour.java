package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.block.Block;

/**
 * Canonical behaviour for explosion-driven block destruction/drop flow.
 */
public final class ExplosionBlockDestructionBehaviour {
    private static final ExplosionBlockDestructionBehaviour INSTANCE = new ExplosionBlockDestructionBehaviour();

    private ExplosionBlockDestructionBehaviour() {
    }

    public static ExplosionBlockDestructionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldDestroyBlock(int blockTypeId) {
        return blockTypeId > 0 && blockTypeId != Block.FIRE.id;
    }

    public void destroyAndDrop(Object world, int x, int y, int z, int blockTypeId, float yield) {
        int data = invokeInt(world, "getData", x, y, z);
        World bridgedWorld = asWorld(world);
        Block.byId[blockTypeId].dropNaturally(bridgedWorld, x, y, z, data, yield);
        invoke(world, "setTypeId", x, y, z, 0);
        Block.byId[blockTypeId].d(bridgedWorld, x, y, z);
    }

    private static int invokeInt(Object target, String method, int x, int y, int z) {
        try {
            Object value = target.getClass()
                    .getMethod(method, Integer.TYPE, Integer.TYPE, Integer.TYPE)
                    .invoke(target, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
            return value instanceof Integer ? ((Integer) value).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }

    private static void invoke(Object target, String method, int x, int y, int z, int typeId) {
        try {
            target.getClass()
                    .getMethod(method, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE)
                    .invoke(target, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), Integer.valueOf(typeId));
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private static World asWorld(Object world) {
        return world instanceof World ? (World) world : new World();
    }
}
