/**
* This class represents a Lannister  object.
* @author Noam Lerner
* @version 1.0
*/
import java.awt.Rectangle;
import javax.swing.ImageIcon;
public class Lannister extends SouthHouse {
    /**
     * Initializes a new house SouthHouse object of the type Lannister.
     * @param  x x-location on screen
     * @param  y y-location on screen
     * @param  bounds - bounds of the screen
     * @return
     */
    public Lannister(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        this.image = new ImageIcon("lion.png");
        this.canReproduce[this.LANNISTER] = true;
        this.canReproduce[this.BARATHEON] = true;
        this.canHarm[this.TULLY] = true;
        this.canHarm[this.STARK] = true;
        this.chanceToHit[this.TULLY] = 80;
        this.chanceToHit[this.STARK] = 60;
        this.maxAge = this.NORM_MAX_AGE - 10;
        this.family = this.LANNISTER;
    }
    /**
     * If the otherHouse and this house can reproduce, might reproduce (0.009
     * chance)
     * @param  otherHouse
     * @return either a Lannister House object if reproduction was succesful
     * or null
     */
    protected House reproduceWithHouse(House otherHouse) {
        if (!reproduced && canReproduceWithHouse(otherHouse)) {
            if (Math.random() < 0.009) {
                return new Lannister(this.xPos, this.yPos, this.bounds);
            }
        }
        return null;
    }
}