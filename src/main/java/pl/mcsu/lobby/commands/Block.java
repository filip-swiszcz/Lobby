package pl.mcsu.lobby.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.player.Profile;

import static pl.mcsu.lobby.utilities.Prefixes.error;
import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Block extends Command {

    public Block(@NotNull String command) {
        super(command);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(error + "Musisz być na serwerze.");
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Organizer.getInstance().getProfile(player);
        if (profile.getRank().getValue() > 3) {
            player.sendMessage(error + "Nie posiadasz wymaganych uprawnień.");
            return false;
        }
        if (args.length > 0) {
            player.sendMessage(error + "Użyj /blok");
            return false;
        }
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            player.sendMessage(error + "Wybierz przedmiot.");
            return false;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("SURVIVAL");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().addItem(itemStack);
        player.sendMessage(success + "Blok teleportacji.");
        return true;
    }

}
