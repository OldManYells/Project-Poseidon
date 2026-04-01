package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public interface ICommandListener {

    void sendMessage(String s);

    String getName();
}
