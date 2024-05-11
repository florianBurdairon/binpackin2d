package tp.optimisation.rendering;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import tp.optimisation.utils.Rectangle;

public class RectangleRenderer extends ObjectRenderer {
    private final Rectangle rectangle;

    public RectangleRenderer(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    void buildObject() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED);

        int height = 200;
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
                rectangle.getX(), height, rectangle.getY(),
                rectangle.getX() + rectangle.getWidth(), height, rectangle.getY(),
                rectangle.getX(), height, rectangle.getHeight() + rectangle.getY(),
                rectangle.getX() + rectangle.getWidth(), height, rectangle.getHeight() + rectangle.getY());
        mesh.getTexCoords().addAll(
                0.5f, 0.5f
        );
        mesh.getFaces().addAll(
                0, 0, 1, 0, 2, 0, 3, 0, 2, 0, 1, 0 // Bottom
        );
        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE);
        world.getChildren().add(meshView);
    }
}
