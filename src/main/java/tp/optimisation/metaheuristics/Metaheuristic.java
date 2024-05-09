package tp.optimisation.metaheuristics;

import tp.optimisation.Bin;

import java.util.List;

public abstract class Metaheuristic {
    protected boolean isAlgorithmRunning = true;
    public abstract List<Bin> getNextIteration(List<Bin> bins);
    public boolean isAlgorithmRunning() {
        return isAlgorithmRunning;
    }
}
