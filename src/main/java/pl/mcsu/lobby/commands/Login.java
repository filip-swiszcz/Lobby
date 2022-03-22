package pl.mcsu.lobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.database.Queries;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.objects.Password;
import pl.mcsu.lobby.player.Profile;

import static pl.mcsu.lobby.utilities.Colors.green;
import static pl.mcsu.lobby.utilities.Prefixes.error;

public class Login extends Command {

    public Login(@NotNull String command) {
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
        if (profile.isAuthorized()) {
            player.sendMessage(error + "Jesteś już zalogowany.");
            return false;
        }
        if (!profile.isAuthorized() && !profile.isRegistered()) {
            player.sendMessage(error + "Użyj /r [hasło] [hasło]");
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(error + "Użyj /l [hasło]");
            return false;
        }
        Object[] data = Queries.getInstance().getPasswordAndSalt(player);
        if (data == null) {
            player.sendMessage(error + "Wystąpił błąd. Nie można się zalogować.");
            return false;
        }
        Password passwordChecked = new Password(args[0], (byte[]) data[1]);
        if (!passwordChecked.getPassword().equals(data[0])) {
            player.sendMessage(error + "Hasło jest niepoprawne.");
            return false;
        }
        int taskId = Container.getInstance().getTimers().get(player.getUniqueId()).getId();
        Bukkit.getScheduler().cancelTask(taskId);
        Container.getInstance().getTimers().remove(player.getUniqueId());
        profile.setAuthorized(true);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.sendTitle("", "");
        player.sendActionBar(green + "Zalogowano!");
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 10, 1);
        return true;
    }

}
