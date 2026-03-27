package com.legacyminecraft.poseidon.block;


/**
 * Canonical block-id remapping and bootstrap lookup policy for legacy BlockRegister wrappers.
 */
public final class BlockRegisterMappingBehaviour {
    private static final BlockRegisterMappingBehaviour INSTANCE = new BlockRegisterMappingBehaviour();

    private BlockRegisterMappingBehaviour() {
    }

    public static BlockRegisterMappingBehaviour getInstance() {
        return INSTANCE;
    }

    public void remapChunkIds(byte[] chunkData, byte[] lookup) {
        for (int i = 0; i < chunkData.length; ++i) {
            chunkData[i] = lookup[chunkData[i] & 255];
        }
    }

    public byte sanitizeBlockId(byte id, Block[] byId) {
        if (id != 0 && byId[id & 255] == null) {
            return 0;
        }
        return id;
    }

    public void initializeLookup(byte[] lookup, Block[] byId) {
        for (int i = 0; i < lookup.length; ++i) {
            lookup[i] = sanitizeBlockId((byte) i, byId);
        }
    }
}
