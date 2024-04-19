package tp.optimisation.utils;

import tp.optimisation.Dataset;
import tp.optimisation.Item;

public class Utils {
    public static int minimumBinPacking(Dataset dataset) {
        int binArea = dataset.getBinWidth() * dataset.getBinHeight();
        int sumItemsArea = 0;
        for (Item item : dataset.getItems()) {
            sumItemsArea += item.getWidth() * item.getHeight();
        }
        return Math.ceilDiv(sumItemsArea, binArea);
    }
}
