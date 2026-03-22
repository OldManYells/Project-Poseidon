package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.EntityWaterAnimalBehaviour;

public class EntityWaterAnimal extends EntityCreature implements IAnimal {
    private static final EntityWaterAnimalBehaviour ENTITY_WATER_ANIMAL_BEHAVIOUR = EntityWaterAnimalBehaviour.getInstance();

    public EntityWaterAnimal(World world) {
        super(world);
    }

    public boolean b_() {
        return ENTITY_WATER_ANIMAL_BEHAVIOUR.canBreatheUnderwater();
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    public boolean d() {
        return ENTITY_WATER_ANIMAL_BEHAVIOUR.canSpawn(this.world, this.boundingBox);
    }

    public int e() {
        return ENTITY_WATER_ANIMAL_BEHAVIOUR.getAmbientSoundInterval();
    }
}
