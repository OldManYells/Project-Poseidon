package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntitySpawnPacketFactory;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;

public class EntitySpawnPacketFactoryTest {
    @Test
    public void helperMappingsMatchLegacySpawnIds() {
        EntitySpawnPacketFactory factory = EntitySpawnPacketFactory.getInstance();

        Assert.assertEquals("abcdefghijklmnop", factory.sanitizePlayerNameForSpawnPacket("abcdefghijklmnopq"));
        Assert.assertEquals(10, factory.resolveMinecartSpawnType(0));
        Assert.assertEquals(11, factory.resolveMinecartSpawnType(1));
        Assert.assertEquals(12, factory.resolveMinecartSpawnType(2));
        Assert.assertEquals(-1, factory.resolveMinecartSpawnType(9));
        Assert.assertEquals(70, factory.resolveFallingSandSpawnType(Block.SAND.id));
        Assert.assertEquals(71, factory.resolveFallingSandSpawnType(Block.GRAVEL.id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownEntityTypeFailsFast() {
        EntitySpawnPacketFactory factory = EntitySpawnPacketFactory.getInstance();
        factory.createSpawnPacket(new UnknownEntity());
    }

    private static final class UnknownEntity extends Entity {
        private UnknownEntity() {
            super(null);
        }

        @Override
        protected void b() {
        }

        @Override
        protected void a(NBTTagCompound nbttagcompound) {
        }

        @Override
        protected void b(NBTTagCompound nbttagcompound) {
        }
    }
}
