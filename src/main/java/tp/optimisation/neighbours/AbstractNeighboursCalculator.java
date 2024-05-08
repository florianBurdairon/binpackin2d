package tp.optimisation.neighbours;

import tp.optimisation.Dataset;

import java.util.List;

public abstract class AbstractNeighboursCalculator {
    public abstract List<Dataset> calcNeighbours(Dataset dataset);
}
