import java.util.Queue;
import java.util.Iterator;
import java.util.Set;
public abstract class AbstractPizzeria implements Pizzeria {
    protected Set<Order> menu;
    protected Queue<Customer> queue;
    protected int ordersRecieved;
    protected int deliveryAttempts;
    protected double revenue;
    protected int delivered;
    protected String name;

    /**
     * Constructor for a Pizzeria that takes the menu in
     * @param  menu Today's pizzas
     * @return
     */
    public AbstractPizzeria(Set<Order> menu) {
        this.menu = menu;
    }

    /**
     * Tells the pizzeria to place an order with a Customer
     *
     * @param customer Customer who wants a pizza
     */
    public void placeOrder(Customer customer) {
        queue.add(customer);
        ordersRecieved++;
    }

    /**
     * Returns the cheapest menu item based on price
     *
     * @return cheapest menu item
     */
    public Order getCheapestMenuItem() {
        Iterator<Order> iterator = menu.iterator();
        Order cheapestItem = iterator.next();
        while (iterator.hasNext()) {
            Order nextOrder = iterator.next();
            if (nextOrder.getPrice() < cheapestItem.getPrice()) {
                cheapestItem = nextOrder;
            }
        }
        return cheapestItem;
    }

    /**
     * Returns the most expensive menu item based on price
     *
     * @return most expensive menu item
     */
    public Order getMostExpensiveMenuItem() {
        Iterator<Order> iterator = menu.iterator();
        Order expensiveItem = iterator.next();
        while (iterator.hasNext()) {
            Order nextOrder = iterator.next();
            if (nextOrder.getPrice() > expensiveItem.getPrice()) {
                expensiveItem = nextOrder;
            }
        }
        return expensiveItem;
    }

    /**
     * Pizzeria name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Attempts to deliver an order to a customer.
     * If the customer placed an order that's not on the menu,
     * we don't deliver. If the customer placed an order that is
     * on the menu but he doesn't have enough money to pay,
     * we don't deliver. Otherwise, we deliver to customer
     * and collect our money.
     */
    public void processOrder() {
        Customer customer = queue.poll();
        if (customer != null) {
            deliveryAttempts++;
            Order order = customer.getOrder();
            if (onMenu(order)) {
                if (customer.canPay()) {
                    delivered++;
                    revenue += order.getPrice();
                }
            }
        }
    }
    /**
     * Takes in order and checks to see that its on the menu
     * @param  order
     * @return       true if on menu
     */
    private boolean onMenu(Order order) {
        Iterator<Order> iterator = menu.iterator();
        while (iterator.hasNext()) {
            if (order.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Status message of a Pizzeria's performance
     * Should say what percentage of orders have been delivered
     * and what percentage of attempted orders have been delivered
     * along with total revenues
     *
     * @return Message string
     */
    public String status() {
        int totalPercent = (int) ((double) delivered / ordersRecieved * 100);
        int percentDelivered = (int) ((double) delivered
            / deliveryAttempts * 100);
        return this.name + "\n -We delivered " + totalPercent
            + "% of our orders! We delivered " + percentDelivered
            + "% of our attempted orders and made $" + revenue;
    }
}