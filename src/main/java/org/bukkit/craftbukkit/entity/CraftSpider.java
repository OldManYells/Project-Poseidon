package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.CraftEntityIdentityBehaviour;
import net.minecraft.server.EntitySpider;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Spider;

public class CraftSpider extends CraftMonster implements Spider {
    private static final CraftEntityIdentityBehaviour CRAFT_ENTITY_IDENTITY_BEHAVIOUR =
            CraftEntityIdentityBehaviour.getInstance();

    public CraftSpider(CraftServer server, EntitySpider entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return CRAFT_ENTITY_IDENTITY_BEHAVIOUR.toString(this);
    }

}
