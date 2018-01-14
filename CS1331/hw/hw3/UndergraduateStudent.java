public class UndergraduateStudent extends Student {
    public UndergraduateStudent(String firstName, String lastName,
        int intelligence, double motiation) {
        super(firstName, lastName, intelligence, (int) motiation);
    }
    public String toString() {
        return "Hi, my name is " + firstName + " " + lastName
            + ". My intelligence is " + intelligence + "/10 and my "
            + "motivation is " + motivation + "/10. I'm going home this weekend"
            + " to get laundry done;" + " talk about clutch.";
    }
}