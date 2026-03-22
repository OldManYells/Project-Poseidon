package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;

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

    public List<Block> collectLineOfSight(
            LivingEntity viewer,
            HashSet<Byte> transparentBlockIds,
            int requestedDistance,
            int maxLength
    ) {
        int maxDistance = clampMaxDistance(requestedDistance);
        ArrayList<Block> blocks = new ArrayList<Block>();
        Iterator<Block> blockIterator = new BlockIterator(viewer, maxDistance);
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            blocks.add(block);

            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }

            int blockTypeId = block.getTypeId();
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

    private int clampMaxDistance(int requestedDistance) {
        return requestedDistance > MAX_TRACE_DISTANCE ? MAX_TRACE_DISTANCE : requestedDistance;
    }
}
