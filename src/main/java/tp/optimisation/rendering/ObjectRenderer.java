package tp.optimisation.rendering;

import javafx.scene.Group;
import javafx.scene.Scene;

public abstract class ObjectRenderer extends Group {
    protected final XForm world = new XForm();

    protected void addKeyboardEvents(Scene scene) {}
    protected void addMouseEvents(Scene scene) {}

    public void render(Group parent)
    {
        parent.getChildren().add(world);
    }

    public void registerEvents(Scene scene) {
        addKeyboardEvents(scene);
        addMouseEvents(scene);
    }
}
