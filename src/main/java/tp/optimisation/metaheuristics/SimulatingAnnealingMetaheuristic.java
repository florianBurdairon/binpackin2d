package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.List;

public class SimulatingAnnealingMetaheuristic extends Metaheuristic {

    private final double TEMPERATURE = 10000;
    private final double COOLING_RATE = 0.01;
    private final int NB_ITERATION_PER_TEMPERATURE = 10;

    private double temperature = TEMPERATURE;
    private double coolingRate = COOLING_RATE;
    private int nbIterationPerTemperature = NB_ITERATION_PER_TEMPERATURE;

    public SimulatingAnnealingMetaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        super(neighboursCalculator);
    }

    @Override
    public void reset() {
        temperature = TEMPERATURE;
        coolingRate = COOLING_RATE;
        nbIterationPerTemperature = NB_ITERATION_PER_TEMPERATURE;
        super.reset();
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getCoolingRate() {
        return coolingRate;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    public int getNbIterationPerTemperature() {
        return nbIterationPerTemperature;
    }

    public void setNbIterationPerTemperature(int nbIterationPerTemperature) {
        this.nbIterationPerTemperature = nbIterationPerTemperature;
    }

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        List<Bin> currentSolution = bins;
        List<Bin> newSolution;

        double currentWeight = Utils.getBinPackingWeight(currentSolution);
        double bestWeight = Utils.getBinPackingWeight(bestSolution);

        for (int i = 0; i < nbIterationPerTemperature; i++) {
            // Create new neighbour solution
            newSolution = neighboursCalculator.randomNeighbour(currentSolution);

            // Get the weights of the solutions
            double newWeight = Utils.getBinPackingWeight(newSolution);

            // Decide if we should accept the new solution
            if (acceptanceProbability(currentWeight, newWeight, temperature) > Math.random()) {
                currentSolution = newSolution;
                currentWeight = newWeight;
            }

            // Keep track of the best solution
            if (currentWeight < bestWeight) {
                bestSolution = currentSolution;
                bestWeight = currentWeight;
            }
        }

        // Cool down the system
        temperature *= 1 - coolingRate;

        if (temperature <= 1) {
            isAlgorithmRunning = false;
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
