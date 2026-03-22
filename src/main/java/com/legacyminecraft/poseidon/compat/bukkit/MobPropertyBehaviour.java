package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySlime;
import org.bukkit.DyeColor;

/**
 * Canonical behavior for CraftBukkit mob wrapper property access/mutation.
 */
public final class MobPropertyBehaviour {
    private static final MobPropertyBehaviour INSTANCE = new MobPropertyBehaviour();

    private MobPropertyBehaviour() {
    }

    public static MobPropertyBehaviour getInstance() {
        return INSTANCE;
    }

    public int getSlimeSize(EntitySlime slime) {
        return slime.getSize();
    }

    public void setSlimeSize(EntitySlime slime, int size) {
        slime.setSize(size);
    }

    public int getPigZombieAnger(EntityPigZombie pigZombie) {
        return pigZombie.angerLevel;
    }

    public void setPigZombieAnger(EntityPigZombie pigZombie, int angerLevel) {
        pigZombie.angerLevel = angerLevel;
    }

    public void setPigZombieAngry(EntityPigZombie pigZombie, boolean angry) {
        setPigZombieAnger(pigZombie, angry ? 400 : 0);
    }

    public boolean isPigZombieAngry(EntityPigZombie pigZombie) {
        return getPigZombieAnger(pigZombie) > 0;
    }

    public DyeColor getSheepColor(EntitySheep sheep) {
        return DyeColor.getByData((byte) sheep.getColor());
    }

    public void setSheepColor(EntitySheep sheep, DyeColor color) {
        sheep.setColor(color.getData());
    }

    public boolean isSheepSheared(EntitySheep sheep) {
        return sheep.isSheared();
    }

    public void setSheepSheared(EntitySheep sheep, boolean sheared) {
        sheep.setSheared(sheared);
    }

    public boolean hasPigSaddle(EntityPig pig) {
        return pig.hasSaddle();
    }

    public void setPigSaddle(EntityPig pig, boolean saddled) {
        pig.setSaddle(saddled);
    }
}
