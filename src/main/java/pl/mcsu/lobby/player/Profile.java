package pl.mcsu.lobby.player;

import org.bukkit.entity.Player;
import pl.mcsu.lobby.objects.Rank;

import java.net.InetAddress;
import java.util.Objects;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private final String name;
    private InetAddress inetAddress;
    private Rank rank;
    private boolean registered;
    private boolean authorized;

    public Profile(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.inetAddress = Objects.requireNonNull(player.getAddress()).getAddress();
        this.rank = null;
        this.registered = false;
        this.authorized = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isRegistered() {
        return registered;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

}
