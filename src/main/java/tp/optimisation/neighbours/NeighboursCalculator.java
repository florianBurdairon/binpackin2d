package tp.optimisation.neighbours;

import tp.optimisation.Bin;
import tp.optimisation.Item;

import java.util.ArrayList;
import java.util.List;

public class NeighboursCalculator extends AbstractNeighboursCalculator {
    @Override
    public List<List<Bin>> calcNeighbours(List<Bin> bins) {
        List<List<Bin>> neighbours = new ArrayList<>();

        for (int i = 0; i < bins.size(); i++) {
            for (int k = 0; k < bins.get(i).getItems().size(); k++) {

                for (int j = 0; j < bins.size(); j++) {
                    if (i != j) {
                        List<Bin> b = new ArrayList<>(bins.stream().map(Bin::clone).toList());
                        Item item = (Item) b.get(i).getItems().keySet().toArray()[k];
                        if (b.get(j).addItem(item)) {
                            b.get(i).removeItem(item);
                            if (b.get(i).isEmpty()) {
                                b.remove(i);
                            }

                            neighbours.add(b);
                        }
                    }
                }
            }
        }

        return neighbours;
    }
}
