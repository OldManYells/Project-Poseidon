package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ConvertableContract;

public interface Convertable extends ConvertableContract {

    boolean isConvertable(String s);

    boolean convert(String s, IProgressUpdate iprogressupdate);

    default boolean canConvertWorld(String worldName) {
        return this.isConvertable(worldName);
    }

    default boolean convertWorld(String worldName, IProgressUpdate progressUpdate) {
        return this.convert(worldName, progressUpdate);
    }
}
