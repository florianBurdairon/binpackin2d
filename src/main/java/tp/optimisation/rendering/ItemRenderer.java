package tp.optimisation.rendering;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
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
        material.setDiffuseColor(Color.BLUE);
        material.setSpecularColor(Color.LIGHTBLUE);

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
                0, 1, 0,
                item.getWidth(), 1, 0,
                0, 1, item.getHeight(),
                item.getWidth(), 1, item.getHeight());
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
        meshView.setTranslateX(position.getX());
        meshView.setTranslateY(position.getY());

        world.getChildren().add(meshView);
    }
}
