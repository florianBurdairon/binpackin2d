package tp.optimisation;


import tp.optimisation.metaheuristics.HillClimbingMetaheuristic;
import tp.optimisation.metaheuristics.Metaheuristic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinPacking {
    private final Dataset dataset;
    private List<Bin> bins;
    private Metaheuristic metaheuristic;

    private Runnable runOnUpdate;

    public BinPacking(Dataset dataset) {
        this.dataset = dataset;
        this.metaheuristic = new HillClimbingMetaheuristic();
        reset();
    }

    public void setRunOnUpdate(Runnable runOnUpdate) {
        this.runOnUpdate = runOnUpdate;
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

    public void setMetaheuristic(Metaheuristic metaheuristic) {
        this.metaheuristic = metaheuristic;
        reset();
    }

    public void reset() {
        // Reset the bins to their initial state
        bins = new ArrayList<>();
        FFDH();
    }

    public void getNextIteration() {
        // Get the next bin iteration using the metaheuristic
        bins = metaheuristic.getNextIteration(bins);
        runOnUpdate.run();
    }

    public void processUntilConvergence()
    {
        for (int i = 0; i < 10; i++) {
            getNextIteration();
        }
    }
}
