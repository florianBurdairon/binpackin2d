package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.NeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TabouMetaheuristic extends Metaheuristic {

    private final int maxSizeTabouList = 2;
    private final Queue<Bin> prohibitedMoves = new java.util.LinkedList<Bin>();

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        float binsWeight = 0;
        for (Bin bin : bins) {
            binsWeight += bin.getWeight();
        }

        NeighboursCalculator neighboursCalculator = new NeighboursCalculator();
        List<List<Bin>> neighbours = neighboursCalculator.calcNeighbours(bins);
        List<Bin> bestNeighbour = new ArrayList<Bin>();
        float bestWeight = Float.MAX_VALUE;
        for (List<Bin> neighbour : neighbours) {
            Bin goalBin = getGoalBin(bins, neighbour);
            // If goal bin is in prohibited bins
            if (prohibitedMoves.contains(goalBin)) {
                break;
            }
            float neighbourWeight = Utils.getBinPackingWeight(neighbour);
            if (neighbourWeight < bestWeight) {
                bestNeighbour = neighbour;
                bestWeight = neighbourWeight;
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
            Bin originalBin = getOriginalBin(bins, bestNeighbour);
            System.out.println("Banning bin " + originalBin.getId());
            prohibitedMoves.offer(originalBin);
            if (prohibitedMoves.size() > maxSizeTabouList) {
                prohibitedMoves.poll();
            }
            return bestNeighbour;
        }
    }

    private Bin getOriginalBin(List<Bin> previousList, List<Bin> newList) {
        if (previousList.size() != newList.size())
            return null;
        for (int i = 0; i < previousList.size(); i++) {
            if (previousList.get(i).getItemsCount() > newList.get(i).getItemsCount()) {
                return newList.get(i);
            }
        }
        return null;
    }

    private Bin getGoalBin(List<Bin> previousList, List<Bin> newList) {
        if (previousList.size() != newList.size())
            return null;
        for (int i = 0; i < previousList.size(); i++) {
            if (previousList.get(i).getItemsCount() < newList.get(i).getItemsCount()) {
                return newList.get(i);
            }
        }
        return null;
    }
}
