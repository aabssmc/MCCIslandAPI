package aabss.mcciapi.objects;

/**
 * An entry in a leaderboard.
 */
public record LeaderboardEntry(UnparsedPlayer mcciPlayer, Integer rank, Integer value) {

    /**
     * @return The player who has this entry.
     */
    @Override
    public UnparsedPlayer mcciPlayer() {
        return mcciPlayer;
    }

    /**
     * @return The rank for this entry.
     */
    @Override
    public Integer rank() {
        return rank;
    }

    /**
     * @return The value for this entry.
     */
    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return "LeaderboardEntry{" +
                "mcciPlayer=" + mcciPlayer +
                ", rank=" + rank +
                ", value=" + value +
                '}';
    }
}
