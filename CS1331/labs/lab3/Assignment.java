import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

/**
 * See PDF for instructions.
 *
 * @author Noam Lerner
 * @version 6:00-7:30 Section
 */
public class Assignment implements Comparable<Assignment> {

    /* Instance variables. DO NOT MODIFY */
    private String title;
    private Category category;
    private int maxGrade;

    /** Provided Constructor. DO NOT MODIFY */
    public Assignment(String title, Category category, int maxGrade) {
        this.title = title;
        this.category = category;
        this.maxGrade = maxGrade;
    }

    public boolean equals(Object other) {
        if (other != null && other instanceof Assignment) {
            Assignment otherA = (Assignment) other;
            if (otherA.getTitle() == this.getTitle()
                && otherA.getCategory() == this.getCategory()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.getTitle().hashCode() + this.category.ordinal();
    }

    public int compareTo(Assignment other) {
        if (this.getCategory() != other.getCategory()) {
            return this.getCategory().ordinal() - other.getCategory().ordinal();
        } else {
            return this.getTitle().compareTo(other.getTitle());
        }
    }

    /*--------------------------------------*/
    /* Provided getters and toString method */
    /*            DO NOT MODIFY             */
    /*--------------------------------------*/

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-10s", title, category);
    }

    /**
     * Provided main method for testing.
     * Feel free to add your own assignments.
     */
    public static void main(String[] args) {
        // Homework
        Assignment hw0 = new Assignment("Homework 0", Category.HOMEWORK, 100);
        Assignment hw1 = new Assignment("Homework 1", Category.HOMEWORK, 100);
        Assignment hw2 = new Assignment("Homework 2", Category.HOMEWORK, 100);

        // Exams
        Assignment exam1 = new Assignment("Exam 1", Category.EXAM, 110);
        Assignment exam2 = new Assignment("Exam 2", Category.EXAM, 110);
        Assignment exam3 = new Assignment("Exam 3", Category.EXAM, 110);

        // Timed Labs
        Assignment lab1 = new Assignment("Lab 1", Category.LAB, 100);
        Assignment lab2 = new Assignment("Lab 2", Category.LAB, 100);

        // All Assignments
        Set<Assignment> assignments = new TreeSet<>();
        assignments.add(hw1);
        assignments.add(exam1);
        assignments.add(lab2);
        assignments.add(hw0);
        assignments.add(exam2);
        assignments.add(exam3);
        assignments.add(lab1);
        assignments.add(hw2);

        // Markbook
        Map<Assignment, Integer> markbook = new HashMap<>();
        markbook.put(hw0, 54);
        markbook.put(hw1, 12);
        markbook.put(hw2, 46);
        markbook.put(exam1, 89);
        markbook.put(exam2, 97);
        markbook.put(exam3, 14);
        markbook.put(lab1, 25);
        markbook.put(lab2, 91);

        // Well behaved 1
        assert assignments.contains(new Assignment("Lab 1", Category.LAB, 0));

        // Well behaved 2
        assert markbook.get(new Assignment("Lab 1", Category.LAB, 0)) == 25;

        // Natural Order
        assert assignments.toArray()[0] == hw0;

        // Print out map in order
        assignments.forEach(a -> System.out.println(a.toString()
                    + markbook.get(a)));
    }
}
