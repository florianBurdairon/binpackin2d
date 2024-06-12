package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.Item;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TabouMetaheuristic extends Metaheuristic {
    private final int MAX_SIZE = 4;

    private int maxSizeTabouList = MAX_SIZE;
    private final Queue<Integer> prohibitedMoves = new java.util.LinkedList<Integer>();

    public TabouMetaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        super(neighboursCalculator);
    }

    @Override
    public void reset() {
        prohibitedMoves.clear();
        maxSizeTabouList = MAX_SIZE;
        super.reset();
    }

    public int getMaxSizeTabouList() {
        return maxSizeTabouList;
    }

    public void setMaxSizeTabouList(int maxSizeTabouList) {
        this.maxSizeTabouList = maxSizeTabouList;
    }

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        float binsWeight = Utils.getBinPackingWeight(bins);

        List<List<Bin>> neighbours = neighboursCalculator.calcNeighbours(bins);
        List<Bin> bestNeighbour = new ArrayList<Bin>();
        float bestWeight = Float.MAX_VALUE;
        for (List<Bin> neighbour : neighbours) {
            List<Integer> modifiedItemIds = getModifiedItemsId(bins, neighbour);
            // If goal bin is in prohibited bins
            boolean isProhibited = false;
            for (Integer itemId : modifiedItemIds) {
                for (Integer prohibitedItemId : prohibitedMoves) {
                    if (itemId.equals(prohibitedItemId)) {
                        isProhibited = true;
                        break;
                    }
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
            return bestSolution;
        }

        // Found better solution
        if (bestWeight < binsWeight) {
            if (bestWeight < Utils.getBinPackingWeight(bestSolution)) {
                bestSolution = bestNeighbour;
            }
            return bestNeighbour;
        }
        // Found one solution but degrades the last one
        else {
            List<Integer> modifiedItemsId = getModifiedItemsId(bins, bestNeighbour);
            prohibitedMoves.addAll(modifiedItemsId);
            while (prohibitedMoves.size() > maxSizeTabouList) {
                prohibitedMoves.remove();
            }
            return bestNeighbour;
        }
    }

    private List<Integer> getModifiedItemsId(List<Bin> bins, List<Bin> neighbour) {
        List<Integer> modifiedItems = new ArrayList<Integer>();
        for (Bin value : bins) {
            for (Bin bin : neighbour) {
                if (value.getId() == bin.getId()) {
                    Integer id = getModifiedItemIdInBin(value, bin);
                    if (id != null) {
                        modifiedItems.add(id);
                    }
                }
            }
        }
        return modifiedItems;
    }

    private Integer getModifiedItemIdInBin(Bin previousBin, Bin newBin) {
        for (Item item : previousBin.getItems().keySet()) {
            if (!newBin.getItemIdList().contains(item.getId())) {
                return item.getId();
            }
        }
        return null;
    }
}
