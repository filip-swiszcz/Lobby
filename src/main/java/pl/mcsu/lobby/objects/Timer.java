package pl.mcsu.lobby.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.mcsu.lobby.Main;

import static pl.mcsu.lobby.utilities.Colors.*;

public class Timer {

    private int time;
    private final int id;

    public Timer(Player player) {
        this.time = 60;
        this.id = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (time == 0) {
                    Bukkit.getScheduler().runTask(Main.getInstance(), () -> player.kickPlayer(light_purple + "Upłynął czas dostępny na autoryzację"));
                    Bukkit.getScheduler().cancelTask(id);
                } else {
                    player.sendActionBar(light_purple + "Pozostały czas - " + light_gray + time + light_purple + " sekund");
                    time--;
                }
            }
        }, 0, 20).getTaskId();
    }

    public int getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
