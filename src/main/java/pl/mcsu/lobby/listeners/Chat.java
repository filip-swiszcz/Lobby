package pl.mcsu.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.player.Profile;

import static pl.mcsu.lobby.utilities.Colors.gray;
import static pl.mcsu.lobby.utilities.Colors.light_gray;
import static pl.mcsu.lobby.utilities.Prefixes.space;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = Organizer.getInstance().getProfile(player);
        if (!profile.isAuthorized()) {
            event.setCancelled(true);
            return;
        }
        event.setFormat(profile.getRank().getPrefix() + space + gray + "%s" + ":" + space + light_gray + "%s");
    }

}
