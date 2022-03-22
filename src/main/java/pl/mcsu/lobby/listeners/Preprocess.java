package pl.mcsu.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.mcsu.lobby.Main;

import java.util.Locale;

import static pl.mcsu.lobby.utilities.Prefixes.error;

public class Preprocess implements Listener {

    @EventHandler
    public void onPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        for (String command : Main.getInstance().getConfig().getStringList("Commands.block")) {
            if (event.getMessage().toLowerCase(Locale.ROOT).startsWith(command)) {
                event.setCancelled(true);
                player.sendMessage(error + "Nie ma takiej komendy.");
            }
        }
    }

}
