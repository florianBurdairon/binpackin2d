package tp.optimisation.rendering;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import tp.optimisation.Item;
import tp.optimisation.utils.Position;

public class ItemRenderer extends ObjectRenderer {
    private final Item item;
    private final Position position;

    public ItemRenderer(Item item, Position position) {
        this.item = item;
        this.position = position;
    }

    @Override
    void buildObject() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(new Color(item.getRed(), item.getGreen(), item.getBlue(), 1));

        TriangleMesh mesh = new TriangleMesh();
        float higher = Math.max(item.getWidth(), item.getHeight());
        mesh.getPoints().addAll(
                0, 0, 0,
                item.getWidth(), 0, 0,
                0, 0, item.getHeight(),
                item.getWidth(), 0, item.getHeight(),
                0, higher, 0,
                item.getWidth(), higher, 0,
                0, higher, item.getHeight(),
                item.getWidth(), higher, item.getHeight());
        mesh.getTexCoords().addAll(
                0.5f, 0.5f
        );
        mesh.getFaces().addAll(
                0, 0, 1, 0, 2, 0, 3, 0, 2, 0, 1, 0, // Bottom
                3, 0, 6, 0, 2, 0, 3, 0, 7, 0, 6, 0, // Front
                0, 0, 6, 0, 4, 0, 0, 0, 2, 0, 6, 0, // Left
                1, 0, 7, 0, 3, 0, 1, 0, 5, 0, 7, 0, // Right
                1, 0, 0, 0, 4, 0, 1, 0, 4, 0, 5, 0, // Back
                4, 0, 6, 0, 5, 0, 5, 0, 6, 0, 7, 0 // Top
        );
        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        meshView.setTranslateX(position.getX());
        meshView.setTranslateY(position.getY());

        world.getChildren().add(meshView);
    }
}
