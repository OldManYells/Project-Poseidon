package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.PrimedTntPropertyBehaviour;
import net.minecraft.server.EntityTNTPrimed;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.TNTPrimed;

public class CraftTNTPrimed extends CraftEntity implements TNTPrimed {
    private static final PrimedTntPropertyBehaviour PRIMED_TNT_PROPERTY_BEHAVIOUR =
            PrimedTntPropertyBehaviour.getInstance();

    public CraftTNTPrimed(CraftServer server, EntityTNTPrimed entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftTNTPrimed";
    }

    @Override
    public EntityTNTPrimed getHandle() {
        return (EntityTNTPrimed) super.getHandle();
    }

    public float getYield() {
        return PRIMED_TNT_PROPERTY_BEHAVIOUR.getYield(getHandle());
    }

    public boolean isIncendiary() {
        return PRIMED_TNT_PROPERTY_BEHAVIOUR.isIncendiary(getHandle());
    }

    public void setIsIncendiary(boolean isIncendiary) {
        PRIMED_TNT_PROPERTY_BEHAVIOUR.setIncendiary(getHandle(), isIncendiary);
    }

    public void setYield(float yield) {
        PRIMED_TNT_PROPERTY_BEHAVIOUR.setYield(getHandle(), yield);
    }

    public int getFuseTicks() {
        return PRIMED_TNT_PROPERTY_BEHAVIOUR.getFuseTicks(getHandle());
    }

    public void setFuseTicks(int fuseTicks) {
        PRIMED_TNT_PROPERTY_BEHAVIOUR.setFuseTicks(getHandle(), fuseTicks);
    }

}
