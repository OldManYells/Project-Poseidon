package com.projectposeidon.johnymuffin;

import com.legacyminecraft.poseidon.auth.login.LoginPauseController;

/**
 * @deprecated Use canonical authentication classes under com.legacyminecraft.poseidon.auth.
 */
@Deprecated
public class ConnectionPause {
    private final com.legacyminecraft.poseidon.auth.login.ConnectionPause delegate;
    private final LoginPauseController pauseController;
    private final LoginProcessHandler loginProcessHandler;

    public ConnectionPause(String pluginName, String connectionPauseName, LoginProcessHandler loginProcessHandler) {
        this(new com.legacyminecraft.poseidon.auth.login.ConnectionPause(pluginName, connectionPauseName), loginProcessHandler, loginProcessHandler);
    }

    private ConnectionPause(com.legacyminecraft.poseidon.auth.login.ConnectionPause delegate, LoginPauseController pauseController, LoginProcessHandler loginProcessHandler) {
        this.delegate = delegate;
        this.pauseController = pauseController;
        this.loginProcessHandler = loginProcessHandler;
    }

    public static ConnectionPause fromCanonical(com.legacyminecraft.poseidon.auth.login.ConnectionPause delegate, LoginPauseController pauseController, LoginProcessHandler loginProcessHandler) {
        return new ConnectionPause(delegate, pauseController, loginProcessHandler);
    }

    /**
     * This method is still undecided, please don't use it in production.
     */
    public void removeConnectionPause() {
        if (pauseController != null) {
            pauseController.clearConnectionPause(delegate);
        }
    }

    public String getPluginName() {
        return delegate.getPluginName();
    }

    public String getConnectionPauseName() {
        return delegate.getConnectionPauseName();
    }

    public long getCreationTime() {
        return delegate.getCreationTime();
    }

    public boolean isActive() {
        return delegate.isActive();
    }

    /**
     * This method is for Poseidon, not plugin use. DON'T TOUCH THIS IF YOU DON'T KNOW WHAT YOU ARE DOING.
     */
    public void setActive(boolean active) {
        delegate.setActive(active);
    }

    public int getRunningTime() {
        return delegate.getRunningTime();
    }

    public LoginProcessHandler getLoginProcessHandler() {
        return loginProcessHandler;
    }

    public long getCompletionTime() {
        return delegate.getCompletionTime();
    }

    public com.legacyminecraft.poseidon.auth.login.ConnectionPause getCanonicalConnectionPause() {
        return delegate;
    }
}
