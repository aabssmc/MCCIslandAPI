package aabss.mcciapi.objects;

import java.util.List;

/**
 * A player who has logged in to MCC Island.
 */
public record Player(Collections collections, CrownLevel crownLevel, List<Rank> ranks, Social social, Status status,
                     String username, String uuid) {

    /**
     * @return The collections data.
     */
    @Override
    public Collections collections() {
        return collections;
    }

    /**
     * @return The player's Crown Level.
     */
    @Override
    public CrownLevel crownLevel() {
        return crownLevel;
    }

    /**
     * @return The list of ranks.
     */
    @Override
    public List<Rank> ranks() {
        return ranks;
    }

    /**
     * @return The social data.
     */
    @Override
    public Social social() {
        return social;
    }

    /**
     * @return The player's status.
     */
    @Override
    public Status status() {
        return status;
    }

    /**
     * @return The player's username.
     */
    @Override
    public String username() {
        return username;
    }

    /**
     * @return The player's UUID.
     */
    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Player{" +
                "collections=" + collections +
                ", crownLevel=" + crownLevel +
                ", ranks=" + ranks +
                ", social=" + social +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
