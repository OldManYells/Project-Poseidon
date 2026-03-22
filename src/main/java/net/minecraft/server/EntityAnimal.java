package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.EntityAnimalBehaviour;

public abstract class EntityAnimal extends EntityCreature implements IAnimal {
    private static final EntityAnimalBehaviour ENTITY_ANIMAL_BEHAVIOUR = EntityAnimalBehaviour.getInstance();

    public EntityAnimal(World world) {
        super(world);
    }

    protected float a(int i, int j, int k) {
        return ENTITY_ANIMAL_BEHAVIOUR.resolvePathWeight(this.world, i, j, k, this.world.n(i, j, k));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    public boolean d() {
        return ENTITY_ANIMAL_BEHAVIOUR.canSpawn(this.world, this.locX, this.boundingBox.b, this.locZ, super.d());
    }

    public int e() {
        return ENTITY_ANIMAL_BEHAVIOUR.getAmbientSoundInterval();
    }
}
