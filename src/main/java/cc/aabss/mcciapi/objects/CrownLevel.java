package cc.aabss.mcciapi.objects;

/**
 * A Crown Level and associated trophy data.
 */
public record CrownLevel(Integer level, Integer nextEvolutionLevel, ProgressionData nextLevelProgress, TrophyData trophies) {

    /**
     * @return The overall Crown Level.
     */
    @Override
    public Integer level() {
        return level;
    }

    /**
     * @return The next level that the crown will evolve, if any.
     */
    @Override
    public Integer nextEvolutionLevel() {
        return nextEvolutionLevel;
    }

    /**
     * @return The progress the player is making towards their next level, if any.
     */
    @Override
    public ProgressionData nextLevelProgress() {
        return nextLevelProgress;
    }

    /**
     * @return The amount of trophies the player has.
     */
    @Override
    public TrophyData trophies() {
        return trophies;
    }

    @Override
    public String toString() {
        return "CrownLevel{" +
                "level=" + level +
                ", nextEvolutionLevel=" + nextEvolutionLevel +
                ", nextLevelProgress=" + nextLevelProgress +
                ", trophies=" + trophies +
                '}';
    }
}