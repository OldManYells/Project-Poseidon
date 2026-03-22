package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.WolfStateBehaviour;
import net.minecraft.server.EntityWolf;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Wolf;

public class CraftWolf extends CraftAnimals implements Wolf {
    private static final WolfStateBehaviour WOLF_STATE_BEHAVIOUR = WolfStateBehaviour.getInstance();

    private AnimalTamer owner;

    public CraftWolf(CraftServer server, EntityWolf wolf) {
        super(server, wolf);
    }

    public boolean isAngry() {
        return WOLF_STATE_BEHAVIOUR.isAngry(getHandle());
    }

    public void setAngry(boolean angry) {
        WOLF_STATE_BEHAVIOUR.setAngry(getHandle(), angry);
    }

    public boolean isSitting() {
        return WOLF_STATE_BEHAVIOUR.isSitting(getHandle());
    }

    public void setSitting(boolean sitting) {
        WOLF_STATE_BEHAVIOUR.setSitting(getHandle(), sitting);
    }

    public boolean isTamed() {
        return WOLF_STATE_BEHAVIOUR.isTamed(getHandle());
    }

    public void setTamed(boolean tame) {
        WOLF_STATE_BEHAVIOUR.setTamed(getHandle(), tame);
    }

    public AnimalTamer getOwner() {
        owner = WOLF_STATE_BEHAVIOUR.resolveOwner(owner, getServer(), getOwnerName());
        return owner;
    }

    public void setOwner(AnimalTamer tamer) {
        owner = WOLF_STATE_BEHAVIOUR.applyOwner(getHandle(), tamer);
    }

    /**
     * The owner's name is how MC knows and persists the Wolf's owner. Since we choose to instead use an AnimalTamer, this functionality
     * is used only as a backup. If the animal tamer is a player, we will store their name, otherwise we store an empty string.
     * @return the owner's name, if they are a player; otherwise, the empty string or null.
     */
    String getOwnerName() {
        return WOLF_STATE_BEHAVIOUR.getOwnerName(getHandle());
    }

    void setOwnerName(String ownerName) {
        WOLF_STATE_BEHAVIOUR.setOwnerName(getHandle(), ownerName);
    }

    /*
     * This method requires a(boolean) to be made visible. It will allow for hearts to be animated on a successful taming.
     * TODO add this to the API, and make it visible
    private void playTamingAnimation(boolean successful){
        getHandle().a(successful);
    }
    */

    @Override
    public EntityWolf getHandle() {
        // It's somewhat easier to override this here, as many internal methods rely on EntityWolf specific methods.
        // Doing this has no impact on anything outside this class.
        return (EntityWolf) entity;
    }

    @Override
    public String toString() {
        return "CraftWolf[anger=" + isAngry() + ",owner=" + getOwner() + ",tame=" + isTamed() + ",sitting=" + isSitting() + "]";
    }
}
