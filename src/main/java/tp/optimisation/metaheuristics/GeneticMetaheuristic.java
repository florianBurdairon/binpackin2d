package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.neighbours.SwitchNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GeneticMetaheuristic extends Metaheuristic {
    private Random random = new Random();
    private final float nbBestSolutions = 5.0f;
    private final float mutationRate = 0.5f;

    private List<List<Bin>> population = new ArrayList<>();

    private AbstractNeighboursCalculator neighboursCalculator;

    @Override
    public List<Bin> getNextIteration(List<Bin> bins) {
        // Create x random solutions (if no population)
        if (population.isEmpty()) {
            neighboursCalculator = new SwitchNeighboursCalculator();
            population = neighboursCalculator.calcNeighbours(bins);
            System.out.println("Nb population: " + population.size());
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
                List<List<Bin>> newSolution = neighboursCalculator.calcNeighbours(solution);
                population.add(newSolution.stream().max(Comparator.comparing(Utils::getBinPackingWeight)).orElse(solution));
            } else {
                population.add(solution);
            }
        }

        float sum = 0.0f;
        for (List<Bin> solution : population) {
            sum += Utils.getBinPackingWeight(solution);
        }
        System.out.println("Mean of weights: " + (sum / nbBestSolutions));

        // Return best solution but keep others in memory
        return population.stream().max(Comparator.comparing(Utils::getBinPackingWeight)).orElse(bins);
    }
}
