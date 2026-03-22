package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerTickLoopPlanner;
import org.junit.Assert;
import org.junit.Test;

public class ServerTickLoopPlannerTest {
    @Test
    public void lagAccumulatorAddsElapsedMillis() {
        ServerTickLoopPlanner planner = ServerTickLoopPlanner.getInstance();

        Assert.assertEquals(75L, planner.accumulateLag(50L, 25L));
    }

    @Test
    public void sleepingWorldAlwaysRunsSingleTickAndResetsLag() {
        ServerTickLoopPlanner planner = ServerTickLoopPlanner.getInstance();

        ServerTickLoopPlanner.TickExecutionPlan plan = planner.createTickExecutionPlan(500L, true);
        Assert.assertEquals(1, plan.getTicksToExecute());
        Assert.assertEquals(0L, plan.getNextLagAccumulatorMillis());
        Assert.assertFalse(plan.shouldUpdateWatchdogPerTick());
    }

    @Test
    public void nonSleepingPlanPreservesLegacyStrictGreaterThanFiftyRule() {
        ServerTickLoopPlanner planner = ServerTickLoopPlanner.getInstance();

        ServerTickLoopPlanner.TickExecutionPlan noTickAtFifty = planner.createTickExecutionPlan(50L, false);
        Assert.assertEquals(0, noTickAtFifty.getTicksToExecute());
        Assert.assertEquals(50L, noTickAtFifty.getNextLagAccumulatorMillis());
        Assert.assertTrue(noTickAtFifty.shouldUpdateWatchdogPerTick());

        ServerTickLoopPlanner.TickExecutionPlan oneTickAtFiftyOne = planner.createTickExecutionPlan(51L, false);
        Assert.assertEquals(1, oneTickAtFiftyOne.getTicksToExecute());
        Assert.assertEquals(1L, oneTickAtFiftyOne.getNextLagAccumulatorMillis());

        ServerTickLoopPlanner.TickExecutionPlan oneTickAtHundred = planner.createTickExecutionPlan(100L, false);
        Assert.assertEquals(1, oneTickAtHundred.getTicksToExecute());
        Assert.assertEquals(50L, oneTickAtHundred.getNextLagAccumulatorMillis());

        ServerTickLoopPlanner.TickExecutionPlan twoTicksAtHundredOne = planner.createTickExecutionPlan(101L, false);
        Assert.assertEquals(2, twoTicksAtHundredOne.getTicksToExecute());
        Assert.assertEquals(1L, twoTicksAtHundredOne.getNextLagAccumulatorMillis());
    }

    @Test
    public void currentTickUsesFiftyMillisecondBuckets() {
        ServerTickLoopPlanner planner = ServerTickLoopPlanner.getInstance();

        Assert.assertEquals(20, planner.computeCurrentTick(1000L));
        Assert.assertEquals(21, planner.computeCurrentTick(1050L));
    }
}
