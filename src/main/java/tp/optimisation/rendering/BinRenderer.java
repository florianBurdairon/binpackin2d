package tp.optimisation.rendering;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
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
    private final double scaleFactor;

    public BinRenderer(Bin bin, Position position, double scaleFactor) {
        this.bin = bin;
        this.position = position;
        this.scaleFactor = scaleFactor;
    }

    @Override
    void buildObject() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BEIGE);
        material.setSpecularColor(Color.WHEAT);

        float boxHeight = 100;

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
                0, 0, 0,
                bin.getWidth(), 0, 0,
                0, 0, bin.getHeight(),
                bin.getWidth(), 0, bin.getHeight(),
                0, boxHeight, 0,
                bin.getWidth(), boxHeight, 0,
                0, boxHeight, bin.getHeight(),
                bin.getWidth(), boxHeight, bin.getHeight());
        mesh.getTexCoords().addAll(
                0.5f, 0.5f
        );
        mesh.getFaces().addAll(
                0, 0, 1, 0, 2, 0, 3, 0, 2, 0, 1, 0, // Bottom
                3, 0, 6, 0, 2, 0, 3, 0, 7, 0, 6, 0, // Front
                0, 0, 6, 0, 4, 0, 0, 0, 2, 0, 6, 0, // Left
                1, 0, 7, 0, 3, 0, 1, 0, 5, 0, 7, 0, // Right
                1, 0, 0, 0, 4, 0, 1, 0, 4, 0, 5, 0 // Back
        );
        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE);

        meshView.setScaleX(1.05);
        meshView.setScaleY(1.05);
        meshView.setTranslateY(boxHeight - boxHeight*1.05);
        meshView.setScaleZ(1.05);

        System.out.println("Rendering bin...");
        for(Map.Entry<Item, Position> entry : bin.getItems().entrySet()) {
            ItemRenderer itemRenderer = new ItemRenderer(
                    entry.getKey(),
                    entry.getValue());
            System.out.println("Rendering item " + entry.getKey());
            itemRenderer.renderInto(binGroup);
            binGroup.getChildren().add(itemRenderer);
        }

        binGroup.getChildren().add(meshView);
        binGroup.setScale(scaleFactor);
        binGroup.setTranslateX(position.getX() - (double) bin.getWidth() / 2 * scaleFactor);
        binGroup.setTranslateZ(position.getY() - (double) bin.getHeight() / 2 * scaleFactor);
        world.getChildren().addAll(binGroup);
    }
}
