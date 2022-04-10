package pl.mcsu.lobby.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.mcsu.lobby.data.Container;

import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Damage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            player.teleport(Container.getInstance().getCenter().get("center"));
            player.setHealth(20);
            player.sendMessage(success + "Ups! Było blisko.");
            player.setFallDistance(0);
        }
        event.setCancelled(true);
    }

}
