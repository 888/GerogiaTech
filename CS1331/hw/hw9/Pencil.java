import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
/**
 * Pencil object, draws lines
 * @author Noam Lerner
 * @version 1.0
 */
public class Pencil implements Tool {
    /**
     * Tool method that is called when the mouse is pressed.
     * Begins the pencil path that will be drawn
     *
     * @param e The mouseevent that fired this onPress.
     * @param g The current graphics context.
     */
    public void onPress(MouseEvent e, GraphicsContext g) {
        g.beginPath();
        g.moveTo(e.getSceneX(), e.getSceneY());
        g.stroke();
    }

    /**
     * Tool method that is called when the mouse is dragged.
     * continues to draw a line
     *
     * @param e The mouseevent that fired this onDrag.
     * @param g The current graphics context.
     */
    public void onDrag(MouseEvent e, GraphicsContext g) {
        g.lineTo(e.getSceneX(), e.getSceneY());
        g.stroke();
    }

    /**
     * Tool method that is called when the mouse is released.
     * closes the path.
     *
     * @param e The mouseevent that fired this onRelease.
     * @param g The current graphics context.
     */
    public void onRelease(MouseEvent e, GraphicsContext g) {
        g.closePath();
    }

    /**
     * The name of this tool.
     *
     * @return This tool's name.
     */
    public String getName() {
        return "Pencil";
    }
}