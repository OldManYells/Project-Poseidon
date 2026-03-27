package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.PrimedTntPropertyBehaviour;
import net.minecraft.server.EntityTNTPrimed;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.TNTPrimed;

public class CraftTNTPrimed extends CraftEntity implements TNTPrimed {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final PrimedTntPropertyBehaviour PRIMED_TNT_PROPERTY_BEHAVIOUR =
            PrimedTntPropertyBehaviour.getInstance();

    public CraftTNTPrimed(CraftServer server, EntityTNTPrimed entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftTNTPrimed");
    }

    @Override
    public EntityTNTPrimed getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(super.getHandle(), EntityTNTPrimed.class);
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
