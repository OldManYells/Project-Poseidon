package com.legacyminecraft.poseidon.world.entity;

import com.legacyminecraft.poseidon.api.IAnimal;
import com.legacyminecraft.poseidon.world.core.World;
import com.legacyminecraft.poseidon.world.storage.nbt.NBTTagCompound;

public class EntityWaterAnimal extends EntityCreature implements IAnimal {

    public EntityWaterAnimal(World world) {
        super(world);
    }

    public boolean b_() {
        return true;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    public boolean d() {
        return this.world.containsEntity(this.boundingBox);
    }

    public int e() {
        return 120;
    }
}
