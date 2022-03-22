package pl.mcsu.lobby.utilities;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.manager.Organizer;
import pl.mcsu.lobby.objects.Rank;

public class Tags {

    private static Tags instance = new Tags();

    public Tags() {
        instance = this;
    }

    public static Tags getInstance() {
        return instance;
    }

    /*
    *
    *   Creating tags and setting it to players
    *
    * */

    public void set(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        for (Rank rank : Container.getInstance().getRanks().values()) {
            if (scoreboard.getTeam(rank.getValue() + rank.getName()) != null) continue;
            Team team = scoreboard.registerNewTeam(rank.getValue() + rank.getName());
            team.setPrefix(rank.getPrefix() + " ");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
            team.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.NEVER);
        }
    }

    public void add(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Rank rank = Organizer.getInstance().getProfile(player).getRank();
        Team team = scoreboard.getTeam(rank.getValue() + rank.getName());
        if (team == null) return;
        team.addEntry(player.getName());
    }

}
