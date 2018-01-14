import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
/**
 * Plant enumration, holds EARTH, MERCURY, VENUS, and MARS values
 * @author  Noam Lerner
 * @version 1.0
 */
public enum Planet {

    EARTH(Color.SPRINGGREEN, 1, 1, 1),
    MERCURY(Color.SILVER, 0.24, 0.1915, 0.387),
    VENUS(Color.GREEN, 0.62, 0.4745, 0.723),
    MARS(Color.RED, 1.88, 0.266, 1.52);
    /** DO NOT MODIFY IT'S FOR YOUR OWN GOOD**/
    private final int earthRadius = 35;
    private final int earthDistance = 160;
    private final int earthPeriod = 5;
    /** OK YOU'RE GOOD GO AHEAD AND DO WORK NOW **/
    private Color color;
    private double radius;
    private double distance;
    private double period;

    /**
     * Constructor for the planet Enumeration. Sets the color, period
     * radius and distance of the specified planet.
     */
    Planet(Color color, double period, double radius, double distance) {
        this.color = color;
        this.radius = radius * earthRadius;
        this.distance = distance * earthDistance;
        this.period = period * earthPeriod * 1000;
    }
    /**
     * @return a double value for the planet's radius
     */
    public double getRadius() {
        return radius;
    }
    /**
     * @return a double value for the radius of the planet's orbit around
     * the sun
     */
    public double getDistance() {
        return distance;
    }
    /**
     * @return a double value for the amount of miliseconds it takes for the
     * planet to orbit the sun
     */
    public double getPeriod() {
        return period;
    }
    /**
     * @return the color of the planet
     */
    public Color getColor() {
        return color;
    }
    /**
     * @param  xLoc any x position that would be located in the planet's orbit
     * @param  yLoc any y position that would be located in the planet's orbit
     * @return a path object which contains the orbit which goes through the
     * given x and y coordinates with this enumeration's distance as the radius
     */
    public Path getOrbit(double xLoc, double yLoc) {
        Path orbit = new Path();
        orbit.getElements().add(new MoveTo(xLoc, yLoc));
        ArcTo arcTo = new ArcTo();
        arcTo.setX(xLoc - (distance * 2));
        arcTo.setY(yLoc - (distance * 2));
        arcTo.setRadiusX(radius);
        arcTo.setRadiusY(radius);
        orbit.getElements().add(arcTo);
        arcTo = new ArcTo();
        arcTo.setX(xLoc);
        arcTo.setY(yLoc);
        arcTo.setRadiusX(radius);
        arcTo.setRadiusY(radius);
        orbit.getElements().add(arcTo);
        orbit.setStroke(Color.WHITE);
        return orbit;
    }
}

