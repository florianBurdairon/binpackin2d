package tp.optimisation.neighbours;

import tp.optimisation.Bin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class AbstractNeighboursCalculator {
    public List<List<Bin>> calcNeighbours(List<Bin> bins) {
        List<List<Bin>> neighbours = new ArrayList<>();

        for (int i = 0; i < bins.size(); i++) {
            for (int j = 0; j < bins.size(); j++) {
                if (i != j) {
                    neighbours.addAll(calcNeighbours(i, j, bins));
                }
            }
        }

        return neighbours;
    }

    protected abstract List<List<Bin>> calcNeighbours(int i, int j, List<Bin> bins);

    public List<Bin> randomNeighbour(List<Bin> bins) {
        List<Integer> indexes = randomIndexes(bins.size());
        List<Integer> indexes2 = randomIndexes(bins.size());
        for (int i : indexes) {
            for (int j : indexes2) {
                if (i != j) {
                    List<List<Bin>> neighbours = calcNeighbours(i, j, bins);
                    if (!neighbours.isEmpty()) {
                        return neighbours.get(new Random().nextInt(neighbours.size()));
                    }
                }
            }
        }
        return bins;
    }

    private List<Integer> randomIndexes(int size) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);
        return indexes;
    }
}
