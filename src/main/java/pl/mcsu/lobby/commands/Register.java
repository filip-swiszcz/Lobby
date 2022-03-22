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
import pl.mcsu.lobby.player.Profile;

import java.util.Objects;

import static pl.mcsu.lobby.utilities.Colors.green;
import static pl.mcsu.lobby.utilities.Prefixes.error;

public class Register extends Command {

    public Register(@NotNull String command) {
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
            player.sendMessage(error + "Jesteś już zarejestrowany.");
            return false;
        }
        if (!profile.isAuthorized() && profile.isRegistered()) {
            player.sendMessage(error + "Użyj /l [hasło]");
            return false;
        }
        if (args.length != 2) {
            player.sendMessage(error + "Użyj /r [hasło] [hasło]");
            return false;
        }
        final String regex = "(?=\\S+$).{6,}";
        if (!args[0].matches(regex)) {
            player.sendMessage(error + "Hasło powinno zawierać co najmniej 6 znaków.");
            return false;
        }
        if (!args[0].equals(args[1])) {
            player.sendMessage(error + "Hasła muszą być takie same.");
            return false;
        }
        if (Queries.getInstance().isIpRegistered(Objects.requireNonNull(player.getAddress()).getAddress())) {
            player.sendMessage(error + "Posiadasz już konto na serwerze. Zgłoś się na mcsu.pl/discord");
            return false;
        }
        Queries.getInstance().setRegistered(player, args[0], player.getAddress().getAddress());
        int taskId = Container.getInstance().getTimers().get(player.getUniqueId()).getId();
        Bukkit.getScheduler().cancelTask(taskId);
        Container.getInstance().getTimers().remove(player.getUniqueId());
        profile.setRegistered(true);
        profile.setAuthorized(true);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.sendTitle("", "");
        player.sendActionBar(green + "Zarejestrowano!");
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 10, 1);
        return true;
    }

}
