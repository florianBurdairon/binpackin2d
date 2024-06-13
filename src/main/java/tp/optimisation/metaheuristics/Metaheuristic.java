package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.utils.Utils;

import java.util.List;

public abstract class Metaheuristic {
    private int nbIterations = 0;
    private int maxIterations = 1000;
    private double epsilon = 0.0005;
    protected boolean isAlgorithmRunning = true;

    protected AbstractNeighboursCalculator neighboursCalculator;

    protected List<Bin> bestSolution = null;

    private float lastSolutionWeight = Integer.MAX_VALUE;

    public Metaheuristic(AbstractNeighboursCalculator neighboursCalculator) {
        this.neighboursCalculator = neighboursCalculator;
    }

    public void reset() {
        bestSolution = null;
        nbIterations = 0;
        isAlgorithmRunning = true;
        lastSolutionWeight = Integer.MAX_VALUE;
    }

    public List<Bin> nextIteration(List<Bin> bins) {
        if (bestSolution == null) {
            bestSolution = bins;
        }
        List<Bin> newSolution = getNextIteration(bins);
        if (Math.abs(Utils.getBinPackingWeight(newSolution) - lastSolutionWeight) < epsilon) {
            isAlgorithmRunning = false;
            System.out.println("Algorithm finished : Not enough change in score");
            return bestSolution;
        }
        if (nbIterations >= maxIterations) {
            isAlgorithmRunning = false;
            System.out.println("Algorithm finished : Reach iterations amount limit");
            return bestSolution;
        }
        if (isAlgorithmRunning) {
            addOneIteration();
            lastSolutionWeight = Utils.getBinPackingWeight(newSolution);
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
    public void setMaxIterations(int maxIterations) { this.maxIterations = maxIterations; }
    public int getMaxIterations() { return maxIterations; }
    public double getEpsilon() { return epsilon; }
    public void setEpsilon(double epsilon) { this.epsilon = epsilon; }
}
