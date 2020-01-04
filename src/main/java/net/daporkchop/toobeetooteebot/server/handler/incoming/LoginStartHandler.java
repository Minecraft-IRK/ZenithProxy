/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2020 DaPorkchop_
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.toobeetooteebot.server.handler.incoming;

import com.github.steveice10.mc.protocol.packet.login.client.LoginStartPacket;
import lombok.NonNull;
import net.daporkchop.toobeetooteebot.Bot;
import net.daporkchop.toobeetooteebot.server.PorkServerConnection;
import net.daporkchop.toobeetooteebot.util.handler.HandlerRegistry;

import static net.daporkchop.toobeetooteebot.util.Constants.*;

/**
 * @author DaPorkchop_
 */
public class LoginStartHandler implements HandlerRegistry.IncomingHandler<LoginStartPacket, PorkServerConnection> {
    @Override
    public boolean apply(@NonNull LoginStartPacket packet, @NonNull PorkServerConnection session) {
        //SERVER_LOG.info("login start");
        if (CONFIG.server.extra.whitelist.enable && !CONFIG.server.extra.whitelist.allowedUsers.contains(packet.getUsername())) {
            SERVER_LOG.warn("User %s [%s] tried to connect!", packet.getUsername(), session.getRemoteAddress());
            session.disconnect(CONFIG.server.extra.whitelist.kickmsg);
            return false;
        }
        if (!Bot.getInstance().isConnected()) {
            session.disconnect("Not connected to server!");
        }
        return false;
    }

    @Override
    public Class<LoginStartPacket> getPacketClass() {
        return LoginStartPacket.class;
    }
}
