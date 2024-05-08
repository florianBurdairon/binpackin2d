package tp.optimisation.rendering;

import tp.optimisation.Bin;
import tp.optimisation.BinPacking;
import tp.optimisation.utils.Position;

public class BinPackingRenderer extends ObjectRenderer {
    private final BinPacking binPacking;

    public BinPackingRenderer(BinPacking binPacking) {
        this.binPacking = binPacking;
    }

    @Override
    void buildObject() {
        int nbBins = binPacking.getBins().size();
        int i = 0;
        for (Bin b : binPacking.getBins()) {
            ObjectRenderer binRenderer = new BinRenderer(
                    b,
                    new Position((int) ((b.getWidth() + 10) * (i - nbBins/2 - 0.5f)), 0),
                    b.getWidth(),
                    b.getHeight());
            binRenderer.renderInto(world);
            i++;
        }
    }
}
