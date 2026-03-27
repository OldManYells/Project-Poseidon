package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.CraftEntityIdentityBehaviour;
import net.minecraft.server.EntityCow;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Cow;

public class CraftCow extends CraftAnimals implements Cow {
    private static final CraftEntityIdentityBehaviour CRAFT_ENTITY_IDENTITY_BEHAVIOUR =
            CraftEntityIdentityBehaviour.getInstance();

    public CraftCow(CraftServer server, EntityCow entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return CRAFT_ENTITY_IDENTITY_BEHAVIOUR.toString(this);
    }
}
