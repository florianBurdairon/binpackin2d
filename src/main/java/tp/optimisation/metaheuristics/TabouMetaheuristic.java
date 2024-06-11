package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.Item;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.neighbours.SwitchNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TabouMetaheuristic extends Metaheuristic {

    private final int maxSizeTabouList = 4;
    private final Queue<Item> prohibitedMoves = new java.util.LinkedList<Item>();

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        float binsWeight = 0;
        for (Bin bin : bins) {
            binsWeight += bin.getWeight();
        }

        AbstractNeighboursCalculator neighboursCalculator = new SwitchNeighboursCalculator();
        List<List<Bin>> neighbours = neighboursCalculator.calcNeighbours(bins);
        List<Bin> bestNeighbour = new ArrayList<Bin>();
        float bestWeight = Float.MAX_VALUE;
        for (List<Bin> neighbour : neighbours) {
            List<Item> modifiedItems = getModifiedItems(bins, neighbour);
            // If goal bin is in prohibited bins
            boolean isProhibited = false;
            for (Item item : modifiedItems) {
                if (prohibitedMoves.contains(item)) {
                    isProhibited = true;
                }
            }
            if (!isProhibited) {
                float neighbourWeight = Utils.getBinPackingWeight(neighbour);
                if (neighbourWeight < bestWeight) {
                    bestNeighbour = neighbour;
                    bestWeight = neighbourWeight;
                }
            }
        }

        // No solution reachable with banned bins : end of algorithm
        if (bestNeighbour.isEmpty()) {
            isAlgorithmRunning = false;
            return bins;
        }

        System.out.println("Best weight: " + bestWeight);
        // Found better solution
        if (bestWeight < binsWeight) {
            return bestNeighbour;
        }
        // Found one solution but degrades the last one
        else {
            List<Item> modifiedItems = getModifiedItems(bins, bestNeighbour);
            for (Item item : modifiedItems) {
                System.out.println("Banning item: " + item.getId());
            }
            prohibitedMoves.addAll(modifiedItems);
            while (prohibitedMoves.size() > maxSizeTabouList) {
                prohibitedMoves.remove();
            }
            return bestNeighbour;
        }
    }

    private List<Item> getModifiedItems(List<Bin> bins, List<Bin> neighbour) {
        List<Item> modifiedItems = new ArrayList<Item>();
        for (Bin value : bins) {
            for (Bin bin : neighbour) {
                if (value.getId() == bin.getId()) {
                    Item i = getModifiedItemInBin(value, bin);
                    if (i != null) {
                        modifiedItems.add(i);
                    }
                }
            }
        }
        return modifiedItems;
    }

    private Item getModifiedItemInBin(Bin previousBin, Bin newBin) {
        for (Item item : previousBin.getItems().keySet()) {
            if (!newBin.getItems().containsKey(item)) {
                return item;
            }
        }
        return null;
    }
}
