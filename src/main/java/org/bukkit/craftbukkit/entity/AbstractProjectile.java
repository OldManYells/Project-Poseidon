package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.ProjectileBounceBehaviour;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Projectile;

public abstract class AbstractProjectile extends CraftEntity implements Projectile {
    private static final ProjectileBounceBehaviour PROJECTILE_BOUNCE_BEHAVIOUR =
            ProjectileBounceBehaviour.getInstance();

    public AbstractProjectile(CraftServer server, net.minecraft.server.Entity entity) {
        super(server, entity);
    }

    public boolean doesBounce() {
        return PROJECTILE_BOUNCE_BEHAVIOUR.doesBounce(this);
    }

    public void setBounce(boolean doesBounce) {
        PROJECTILE_BOUNCE_BEHAVIOUR.setBounce(this, doesBounce);
    }

}
