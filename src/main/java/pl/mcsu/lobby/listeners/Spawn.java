package pl.mcsu.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import pl.mcsu.lobby.data.Container;

public class Spawn implements Listener {

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        if (Container.getInstance().getCenter().isEmpty()) return;
        event.setSpawnLocation(Container.getInstance().getCenter().get("center"));
    }

}
