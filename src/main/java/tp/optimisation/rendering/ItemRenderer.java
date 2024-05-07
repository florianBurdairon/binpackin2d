package tp.optimisation.rendering;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import tp.optimisation.Item;
import tp.optimisation.utils.Position;

import java.util.Random;

public class ItemRenderer extends ObjectRenderer {
    private final Item item;
    private final Position position;

    public ItemRenderer(Item item, Position position) {
        this.item = item;
        this.position = position;
    }

    @Override
    void buildObject() {
        Random r = new Random();
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(new Color(r.nextDouble(0, 1), r.nextDouble(0, 1), r.nextDouble(0, 1), 1));
        //material.setSpecularColor(Color.LIGHTBLUE);

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
                0, 5, 0,
                item.getWidth(), 5, 0,
                0, 5, item.getHeight(),
                item.getWidth(), 5, item.getHeight());
        mesh.getTexCoords().addAll(
                0.5f, 0.5f
        );
        mesh.getFaces().addAll(
                0, 0, 2, 0, 1, 0,
                3, 0, 1, 0, 2, 0
        );
        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        meshView.setTranslateX(position.getX());
        meshView.setTranslateY(position.getY());

        world.getChildren().add(meshView);
    }
}
