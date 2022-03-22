package pl.mcsu.lobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.lobby.Main;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.manager.Organizer;

import static pl.mcsu.lobby.utilities.Prefixes.error;
import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Center extends Command {

    public Center(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda dostępna tylko na serwerze.");
            return false;
        }
        Player player = (Player) sender;
        if (Organizer.getInstance().getProfile(player).getRank().getValue() > 2) {
            player.sendMessage(error + "Nie posiadasz wymaganych uprawnień.");
            return false;
        }
        Container.getInstance().getCenter().put("center", player.getLocation());
        Main.getInstance().getConfig().set("Center", player.getLocation());
        Main.getInstance().saveConfig();
        player.sendMessage(success + "Centrum zostało ustawione.");
        return true;
    }

}
