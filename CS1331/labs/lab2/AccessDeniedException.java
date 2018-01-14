/**
 * AccessDeniedException
 *
 * you may ADD to this class header, but do not change the
 * visibility or class name
 */
public class AccessDeniedException extends Exception {
    private SecurityClearance requiredClearance;

    public AccessDeniedException(SecurityClearance requiredClearance) {
        this.requiredClearance = requiredClearance;
    }

    /**
     * this is here so we can test your code; no need to change it
     * @return clearance that was needed to access the mission that threw
     *         the exception.
     */
    public SecurityClearance getRequiredClearance() {
        return requiredClearance;
    }

    public String getMessage() {
        return "This mission requires a clearance of "
            + this.requiredClearance.toString();
    }
}
