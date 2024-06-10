package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.NeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HillClimbingMetaheuristic extends Metaheuristic {
    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        float binsWeight = 0;
        for (Bin bin : bins) {
            binsWeight += bin.getWeight();
        }

        NeighboursCalculator neighboursCalculator = new NeighboursCalculator();
        List<List<Bin>> neighbours = neighboursCalculator.calcNeighbours(bins);
        List<Bin> bestNeighbour = new ArrayList<Bin>();
        float bestWeight = binsWeight;
        for (List<Bin> neighbour : neighbours) {
            float neighbourWeight = Utils.getBinPackingWeight(neighbour);
            if (neighbourWeight < bestWeight) {
                bestNeighbour = neighbour;
                bestWeight = neighbourWeight;
            }
        }
        if (bestWeight < binsWeight) {
            return bestNeighbour;
        }
        isAlgorithmRunning = false;
        return bins;
    }
}
