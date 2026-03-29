package org.bukkit.craftbukkit.entity;

import net.minecraft.server.IAnimal;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

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
