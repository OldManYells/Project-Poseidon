package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketDataCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet71Weather extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    private final PacketDataCodec packetDataCodec = PacketDataCodec.getInstance();

    public Packet71Weather() {}

    public Packet71Weather(Entity entity) {
        PacketDataCodec.Packet71WeatherData packetData = packetDataCodec.packet71FromWeatherEntity(entity);
        this.a = packetData.getEntityId();
        this.e = packetData.getWeatherType();
        this.b = packetData.getX();
        this.c = packetData.getY();
        this.d = packetData.getZ();
    }

    public void a(DataInputStream datainputstream) throws IOException {
        PacketDataCodec.Packet71WeatherData data = packetDataCodec.readPacket71Weather(datainputstream);
        this.a = data.getEntityId();
        this.e = data.getWeatherType();
        this.b = data.getX();
        this.c = data.getY();
        this.d = data.getZ();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        packetDataCodec.writePacket71Weather(
                new PacketDataCodec.Packet71WeatherData(this.a, this.e, this.b, this.c, this.d),
                dataoutputstream
        );
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return packetDataCodec.packet71Length();
    }
}
