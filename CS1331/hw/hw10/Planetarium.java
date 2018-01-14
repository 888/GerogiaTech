import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.animation.PathTransition;
import javafx.util.Duration;
import javafx.animation.Interpolator;
/**
 * Creates a javafx application using the Planet enumeration to set a create
 * a to-scale version of our universe.
 * @author  Noam Lerner
 * @version 1.0
 */
public class Planetarium extends Application {

    /**
     * Starts the application. creates the gui and runs the animations
     * @param stage MAGICAL PARAMETER
     */
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPrefSize(800, 700);
        root.setStyle("-fx-background-color: black;");

        Circle sun = new Circle(root.getPrefWidth() / 2,
            root.getPrefHeight() / 2, 65, Color.YELLOW);

        Planet [] planets = new Planet[4];
        planets[0] = Planet.EARTH;
        planets[1] = Planet.MERCURY;
        planets[2] = Planet.VENUS;
        planets[3] = Planet.MARS;
        Circle [] planetCircles = new Circle[planets.length];
        for (int i = 0; i < planets.length; i++) {
            double xLoc = sun.getCenterX() + planets[i].getDistance();
            double yLoc = sun.getCenterY() + planets[i].getDistance();
            planetCircles[i] = new Circle(xLoc, yLoc, planets[i].getRadius(),
                planets[i].getColor());
            root.getChildren().addAll(planets[i].getOrbit(xLoc, yLoc),
                planetCircles[i]);
            PathTransition animation = new PathTransition(
                Duration.millis(planets[i].getPeriod()),
                planets[i].getOrbit(xLoc, yLoc), planetCircles[i]);
            animation.setCycleCount(-1);
            animation.setInterpolator(Interpolator.LINEAR);
            animation.play();
        }

        root.getChildren().add(sun);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}