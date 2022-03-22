package pl.mcsu.lobby.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.lobby.database.Queries;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.objects.Password;
import pl.mcsu.lobby.player.Profile;

import static pl.mcsu.lobby.utilities.Colors.green;
import static pl.mcsu.lobby.utilities.Prefixes.error;

public class Change extends Command {

    public Change(@NotNull String command) {
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
        if (!profile.isAuthorized()) {
            player.sendMessage(error + "Zaloguj się.");
            return false;
        }
        if (args.length != 2) {
            player.sendMessage(error + "Użyj /haslo [stare] [nowe]");
            return false;
        }
        Object[] data = Queries.getInstance().getPasswordAndSalt(player);
        if (data == null) {
            player.sendMessage(error + "Wystąpił błąd. Nie można się zalogować.");
            return false;
        }
        Password passwordChecked = new Password(args[0], (byte[]) data[1]);
        if (!passwordChecked.getPassword().equals(data[0])) {
            player.sendMessage(error + "Stare hasło jest niepoprawne.");
            return false;
        }
        final String regex = "(?=\\S+$).{6,}";
        if (!args[1].matches(regex)) {
            player.sendMessage(error + "Nowe hasło powinno zawierać co najmniej 6 znaków.");
            return false;
        }
        Queries.getInstance().setPassword(player, args[1]);
        player.sendActionBar(green + "Zmieniono hasło!");
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 10, 1);
        return true;
    }

}
