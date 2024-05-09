package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.NeighboursCalculator;

import java.util.List;

public class HillClimbingMetaheuristic extends Metaheuristic {
    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        NeighboursCalculator neighboursCalculator = new NeighboursCalculator();
        List<List<Bin>> neighbours = neighboursCalculator.calcNeighbours(bins);
        for (List<Bin> neighbour : neighbours) {
            if (neighbour.size() < bins.size()) {
                return neighbour;
            }
        }
        isAlgorithmRunning = false;
        return bins;
    }
}
