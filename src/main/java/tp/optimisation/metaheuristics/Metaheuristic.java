package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;

import java.util.List;

public abstract class Metaheuristic {
    private int nbIterations = 0;
    protected boolean isAlgorithmRunning = true;

    protected AbstractNeighboursCalculator neighboursCalculator;

    protected List<Bin> bestSolution = null;

    public Metaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        this.neighboursCalculator = neighboursCalculator;
    }

    public void reset() {
        bestSolution = null;
        nbIterations = 0;
        isAlgorithmRunning = true;
    }

    public List<Bin> nextIteration(List<Bin> bins) {
        if (bestSolution == null) {
            bestSolution = bins;
        }
        List<Bin> newSolution = getNextIteration(bins);
        if (isAlgorithmRunning) {
            addOneIteration();
            return newSolution;
        } else {
            System.out.println("Algorithm finished : Impossible to find better solution");
            return bestSolution;
        }
    }

    public abstract List<Bin> getNextIteration(List<Bin> bins);

    public boolean isAlgorithmRunning() {
        return isAlgorithmRunning;
    }
    protected void addOneIteration(){ nbIterations++; }
    public int getNbIterations() { return nbIterations; }
}
