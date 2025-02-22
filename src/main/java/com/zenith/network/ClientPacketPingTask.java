package com.zenith.network;

import com.github.steveice10.mc.protocol.data.ProtocolState;
import com.github.steveice10.mc.protocol.packet.status.serverbound.ServerboundPingRequestPacket;
import com.zenith.network.client.ClientSession;
import com.zenith.util.Config;

import java.util.concurrent.TimeUnit;

import static com.zenith.Shared.CONFIG;
import static com.zenith.Shared.EXECUTOR;

/**
 * Zenith client -> server ping task
 */
public class ClientPacketPingTask implements Runnable {
    private final ClientSession session;

    public ClientPacketPingTask(final ClientSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        if (CONFIG.client.ping.mode != Config.Client.Ping.Mode.PACKET) return;
        if (session.isDisconnected()) return;
        if (session.getPacketProtocol().getState() == ProtocolState.GAME) {
            var id = System.currentTimeMillis();
            try {
                session.send(new ServerboundPingRequestPacket(id), f -> {
                    session.setLastPingId(id);
                    session.setLastPingSentTime(System.currentTimeMillis());
                });
            } catch (final Throwable e) {
                // fall through
            }
        }
        EXECUTOR.schedule(this, CONFIG.client.ping.packetPingIntervalSeconds, TimeUnit.SECONDS);
    }
}
