package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.CraftEntityIdentityBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import net.minecraft.server.EntityAnimal;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Animals;

public class CraftAnimals extends CraftCreature implements Animals {
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final CraftEntityIdentityBehaviour CRAFT_ENTITY_IDENTITY_BEHAVIOUR =
            CraftEntityIdentityBehaviour.getInstance();

    public CraftAnimals(CraftServer server, EntityAnimal entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return CRAFT_ENTITY_IDENTITY_BEHAVIOUR.toString(this);
    }

    @Override
    public EntityAnimal getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityAnimal.class);
    }
}
