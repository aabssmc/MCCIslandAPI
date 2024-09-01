package aabss.mcciapi.objects;

public record StatisticValueResult(Statistic statistic, int value) {

    /**
     * @return The statistic.
     */
    @Override
    public Statistic statistic() {
        return statistic;
    }

    /**
     * @return The value.
     */
    @Override
    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return "StatisticValueResult{" +
                "statistic=" + statistic +
                ", value=" + value +
                '}';
    }
}
