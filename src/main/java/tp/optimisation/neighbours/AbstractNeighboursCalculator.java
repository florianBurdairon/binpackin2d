package tp.optimisation.neighbours;

import tp.optimisation.Bin;

import java.util.List;

public abstract class AbstractNeighboursCalculator {
    public abstract List<List<Bin>> calcNeighbours(List<Bin> bins);
}
