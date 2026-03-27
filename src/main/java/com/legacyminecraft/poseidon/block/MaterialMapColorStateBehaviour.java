package com.legacyminecraft.poseidon.block;


/**
 * Canonical state behaviour for legacy material map colors.
 */
public final class MaterialMapColorStateBehaviour {
    private static final MaterialMapColorStateBehaviour INSTANCE = new MaterialMapColorStateBehaviour();

    private MaterialMapColorStateBehaviour() {
    }

    public static MaterialMapColorStateBehaviour getInstance() {
        return INSTANCE;
    }

    public MaterialMapColorState initialize(Object[] registry, int colorIndex, int colorRgb, Object self) {
        registry[colorIndex] = self;
        return new MaterialMapColorState(colorRgb, colorIndex);
    }

    public static final class MaterialMapColorState {
        private final int colorRgb;
        private final int colorIndex;

        public MaterialMapColorState(int colorRgb, int colorIndex) {
            this.colorRgb = colorRgb;
            this.colorIndex = colorIndex;
        }

        public int getColorRgb() {
            return colorRgb;
        }

        public int getColorIndex() {
            return colorIndex;
        }
    }
}
