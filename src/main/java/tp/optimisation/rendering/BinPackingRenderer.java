package tp.optimisation.rendering;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tp.optimisation.Bin;
import tp.optimisation.BinPacking;
import tp.optimisation.utils.Position;

import java.util.Objects;

public class BinPackingRenderer extends ObjectRenderer {
    private final BinPacking binPacking;
    private int farLeft;
    private double scaleFactor;
    private double width;

    public BinPackingRenderer(BinPacking binPacking) {
        this.binPacking = binPacking;
    }

    @Override
    void buildObject() {
        int nbBins = binPacking.getBins().size();
        width = binPacking.getBins().getFirst().getWidth() * 1.2;
        scaleFactor = (500.0 / nbBins) / width;
        farLeft = (int) ((- nbBins / 2) * (width) * scaleFactor);
        renderBins();
    }

    private void renderBins() {
        int i = 0;
        for (Bin b : binPacking.getBins()) {
            ObjectRenderer binRenderer = new BinRenderer(
                    b,
                    new Position((int) (farLeft + i * (width + 20) * scaleFactor), 0),//(int) ((b.getWidth() + 10) * (i - nbBins/2 - 0.5f)), 0),
                    scaleFactor);
            binRenderer.renderInto(world);
            i++;
        }
    }

    public void addNextRow() {
        for (Node n : world.getChildren()) {
            n.setTranslateZ(n.getTranslateZ() - (width + 20) * scaleFactor);
        }

        renderBins();
    }

    @Override
    protected void addKeyboardEvents(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.R) {
                binPacking.getBins().removeFirst();
                System.out.println("Removed one bin and printed new generation");
                this.addNextRow();
            }
        });
    }
}
