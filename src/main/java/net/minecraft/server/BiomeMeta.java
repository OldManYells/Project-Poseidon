package net.minecraft.server;

public class BiomeMeta {
    public Class a;
    public int b;

    public BiomeMeta(Class oclass, int i) {
        this.a = oclass;
        this.b = i;
    }

    public Class getEntityClass() {
        return this.a;
    }

    public int getSpawnWeight() {
        return this.b;
    }

    public void poseidonSetEntityClass(Class entityClass) {
        this.a = entityClass;
    }

    public void poseidonSetSpawnWeight(int spawnWeight) {
        this.b = spawnWeight;
    }

    public Class poseidonGetEntityClass() {
        return this.a;
    }

    public int poseidonGetSpawnWeight() {
        return this.b;
    }
}
