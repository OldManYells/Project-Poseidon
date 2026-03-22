package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.WorldTickOrchestrator;
import net.minecraft.server.IUpdatePlayerListBox;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WorldTickOrchestratorTest {
    @Test
    public void ticksAllUpdateBoxes() {
        final int[] counter = new int[]{0};
        List updateBoxes = new ArrayList();

        updateBoxes.add(new IUpdatePlayerListBox() {
            @Override
            public void a() {
                counter[0]++;
            }
        });
        updateBoxes.add(new IUpdatePlayerListBox() {
            @Override
            public void a() {
                counter[0]++;
            }
        });

        WorldTickOrchestrator.getInstance().tickUpdateBoxes(updateBoxes);
        Assert.assertEquals(2, counter[0]);
    }
}
