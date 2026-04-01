package com.legacyminecraft.poseidon.world.inventory;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.item.*;
import com.legacyminecraft.poseidon.world.block.*;
import com.legacyminecraft.poseidon.world.item.*;

import java.util.List;

public interface ICrafting {

    void a(Container container, List list);

    void a(Container container, int i, ItemStack itemstack);

    void a(Container container, int i, int j);
}
