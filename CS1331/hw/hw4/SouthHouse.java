/**
* This is an abstract class which defines a NorthHouse.
* @author Noam Lerner
* @version 1.0
*/
import java.awt.Rectangle;
public abstract class SouthHouse extends House {
    protected int normalSpeed;
    /**
     * Constructor for the abstract SouthHouse class, sets the x position, y
     * position
     * @param  x - x location on map
     * @param  y - y location on map
     * @param  bounds - bounds of map
     * @return
     */
    public SouthHouse(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        normalSpeed = this.speed;
    }
    /**
     * Movement effects for the SouthHouse, speed is increased in the south
     */
    protected void movementEffects() {
        if (this.yPos > this.bounds.height / 2) {
            speed = normalSpeed + 10;
        } else {
            speed = normalSpeed;
        }
        age++;
    }
}