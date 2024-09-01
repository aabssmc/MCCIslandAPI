package cc.aabss.mcciapi.objects;

/**
 * A player's current status.
 */
public record Status(String firstJoin, String lastJoin, Boolean online, Server server) {

    /**
     * @return When the player first joined MCC Island, if known.
     */
    @Override
    public String firstJoin() {
        return firstJoin;
    }

    /**
     * @return When the player most recently joined MCC Island, if known.
     */
    @Override
    public String lastJoin() {
        return lastJoin;
    }

    /**
     * @return Whether the player is online or not.
     */
    @Override
    public Boolean online() {
        return online;
    }

    /**
     * @return The player's current server, populated if they are online.
     */
    @Override
    public Server server() {
        return server;
    }

    @Override
    public String toString() {
        return "Status{" +
                "firstJoin='" + firstJoin + '\'' +
                ", lastJoin='" + lastJoin + '\'' +
                ", online=" + online +
                ", server=" + server +
                '}';
    }
}
