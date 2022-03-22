package pl.mcsu.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.mcsu.lobby.data.Container;

public class Quit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.quitMessage(null);
        Player player = event.getPlayer();
        if (Container.getInstance().getTimers().containsKey(player.getUniqueId())) {
            int taskId = Container.getInstance().getTimers().get(player.getUniqueId()).getId();
            Bukkit.getScheduler().cancelTask(taskId);
            Container.getInstance().getTimers().remove(player.getUniqueId());
        }
    }

}
