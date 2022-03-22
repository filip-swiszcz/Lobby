package pl.mcsu.lobby.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.player.Profile;
import pl.mcsu.lobby.proxy.Messaging;

import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Move implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Profile profile = Organizer.getInstance().getProfile(player);
        if (!profile.isAuthorized()) {
            event.setTo(event.getFrom());
            event.setCancelled(true);
            return;
        }
        Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
        if (!block.hasMetadata("survival")) return;
        if (event.getTo().getBlockX() == event.getFrom().getBlockX()
                && event.getTo().getBlockY() == event.getFrom().getBlockY()
                && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) return;
        player.sendMessage(success + "Łączenie z SURVIVAL.");
        Messaging.getInstance().connect(player, "Survival");
    }

}
