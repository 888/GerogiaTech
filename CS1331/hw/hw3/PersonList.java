public class PersonList {
    protected Person [] people;
    protected int count = 0;

    PersonList(int maxSize) {
        people = new Person[maxSize];
    }

    public void listPeople() {
        for (int i = 0; i < count; i++) {
            System.out.println(people[i].toString());
        }
    }

    public void add(Person p) {
        if (count < people.length) {
            people[count] = p;
            count++;
        }
    }
}