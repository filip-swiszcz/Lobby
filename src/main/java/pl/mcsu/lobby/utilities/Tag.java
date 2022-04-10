package pl.mcsu.lobby.utilities;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.objects.Rank;

public class Tag {

    private static Tag instance = new Tag();

    public Tag() {
        instance = this;
    }

    public static Tag getInstance() {
        return instance;
    }

    /*
     *
     *   Creating tags and adding player to one of them, according to rank
     *
     * */

    public void set(Player player) {
        Scoreboard scoreboard = Container.getInstance().getScoreboard().get("scoreboard");
        if (!player.getScoreboard().equals(scoreboard)) player.setScoreboard(scoreboard);
        Rank rank = Organizer.getInstance().getProfile(player).getRank();
        Team team = scoreboard.getTeam(rank.getValue() + rank.getName());
        if (team == null) return;
        if (team.hasEntry(player.getName())) return;
        team.addEntry(player.getName());
    }

}
