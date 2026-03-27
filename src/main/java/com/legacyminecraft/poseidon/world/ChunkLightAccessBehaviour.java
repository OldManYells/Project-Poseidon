package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for chunk-local sky/block light read/write and brightness combine rules.
 */
public final class ChunkLightAccessBehaviour {
    private static final ChunkLightAccessBehaviour INSTANCE = new ChunkLightAccessBehaviour();

    private ChunkLightAccessBehaviour() {
    }

    public static ChunkLightAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public int getLight(NibbleArray skyLight, NibbleArray blockLight, Object lightType, int x, int y, int z) {
        String lightTypeName = lightType == null ? "" : lightType.toString();
        if ("SKY".equals(lightTypeName)) {
            return skyLight.a(x, y, z);
        }
        if ("BLOCK".equals(lightTypeName)) {
            return blockLight.a(x, y, z);
        }
        return 0;
    }

    public void setLight(NibbleArray skyLight, NibbleArray blockLight, Object lightType, int x, int y, int z, int value) {
        String lightTypeName = lightType == null ? "" : lightType.toString();
        if ("SKY".equals(lightTypeName)) {
            skyLight.a(x, y, z, value);
            return;
        }
        if ("BLOCK".equals(lightTypeName)) {
            blockLight.a(x, y, z, value);
        }
    }

    public int resolveBrightness(NibbleArray skyLight, NibbleArray blockLight, int x, int y, int z, int attenuation) {
        int sky = skyLight.a(x, y, z) - attenuation;
        int block = blockLight.a(x, y, z);
        return block > sky ? block : sky;
    }
}
