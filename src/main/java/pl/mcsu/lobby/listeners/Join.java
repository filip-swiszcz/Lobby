package pl.mcsu.lobby.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.database.Queries;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.objects.Rank;
import pl.mcsu.lobby.utilities.Prefixes;
import pl.mcsu.lobby.utilities.Tag;

import java.util.Objects;

import static pl.mcsu.lobby.utilities.Colors.*;
import static pl.mcsu.lobby.utilities.Prefixes.success;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        player.setPlayerListHeaderFooter("\n" + gradient(Prefixes.name, blue.getColor(), purple.getColor(), true) + "\n", light_gray + "\n   Dołącz do nas również na   \n" + gradient(Prefixes.discord, blue.getColor(), purple.getColor(), false));
        player.setGameMode(GameMode.ADVENTURE);
        if (!Organizer.getInstance().hasProfile(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 180 * 20, 500));
            Organizer.getInstance().setProfile(player);
            Organizer.getInstance().setTimer(player);
            if (Queries.getInstance().isRegistered(player)) {
                Organizer.getInstance().getProfile(player).setRegistered(true);
                String name = Queries.getInstance().getRank(player);
                Rank rank = Container.getInstance().getRanks().get(name);
                Organizer.getInstance().getProfile(player).setRank(rank);
                Tag.getInstance().set(player);
                player.sendMessage(success + "Użyj /l [hasło]");
                player.sendTitle(light_purple + "Logowanie", light_gray + "/l [hasło]", 20, 60 * 20, 20);
                return;
            }
            String name = "Player";
            Rank rank = Container.getInstance().getRanks().get(name);
            Organizer.getInstance().getProfile(player).setRank(rank);
            Tag.getInstance().set(player);
            player.sendMessage(success + "Użyj /r [hasło] [hasło]");
            player.sendTitle(light_purple + "Rejestracja", light_gray + "/r [hasło] [hasło]", 20, 60 * 20, 20);
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 180 * 20, 500));
        if (!Organizer.getInstance().getProfile(player).isAuthorized()) {
            Organizer.getInstance().setTimer(player);
            if (Organizer.getInstance().getProfile(player).isRegistered()) {
                String name = Queries.getInstance().getRank(player);
                Rank rank = Container.getInstance().getRanks().get(name);
                Organizer.getInstance().getProfile(player).setRank(rank);
                Tag.getInstance().set(player);
                player.sendMessage(success + "Użyj /l [hasło]");
                player.sendTitle(light_purple + "Logowanie", light_gray + "/l [hasło]", 20, 60 * 20, 20);
                return;
            }
            String name = "Player";
            Rank rank = Container.getInstance().getRanks().get(name);
            Organizer.getInstance().getProfile(player).setRank(rank);
            Tag.getInstance().set(player);
            player.sendMessage(success + "Użyj /r [hasło] [hasło]");
            player.sendTitle(light_purple + "Rejestracja", light_gray + "/r [hasło] [hasło]", 20, 60 * 20, 20);
            return;
        }
        if (!Organizer.getInstance().getProfile(player).getInetAddress().equals(Objects.requireNonNull(player.getAddress()).getAddress())) {
            Organizer.getInstance().getProfile(player).setAuthorized(false);
            Organizer.getInstance().setTimer(player);
            String name = Queries.getInstance().getRank(player);
            Rank rank = Container.getInstance().getRanks().get(name);
            Organizer.getInstance().getProfile(player).setRank(rank);
            Tag.getInstance().set(player);
            player.sendMessage(success + "Użyj /l [hasło]");
            player.sendTitle(light_purple + "Logowanie", light_gray + "/l [hasło]", 20, 60 * 20, 20);
            return;
        }
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        String name = Queries.getInstance().getRank(player);
        Rank rank = Container.getInstance().getRanks().get(name);
        Organizer.getInstance().getProfile(player).setRank(rank);
        Tag.getInstance().set(player);
        player.sendTitle("", "");
        player.sendActionBar(green + "Zalogowano automatycznie!");
    }
}
