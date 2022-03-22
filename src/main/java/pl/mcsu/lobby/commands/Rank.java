package pl.mcsu.lobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.database.Queries;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.player.Profile;
import pl.mcsu.lobby.utilities.Tags;

import java.util.Locale;

import static pl.mcsu.lobby.utilities.Prefixes.error;
import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Rank extends Command {

    public Rank(@NotNull String command) {
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
        if (!player.isOp() || profile.getRank().getValue() > 2) {
            player.sendMessage(error + "Nie posiadasz wymaganych uprawnień.");
            return false;
        }
        if (args.length != 2) {
            player.sendMessage(error + "Użyj /ranga [gracz] [ranga]");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(error + "Gracz jest niedostępny.");
            return false;
        }
        switch (args[1].toLowerCase(Locale.ROOT)) {
            case "gracz":
                Queries.getInstance().setRank(target, "Player");
                Organizer.getInstance().getProfile(target).setRank(Container.getInstance().getRanks().get("Player"));
                Tags.getInstance().add(player);
                player.sendMessage(success + "Ranga " + target.getName() + " została zmieniona.");
                target.sendMessage(success + "Twoja ranga uległa zmianie.");
                return true;
            case "pomocnik":
                Queries.getInstance().setRank(target, "Helper");
                Organizer.getInstance().getProfile(target).setRank(Container.getInstance().getRanks().get("Helper"));
                Tags.getInstance().add(player);
                player.sendMessage(success + "Ranga " + target.getName() + " została zmieniona.");
                target.sendMessage(success + "Twoja ranga uległa zmianie.");
                return true;
            case "moderator":
                Queries.getInstance().setRank(target, "Moderator");
                Organizer.getInstance().getProfile(target).setRank(Container.getInstance().getRanks().get("Moderator"));
                Tags.getInstance().add(player);
                player.sendMessage(success + "Ranga " + target.getName() + " została zmieniona.");
                target.sendMessage(success + "Twoja ranga uległa zmianie.");
                return true;
            case "admin":
                if (profile.getRank().getValue() > 2) {
                    player.sendMessage(error + "Twoje uprawnienia są za niskie.");
                    return false;
                }
                Queries.getInstance().setRank(target, "Admin");
                Organizer.getInstance().getProfile(target).setRank(Container.getInstance().getRanks().get("Admin"));
                Tags.getInstance().add(player);
                player.sendMessage(success + "Ranga " + target.getName() + " została zmieniona.");
                target.sendMessage(success + "Twoja ranga uległa zmianie.");
                return true;
            case "wlasciciel":
                if (profile.getRank().getValue() > 1) {
                    player.sendMessage(error + "Twoje uprawnienia są za niskie.");
                    return false;
                }
                Queries.getInstance().setRank(target, "Owner");
                Organizer.getInstance().getProfile(target).setRank(Container.getInstance().getRanks().get("Owner"));
                Tags.getInstance().add(player);
                player.sendMessage(success + "Ranga " + target.getName() + " została zmieniona.");
                target.sendMessage(success + "Twoja ranga uległa zmianie.");
                return true;
            case "deweloper":
                if (profile.getRank().getValue() > 1) {
                    player.sendMessage(error + "Twoje uprawnienia są za niskie.");
                    return false;
                }
                Queries.getInstance().setRank(target, "Developer");
                Organizer.getInstance().getProfile(target).setRank(Container.getInstance().getRanks().get("Developer"));
                Tags.getInstance().add(player);
                player.sendMessage(success + "Ranga " + target.getName() + " została zmieniona.");
                target.sendMessage(success + "Twoja ranga uległa zmianie.");
                return true;
            default:
                player.sendMessage(error + "Dostępne rangi: Gracz, Pomocnik, Moderator, Admin, Wlasciciel, Deweloper");
                return false;
        }
    }

}
