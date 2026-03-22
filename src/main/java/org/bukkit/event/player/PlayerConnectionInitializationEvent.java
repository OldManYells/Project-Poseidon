package org.bukkit.event.player;

import com.legacyminecraft.poseidon.auth.login.LoginProcessCallbacks;
import com.projectposeidon.johnymuffin.LoginProcessHandler;
import org.bukkit.event.Event;

import java.net.InetAddress;

public class PlayerConnectionInitializationEvent extends Event {
    private String username;
    private InetAddress ipAddress;
    private LoginProcessCallbacks loginProcessHandler;
    private Object legacyLoginProcessHandler;

    public PlayerConnectionInitializationEvent(String username, InetAddress ipAddress, LoginProcessHandler loginProcessHandler) {
        this(username, ipAddress, (LoginProcessCallbacks) loginProcessHandler, loginProcessHandler);
    }

    public PlayerConnectionInitializationEvent(String username, InetAddress ipAddress, LoginProcessCallbacks loginProcessHandler) {
        this(username, ipAddress, loginProcessHandler, null);
    }

    public PlayerConnectionInitializationEvent(String username, InetAddress ipAddress, LoginProcessCallbacks loginProcessHandler, Object legacyLoginProcessHandler) {
        super(Type.Player_Connection_Initialization);
        this.username = username;
        this.ipAddress = ipAddress;
        this.loginProcessHandler = loginProcessHandler;
        this.legacyLoginProcessHandler = legacyLoginProcessHandler;
    }

    public void disconnectPlayer(String kickReason) {
        loginProcessHandler.cancelLoginProcess(kickReason);
    }

    /**
     * Gets the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return username;
    }

    /**
     * Gets the player IP address.
     *
     * @return
     */
    public InetAddress getAddress() {
        return ipAddress;
    }

}
