package com.legacyminecraft.poseidon.block;

/**
 * Canonical behavior for piston moving-tile tick and finalization sequencing decisions.
 */
public final class PistonTileTickOrchestrationBehaviour {
    private static final PistonTileTickOrchestrationBehaviour INSTANCE = new PistonTileTickOrchestrationBehaviour();

    private PistonTileTickOrchestrationBehaviour() {
    }

    public static PistonTileTickOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public FinalizationDecision resolveImmediateFinalization(float previousProgress, PistonLifecycleGateBehaviour lifecycleGateBehaviour) {
        if (!lifecycleGateBehaviour.shouldFinalizeImmediately(previousProgress)) {
            return FinalizationDecision.noop();
        }
        return FinalizationDecision.finalizeNow(1.0F);
    }

    public TickDecision resolveTickProgression(
            float previousProgress,
            float currentProgress,
            boolean extending,
            PistonLifecycleGateBehaviour lifecycleGateBehaviour,
            PistonTickProgressionBehaviour tickProgressionBehaviour
    ) {
        float nextPreviousProgress = currentProgress;
        if (lifecycleGateBehaviour.shouldFinalizeOnTick(nextPreviousProgress)) {
            return TickDecision.finalize(nextPreviousProgress, currentProgress, 1.0F, 0.25F);
        }

        float nextCurrentProgress = tickProgressionBehaviour.advanceProgress(currentProgress, 0.5F);
        if (tickProgressionBehaviour.shouldPushEntities(extending)) {
            float pushDelta = tickProgressionBehaviour.computePushDelta(nextCurrentProgress, nextPreviousProgress, 0.0625F);
            return TickDecision.progressWithPush(nextPreviousProgress, nextCurrentProgress, pushDelta);
        }

        return TickDecision.progressOnly(nextPreviousProgress, nextCurrentProgress);
    }

    public static final class FinalizationDecision {
        private final boolean shouldFinalize;
        private final float completionProgress;

        private FinalizationDecision(boolean shouldFinalize, float completionProgress) {
            this.shouldFinalize = shouldFinalize;
            this.completionProgress = completionProgress;
        }

        public static FinalizationDecision noop() {
            return new FinalizationDecision(false, 0.0F);
        }

        public static FinalizationDecision finalizeNow(float completionProgress) {
            return new FinalizationDecision(true, completionProgress);
        }

        public boolean shouldFinalize() {
            return shouldFinalize;
        }

        public float getCompletionProgress() {
            return completionProgress;
        }
    }

    public static final class TickDecision {
        private final float previousProgress;
        private final float currentProgress;
        private final boolean shouldPushEntities;
        private final float pushProgress;
        private final float pushDelta;
        private final boolean shouldFinalize;

        private TickDecision(
                float previousProgress,
                float currentProgress,
                boolean shouldPushEntities,
                float pushProgress,
                float pushDelta,
                boolean shouldFinalize
        ) {
            this.previousProgress = previousProgress;
            this.currentProgress = currentProgress;
            this.shouldPushEntities = shouldPushEntities;
            this.pushProgress = pushProgress;
            this.pushDelta = pushDelta;
            this.shouldFinalize = shouldFinalize;
        }

        public static TickDecision finalize(float previousProgress, float currentProgress, float pushProgress, float pushDelta) {
            return new TickDecision(previousProgress, currentProgress, true, pushProgress, pushDelta, true);
        }

        public static TickDecision progressWithPush(float previousProgress, float currentProgress, float pushDelta) {
            return new TickDecision(previousProgress, currentProgress, true, currentProgress, pushDelta, false);
        }

        public static TickDecision progressOnly(float previousProgress, float currentProgress) {
            return new TickDecision(previousProgress, currentProgress, false, currentProgress, 0.0F, false);
        }

        public float getPreviousProgress() {
            return previousProgress;
        }

        public float getCurrentProgress() {
            return currentProgress;
        }

        public boolean shouldPushEntities() {
            return shouldPushEntities;
        }

        public float getPushProgress() {
            return pushProgress;
        }

        public float getPushDelta() {
            return pushDelta;
        }

        public boolean shouldFinalize() {
            return shouldFinalize;
        }
    }
}

