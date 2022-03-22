package pl.mcsu.lobby.manager;

import org.bukkit.entity.Player;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.objects.Timer;
import pl.mcsu.lobby.player.Profile;

public class Organizer {

    private static Organizer instance = new Organizer();

    public Organizer() {
        instance = this;
    }

    public static Organizer getInstance() {
        return instance;
    }

    /*
    *
    *   Simplifies saving objects in cache
    *
    * */

    public Profile getProfile(Player player) {
        return Container.getInstance().getProfiles().get(player.getUniqueId());
    }

    public Timer getTimer(Player player) {
        return Container.getInstance().getTimers().get(player.getUniqueId());
    }

    public boolean hasProfile(Player player) {
        return Container.getInstance().getProfiles().containsKey(player.getUniqueId());
    }

    public boolean hasTimer(Player player) {
        return Container.getInstance().getTimers().containsKey(player.getUniqueId());
    }

    public void setProfile(Player player) {
        Profile profile = new Profile(player);
        Container.getInstance().getProfiles().put(player.getUniqueId(), profile);
    }

    public void setTimer(Player player) {
        Timer timer = new Timer(player);
        Container.getInstance().getTimers().put(player.getUniqueId(), timer);
    }

}
