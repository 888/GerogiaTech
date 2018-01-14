import java.util.Set;
import java.util.PriorityQueue;
public class MarySuePizza extends AbstractPizzeria {
    /**
	 * Constructs a Mary Sue Pizza store which Delivers orders based on their price
	 * @param  menu Today's menu
	 * @return
	 */
    public MarySuePizza(Set<Order> menu) {
        super(menu);
        this.name = "Marry Sue Pizza";
        this.queue = new PriorityQueue<Customer>((Customer c1, Customer c2) ->
            c1.getOrderPrice() - c2.getOrderPrice());
    }
}