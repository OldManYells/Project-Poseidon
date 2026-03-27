package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Canonical behaviour for CraftLivingEntity line-of-sight target block collection policy.
 */
public final class LivingEntityTargetingBehaviour {
    private static final int MAX_TRACE_DISTANCE = 120;
    private static final LivingEntityTargetingBehaviour INSTANCE = new LivingEntityTargetingBehaviour();

    private LivingEntityTargetingBehaviour() {
    }

    public static LivingEntityTargetingBehaviour getInstance() {
        return INSTANCE;
    }

    public List collectLineOfSight(
            Object viewer,
            HashSet<Byte> transparentBlockIds,
            int requestedDistance,
            int maxLength
    ) {
        int maxDistance = clampMaxDistance(requestedDistance);
        ArrayList blocks = new ArrayList();
        Iterator blockIterator = createBlockIterator(viewer, maxDistance);
        while (blockIterator.hasNext()) {
            Object block = blockIterator.next();
            blocks.add(block);

            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }

            int blockTypeId = ((Number) BridgeReflection.invoke(block, "getTypeId")).intValue();
            if (transparentBlockIds == null) {
                if (blockTypeId != 0) {
                    break;
                }
                continue;
            }

            if (!transparentBlockIds.contains((byte) blockTypeId)) {
                break;
            }
        }

        return blocks;
    }

    public <T> T resolveTargetBlock(Object viewer, HashSet<Byte> transparentBlockIds, int requestedDistance) {
        List blocks = collectLineOfSight(viewer, transparentBlockIds, requestedDistance, 1);
        return BridgeReflection.cast(blocks.get(0));
    }

    public List collectLastTargetBlocks(
            Object viewer,
            HashSet<Byte> transparentBlockIds,
            int requestedDistance,
            int maxLength
    ) {
        return collectLineOfSight(viewer, transparentBlockIds, requestedDistance, maxLength);
    }

    private int clampMaxDistance(int requestedDistance) {
        return requestedDistance > MAX_TRACE_DISTANCE ? MAX_TRACE_DISTANCE : requestedDistance;
    }

    private Iterator createBlockIterator(Object viewer, int maxDistance) {
        try {
            Class<?> iteratorClass = Class.forName("org.bukkit.util.BlockIterator");
            return BridgeReflection.cast(iteratorClass.getConstructor(Class.forName("org.bukkit.entity.LivingEntity"), int.class)
                    .newInstance(viewer, maxDistance));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to create BlockIterator bridge", exception);
        }
    }
}
