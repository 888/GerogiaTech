/**
* This class represents a Stark object.
* @author Noam Lerner
* @version 1.0
*/
import java.awt.Rectangle;
import javax.swing.ImageIcon;
public class Tully extends NorthHouse {
    /**
     * Initializes a new house House object of the type Tully.
     * @param  x x-location on screen
     * @param  y y-location on screen
     * @param  bounds - bounds of the screen
     * @return
     */
    public Tully(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        this.image = new ImageIcon("trout.png");
        this.canReproduce[this.STARK] = true;
        this.maxAge = this.NORM_MAX_AGE + 50;
        this.canHarm[this.LANNISTER] = true;
        this.family = this.TULLY;
        this.chanceToHit[this.LANNISTER] = 20;
    }
    /**
     * If the otherHouse and this house can reproduce, might reproduce (0.009
     * chance)
     * @param  otherHouse
     * @return either a Tully House object if reproduction was succesful
     * or null
     */
    protected House reproduceWithHouse(House otherHouse) {
        if (!reproduced && canReproduceWithHouse(otherHouse)) {
            if (Math.random() < 0.009) {
                return new Tully(this.xPos, this.yPos, this.bounds);
            }
        }
        return null;
    }
}