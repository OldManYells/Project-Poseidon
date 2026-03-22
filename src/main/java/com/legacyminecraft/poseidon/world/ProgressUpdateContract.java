package com.legacyminecraft.poseidon.world;

/**
 * Canonical progress callback contract bridged by legacy wrappers.
 */
public interface ProgressUpdateContract {
    void setPrimaryMessage(String message);

    void setSecondaryMessage(String message);

    void setProgress(int progressPercent);
}
