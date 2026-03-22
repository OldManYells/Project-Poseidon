package com.legacyminecraft.poseidon.item;

public final class ItemPlacementMathBehaviour {
    private static final ItemPlacementMathBehaviour INSTANCE = new ItemPlacementMathBehaviour();

    private ItemPlacementMathBehaviour() {
    }

    public static ItemPlacementMathBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isTopFace(int face) {
        return face == 1;
    }

    public Position applyFaceOffset(int x, int y, int z, int face) {
        int tx = x;
        int ty = y;
        int tz = z;
        if (face == 0) {
            --ty;
        }
        if (face == 1) {
            ++ty;
        }
        if (face == 2) {
            --tz;
        }
        if (face == 3) {
            ++tz;
        }
        if (face == 4) {
            --tx;
        }
        if (face == 5) {
            ++tx;
        }
        return new Position(tx, ty, tz);
    }

    public int yawToCardinal4(float yaw) {
        return ((int) Math.floor((double) (yaw * 4.0F / 360.0F) + 0.5D)) & 3;
    }

    public int yawToCardinal4ForDoor(float yaw) {
        return ((int) Math.floor((double) ((yaw + 180.0F) * 4.0F / 360.0F) - 0.5D)) & 3;
    }

    public int yawToSignRotation16(float yaw) {
        return ((int) Math.floor((double) ((yaw + 180.0F) * 16.0F / 360.0F) + 0.5D)) & 15;
    }

    public Offset facingToOffset(int facing) {
        if (facing == 0) {
            return new Offset(0, 1);
        }
        if (facing == 1) {
            return new Offset(-1, 0);
        }
        if (facing == 2) {
            return new Offset(0, -1);
        }
        return new Offset(1, 0);
    }

    public boolean isSnow(int clickedTypeId, int snowBlockId) {
        return clickedTypeId == snowBlockId;
    }

    public static final class Position {
        public final int x;
        public final int y;
        public final int z;

        public Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static final class Offset {
        public final int x;
        public final int z;

        public Offset(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }
}
