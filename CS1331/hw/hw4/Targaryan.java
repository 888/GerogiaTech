/**
* This class represents a Stark object.
* @author Noam Lerner
* @version 1.0
*/
import java.awt.Rectangle;
import javax.swing.ImageIcon;
public class Targaryan extends House {
    /**
     * Initializes a new house House object of the type Targaryan.
     * @param  x x-location on screen
     * @param  y y-location on screen
     * @param  bounds - bounds of the screen
     * @return
     */
    public Targaryan(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        this.image = new ImageIcon("dragon.png");
        this.canReproduce[this.TARGARYAN] = true;
        for (int i = 0; i < this.canHarm.length; i++) {
            if (i != this.TARGARYAN && i != this.BARATHEON) {
                canHarm[i] = true;
            }
        }
        this.chanceToHit[this.TULLY] = 100;
        this.maxAge = -1;
        this.family = this.TARGARYAN;
    }
    /**
     * If the otherHouse and this house can reproduce, might reproduce (0.009
     * chance)
     * @param  otherHouse
     * @return either a Targaryan House object if reproduction was succesful
     * or null
     */
    protected House reproduceWithHouse(House otherHouse) {
        if (!reproduced && canReproduceWithHouse(otherHouse)) {
            if (Math.random() < 0.009) {
                reproduced = true;
                return new Targaryan(this.xPos, this.yPos, this.bounds);
            }
        }
        return null;
    }
}