import aabss.mcciapi.MCCIslandAPI;
import aabss.mcciapi.objects.Statistic;

public class MainTest {
    public static void main(String args[]) {
        MCCIslandAPI api = new MCCIslandAPI("some key");
        for (Statistic statistic : api.getStatistics()) {
            System.out.println(statistic.toString());
        }
    }
}
