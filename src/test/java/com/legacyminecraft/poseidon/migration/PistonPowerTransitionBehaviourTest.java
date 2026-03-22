package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.PistonPowerTransitionBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class PistonPowerTransitionBehaviourTest {
    private final PistonPowerTransitionBehaviour behaviour = PistonPowerTransitionBehaviour.getInstance();

    @Test
    public void resolveTransitionMatchesLegacyPowerStateCases() {
        Assert.assertEquals(PistonPowerTransitionBehaviour.Transition.NONE, behaviour.resolveTransition(7, true, false));
        Assert.assertEquals(PistonPowerTransitionBehaviour.Transition.EXTEND, behaviour.resolveTransition(0, true, false));
        Assert.assertEquals(PistonPowerTransitionBehaviour.Transition.RETRACT, behaviour.resolveTransition(8, false, true));
        Assert.assertEquals(PistonPowerTransitionBehaviour.Transition.NONE, behaviour.resolveTransition(8, true, true));
    }
}
