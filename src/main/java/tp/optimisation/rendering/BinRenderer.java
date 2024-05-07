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

    public BinRenderer(Bin bin) {
        this.bin = bin;
    }

    @Override
    void buildObject() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BEIGE);
        material.setSpecularColor(Color.WHEAT);

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
                0, 0, 0,
                250, 0, 0,
                0, 0, 250,
                250, 0, 250);
        mesh.getTexCoords().addAll(
                0.5f, 0.5f
        );
        mesh.getFaces().addAll(
                0, 0, 2, 0, 1, 0,
                3, 0, 1, 0, 2, 0
        );
        MeshView meshView = new MeshView(mesh);
        meshView.setCullFace(CullFace.NONE);
        meshView.setMaterial(material);
        binGroup.getChildren().add(meshView);

        System.out.println("Rendering... ");
        for(Map.Entry<Item, Position> entry : bin.getItems().entrySet()) {
            ItemRenderer itemRenderer = new ItemRenderer(entry.getKey(), entry.getValue());
            System.out.println("Rendering " + entry.getKey());
            binGroup.getChildren().add(itemRenderer);
            itemRenderer.renderInto(binGroup);
        }

        world.getChildren().addAll(binGroup);
    }
}
