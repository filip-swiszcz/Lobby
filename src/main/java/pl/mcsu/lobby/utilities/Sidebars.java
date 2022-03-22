package pl.mcsu.lobby.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.proxy.Messaging;

import java.util.Objects;

import static pl.mcsu.lobby.utilities.Colors.*;

public class Sidebars {

    private static Sidebars instance = new Sidebars();

    public Sidebars() {
        instance = this;
    }

    public static Sidebars getInstance() {
        return instance;
    }

    /*
    *
    *   Creating sidebar and setting it to players
    *
    * */

    public void set(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy", gradient(Prefixes.name, blue.getColor(), purple.getColor(), true));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        setScore(objective, " ", 7);
        setTeam(scoreboard, "information", light_green + "", purple + "Informacje " + gray + "»");
        setScore(objective, light_green + "", 6);
        setTeam(scoreboard, "online", green + "", gray + "» " + light_gray + "Gracze" + light_gray + ": " + getOnline(player));
        setScore(objective, green + "", 5);
        setTeam(scoreboard, "servers", blue + "", purple + "Serwery " + gray + "»");
        setScore(objective, blue + "", 4);
        setTeam(scoreboard, "survival", light_purple + "", gray + "» " + light_gray + "PRZETRWANIE: " + green + getOnline(player, "survival"));
        setScore(objective, light_purple + "", 3);
        setTeam(scoreboard, "skyblock", purple + "", gray + "» " + light_gray + "WYSPY: " + red + "-");
        setScore(objective, purple + "", 2);
        setScore(objective, "  ", 1);
        setTeam(scoreboard, "website", light_gray + "", gradient(Prefixes.discord, blue.getColor(), purple.getColor(), false));
        setScore(objective, light_gray + "", 0);
        player.setScoreboard(scoreboard);
    }

    public void update(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objects.requireNonNull(scoreboard.getTeam("online")).setPrefix(gray + "» " + light_gray + "Gracze" + light_gray + ": " + getOnline(player));
        Objects.requireNonNull(scoreboard.getTeam("survival")).setPrefix(gray + "» " + light_gray + "PRZETRWANIE: " + green + getOnline(player, "survival"));
    }

    public void setScoreboard(Player player, boolean update) {
        if (!update) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

            System.out.println(scoreboard.getTeams());

            Objects.requireNonNull(scoreboard.getObjective("sidebar")).unregister();

            Objective objective = scoreboard.registerNewObjective("sidebar", "dummy", gradient(Prefixes.name, blue.getColor(), purple.getColor(), true));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            setScore(objective, " ", 7);
            setTeam(scoreboard, "information", light_green + "", purple + "Informacje " + gray + "»");
            setScore(objective, light_green + "", 6);
            setTeam(scoreboard, "online", green + "", gray + "» " + light_gray + "Gracze" + light_gray + ": " + getOnline(player));
            setScore(objective, green + "", 5);
            setTeam(scoreboard, "servers", blue + "", purple + "Serwery " + gray + "»");
            setScore(objective, blue + "", 4);
            setTeam(scoreboard, "survival", light_purple + "", gray + "» " + light_gray + "PRZETRWANIE: " + green + getOnline(player, "Survival"));
            setScore(objective, light_purple + "", 3);
            setTeam(scoreboard, "skyblock", purple + "", gray + "» " + light_gray + "WYSPY: " + red + "-");
            setScore(objective, purple + "", 2);
            setScore(objective, "  ", 1);
            setTeam(scoreboard, "website", light_gray + "", gradient(Prefixes.discord, blue.getColor(), purple.getColor(), false));
            setScore(objective, light_gray + "", 0);
            player.setScoreboard(scoreboard);
            return;
        }
        Scoreboard scoreboard = player.getScoreboard();
        Objects.requireNonNull(scoreboard.getTeam("online")).setPrefix(gray + "» " + light_gray + "Gracze" + light_gray + ": " + getOnline(player));
        Objects.requireNonNull(scoreboard.getTeam("survival")).setPrefix(gray + "» " + light_gray + "SURVIVAL: " + green + getOnline(player, "Survival"));
    }

    private void setTeam(Scoreboard scoreboard, String name, String entry, String prefix) {
        Team team = scoreboard.registerNewTeam(name);
        team.addEntry(entry);
        team.setPrefix(prefix);
    }

    private void setScore(Objective objective, String entry, int number) {
        Score score = objective.getScore(entry);
        score.setScore(number);
    }

    private int getOnline(Player player) {
        Messaging.getInstance().online(player, "ALL");
        if (Container.getInstance().getOnline().containsKey("ALL")) {
            return Container.getInstance().getOnline().get("ALL");
        }
        return 0;
    }

    private int getOnline(Player player, String server) {
        Messaging.getInstance().online(player, server);
        if (Container.getInstance().getOnline().containsKey(server)) {
            return Container.getInstance().getOnline().get(server);
        }
        return 0;
    }

}
