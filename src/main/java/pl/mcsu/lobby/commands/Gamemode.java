package pl.mcsu.lobby.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.player.Profile;

import static pl.mcsu.lobby.utilities.Prefixes.error;
import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Gamemode extends Command {

    public Gamemode(@NotNull String command) {
        super(command);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(error  + "Musisz być na serwerze.");
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Organizer.getInstance().getProfile(player);
        if (profile.getRank().getValue() > 3) {
            player.sendMessage(error + "Nie posiadasz wymaganych uprawnień.");
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(error + "Użyj /gm [1/2]");
            return false;
        }
        switch (args[0]) {
            case "1":
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(success + "Włączono tryb kreatywny.");
                break;
            case "2":
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(success + "Włączono tryb przygody.");
                break;
        }
        return true;
    }

}
