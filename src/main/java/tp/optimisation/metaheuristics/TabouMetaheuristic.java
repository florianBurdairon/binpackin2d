package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.Item;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class TabouMetaheuristic extends Metaheuristic {
    private final int MAX_SIZE = 2;

    private int maxSizeTabouList = MAX_SIZE;
    private final Queue<Integer> prohibitedMoves = new java.util.LinkedList<>();

    private final List<String> alreadyProcessedState = new ArrayList<>();

    public TabouMetaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        super(neighboursCalculator);
    }

    @Override
    public void reset() {
        prohibitedMoves.clear();
        alreadyProcessedState.clear();
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
        String state = getState(bins);
        if (alreadyProcessedState.contains(state)){
            System.out.println("Already been there, finishing now on: " + state);
            isAlgorithmRunning = false;
            return bestSolution;
        } else {
            alreadyProcessedState.add(state);
        }

        float binsWeight = Utils.getBinPackingWeight(bins);

        List<List<Bin>> neighbours = neighboursCalculator.calcNeighbours(bins);
        List<Bin> bestNeighbour = new ArrayList<>();
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
        }
        // Found one solution but degrades the last one
        else {
            List<Integer> modifiedItemsId = getModifiedItemsId(bins, bestNeighbour);
            prohibitedMoves.addAll(modifiedItemsId);
            while (prohibitedMoves.size() > maxSizeTabouList) {
                prohibitedMoves.remove();
            }
        }
        return bestNeighbour;
    }

    private List<Integer> getModifiedItemsId(List<Bin> bins, List<Bin> neighbour) {
        List<Integer> modifiedItems = new ArrayList<>();
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

    private String getState(List<Bin> bins) {
        StringBuilder state = new StringBuilder();

        for (Bin b : bins) {
            for (Item i : b.getItems().keySet().stream().sorted(Comparator.comparingInt(Item::getId)).toList()) {
                state.append(String.format("%02d", i.getId()));
            }
            state.append("-");
        }
        state.append(":");
        for (Integer i : prohibitedMoves.stream().toList().stream().sorted(Comparator.comparingInt(i -> i)).toList()) {
            state.append(String.format("%02d", i));
        }
        return state.toString();
    }
}
