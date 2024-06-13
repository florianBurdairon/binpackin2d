package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HillClimbingMetaheuristic extends Metaheuristic {
    public HillClimbingMetaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        super(neighboursCalculator);
    }

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        float binsWeight = Utils.getBinPackingWeight(bins);

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
            bestSolution = bestNeighbour;
            return bestNeighbour;
        }
        isAlgorithmRunning = false;
        return bestSolution;
    }
}
