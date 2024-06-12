package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.List;

public class SimulatingAnnealingMetaheuristic extends Metaheuristic {

    private final double TEMPERATURE = 10000;
    private final double COOLING_RATE = 0.01;

    private double temperature = TEMPERATURE;
    private double coolingRate = COOLING_RATE;

    public SimulatingAnnealingMetaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        super(neighboursCalculator);
    }

    @Override
    public void reset() {
        temperature = TEMPERATURE;
        coolingRate = COOLING_RATE;
        super.reset();
    }

    public double getCoolingRate() {
        return coolingRate;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        List<Bin> currentSolution = bins;
        List<Bin> newSolution = bins;

        // Create new neighbour solution
        List<List<Bin>> neighbours = neighboursCalculator.calcNeighbours(bins);

        boolean isNewSolutionFound = false;

        while (!isNewSolutionFound) {
            if (!neighbours.isEmpty()) {
                newSolution = neighbours.get((int) (Math.random() * neighbours.size()));
            }

            // Get the weights of the solutions
            double currentWeight = Utils.getBinPackingWeight(currentSolution);
            double newWeight = Utils.getBinPackingWeight(newSolution);
            double bestWeight = Utils.getBinPackingWeight(bestSolution);

            // Decide if we should accept the new solution
            if (acceptanceProbability(currentWeight, newWeight, temperature) > Math.random()) {
                currentSolution = newSolution;
                currentWeight = newWeight;
                isNewSolutionFound = true;
            }

            // Keep track of the best solution
            if (currentWeight < bestWeight) {
                bestSolution = currentSolution;
            }

            // Cool down the system
            temperature *= 1 - coolingRate;

            if (temperature <= 1) {
                isAlgorithmRunning = false;
                break;
            }
        }

        return currentSolution;
    }

    private double acceptanceProbability(double currentWeight, double newWeight, double temperature) {
        // If the new solution is better, accept it
        if (newWeight < currentWeight) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((currentWeight - newWeight) / temperature);
    }
}
