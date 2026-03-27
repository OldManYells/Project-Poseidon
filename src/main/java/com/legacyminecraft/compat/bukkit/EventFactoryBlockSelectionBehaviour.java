package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory clicked-block and clicked-face projection.
 */
public final class EventFactoryBlockSelectionBehaviour {
    private static final EventFactoryBlockSelectionBehaviour INSTANCE = new EventFactoryBlockSelectionBehaviour();

    private static final BlockFaceConversionBehaviour BLOCK_FACE_CONVERSION_BEHAVIOUR =
            BlockFaceConversionBehaviour.getInstance();

    private EventFactoryBlockSelectionBehaviour() {
    }

    public static EventFactoryBlockSelectionBehaviour getInstance() {
        return INSTANCE;
    }

    public Block resolveClickedBlock(CraftWorld craftWorld, int clickedX, int clickedY, int clickedZ) {
        return craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
    }

    public BlockFace resolveClickedFace(int clickedFace) {
        return BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(clickedFace);
    }
}
