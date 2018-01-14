/**
* This is an abstract class which defines a NorthHouse.
* @author Noam Lerner
* @version 1.0
*/
import java.awt.Rectangle;
public abstract class NorthHouse extends House {
   /**
     * Constructor for the abstract NorthHouse class, sets the x position, y
     * position
     * @param  x - x location on map
     * @param  y - y location on map
     * @param  bounds - bounds of map
     * @return
     */
    public NorthHouse(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
    }
    /**
     * Movement effects for a NorthHouse, increases health if in the north.
     */
    protected void movementEffects() {
        if (this.yPos < this.bounds.height / 2) {
            health += 5;
        } else {
            health -= 5;
        }
        age++;
    }
}