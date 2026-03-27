package com.legacyminecraft.poseidon.block;

/**
 * Canonical NBT serialization behaviour for piston tile-entity state.
 */
public final class PistonTileNbtBehaviour {
    private static final PistonTileNbtBehaviour INSTANCE = new PistonTileNbtBehaviour();

    private PistonTileNbtBehaviour() {
    }

    public static PistonTileNbtBehaviour getInstance() {
        return INSTANCE;
    }

    public PistonTileState readState(Object nbt) {
        int movedBlockId = ((Number) Reflection.invoke(nbt, "e", "blockId")).intValue();
        int movedBlockData = ((Number) Reflection.invoke(nbt, "e", "blockData")).intValue();
        int facing = ((Number) Reflection.invoke(nbt, "e", "facing")).intValue();
        float progress = ((Number) Reflection.invoke(nbt, "g", "progress")).floatValue();
        boolean extending = (Boolean) Reflection.invoke(nbt, "m", "extending");
        return new PistonTileState(movedBlockId, movedBlockData, facing, progress, extending);
    }

    public void writeState(Object nbt,
                           int movedBlockId,
                           int movedBlockData,
                           int facing,
                           float progress,
                           boolean extending) {
        Reflection.invoke(nbt, "a", "blockId", movedBlockId);
        Reflection.invoke(nbt, "a", "blockData", movedBlockData);
        Reflection.invoke(nbt, "a", "facing", facing);
        Reflection.invoke(nbt, "a", "progress", progress);
        Reflection.invoke(nbt, "a", "extending", extending);
    }

    public static final class PistonTileState {
        private final int movedBlockId;
        private final int movedBlockData;
        private final int facing;
        private final float progress;
        private final boolean extending;

        public PistonTileState(int movedBlockId, int movedBlockData, int facing, float progress, boolean extending) {
            this.movedBlockId = movedBlockId;
            this.movedBlockData = movedBlockData;
            this.facing = facing;
            this.progress = progress;
            this.extending = extending;
        }

        public int getMovedBlockId() {
            return movedBlockId;
        }

        public int getMovedBlockData() {
            return movedBlockData;
        }

        public int getFacing() {
            return facing;
        }

        public float getProgress() {
            return progress;
        }

        public boolean isExtending() {
            return extending;
        }
    }

    private static final class Reflection {
        private Reflection() {
        }

        static Object invoke(Object target, String methodName, Object... args) {
            try {
                for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(target, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to invoke method: " + methodName, exception);
            }
        }
    }
}
