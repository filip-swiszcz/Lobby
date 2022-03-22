package pl.mcsu.lobby.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import pl.mcsu.lobby.Main;

import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Place implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("SURVIVAL")) {
            block.setMetadata("survival", new FixedMetadataValue(Main.getInstance(), "survival"));
            int index = Main.getInstance().getConfig().getInt("Portals.index") + 1;
            Main.getInstance().getConfig().set("Portals.blocks." + index, block.getLocation());
            Main.getInstance().getConfig().set("Portals.index", index);
            Main.getInstance().saveConfig();
            player.sendMessage(success + "Zapisano portal.");
        }
    }

}
