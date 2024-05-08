package tp.optimisation;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinPacking {
    private final Dataset dataset;
    private final List<Bin> bins;

    public BinPacking(Dataset dataset) {
        this.dataset = dataset;
        this.bins = new ArrayList<>();
        FFDH();
    }

    public Dataset getDataset() {
        return dataset;
    }

    public List<Bin> getBins() {
        return bins;
    }

    public void FFDH() {
        // Use First Fit Decreasing Height algorithm to pack items from dataset into bins
        // Sort items by decreasing width
        List<Item> items = new ArrayList<>(dataset.getItems());
        items.sort(Comparator.comparing(Item::getWidth).reversed());

        for (Item item : items) {
            boolean placed = false;

            // Try to place the item in each bin
            for (Bin bin : bins) {
                if (bin.canFitHorizontally(item)) {
                    bin.addItemHorizontally(item);
                    placed = true;
                    break;
                }
            }

            // If the item couldn't be placed in any bin, open a new one
            if (!placed) {
                Bin newBin = new Bin(dataset.getBinWidth(), dataset.getBinHeight());
                newBin.addItemHorizontally(item);
                bins.add(newBin);
            }
        }
    }
}
