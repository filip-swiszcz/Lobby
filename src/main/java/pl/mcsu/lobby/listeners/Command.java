package pl.mcsu.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import pl.mcsu.lobby.Main;

public class Command implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;
        for (String command : Main.getInstance().getConfig().getStringList("Commands.hide")) {
            event.getCommands().remove(command);
        }
    }

}
