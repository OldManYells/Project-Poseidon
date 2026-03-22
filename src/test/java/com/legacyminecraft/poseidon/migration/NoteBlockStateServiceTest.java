package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.NoteBlockStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class NoteBlockStateServiceTest {
    @Test
    public void powerChangeAndInteractionRulesMatchLegacyNoteBehavior() {
        NoteBlockStateBehaviour service = NoteBlockStateBehaviour.getInstance();

        Assert.assertTrue(service.shouldHandleNeighborPowerUpdate(1, true));
        Assert.assertFalse(service.shouldHandleNeighborPowerUpdate(0, true));
        Assert.assertFalse(service.shouldHandleNeighborPowerUpdate(1, false));
        Assert.assertTrue(service.hasPowerStateChanged(false, true));
        Assert.assertFalse(service.hasPowerStateChanged(true, true));
        Assert.assertTrue(service.shouldPlayOnPowerChange(true));
        Assert.assertFalse(service.shouldPlayOnPowerChange(false));
        Assert.assertTrue(service.shouldIgnoreClientInteraction(true));
        Assert.assertFalse(service.shouldIgnoreClientInteraction(false));
    }

    @Test
    public void pitchInstrumentAndParticleRulesMatchLegacyNoteBehavior() {
        NoteBlockStateBehaviour service = NoteBlockStateBehaviour.getInstance();

        Assert.assertEquals(1.0F, service.resolvePitchFromNoteValue(12), 0.0F);
        Assert.assertEquals("harp", service.resolveInstrumentName(0));
        Assert.assertEquals("bd", service.resolveInstrumentName(1));
        Assert.assertEquals("snare", service.resolveInstrumentName(2));
        Assert.assertEquals("hat", service.resolveInstrumentName(3));
        Assert.assertEquals("bassattack", service.resolveInstrumentName(4));
        Assert.assertEquals("note.harp", service.resolveSoundEffectName("harp"));
        Assert.assertEquals(10.5D, service.resolveCenteredCoordinate(10), 0.0D);
        Assert.assertEquals(65.2D, service.resolveNoteParticleY(64), 0.0D);
        Assert.assertEquals(0.5D, service.resolveNoteParticleData(12), 0.0D);
    }
}
