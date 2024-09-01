package cc.aabss.mcciapi;

import cc.aabss.mcciapi.objects.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MCCIslandAPI {
    final String URL = "https://api.mccisland.net/graphql";
    String API_KEY;
    final Gson gson = new Gson();

    final HttpClient client = HttpClient.newHttpClient();

    /**
     * Makes a new MCCIslandAPI instance.
     * @param API_KEY The MCCIsland api key, can be generated <a href="https://gateway.noxcrew.com">here</a>
     */
    public MCCIslandAPI(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    /**
     * Internal use only, shouldn't even be accessible to nons.
     * @param query Query name.
     * @return Body response.
     */
    private String sendRequest(String query) {
        String jsonRequest = gson.toJson(Map.of("query", query));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .header("X-API-Key", API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    /**
     * Gets the next rotation cycle.
     * @param rotation Rotation cycle of the day.
     * @return The next rotation.
     */
    public String getNextRotation(Rotation rotation) {
        String query = """
                query {
                  nextRotation(rotation: <ROTATION>)
                }
                """.replaceAll("<ROTATION>", rotation.name());
        String response = sendRequest(query);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.getAsJsonObject("data").get("nextRotation").getAsString();
    }

    /**
     * Gets a player either by its name or uuid.
     * @param nameOrUUID The name or uuid.
     * @return A complete player object.
     */
    public Player getPlayer(String nameOrUUID) {
        String query = """
                query {
                  <TYPE> {
                    uuid
                    username
                    ranks
                    collections {
                      currency {
                        coins
                        gems
                        materialDust
                        royalReputation
                        silver
                      }
                    }
                    crownLevel {
                      level
                      nextEvolutionLevel
                      trophies {
                        obtained
                        obtainable
                        bonus
                      }
                      nextLevelProgress {
                        obtained
                        obtainable
                      }
                    }
                    social {
                      party {
                        active
                        leader {
                          username
                          uuid
                        }
                        members {
                          username
                          uuid
                        }
                      }
                      friends {
                        username
                        uuid
                      }
                    }
                    status {
                      firstJoin
                      lastJoin
                      online
                      server {
                        subType
                        category
                        associatedGame
                      }
                    }
                  }
                }
                """.replaceAll("<TYPE>", nameOrUUID.length() > 16 ? "player(uuid: \""+nameOrUUID+"\")" : "playerByUsername(username: \""+nameOrUUID+"\")");
        String response = sendRequest(query);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        if (!jsonObject.getAsJsonObject("data").has(nameOrUUID.length() > 16 ? "player" : "playerByUsername")) {
            System.out.println(jsonObject);
            return null;
        }

        JsonObject playerObject = jsonObject.getAsJsonObject("data").getAsJsonObject(nameOrUUID.length() > 16 ? "player" : "playerByUsername");

        Integer currencyCoins = null;
        Integer currencyGems = null;
        Integer currencyMaterialDust = null;
        Integer currencyRoyalReputation = null;
        Integer currencySilver = null;

        Integer crownLevelLevel = null;
        Integer crownNextEvolutionLevel = null;
        Integer trophiesObtained = null;
        Integer trophiesObtainable = null;
        Integer trophiesBonus = null;
        Integer nextObtained = null;
        Integer nextObtainable = null;

        Boolean partyActive = null;
        UnparsedPlayer leader = null;
        List<UnparsedPlayer> members = new ArrayList<>();
        List<UnparsedPlayer> friends = new ArrayList<>();

        String firstJoin = null;
        String lastJoin = null;
        Boolean online = null;
        String serverSubType = null;
        ServerCategory serverCategory = null;
        Game associatedGame = null;

        Collections collections = null;
        CrownLevel crownLevel = null;
        List<Rank> ranks = new ArrayList<>();
        Social social = null;
        Status status = null;
        String username = null;
        String uuid = null;

        if (playerObject.has("uuid")) uuid = playerObject
                .get("uuid").getAsString();
        if (playerObject.has("username")) username = playerObject
                .get("username").getAsString();
        if (playerObject.has("ranks")) {
            JsonArray ranksArray = playerObject.getAsJsonArray("ranks");
            for (int i = 0; i < ranksArray.size(); i++) {
                ranks.add(Rank.valueOf(ranksArray.get(i).getAsString()));
            }
        }
        if (playerObject.has("collections")) {
            JsonObject collectionsObject = playerObject.getAsJsonObject("collections");
            if (collectionsObject.has("currency")) {
                JsonObject currencyObject = collectionsObject.getAsJsonObject("currency");
                if (currencyObject.has("coins")) currencyCoins = currencyObject
                        .get("coins").getAsInt();
                if (currencyObject.has("gems")) currencyGems = currencyObject
                        .get("gems").getAsInt();
                if (currencyObject.has("materialDust")) currencyMaterialDust = currencyObject
                        .get("materialDust").getAsInt();
                if (currencyObject.has("royalReputation")) currencyRoyalReputation = currencyObject
                        .get("royalReputation").getAsInt();
                if (currencyObject.has("silver")) currencySilver = currencyObject
                        .get("silver").getAsInt();
            }
            collections = new Collections(new Currency(currencyCoins, currencyGems, currencyMaterialDust, currencyRoyalReputation, currencySilver));
        }
        if (playerObject.has("crownLevel")) {
            JsonObject crownObject = playerObject.getAsJsonObject("crownLevel");
            if (crownObject.has("level")) crownLevelLevel = crownObject
                    .get("level").getAsInt();
            if (crownObject.has("nextEvolutionLevel")) crownNextEvolutionLevel = crownObject
                    .get("nextEvolutionLevel").getAsInt();
            if (crownObject.has("nextLevelProgress")) {
                JsonObject nextObject = crownObject.getAsJsonObject("nextLevelProgress");
                if (nextObject.has("obtained")) nextObtained = nextObject
                        .get("obtained").getAsInt();
                if (nextObject.has("obtainable")) nextObtainable = nextObject
                        .get("obtainable").getAsInt();
            }
            if (crownObject.has("trophies")) {
                JsonObject trophiesObject = crownObject.getAsJsonObject("trophies");
                if (trophiesObject.has("obtained")) trophiesObtained = trophiesObject
                        .get("obtained").getAsInt();
                if (trophiesObject.has("obtainable")) trophiesObtainable = trophiesObject
                        .get("obtainable").getAsInt();
                if (trophiesObject.has("bonus")) trophiesBonus = trophiesObject
                        .get("bonus").getAsInt();
            }
            crownLevel = new CrownLevel(
                    crownLevelLevel,
                    crownNextEvolutionLevel,
                    new ProgressionData(nextObtained, nextObtainable),
                    new TrophyData(trophiesBonus, trophiesObtainable, trophiesObtained)
            );
        }
        if (playerObject.has("social")) {
            JsonObject socialObject = playerObject.getAsJsonObject("social");
            if (socialObject.has("party")) {
                JsonObject partyObject = socialObject.getAsJsonObject("party");
                if (partyObject.has("active")) partyActive = partyObject
                        .get("active").getAsBoolean();
                if (partyObject.has("leader")) {
                    JsonObject leaderObject = partyObject.getAsJsonObject("leader");
                    leader = new UnparsedPlayer(leaderObject.get("username").getAsString(), leaderObject.get("uuid").getAsString());
                }
                if (partyObject.has("members")) {
                    JsonArray membersObject = partyObject.getAsJsonArray("members");
                    for (int i = 0; i < membersObject.size(); i++) {
                        JsonObject member = membersObject.get(i).getAsJsonObject();
                        members.add(new UnparsedPlayer(member.get("username").getAsString(), member.get("uuid").getAsString()));
                    }
                }
            }
            if (socialObject.has("friends")) {
                JsonArray friendsObject = socialObject.getAsJsonArray("friends");
                for (int i = 0; i < friendsObject.size(); i++) {
                    JsonObject friend = friendsObject.get(i).getAsJsonObject();
                    members.add(new UnparsedPlayer(friend.get("username").getAsString(), friend.get("uuid").getAsString()));
                }
            }
            social = new Social(friends, new Party(partyActive, leader, members));
        }
        if (playerObject.has("status")) {
            JsonObject statusObject = playerObject.getAsJsonObject("status");
            if (statusObject.has("firstJoin")) firstJoin = statusObject
                    .get("firstJoin").getAsString();
            if (statusObject.has("lastJoin")) lastJoin = statusObject
                    .get("lastJoin").getAsString();
            if (statusObject.has("online")) online = statusObject
                    .get("online").getAsBoolean();
            if (statusObject.has("server")) {
                JsonObject serverObject = statusObject.getAsJsonObject("server");
                if (serverObject.has("subType")) serverSubType = serverObject
                        .get("subType").getAsString();
                if (serverObject.has("category")) serverCategory = ServerCategory.valueOf(
                        serverObject.get("category").getAsString());
                if (serverObject.has("associatedGame")) associatedGame = Game.valueOf(
                        serverObject.get("associatedGame").getAsString());
            }
            status = new Status(firstJoin, lastJoin, online, new Server(associatedGame, serverCategory, serverSubType));
        }
        return new Player(collections, crownLevel, ranks, social, status, username, uuid);
    }

    /**
     * Gets a player either by an unparsed player object.
     * @param player The unparsed player.
     * @return A complete player object.
     */
    public Player getPlayer(UnparsedPlayer player) {
        return getPlayer(player.getUuid());
    }

    /**
     * Gets the previous rotation cycle.
     * @param rotation The unparsed player.
     * @return The previous rotation cycle
     */
    public String getPreviousRotation(Rotation rotation) {
        String query = """
                query {
                  previousRotation(rotation: <ROTATION>)
                }
                """.replaceAll("<ROTATION>", rotation.name());
        String response = sendRequest(query);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.getAsJsonObject("data").get("previousRotation").getAsString();
    }

    /**
     * Gets a statistic by its key.
     * @param setKey The statistic key.
     * @return The statistic.
     */
    public Statistic getStatistic(String setKey) {
        String query = """
                {
                  statistic(key: "<KEY>") {
                    rotations
                    forLeaderboard
                    key
                    leaderboard {
                      player {
                        username
                        uuid
                      }
                      rank
                      value
                    }
                  }
                }
                """.replaceAll("<KEY>", setKey);
        String response = sendRequest(query);
        JsonObject statisticObject = JsonParser.parseString(response).getAsJsonObject()
                .getAsJsonObject("data")
                .getAsJsonObject("statistic");
        if (statisticObject == null) {
            return null;
        }
        Boolean forLeaderboard = null;
        String key = null;
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        List<Rotation> rotations = new ArrayList<>();
        if (statisticObject.has("rotations")) {
            JsonArray rotationsObject = statisticObject.getAsJsonArray("rotations");
            for (int i = 0; i < rotationsObject.size(); i++) {
                rotations.add(Rotation.valueOf(rotationsObject.get(i).getAsString()));
            }
        }
        if (statisticObject.has("forLeaderboard")) forLeaderboard = statisticObject
                .get("forLeaderboard").getAsBoolean();
        if (statisticObject.has("key")) key = statisticObject
                .get("key").getAsString();
        if (statisticObject.has("leaderboard")) {
            JsonArray leaderboardObject = statisticObject.getAsJsonArray("leaderboard");
            for (int i = 0; i < leaderboardObject.size(); i++) {
                JsonObject leaderboardEntryObject = leaderboardObject.get(i).getAsJsonObject();
                Integer rank = null;
                Integer value = null;
                UnparsedPlayer player = null;

                if (leaderboardEntryObject.has("rank")) rank = leaderboardEntryObject
                        .get("rank").getAsInt();
                if (leaderboardEntryObject.has("value")) value = leaderboardEntryObject
                        .get("value").getAsInt();
                if (leaderboardEntryObject.has("player")) {
                    JsonObject playerObject = leaderboardEntryObject.getAsJsonObject("player");
                    player = new UnparsedPlayer(
                            playerObject.get("username").getAsString(),
                            playerObject.get("uuid").getAsString());
                }
                leaderboardEntries.add(new LeaderboardEntry(player, rank, value));
            }
        }
        return new Statistic(forLeaderboard, key, leaderboardEntries, rotations);
    }

    /**
     * Gets all the statistics.
     * @return All available statistics.
     */
    public List<Statistic> getStatistics() {
        String query = """
                {
                  statistics {
                    forLeaderboard
                    key
                    leaderboard {
                      rank
                      value
                      player {
                        username
                        uuid
                      }
                    }
                    rotations
                  }
                }
                """;
        String response = sendRequest(query);
        JsonArray statisticArray = JsonParser.parseString(response).getAsJsonObject()
                .getAsJsonObject("data")
                .getAsJsonArray("statistics");
        if (statisticArray == null) {
            return null;
        }
        List<Statistic> statistics = new ArrayList<>();
        for (int i = 0; i < statisticArray.size(); i++) {
            JsonObject statisticObject = statisticArray.get(i).getAsJsonObject();
            Boolean forLeaderboard = null;
            String key = null;
            List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
            List<Rotation> rotations = new ArrayList<>();
            if (statisticObject.has("rotations")) {
                JsonArray rotationsObject = statisticObject.getAsJsonArray("rotations");
                for (int ii = 0; ii < rotationsObject.size(); ii++) {
                    rotations.add(Rotation.valueOf(rotationsObject.get(ii).getAsString()));
                }
            }
            if (statisticObject.has("forLeaderboard")) forLeaderboard = statisticObject
                    .get("forLeaderboard").getAsBoolean();
            if (statisticObject.has("key")) key = statisticObject
                    .get("key").getAsString();
            if (statisticObject.has("leaderboard")) {
                JsonArray leaderboardObject = statisticObject.getAsJsonArray("leaderboard");
                for (int ii = 0; ii < leaderboardObject.size(); ii++) {
                    JsonObject leaderboardEntryObject = leaderboardObject.get(ii).getAsJsonObject();
                    Integer rank = null;
                    Integer value = null;
                    UnparsedPlayer player = null;

                    if (leaderboardEntryObject.has("rank")) rank = leaderboardEntryObject
                            .get("rank").getAsInt();
                    if (leaderboardEntryObject.has("value")) value = leaderboardEntryObject
                            .get("value").getAsInt();
                    if (leaderboardEntryObject.has("player")) {
                        JsonObject playerObject = leaderboardEntryObject.getAsJsonObject("player");
                        player = new UnparsedPlayer(
                                playerObject.get("username").getAsString(),
                                playerObject.get("uuid").getAsString());
                    }
                    leaderboardEntries.add(new LeaderboardEntry(player, rank, value));
                }
            }
            statistics.add(new Statistic(forLeaderboard, key, leaderboardEntries, rotations));
        }
        return statistics;
    }


    // ASYNC -------


    /**
     * Gets the next rotation cycle asynchronously.
     * @param rotation Rotation cycle of the day.
     * @return The next rotation cycle.
     */
    public CompletableFuture<String> getNextRotationAsync(Rotation rotation) {
        return CompletableFuture.supplyAsync(() -> getNextRotation(rotation));
    }

    /**
     * Gets a player either by its name or uuid asynchronously.
     * @param nameOrUUID The name or uuid.
     * @return A complete player object.
     */
    public CompletableFuture<Player> getPlayerAsync(String nameOrUUID) {
        return CompletableFuture.supplyAsync(() -> getPlayer(nameOrUUID));
    }

    /**
     * Gets a player either by an unparsed player object asynchronously.
     * @param player The unparsed player.
     * @return A complete player object.
     */
    public CompletableFuture<Player> getPlayerAsync(UnparsedPlayer player) {
        return CompletableFuture.supplyAsync(() -> getPlayer(player));
    }

    /**
     * Gets the previous rotation cycle asynchronously.
     * @param rotation The unparsed player.
     * @return The previous rotation cycle
     */
    public CompletableFuture<String> getPreviousRotationAsync(Rotation rotation) {
        return CompletableFuture.supplyAsync(() -> getPreviousRotation(rotation));
    }

    /**
     * Gets a statistic by its key asynchronously.
     * @param setKey The statistic key.
     * @return The statistic.
     */
    public CompletableFuture<Statistic> getStatisticAsync(String setKey) {
        return CompletableFuture.supplyAsync(() -> getStatistic(setKey));
    }

    /**
     * Gets all the statistics asynchronously.
     * @return All available statistics.
     */
    public CompletableFuture<List<Statistic>> getStatisticsAsync() {
        return CompletableFuture.supplyAsync(this::getStatistics);
    }



}
