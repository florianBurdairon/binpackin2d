package tp.optimisation.rendering;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import tp.optimisation.Bin;
import tp.optimisation.Item;
import tp.optimisation.utils.Position;

import java.util.Map;

public class BinRenderer extends ObjectRenderer {
    private final Bin bin;
    private final XForm binGroup = new XForm();
    private final Position position;

    public BinRenderer(Bin bin, Position position) {
        this.bin = bin;
        this.position = position;
    }

    @Override
    void buildObject() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BEIGE);
        material.setSpecularColor(Color.WHEAT);

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
                position.getX(), 0, position.getY(),
                bin.getWidth() + position.getX(), 0, position.getY(),
                position.getX(), 0, bin.getHeight() + position.getY(),
                bin.getWidth() + position.getX(), 0, bin.getHeight() + position.getY());
        mesh.getTexCoords().addAll(
                0.5f, 0.5f
        );
        mesh.getFaces().addAll(
                0, 0, 2, 0, 1, 0,
                3, 0, 1, 0, 2, 0
        );
        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        binGroup.getChildren().add(meshView);

        System.out.println("Rendering... ");
        for(Map.Entry<Item, Position> entry : bin.getItems().entrySet()) {
            ItemRenderer itemRenderer = new ItemRenderer(entry.getKey(), entry.getValue().add(position));
            System.out.println("Rendering " + entry.getKey());
            binGroup.getChildren().add(itemRenderer);
            itemRenderer.renderInto(binGroup);
        }

        world.getChildren().addAll(binGroup);
    }
}
