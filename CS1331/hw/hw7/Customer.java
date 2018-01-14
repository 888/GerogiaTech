public class Customer implements Comparable<Customer> {
    protected int money;
    protected Order order;
    public Customer(Order order) {
        while (money > 34 || money < 5) {
            money = (int) (Math.random() * 100);
        }
        this.order = order;
    }

    public boolean canPay() {
        return money > order.getPrice();
    }

    public int getOrderPrice() {
        return order.getPrice();
    }

    public int getMoney() {
        return money;
    }

    public Order getOrder() {
        return order;
    }

    public int compareTo(Customer other) {
        return this.getOrderPrice() - other.getOrderPrice();
    }
}