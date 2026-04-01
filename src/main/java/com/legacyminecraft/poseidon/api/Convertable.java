package com.legacyminecraft.poseidon.api;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public interface Convertable {

    boolean isConvertable(String s);

    boolean convert(String s, IProgressUpdate iprogressupdate);
}
