package net.minecraft.server;

import com.legacyminecraft.poseidon.block.BlockRegisterMappingBehaviour;

public class BlockRegister {

    private static byte[] a = new byte[256];
    private static final BlockRegisterMappingBehaviour BLOCK_REGISTER_MAPPING_SERVICE = BlockRegisterMappingBehaviour.getInstance();

    public BlockRegister() {}

    public static void a(byte[] abyte) {
        BLOCK_REGISTER_MAPPING_SERVICE.remapChunkIds(abyte, a);
    }

    static {
        try {
            BLOCK_REGISTER_MAPPING_SERVICE.initializeLookup(a, Block.byId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
