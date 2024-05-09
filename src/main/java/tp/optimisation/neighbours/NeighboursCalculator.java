package tp.optimisation.neighbours;

import tp.optimisation.Bin;
import tp.optimisation.Item;

import java.util.ArrayList;
import java.util.List;

public class NeighboursCalculator extends AbstractNeighboursCalculator {
    @Override
    public List<List<Bin>> calcNeighbours(List<Bin> bins) {
        List<List<Bin>> neighbours = new ArrayList<>();

        for (Bin originalBin : bins) {
            for (Item item : originalBin.getItems().keySet()) {
                List<Bin> b = new ArrayList<>();
                for (Bin bin : bins) {
                    b.add(bin.duplicate());
                }

                for (Bin destBin : b) {
                    if (!destBin.equals(originalBin)) {
                        if (destBin.addItem(item)) {
                            // TODO: Fix this (Add Cloneable everywhere)
                        }
                    }
                }
            }
        }

        return neighbours;
    }
}
