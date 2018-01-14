import java.util.Scanner;
public class TimedLab {
    public static int superSum(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
            }
        }
        return sum;
    }

    public static int hotAndCold(int secretNum) {
        int guesses = 0;
        int guess = secretNum + 1;
        System.out.println("Guess my number!");
        while (guess != secretNum) {
            Scanner sc = new Scanner(System.in);
            guess = sc.nextInt();
            if (guess < secretNum) {
                System.out.println("Too Low");
            } else if (guess > secretNum) {
                System.out.println("Too High");
            }
            guesses++;
        }
        System.out.println("Correct!");
        return guesses;
    }
}
