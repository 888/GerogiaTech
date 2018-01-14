/**
* This class represents a Lerner  object.
* @author Noam Lerner
* @version 1.0
*/
import java.awt.Rectangle;
import javax.swing.ImageIcon;
public class Lerner extends House {
    /**
     * Initializes a new house House object of the type Lerner.
     * @param  x x-location on screen
     * @param  y y-location on screen
     * @param  bounds - bounds of the screen
     * @return
     */
    public Lerner(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        this.image = new ImageIcon("lerner.png");
        this.canHarm[this.STARK] = true;
        this.canHarm[this.LANNISTER] = true;
        this.chanceToHit[this.STARK] = 100;
        this.chanceToHit[this.LANNISTER] = 100;
        this.canReproduce[this.TARGARYAN] = true;
        this.canReproduce[this.BARATHEON] = true;
        this.maxAge = -1;
        this.family = this.LERNER;
    }
    /**
     * If the otherHouse and this house can reproduce, might reproduce (0.009
     * chance)
     * @param  otherHouse
     * @return either a Lerner House object if reproduction was succesful
     * or null
     */
    protected House reproduceWithHouse(House otherHouse) {
        if (!reproduced && canReproduceWithHouse(otherHouse)) {
            if (Math.random() < 0.009) {
                return new Lerner(this.xPos, this.yPos, this.bounds);
            }
        }
        return null;
    }
    /**
     * Adds effect to movement, health decreased by 2 and age goes up
     */
    protected void movementEfects() {
        health -= 2; //LESS DAMAGE Taken
        age++;
    }
}