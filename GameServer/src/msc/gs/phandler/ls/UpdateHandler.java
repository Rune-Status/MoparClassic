package msc.gs.phandler.ls;

import org.apache.mina.common.IoSession;

import msc.gs.Instance;
import msc.gs.connection.LSPacket;
import msc.gs.connection.Packet;
import msc.gs.model.Player;
import msc.gs.model.World;
import msc.gs.phandler.PacketHandler;
import msc.gs.util.Logger;

public class UpdateHandler implements PacketHandler {
    /**
     * World instance
     */
    public static final World world = Instance.getWorld();

    public void handlePacket(Packet p, IoSession session) throws Exception {
	long uID = ((LSPacket) p).getUID();
	Logger.event("LOGIN_SERVER sent update (uID: " + uID + ")");
	String reason = p.readString();
	if (Instance.getServer().shutdownForUpdate()) {
	    for (Player player : world.getPlayers()) {
		player.getActionSender().sendAlert("The server will be shutting down in 60 seconds: " + reason, false);
		player.getActionSender().startShutdown(60);
	    }
	}
    }

}