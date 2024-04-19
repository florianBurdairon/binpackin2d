package tp.optimisation.rendering;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import tp.optimisation.Bin;
import tp.optimisation.BinPacking;
import tp.optimisation.Dataset;
import tp.optimisation.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BinPackingRender extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BinPacking bp = new BinPacking(Dataset.fromFile("data/binpacking2d-01.bp2d"));
        bp.addItemAtRandomPos();

        primaryStage.setTitle("Bin Packing 2D");

        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        final Canvas canvas = new Canvas(560,560);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,560,560);

        renderBin(gc, bp.getBins().getFirst());

        root.getChildren().add(canvas);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Rectangle createBinRenderer(){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(20);
        rectangle.setY(20);
        rectangle.setWidth(560);
        rectangle.setHeight(560);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.GREY);
        rectangle.setStrokeWidth(5.0);
        rectangle.setStrokeType(StrokeType.OUTSIDE);
        return rectangle;
    }

    private void renderBin(GraphicsContext gc, Bin bin) {
        gc.setFill(Color.RED);
        gc.setLineWidth(1.0);
        gc.setStroke(Color.GREY);
        Set<Item> items = bin.getItems().keySet();
        for (Item item : items) {
            double x = (double) bin.getItems().get(item).getX() / bin.getWidth() * 560.0;
            double y = (double) bin.getItems().get(item).getY() / bin.getHeight() * 560.0;
            gc.fillRect(x, y, item.getWidth(), item.getHeight());
        }
    }
}
