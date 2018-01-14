/**
 * Secret Mission
 *
 * you may ADD to this class header, but do not change the
 * visibility or class name
 */
public class SecretMission extends Mission {

    public static final String CLASSIFIED_STRING = "This information "
        + "is Classified.";
    private SecurityClearance clearance;
    private boolean locked;

    public SecretMission(String title, String description,
        SecurityClearance clearance) {
        super(title, description);
        locked = true;
        this.clearance = clearance;
    }

    public void setDescription(String description) {
        if (!locked) {
            this.description = description;
        }
    }

    public String toString() {
        if (locked) {
            return CLASSIFIED_STRING;
        } else {
            return super.toString();
        }
    }

    /**
     * unlocks the mission's info, given some security clearance
     * should check the given clearance against the mission's clearance and
     * throw an AccessDeniedException if it is insufficient clearance.
     *
     * You may (must) ADD to this method header, but do not change the
     * visibility, return type, or method name.
     */
    public void unlockInfo(SecurityClearance clearance)
        throws AccessDeniedException {
        if (this.clearance.ordinal() <= clearance.ordinal()) {
            this.locked = false;
        } else {
            throw new AccessDeniedException(this.clearance);
        }
    }


    /*
        HINT
        the .ordinal() method on an enum value gives you its position relative
        to the other values.
        For example, you can compare like so:
        someClearance.ordinal() < anotherClearance.ordinal()
        someClearance.ordinal() >= anotherClearance.ordinal()
        someClearance.ordinal() == anotherClearance.ordinal()

        e.g, SecurityClearance.CONFIDENTIAL.ordinal() > SecurityClearance.
            SECRET.ordinal() would return true
    */
}
