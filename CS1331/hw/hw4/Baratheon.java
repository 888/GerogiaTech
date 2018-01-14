/**
* This class represents a Baratheon object.
* @author Noam Lerner
* @version 1.0
*/
import java.awt.Rectangle;
import javax.swing.ImageIcon;
public class Baratheon extends SouthHouse {
    /**
     * Initializes a new house SouthHouse object of the type Baratheon.
     * @param  x x-location on screen
     * @param  y y-location on screen
     * @param  bounds - bounds of the screen
     * @return
     */
    public Baratheon(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        this.image = new ImageIcon("stag.png");
        this.canReproduce[this.LANNISTER] = true;
        this.canHarm[this.TARGARYAN] = true;
        this.chanceToHit[this.TULLY] = 100;
        this.family = this.BARATHEON;
    }
    /**
     * If the otherHouse and this house can reproduce, might reproduce (0.009
     * chance)
     * @param  otherHouse
     * @return either a Baratheon House object if reproduction was succesful
     * or null
     */
    protected House reproduceWithHouse(House otherHouse) {
        if (!reproduced && canReproduceWithHouse(otherHouse)) {
            if (Math.random() < 0.009) {
                return new Baratheon(this.xPos, this.yPos, this.bounds);
            }
        }
        return null;
    }
}