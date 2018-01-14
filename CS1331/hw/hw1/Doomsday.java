import java.io.Console;
public class Doomsday {
    private static int [] monthDay = {3, 28, 7, 4, 9, 6, 11, 8, 5, 10, 7, 12};
    private static String [] daysOfWeek = {"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};
    public static void main(String [] args) {
        System.out.println("Welcome to the doomsday calculator!\n");
        System.out.print("What year are you looking for? ");
        Console console = System.console();
        int year = Integer.parseInt(console.readLine().substring(2));
        System.out.print("What month?(1-12) ");
        int month = Integer.parseInt(console.readLine());
        System.out.print("What day? ");
        int day = Integer.parseInt(console.readLine());
        System.out.println(month + "/" + day + "/" + year + " was on a "
            + getDayOfWeek(year, month - 1, day));
    }
    public static String getDayOfWeek(int year, int month, int day) {
        int doomsday = ((year / 12) + (year % 12) + ((year % 12) / 4) + 3) % 7;
        if (year % 4 == 0 && (month == 0 || month == 1)) {
            //accounts for leapyears if the month is jan/feb
            day--;
        }
        int dayOfWeek = (day - monthDay[month]) % 7 + doomsday;
        //Days over 6 or under 0 have feelings too!
        while (dayOfWeek <  0 || dayOfWeek > 7) {
            dayOfWeek = dayOfWeek > 6 ? dayOfWeek - 7 : dayOfWeek < 0 ? 7
                + dayOfWeek : dayOfWeek;
        }
        return daysOfWeek[dayOfWeek];
    }
}