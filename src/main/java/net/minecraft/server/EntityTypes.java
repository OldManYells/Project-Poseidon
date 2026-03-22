package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.EntityTypeRegistryBehaviour;

import java.util.HashMap;
import java.util.Map;

public class EntityTypes {
    private static final EntityTypeRegistryBehaviour ENTITY_TYPE_REGISTRY_BEHAVIOUR = EntityTypeRegistryBehaviour.getInstance();

    private static Map a = new HashMap();
    private static Map b = new HashMap();
    private static Map c = new HashMap();
    private static Map d = new HashMap();

    public EntityTypes() {}

    private static void a(Class oclass, String s, int i) {
        ENTITY_TYPE_REGISTRY_BEHAVIOUR.register(a, b, c, d, oclass, s, i);
    }

    public static Entity a(String s, World world) {
        return ENTITY_TYPE_REGISTRY_BEHAVIOUR.createByName(a, s, world);
    }

    public static Entity a(NBTTagCompound nbttagcompound, World world) {
        return ENTITY_TYPE_REGISTRY_BEHAVIOUR.createFromNbt(a, nbttagcompound, world);
    }

    public static int a(Entity entity) {
        return ENTITY_TYPE_REGISTRY_BEHAVIOUR.idFor(d, entity);
    }

    public static String b(Entity entity) {
        return ENTITY_TYPE_REGISTRY_BEHAVIOUR.nameFor(b, entity);
    }

    static {
        ENTITY_TYPE_REGISTRY_BEHAVIOUR.bootstrapDefaultRegistrations(a, b, c, d);
    }
}
