package org.bukkit.craftbukkit.network;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet27 extends Packet {

    private float strafeInput;
    private float forwardInput;
    private boolean jumping;
    private boolean sneaking;
    private float pitch;
    private float yaw;

    public Packet27() {}

    public Packet27(float strafeInput, float forwardInput, boolean jumping, boolean sneaking, float pitch, float yaw) {
        this.strafeInput = strafeInput;
        this.forwardInput = forwardInput;
        this.jumping = jumping;
        this.sneaking = sneaking;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public void read(DataInputStream input) throws IOException {
        this.strafeInput = input.readFloat();
        this.forwardInput = input.readFloat();
        this.pitch = input.readFloat();
        this.yaw = input.readFloat();
        this.jumping = input.readBoolean();
        this.sneaking = input.readBoolean();
    }

    public void write(DataOutputStream output) throws IOException {
        output.writeFloat(this.strafeInput);
        output.writeFloat(this.forwardInput);
        output.writeFloat(this.pitch);
        output.writeFloat(this.yaw);
        output.writeBoolean(this.jumping);
        output.writeBoolean(this.sneaking);
    }

    public void handle(NetHandler netHandler) {
        netHandler.a(this);
    }

    public int getLength() {
        return 18;
    }

    public float getStrafeInput() {
        return this.strafeInput;
    }

    public float getForwardInput() {
        return this.forwardInput;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    @Override
    public void a(DataInputStream datainputstream) throws IOException {
        this.read(datainputstream);
    }

    @Override
    public void a(DataOutputStream dataoutputstream) throws IOException {
        this.write(dataoutputstream);
    }

    @Override
    public void a(NetHandler nethandler) {
        this.handle(nethandler);
    }

    @Override
    public int a() {
        return this.getLength();
    }

    public float c() {
        return this.getStrafeInput();
    }

    public float d() {
        return this.getPitch();
    }

    public float e() {
        return this.getForwardInput();
    }

    public float f() {
        return this.getYaw();
    }

    public boolean g() {
        return this.isJumping();
    }

    public boolean h() {
        return this.isSneaking();
    }
}