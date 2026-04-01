package com.legacyminecraft.poseidon.util.exception;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public class MinecraftException extends RuntimeException {

    public MinecraftException(String s) {
        super(s);
    }
}
