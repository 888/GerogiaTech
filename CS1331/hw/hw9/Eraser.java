import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
/**
 * Erase object used to erase lines
 * @author Noam Lerner
 * @version 1.0
 */
public class Eraser implements Tool {
    /**
     * Tool method that is called when the mouse is pressed.
     * Erases a square that is the size specified by the bar at the bottom
     *
     * @param e The mouseevent that fired this onPress.
     * @param g The current graphics context.
     */
    public void onPress(MouseEvent e, GraphicsContext g) {
        double side = g.getLineWidth() * 5;
        g.clearRect(e.getSceneX() - side / 2, e.getSceneY() - side / 2,
            side, side);
    }

    /**
     * Tool method that is called when the mouse is dragged.
     * keeps erasing where the mouse is
     *
     * @param e The mouseevent that fired this onDrag.
     * @param g The current graphics context.
     */
    public void onDrag(MouseEvent e, GraphicsContext g) {
        double side = g.getLineWidth() * 5;
        g.clearRect(e.getSceneX() - side / 2, e.getSceneY() - side / 2,
            side, side);
    }

    /**
     * Tool method that is called when the mouse is released.
     * Does nothing
     *
     * @param e The mouseevent that fired this onRelease.
     * @param g The current graphics context.
     */
    public void onRelease(MouseEvent e, GraphicsContext g) {
    }

    /**
     * The name of this tool.
     *
     * @return This tool's name.
     */
    public String getName() {
        return "Eraser";
    }
}