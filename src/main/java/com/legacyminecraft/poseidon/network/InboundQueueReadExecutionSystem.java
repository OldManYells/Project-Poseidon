package com.legacyminecraft.poseidon.network;

import net.minecraft.server.NetHandler;

import java.io.DataInputStream;
import java.util.List;

/**
 * Canonical orchestration for one inbound queue read step, including EOF/exception handling.
 */
public final class InboundQueueReadExecutionSystem {
    private static final InboundQueueReadExecutionSystem INSTANCE = new InboundQueueReadExecutionSystem();

    private InboundQueueReadExecutionSystem() {
    }

    public static InboundQueueReadExecutionSystem getInstance() {
        return INSTANCE;
    }

    public ReadStepResult execute(
            DataInputStream input,
            NetHandler netHandler,
            int[] packetCounters,
            List inboundQueue,
            InboundQueueReadSystem inboundQueueReadSystem,
            InboundReadActions inboundReadActions
    ) {
        try {
            InboundQueueReadSystem.ReadDecision readDecision =
                    inboundQueueReadSystem.readNext(input, netHandler, packetCounters, inboundQueue);
            if (readDecision.isEndOfStream()) {
                inboundReadActions.disconnectEndOfStream();
            }
            return ReadStepResult.packetQueued(readDecision.isPacketQueued());
        } catch (Exception exception) {
            inboundReadActions.handleException(exception);
            return ReadStepResult.packetQueued(false);
        }
    }

    public interface InboundReadActions {
        void disconnectEndOfStream();

        void handleException(Exception exception);
    }

    public static final class ReadStepResult {
        private final boolean packetQueued;

        private ReadStepResult(boolean packetQueued) {
            this.packetQueued = packetQueued;
        }

        public static ReadStepResult packetQueued(boolean packetQueued) {
            return new ReadStepResult(packetQueued);
        }

        public boolean isPacketQueued() {
            return this.packetQueued;
        }
    }
}
