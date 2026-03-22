package com.legacyminecraft.poseidon.world;

import net.minecraft.server.MetadataChunkBlock;

/**
 * Canonical behaviour for metadata light-update bounds merge decisions.
 */
public final class MetadataLightUpdateBoundsBehaviour {
    private static final MetadataLightUpdateBoundsBehaviour INSTANCE = new MetadataLightUpdateBoundsBehaviour();

    private MetadataLightUpdateBoundsBehaviour() {
    }

    public static MetadataLightUpdateBoundsBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean tryMergeBounds(MetadataChunkBlock metadataChunkBlock, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        if (minX >= metadataChunkBlock.b
                && minY >= metadataChunkBlock.c
                && minZ >= metadataChunkBlock.d
                && maxX <= metadataChunkBlock.e
                && maxY <= metadataChunkBlock.f
                && maxZ <= metadataChunkBlock.g) {
            return true;
        }

        byte mergeMargin = 1;
        if (minX < metadataChunkBlock.b - mergeMargin
                || minY < metadataChunkBlock.c - mergeMargin
                || minZ < metadataChunkBlock.d - mergeMargin
                || maxX > metadataChunkBlock.e + mergeMargin
                || maxY > metadataChunkBlock.f + mergeMargin
                || maxZ > metadataChunkBlock.g + mergeMargin) {
            return false;
        }

        int currentWidth = metadataChunkBlock.e - metadataChunkBlock.b;
        int currentHeight = metadataChunkBlock.f - metadataChunkBlock.c;
        int currentDepth = metadataChunkBlock.g - metadataChunkBlock.d;

        if (minX > metadataChunkBlock.b) {
            minX = metadataChunkBlock.b;
        }

        if (minY > metadataChunkBlock.c) {
            minY = metadataChunkBlock.c;
        }

        if (minZ > metadataChunkBlock.d) {
            minZ = metadataChunkBlock.d;
        }

        if (maxX < metadataChunkBlock.e) {
            maxX = metadataChunkBlock.e;
        }

        if (maxY < metadataChunkBlock.f) {
            maxY = metadataChunkBlock.f;
        }

        if (maxZ < metadataChunkBlock.g) {
            maxZ = metadataChunkBlock.g;
        }

        int mergedWidth = maxX - minX;
        int mergedHeight = maxY - minY;
        int mergedDepth = maxZ - minZ;
        int currentVolume = currentWidth * currentHeight * currentDepth;
        int mergedVolume = mergedWidth * mergedHeight * mergedDepth;

        if (mergedVolume - currentVolume > 2) {
            return false;
        }

        metadataChunkBlock.b = minX;
        metadataChunkBlock.c = minY;
        metadataChunkBlock.d = minZ;
        metadataChunkBlock.e = maxX;
        metadataChunkBlock.f = maxY;
        metadataChunkBlock.g = maxZ;
        return true;
    }
}
