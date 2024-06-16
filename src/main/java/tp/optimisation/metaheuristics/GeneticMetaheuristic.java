package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.neighbours.NeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GeneticMetaheuristic extends Metaheuristic {
    private final float MUTATION_RATE = 0.2f;

    private final Random random = new Random();
    private float nbBestSolutions = 5.0f;
    private float mutationRate = MUTATION_RATE;

    private List<List<Bin>> population = new ArrayList<>();

    public GeneticMetaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        super(neighboursCalculator);
    }

    @Override
    public void reset() {
        population.clear();
        mutationRate = MUTATION_RATE;
        super.reset();
    }

    public float getNbBestSolutions() {
        return nbBestSolutions;
    }

    public void setNbBestSolutions(float nbBestSolutions) {
        this.nbBestSolutions = nbBestSolutions;
    }

    public float getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(float mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        // Create x random solutions (if no population)
        if (population.isEmpty()) {
            neighboursCalculator = new NeighboursCalculator();
            population = neighboursCalculator.calcNeighbours(bins);
        }

        // Sort them by fitness
        population.sort(Comparator.comparing(Utils::getBinPackingWeight));

        // Pick 20% best
        int bestPopulationSize = (int) (population.size() / nbBestSolutions);
        List<List<Bin>> bestPopulation = population.subList(0, bestPopulationSize);

        // Duplicate them 5 times (to reach initial population count)
        List<List<Bin>> newPopulation = new ArrayList<>();
        for (int i = 0; i < nbBestSolutions; i++) {
            newPopulation.addAll(bestPopulation);
        }

        // Random mutate
        population.clear();
        for (List<Bin> solution : newPopulation) {
            if (random.nextFloat() < mutationRate) {
                population.add(neighboursCalculator.randomNeighbour(solution));
            } else {
                population.add(solution);
            }
        }

        // Return best current solution but keep others in memory
        List<Bin> solution = population.stream().max(Comparator.comparing(Utils::getBinPackingWeight)).orElse(bins);
        if (Utils.getBinPackingWeight(solution) < Utils.getBinPackingWeight(bestSolution)) {
            bestSolution = solution;
        }
        return solution;
    }
}
