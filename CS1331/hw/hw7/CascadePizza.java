import java.util.Set;
import java.util.ArrayDeque;
public class CascadePizza extends AbstractPizzeria {
	/**
	 * Constructs a Cascade Pizza store which Delivers orders as they are recieved
	 * @param  menu Today's menu
	 * @return
	 */
    public CascadePizza(Set<Order> menu) {
        super(menu);
        this.name = "Cascade Pizza";
        this.queue = new ArrayDeque();
    }
}