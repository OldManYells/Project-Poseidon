package com.legacyminecraft.poseidon.util;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public interface SessionRequestRunnable
{
    public void callback(int responseCode, String username, String uuid, String ip);
}
