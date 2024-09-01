package aabss.mcciapi.objects;

import java.util.List;

/**
 * A player's status within a party.
 */
public record Party(Boolean active, UnparsedPlayer leader, List<UnparsedPlayer> members) {

    /**
     * @return Whether the player is in an active party.
     */
    @Override
    public Boolean active() {
        return active;
    }

    /**
     * @return The leader of the party, populated if the party exists.
     */
    @Override
    public UnparsedPlayer leader() {
        return leader;
    }

    /**
     * @return The members of the party, populated if the party exists.
     */
    @Override
    public List<UnparsedPlayer> members() {
        return members;
    }

    @Override
    public String toString() {
        return "Party{" +
                "active=" + active +
                ", leader=" + leader +
                ", members=" + members +
                '}';
    }
}
