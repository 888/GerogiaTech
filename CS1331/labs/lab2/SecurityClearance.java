/**
 * This enumerated type represents a level of clearance
 * You'll use it for your SecretMission class
 *
 * You do not need to change this code
 *
 * @author Thomas Shields
 * @version 1.0
 */
public enum SecurityClearance {
    NONE, CONFIDENTIAL, SECRET, TOP_SECRET;

    /**
     * Use this method to get a "pretty" representation of a clearance.
     * E.g, SecurityClearance.CONFIDENTIAL.toString() would give "Confidential"
     * @return a pretty string
     */
    public String toString() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
};