package pl.mcsu.lobby;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import pl.mcsu.lobby.commands.*;
import pl.mcsu.lobby.commands.Block;
import pl.mcsu.lobby.data.Container;
import pl.mcsu.lobby.listeners.*;
import pl.mcsu.lobby.objects.Rank;
import pl.mcsu.lobby.proxy.Messaging;
import pl.mcsu.lobby.utilities.Prefixes;
import pl.mcsu.lobby.utilities.Sidebar;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import static pl.mcsu.lobby.utilities.Colors.*;

public final class Main extends JavaPlugin {

    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        setConfig();
        setCenter();
        setCommands();
        setListeners();
        setMessaging();
        setPortals();
        setRanks();
        setSidebar();
    }

    private void setConfig() {
        saveDefaultConfig();
    }

    private void setCenter() {
        Location location = getConfig().getLocation("Center");
        if (location == null) {
            location = new Location(Bukkit.getWorld("world"), 0, 100, 0);
            getLogger().info("Center for lobby is not set yet.");
        }
        Container.getInstance().getCenter().put("center", location);
    }

    private void setCommands() {
        try {
            Field commandMapClass = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapClass.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapClass.get(Bukkit.getServer());
            commandMap.clearCommands();
            commandMap.register("lobby", new Block("blok"));
            commandMap.register("lobby", new Center("centrum"));
            commandMap.register("lobby", new Change("haslo"));
            commandMap.register("lobby", new Gamemode("gm"));
            commandMap.register("lobby", new Login("l"));
            commandMap.register("lobby", new pl.mcsu.lobby.commands.Rank("ranga"));
            commandMap.register("lobby", new Block("portal"));
            commandMap.register("lobby", new Register("r"));
            Objects.requireNonNull(commandMap.getCommand("l")).setAliases(Arrays.asList("login", "loguj"));
            Objects.requireNonNull(commandMap.getCommand("r")).setAliases(Arrays.asList("register", "rejestruj"));
            commandMapClass.setAccessible(false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setListeners() {
        getServer().getPluginManager().registerEvents(new Chat(), this);
        getServer().getPluginManager().registerEvents(new Command(), this);
        getServer().getPluginManager().registerEvents(new Damage(), this);
        getServer().getPluginManager().registerEvents(new Food(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Kick(), this);
        getServer().getPluginManager().registerEvents(new Move(), this);
        getServer().getPluginManager().registerEvents(new Place(), this);
        getServer().getPluginManager().registerEvents(new Portal(), this);
        getServer().getPluginManager().registerEvents(new Preprocess(), this);
        getServer().getPluginManager().registerEvents(new Quit(), this);
        getServer().getPluginManager().registerEvents(new Spawn(), this);
    }

    private void setMessaging() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Messaging());
    }

    private void setPortals() {
        int index = getConfig().getInt("Portals.index");
        if (index == 0) return;
        for (int i = 0; i <= index; i++) {
            if (getConfig().get("Portals.blocks." + i) == null) continue;
            Location location = getConfig().getLocation("Portals.blocks." + i);
            assert location != null;
            location.getBlock().setMetadata("survival", new FixedMetadataValue(this, "survival"));
        }
    }

    private void setRanks() {
        for (String name : getConfig().getStringList("Ranks")) {
            String prefix = convert(getConfig().getString("Contents." + name + ".prefix"));
            int value = getConfig().getInt("Contents." + name + ".value");
            Rank rank = new Rank(name, prefix, value);
            Container.getInstance().getRanks().put(name, rank);
        }
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for (Rank rank : Container.getInstance().getRanks().values()) {
            if (scoreboard.getTeam(rank.getValue() + rank.getName()) != null) continue;
            Team team = scoreboard.registerNewTeam(rank.getValue() + rank.getName());
            team.color(NamedTextColor.GRAY);
            if (team.getName().contains("Player")) {
                team.setPrefix("" + ChatColor.GRAY);
            } else {
                team.setPrefix(rank.getPrefix() + " " + gray);
            }
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
            team.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.NEVER);
        }
        Sidebar.getInstance().set(scoreboard);
        Container.getInstance().getScoreboard().put("scoreboard", scoreboard);
    }

    private void setSidebar() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) return;
                Scoreboard scoreboard = Container.getInstance().getScoreboard().get("scoreboard");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Sidebar.getInstance().update(scoreboard, player);
                }
            }
        }, 0, 20*5);
    }

}
