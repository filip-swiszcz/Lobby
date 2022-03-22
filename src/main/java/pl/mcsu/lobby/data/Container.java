package pl.mcsu.lobby.data;

import org.bukkit.Location;
import pl.mcsu.lobby.objects.Rank;
import pl.mcsu.lobby.objects.Timer;
import pl.mcsu.lobby.player.Profile;

import java.util.*;

public class Container {

    private static Container instance = new Container();

    public Container() {
        instance = this;
    }

    public static Container getInstance() {
        return instance;
    }

    /*
    *
    *   Storing data in server cache
    *
    * */

    private final Map<String, Location> center = new HashMap<>();
    private final Map<UUID, Profile> profiles = new HashMap<>();
    private final Map<String, Rank> ranks = new HashMap<>();
    private final Map<UUID, Timer> timers = new HashMap<>();
    private final Map<String, Integer> online = new HashMap<>();

    public Map<String, Location> getCenter() {
        return center;
    }

    public Map<UUID, Profile> getProfiles() {
        return profiles;
    }

    public Map<String, Rank> getRanks() {
        return ranks;
    }

    public Map<UUID, Timer> getTimers() {
        return timers;
    }

    public Map<String, Integer> getOnline() {
        return online;
    }

}
