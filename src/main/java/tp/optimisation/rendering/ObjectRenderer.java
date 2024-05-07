package tp.optimisation.rendering;

import javafx.scene.Group;
import javafx.scene.Scene;

public abstract class ObjectRenderer extends Group {
    protected final XForm world = new XForm();

    protected void addKeyboardEvents(Scene scene) {}
    protected void addMouseEvents(Scene scene) {}

    abstract void buildObject();

    public void renderInto(Group parent)
    {
        buildObject();
        parent.getChildren().add(world);
    }

    public void registerEvents(Scene scene) {
        addKeyboardEvents(scene);
        addMouseEvents(scene);
    }
}
