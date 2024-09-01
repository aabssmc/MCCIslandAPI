package aabss.mcciapi.objects;

import java.util.List;

/**
 * Social data.
 */
public record Social(List<UnparsedPlayer> friends, Party party) {

    /**
     * @return A list of the player's friends.
     */
    @Override
    public List<UnparsedPlayer> friends() {
        return friends;
    }

    /**
     * @return The player's party.
     */
    @Override
    public Party party() {
        return party;
    }

    @Override
    public String toString() {
        return "Social{" +
                "friends=" + friends +
                ", party=" + party +
                '}';
    }
}
