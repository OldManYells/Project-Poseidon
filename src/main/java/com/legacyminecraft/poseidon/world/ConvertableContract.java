package com.legacyminecraft.poseidon.world;


public interface ConvertableContract {
    boolean canConvertWorld(String worldName);

    boolean convertWorld(String worldName, IProgressUpdate progressUpdate);
}
