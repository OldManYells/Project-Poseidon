package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.WebStateBehaviour;
import net.minecraft.server.Entity;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;
import org.junit.Assert;
import org.junit.Test;

public class WebStateServiceTest {
    @Test
    public void webEntanglementAndDropRulesMatchLegacyBehavior() {
        WebStateBehaviour service = WebStateBehaviour.getInstance();
        DummyEntity entity = new DummyEntity();
        entity.bf = false;

        service.applyEntanglement(entity);
        Assert.assertTrue(entity.bf);
        Assert.assertEquals(287, service.resolveDropItemId(287));
    }

    private static final class DummyEntity extends Entity {
        private DummyEntity() {
            super((World) null);
        }

        protected void b() {
        }

        protected void a(NBTTagCompound nbttagcompound) {
        }

        protected void b(NBTTagCompound nbttagcompound) {
        }
    }
}
