package com.legacyminecraft.poseidon.world.types;

/**
 * Canonical contract for painting art metadata.
 */
public interface PaintingArtTypeContract {
    String getArtKey();

    int getPixelWidth();

    int getPixelHeight();

    int getTextureU();

    int getTextureV();
}
