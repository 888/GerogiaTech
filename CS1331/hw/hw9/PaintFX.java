import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.control.Slider;
/**
 * Creates a window that can be drawn on with various tools
 * @author Noam Lerner
 * @version 1.0
 */
public class PaintFX extends Application {
    protected Tool tool;
    protected GraphicsContext g;

    /**
     * Starts the program
     * @param stage passed in magically
     */
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        tool = new Pencil();

        //center
        Pane drawingPane = new Pane();
        drawingPane.setStyle("-fx-background-color: #ecf0f1;");
        drawingPane.setPrefSize(300, 300);
        Canvas canvas = new Canvas(300, 300);
        canvas.heightProperty().bind(drawingPane.heightProperty());
        canvas.widthProperty().bind(drawingPane.widthProperty());
        g = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(e ->   {
                this.tool.onPress(e, g);
            }
        );
        canvas.setOnMouseDragged(e ->   {
                this.tool.onDrag(e, g);
            }
        );
        canvas.setOnMouseReleased(e ->  {
                this.tool.onRelease(e, g);
            }
        );
        drawingPane.getChildren().add(canvas);
        root.setCenter(drawingPane);

        //Sidebar
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(100);
        root.setRight(sidebar);



        //Tool Settings
        HBox settings = new HBox();
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(e ->    {
                g.setStroke(colorPicker.getValue());
                g.setFill(colorPicker.getValue());
            }
        );
        Slider slider = new Slider(0.1, 20, 1);
        slider.setOnMouseExited(e ->    {
                g.setLineWidth(slider.getValue());
            }
        );
        settings.getChildren().addAll(colorPicker, slider);
        root.setBottom(settings);

        //Tools
        Button [] tools = new Button[4];
        //pencil
        tools[0] = new Button("Pencil");
        tools[0].setOnAction(e ->   {
                this.tool = new Pencil();
            }
        );
        tools[1] = new Button("Rectangle");
        tools[1].setOnAction(e ->   {
                this.tool = new Rectangle();
            }
        );
        tools[2] = new Button("Eraser");
        tools[2].setOnAction(e ->   {
                this.tool = new Eraser();
            }
        );
        tools[3] = new Button("Clear");
        tools[3].setOnAction(e ->   {
                g.clearRect(0, 0, ((Pane) g.getCanvas().getParent()).getWidth(),
                    ((Pane) g.getCanvas().getParent()).getHeight());
            }
        );
        for (int i = 0; i < tools.length; i++) {
            sidebar.getChildren().add(tools[i]);
        }

        //scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}