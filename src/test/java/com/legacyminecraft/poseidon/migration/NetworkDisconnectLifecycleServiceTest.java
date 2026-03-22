package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkDisconnectLifecycleSystem;
import org.junit.Assert;
import org.junit.Test;

public class NetworkDisconnectLifecycleServiceTest {
    @Test
    public void disconnectsOpenConnectionAndTriggersActions() {
        final boolean[] startedMasterThread = new boolean[]{false};
        final boolean[] closedResources = new boolean[]{false};
        Object[] args = new Object[]{"reason"};

        NetworkDisconnectLifecycleSystem.DisconnectState state =
                NetworkDisconnectLifecycleSystem.getInstance().disconnectIfOpen(
                        true,
                        "disconnect.closed",
                        args,
                        new NetworkDisconnectLifecycleSystem.DisconnectActions() {
                            @Override
                            public void startMasterThread() {
                                startedMasterThread[0] = true;
                            }

                            @Override
                            public void closeResources() {
                                closedResources[0] = true;
                            }
                        }
                );

        Assert.assertTrue(state.isChanged());
        Assert.assertFalse(state.isOpen());
        Assert.assertTrue(state.isTerminating());
        Assert.assertEquals("disconnect.closed", state.getDisconnectKey());
        Assert.assertSame(args, state.getDisconnectArgs());
        Assert.assertTrue(startedMasterThread[0]);
        Assert.assertTrue(closedResources[0]);
    }

    @Test
    public void noOpsWhenConnectionAlreadyClosed() {
        final boolean[] ran = new boolean[]{false};

        NetworkDisconnectLifecycleSystem.DisconnectState state =
                NetworkDisconnectLifecycleSystem.getInstance().disconnectIfOpen(
                        false,
                        "disconnect.closed",
                        new Object[0],
                        new NetworkDisconnectLifecycleSystem.DisconnectActions() {
                            @Override
                            public void startMasterThread() {
                                ran[0] = true;
                            }

                            @Override
                            public void closeResources() {
                                ran[0] = true;
                            }
                        }
                );

        Assert.assertFalse(state.isChanged());
        Assert.assertFalse(ran[0]);
    }
}
