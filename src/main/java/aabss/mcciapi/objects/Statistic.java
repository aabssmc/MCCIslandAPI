package aabss.mcciapi.objects;

import java.util.List;

public class Statistic {
    private final Boolean forLeaderboard;
    private final String key;
    private final List<LeaderboardEntry> leaderboard;
    private final List<Rotation> rotations;

    public Statistic(Boolean forLeaderboard, String key, List<LeaderboardEntry> leaderboard, List<Rotation> rotations) {
        this.forLeaderboard = forLeaderboard;
        this.key = key;
        this.leaderboard = leaderboard;
        this.rotations = rotations;
    }

    /**
     * @return If this statistic generates leaderboards.
     */
    public Boolean getForLeaderboard() {
        return forLeaderboard;
    }

    /**
     * @return The key of the statistic.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return Returns the leaderboard for this statistic in a given rotation.
     */
    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }

    /**
     * @return The rotations for which this statistic is tracked.
     */
    public List<Rotation> getRotation() {
        return rotations;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "forLeaderboard=" + forLeaderboard +
                ", key='" + key + '\'' +
                ", leaderboard=" + leaderboard +
                ", rotations=" + rotations +
                '}';
    }
}
