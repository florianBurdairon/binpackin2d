package tp.optimisation.rendering;

import javafx.scene.Node;
import tp.optimisation.Bin;
import tp.optimisation.BinPacking;
import tp.optimisation.utils.Position;

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
        objects.clear();
        int i = 0;
        for (Bin b : binPacking.getBins()) {
            ObjectRenderer binRenderer = new BinRenderer(
                    b,
                    new Position((int) (farLeft + i * (width + 20) * scaleFactor), 0),//(int) ((b.getWidth() + 10) * (i - nbBins/2 - 0.5f)), 0),
                    scaleFactor);
            binRenderer.renderInto(world);
            objects.add(binRenderer);
            i++;
        }
    }

    public void addNextRow() {
        for (Node n : world.getChildren()) {
            n.setTranslateZ(n.getTranslateZ() - (width + 20) * scaleFactor);
        }

        renderBins();
    }
}
