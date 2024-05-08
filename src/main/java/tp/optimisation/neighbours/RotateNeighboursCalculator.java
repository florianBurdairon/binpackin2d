package tp.optimisation.neighbours;

import tp.optimisation.Dataset;
import tp.optimisation.Item;

import java.util.ArrayList;
import java.util.List;

public class RotateNeighboursCalculator extends AbstractNeighboursCalculator {
    @Override
    public List<Dataset> calcNeighbours(Dataset dataset) {
        List<Dataset> neighbours = new ArrayList<>();

        List<Item> originalItems = dataset.getItems();
        for (int i = 0; i < originalItems.size(); i++) {
            List<Item> ithNeighboursItems = new ArrayList<>(List.copyOf(originalItems));
            ithNeighboursItems.set(i, originalItems.get(i).asRotated());
            Dataset ithNeighbour = new Dataset(dataset.getName(), dataset.getComment(), ithNeighboursItems.size(), dataset.getBinWidth(), dataset.getBinHeight());
            ithNeighbour.setItems(ithNeighboursItems);
            neighbours.add(ithNeighbour);
        }

        return neighbours;
    }
}
