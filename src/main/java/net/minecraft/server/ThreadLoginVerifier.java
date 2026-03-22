package net.minecraft.server;

import com.legacyminecraft.poseidon.auth.login.LoginProcessCallbacks;
import com.legacyminecraft.poseidon.auth.login.LoginVerificationThreadSystem;

// CraftBukkit start
// CraftBukkit end

public class ThreadLoginVerifier extends Thread {

    final Packet1Login loginPacket;

    final NetLoginHandler netLoginHandler;

    final LoginProcessCallbacks loginProcessHandler;  //Project Poseidon
    private final LoginVerificationThreadSystem loginVerificationThreadSystem = LoginVerificationThreadSystem.getInstance();

    public ThreadLoginVerifier(LoginProcessCallbacks loginProcessHandler, NetLoginHandler netloginhandler, Packet1Login packet1login) {
        this.loginProcessHandler = loginProcessHandler;  //Project Poseidon

        this.netLoginHandler = netloginhandler;
        this.loginPacket = packet1login;
    }

    public void run() {
        loginVerificationThreadSystem.verify(this.loginPacket, this.netLoginHandler, this.loginProcessHandler);
    }
}
