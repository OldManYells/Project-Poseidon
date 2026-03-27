package com.legacyminecraft.poseidon.world;

/**
 * World conversion facade used by bootstrap flow.
 */
public interface Convertable {
    boolean isConvertable(String worldName);

    void convert(String worldName, IProgressUpdate progressUpdate);
}
