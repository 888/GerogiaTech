public class ArrayUtils {
    public static int reverseProduct(int [] a, int [] b) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[b.length - i - 1];
        }
        return sum;
    }
}