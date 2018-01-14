import java.util.HashSet;
import java.util.Iterator;
public class Order extends HashSet<Ingredient> {
    /**
     * Returns the price of the order
     * @return price
     */
    public int getPrice() {
        Iterator<Ingredient> iterator = this.iterator();
        int price = 0;
        while (iterator.hasNext()) {
            price += iterator.next().getPrice();
        }
        return price;
    }
    /**
     * Checks the ignredients of  the other order and says if they are all
     * contained in this order
     * @param  other order to be checked
     * @return boolean true if they are the same
     */
    public boolean equals(Object other) {
        if (other != null) {
            if (other instanceof Order) {
                Iterator<Ingredient> iterator = ((Order) other).iterator();
                while (iterator.hasNext()) {
                    if (!this.contains(iterator.next())) {
                        return false;
                    }
                }
                iterator = this.iterator();
                while (iterator.hasNext()) {
                    if (!((Order) other).contains(iterator.next())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}