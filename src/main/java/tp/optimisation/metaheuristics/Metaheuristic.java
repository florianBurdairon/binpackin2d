package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;

import java.util.List;

public abstract class Metaheuristic {
    private int nbIterations = 0;
    protected boolean isAlgorithmRunning = true;

    public List<Bin> nextIteration(List<Bin> bins) {
        if (isAlgorithmRunning) {
            addOneIteration();
            return getNextIteration(bins);
        } else {
            System.out.println("Impossible to find better neighbour");
            return bins;
        }
    }

    public abstract List<Bin> getNextIteration(List<Bin> bins);

    public boolean isAlgorithmRunning() {
        return isAlgorithmRunning;
    }
    protected void addOneIteration(){ nbIterations++; }
    public int getNbIterations() { return nbIterations; }
}
