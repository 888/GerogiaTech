/**
 * Mission
 *
 * you may ADD to this class header, but do not change the
 * visibility or class name
 */
public class Mission {
    private String title;
    protected String description;

    public Mission(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return String.format(
            "%s\n--------------------------------------\n\n%s",
            title, description);
    }
}
