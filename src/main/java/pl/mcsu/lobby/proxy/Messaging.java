package pl.mcsu.lobby.proxy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.lobby.Main;
import pl.mcsu.lobby.data.Container;

import java.util.Map;

public class Messaging implements PluginMessageListener {

    private static Messaging instance = new Messaging();

    public Messaging() {
        instance = this;
    }

    public static Messaging getInstance() {
        return instance;
    }

    /*
    *
    *   Messaging with proxy server
    *
    * */

    private final Map<String, Integer> online = Container.getInstance().getOnline();

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        if (subChannel.equals("PlayerCount")) {
            String server = in.readUTF();
            if (server.equals("ALL")) {
                if (!online.containsKey("ALL")) {
                    online.put("ALL", in.readInt());
                } else {
                    online.replace("ALL", in.readInt());
                }
            }
            if (server.equals("Survival")) {
                if (!online.containsKey("Survival")) {
                    online.put("Survival", in.readInt());
                } else {
                    online.replace("Survival", in.readInt());
                }
            }
        }
    }

    public void online(Player player, String server) {
        if (server == null) {
            server = "ALL";
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        player.sendPluginMessage(Main.getInstance(),"BungeeCord", out.toByteArray());
    }

    public void connect(final Player player, final String server) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
    }

}
