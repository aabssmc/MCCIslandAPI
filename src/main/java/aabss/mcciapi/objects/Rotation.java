package aabss.mcciapi.objects;

/**
 * A rotation period.
 * Each period resets at 10AM UTC.
 */
public enum Rotation {
    /**
     * A daily rotation that resets.
     */
    DAILY,
    /**
     * A lifetime rotation; a rotation period used to indicate something never rotates.
     */
    LIFETIME,
    /**
     * A monthly rotation that resets on the first day of every month.
     */
    MONTHLY,
    /**
     * A weekly rotation that resets on Tuesdays.
     */
    WEEKLY,
    /**
     * A yearly rotation that resets on the first day of every year.
     */
    YEARLY
}
