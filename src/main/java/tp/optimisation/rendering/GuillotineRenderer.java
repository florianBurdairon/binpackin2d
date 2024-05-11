package tp.optimisation.rendering;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import tp.optimisation.utils.Guillotine;

public class GuillotineRenderer extends ObjectRenderer{
    private final Guillotine guillotine;
    private final float length;

    public GuillotineRenderer(Guillotine guillotine, float length) {
        this.guillotine = guillotine;
        this.length = length;
    }

    @Override
    void buildObject() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLUE);

        int height = 205;
        TriangleMesh mesh = new TriangleMesh();
        if (guillotine.getDirection() == Guillotine.Direction.Vertical) {
            mesh.getPoints().addAll(
                    guillotine.getPosition(), height, 0,
                    guillotine.getPosition(), height, length,
                    guillotine.getPosition() + 5, height, 0,
                    guillotine.getPosition() + 5, height, length);
        } else {
            mesh.getPoints().addAll(
                    0, height, guillotine.getPosition(),
                    length, height, guillotine.getPosition(),
                    0, height, guillotine.getPosition() + 5,
                    length, height, guillotine.getPosition() + 5);
        }
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
