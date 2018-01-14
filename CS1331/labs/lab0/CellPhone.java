import java.util.Random;
public class CellPhone {
    private String nameOfPhone;
    private String nameOfOwner;
    private double mbRate;
    private double usedMB = 0;

    public CellPhone(String ownerName, String phoneName, double mbUsed) {
        nameOfPhone = phoneName;
        nameOfOwner = ownerName;
        mbRate = mbUsed;
    }

    public double getMB() {
        return usedMB;
    }

    public void goOnReddit() {
        Random rand = new Random();
        usedMB += rand.nextInt(20) + 1;
    }

    public double getBill() {
        return usedMB * mbRate;
    }

    public String toString() {
        return nameOfPhone + " owned by " + nameOfOwner
            + " with " + usedMB + "MB used this month.";
    }
}
