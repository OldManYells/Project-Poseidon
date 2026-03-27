package com.legacyminecraft.poseidon;

import com.legacyminecraft.compat.bukkit.ServerDiagnosticsBridgeBehaviour;
import com.legacyminecraft.poseidon.kernel.PoseidonKernel;

import java.util.LinkedList;

public final class Poseidon {
    private static PoseidonServer server;
    private static final PoseidonKernel kernel = PoseidonKernel.getInstance();
    private static final ServerDiagnosticsBridgeBehaviour serverDiagnosticsBridge = ServerDiagnosticsBridgeBehaviour.getInstance();

    /**
     * Returns a list of the server's TPS (Ticks Per Second) records for performance monitoring.
     * The list contains Double values indicating the TPS at each second, ordered from most recent to oldest.
     *
     * @return LinkedList<Double> of TPS records.
     */
    public static LinkedList<Double> getTpsRecords() {
        Server bukkitServer = Bukkit.getServer();
        return serverDiagnosticsBridge.readTpsRecords(bukkitServer);
    }

    public static PoseidonServer getServer() {
        return server;
    }

    public static PoseidonKernel getKernel() {
        return kernel;
    }

    public static void setServer(PoseidonServer server) {
        if (Poseidon.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Server");
        }

        Poseidon.server = server;
        kernel.registerService(PoseidonServer.class, server);
        kernel.markBootstrapped();
    }


}
