import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
/**
 * Rectangle object that draws rectangles
 * @author Noam Lerner
 * @version 1.0
 */
public class Rectangle implements Tool {
    protected double x = -1;
    protected double y = -1;
    protected double curX = 1;
    protected double curY = 1;
    protected Path [] paths;
    /**
     * Tool method that is called when the mouse is pressed.
     * Starts drawing a rectangle, sets the start point
     *
     * @param e The mouseevent that fired this onPress.
     * @param g The current graphics context.
     */
    public void onPress(MouseEvent e, GraphicsContext g) {
        paths = new Path[4];
        x = e.getSceneX();
        curX = x;
        y = e.getSceneY();
        curY = y;
        for (int i = 0; i < 4; i++) {
            paths[i] = new Path();
            paths[i].setMouseTransparent(true);
            paths[i].setStrokeWidth(1);
            paths[i].setStroke(g.getStroke());
            paths[i].getElements().add(new MoveTo(x, y));
            ((Pane) g.getCanvas().getParent()).getChildren().add(paths[i]);
        }
    }

    /**
     * Tool method that is called when the mouse is dragged.
     * Draws a rectangle based on the mouses location and the starting point
     *
     * @param e The mouseevent that fired this onDrag.
     * @param g The current graphics context.
     */
    public void onDrag(MouseEvent e, GraphicsContext g) {
        curX = e.getSceneX();
        curY = e.getSceneY();
        for (int i = 0; i < 4; i++) {
            paths[i].getElements().clear();
        }
        paths[0].getElements().addAll(new MoveTo(x, y), new LineTo(curX, y));
        paths[1].getElements().addAll(new MoveTo(x, y), new LineTo(x, curY));
        paths[2].getElements().addAll(new MoveTo(x, curY),
            new LineTo(curX, curY));
        paths[3].getElements().addAll(new MoveTo(curX, y),
            new LineTo(curX, curY));
    }

    /**
     * Tool method that is called when the mouse is released.
     * Fills in the rectangle
     *
     * @param e The mouseevent that fired this onRelease.
     * @param g The current graphics context.
     */
    public void onRelease(MouseEvent e, GraphicsContext g) {
        for (int i = 0; i < 4; i++) {
            paths[i].getElements().clear();
        }
        double rectX = x > curX ? curX : x;
        double rectW = Math.abs(x - curX);
        double rectY = y > curY ? curY : y;
        double rectH = Math.abs(y - curY);

        g.fillRect(rectX, rectY, rectW, rectH);
    }

    /**
     * The name of this tool.
     *
     * @return This tool's name.
     */
    public String getName() {
        return "Rectangle";
    }
}