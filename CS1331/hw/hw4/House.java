/**
* Abstract class of that represents a House Objects
* @author Noam Lerner
* @version 1.0
*/
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Rectangle;
public abstract class House {

    protected ImageIcon image;
    protected String imageFilename;
    protected int xPos, yPos, speed, health, age, family, damage, maxAge;
    protected Rectangle bounds;
    protected boolean moveDown, moveRight, dead, reproduced;
    public static final int NORM_MAX_AGE = 50, NUM_FAMILIES = 6, STARK = 0,
    LANNISTER = 1, TULLY = 2, BARATHEON = 3, TARGARYAN = 4, LERNER = 5;
    protected boolean [] canReproduce, canHarm;
    protected int [] chanceToHit;
    /**
     * Constructor for the abstract house class, sets the x position, y position
     * @param  x - x location on map
     * @param  y - y location on map
     * @param  bounds - bounds of map
     * @return
     */
    public House(int x, int y, Rectangle bounds) {
        this.xPos = x;
        this.yPos = y;
        this.bounds = bounds;
        health = 100;
        age = 0;
        maxAge = 150;
        moveDown = Math.random() < 0.5;
        moveRight = Math.random() < 0.5;
        speed = 10;
        damage = 10;
        canReproduce = new boolean[NUM_FAMILIES];
        canHarm = new boolean[NUM_FAMILIES];
        dead = false;
        reproduced = false;
        chanceToHit = new int[NUM_FAMILIES];
    }

    /**
     * Draws the house
     * @param g
     */
    protected void draw(Graphics g) {
        image.paintIcon(null, g, xPos, yPos);
    }
    /**
     * Moves the house
     */
    protected void move() {
        movementEfects();
        if (Math.random() > 0.99) {
            moveRight = !moveRight;
        }
        if (Math.random() > 0.99) {
            moveDown = !moveDown;
        }
        if (xPos + speed > bounds.width) {
            moveRight = false;
        }
        if (xPos - speed < 0) {
            moveRight = true;
        }
        if (yPos + speed > bounds.height) {
            moveDown = false;
        }
        if (yPos - speed < 0) {
            moveDown = true;
        }
        double xSpeed = Math.random();
        double ySpeed = Math.random();
        xPos += moveRight ? (int) (speed * xSpeed) : (int) (-speed * xSpeed);
        yPos += moveDown ? (int) (ySpeed * speed) : (int) (ySpeed * -speed);
    }
    /**
     * Adds extra movement effects, can be overriden to add effects
     */
    protected void movementEfects() {
        health -= 5;
        age++;
    }
    /**
     * Checks if two houses are colliding
     * @param  otherHouse - house to check with this house
     * @return true if the two are colliding, false otherwise
     */
    protected boolean collidesWithHouse(House otherHouse) {
        if (xPos + image.getIconWidth() < otherHouse.xPos
            || otherHouse.xPos + otherHouse.image.getIconWidth() < xPos
            || yPos + image.getIconHeight() < otherHouse.yPos
            || otherHouse.yPos + otherHouse.image.getIconHeight() < yPos) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * checks to see if two houses can reproduce with each other
     * @param  otherHouse - house to check reproduction with
     * @return true if the two can reproduce, false otherwise
     */
    protected boolean canReproduceWithHouse(House otherHouse) {
        return canReproduce[otherHouse.family];
    }
    /**
     * Abstract method that is not defined here, portentially returns a new
     * house object if reproduction possible based on canReproduceWithHouse()
     * and reproduction occurs (random/low chance)
     * @param  otherHouse - house to reproduce with
     * @return new house if reproduction succesful, null otherwise
     */
    protected abstract House reproduceWithHouse(House otherHouse);
    /**
     * returns whether or not a house has lived past its max age
     * @return true if old, false otherwise
     */
    protected boolean isOld() {
        return age > maxAge && maxAge > 0;
    }
    /**
     * Checks to see if this house can harm another house
     * @param  otherHouse - house to check if harming is possible
     * @return true if possible, false otherwise
     */
    protected boolean canHarmHouse(House otherHouse) {
        return canHarm[otherHouse.family];
    }
    /**
     * Harms a house if possible
     * @param otherHouse - house to harm
     */
    protected void harmHouse(House otherHouse) {
        if (canHarmHouse(otherHouse)) {
            if (Math.random() * 100 < chanceToHit[otherHouse.family]) {
                otherHouse.health -= damage;
            }
        }
    }
    /**
     * Sets dead variable to true
     */
    protected void die() {
        dead = true;
    }
    /**
     * returns whether or not the house is dead
     * @return true if dead, false otherwise
     */
    protected boolean isDead() {
        return dead;
    }
}