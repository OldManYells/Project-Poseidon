package com.legacyminecraft.poseidon.item;

public final class ReedItemPlacementBehaviour {
    private static final ReedItemPlacementBehaviour INSTANCE = new ReedItemPlacementBehaviour();

    private ReedItemPlacementBehaviour() {
    }

    public static ReedItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public Placement resolveTargetAndFace(int x, int y, int z, int face, int clickedTypeId, int snowId, ItemPlacementMathBehaviour math) {
        if (math.isSnow(clickedTypeId, snowId)) {
            return new Placement(x, y, z, 0);
        }
        ItemPlacementMathBehaviour.Position target = math.applyFaceOffset(x, y, z, face);
        return new Placement(target.x, target.y, target.z, face);
    }

    public boolean hasItemsLeft(int count) {
        return count > 0;
    }

    public static final class Placement {
        public final int x;
        public final int y;
        public final int z;
        public final int face;

        public Placement(int x, int y, int z, int face) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.face = face;
        }
    }
}
