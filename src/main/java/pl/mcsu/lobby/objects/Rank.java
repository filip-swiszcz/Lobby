package pl.mcsu.lobby.objects;

public class Rank {

    private final String name;
    private final String prefix;
    private final int value;

    public Rank(String name, String prefix, int value) {
        this.name = name;
        this.prefix = prefix;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getValue() {
        return value;
    }

}
