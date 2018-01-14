public class House {
    private String homeOwner;
    private int bedrooms;
    private int bathrooms;
    private boolean garage;
    private static int houses;

    public House(String ownerName, int numBedrooms, int numBathrooms,
         boolean garagePresent) {
        if (ownerName == null ||  ownerName.replace(" ", "").equals("")) {
            homeOwner = "HOMEOWNER";
        } else {
            homeOwner = ownerName;
        }
        bedrooms = numBedrooms;
        bathrooms = numBathrooms;
        garage = garagePresent;
        houses++;
    }

    public String getOwner() {
        return homeOwner;
    }

    public void setOwner(String ownerName) {
        if (ownerName == null || ownerName.replace(" ", "").equals("")) {
            homeOwner = "HOMEOWNER";
        } else {
            homeOwner = ownerName;
        }
    }

    public void buildBedroom() {
        bedrooms++;
    }

    public String toString() {
        return "House owned by " + homeOwner + ", " + bedrooms + "bed/"
            + bathrooms + "bath, garage: " + Boolean.toString(garage)
            + ", total houses:" + houses;
    }
}