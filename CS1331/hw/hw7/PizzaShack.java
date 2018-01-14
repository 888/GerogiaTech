import java.util.Set;
import java.util.PriorityQueue;
public class PizzaShack extends AbstractPizzeria {
    /**
     * Constructs a  Pizza Shack store which Delivers orders based on their
     * price and the customers ability to pay.
     * @param  menu Today's menu
     * @return
     */
    public PizzaShack(Set<Order> menu) {
        super(menu);
        this.name = "Pizza Shack";
        this.queue = new PriorityQueue<Customer>((Customer c1, Customer c2) ->
            {
                if (c1.canPay() && !c2.canPay()) {
                    return 1;
                }
                if (!c1.canPay() && c2.canPay()) {
                    return -1;
                }
                return c1.getOrderPrice() - c2.getOrderPrice();
            });
    }
}