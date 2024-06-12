package tp.optimisation.neighbours;

import tp.optimisation.Bin;
import tp.optimisation.Item;

import java.util.ArrayList;
import java.util.List;

public class SwitchNeighboursCalculator extends AbstractNeighboursCalculator {
    @Override
    public List<List<Bin>> calcNeighbours(List<Bin> bins) {
        List<List<Bin>> neighbours = new ArrayList<>();

        for (int i = 0; i < bins.size(); i++) {
            for (int j = 0; j < bins.size(); j++) {
                if (i != j) {
                    for (int k = 0; k < bins.get(i).getItems().size(); k++) {
                        List<Bin> b = new ArrayList<>(bins.stream().map(Bin::clone).toList());
                        Item item = (Item) b.get(i).getItems().keySet().toArray()[k];
                        if (b.get(j).addItem(item)) {
                            b.get(i).removeItem(item);
                            if (b.get(i).isEmpty()) {
                                b.remove(i);
                            }

                            neighbours.add(b);
                        }

                        b = new ArrayList<>(bins.stream().map(Bin::clone).toList());
                        item = (Item) b.get(i).getItems().keySet().toArray()[k];
                        if (b.get(j).addItem(item.asRotated())) {
                            b.get(i).removeItem(item);
                            if (b.get(i).isEmpty()) {
                                b.remove(i);
                            }

                            neighbours.add(b);
                        }

                        if (j > i) {
                            b = new ArrayList<>(bins.stream().map(Bin::clone).toList());
                            Bin bin1 = b.get(i);
                            Bin bin2 = b.get(j);
                            Item item1 = (Item) bin1.getItems().keySet().toArray()[k];
                            for (int l = 0; l < bin2.getItems().size(); l++) {
                                Item item2 = (Item) bin2.getItems().keySet().toArray()[l];
                                bin1.removeItemById(item1.getId());
                                bin2.removeItemById(item2.getId());
                                // No rotation
                                if (bin1.addItem(item2) && bin2.addItem(item1)) {
                                    neighbours.add(new ArrayList<>(b.stream().map(Bin::clone).toList()));
                                }
                                bin1.removeItemById(item2.getId());
                                bin2.removeItemById(item1.getId());
                                // Item2 as rotated
                                if(bin1.addItem(item2.asRotated()) && bin2.addItem(item1)) {
                                    neighbours.add(new ArrayList<>(b.stream().map(Bin::clone).toList()));
                                }
                                bin1.removeItemById(item2.getId());
                                bin2.removeItemById(item1.getId());
                                // Item1 as rotated
                                if(bin1.addItem(item2) && bin2.addItem(item1.asRotated())) {
                                    neighbours.add(new ArrayList<>(b.stream().map(Bin::clone).toList()));
                                }
                                bin1.removeItemById(item2.getId());
                                bin2.removeItemById(item1.getId());
                                // Both as rotated
                                if(bin1.addItem(item2.asRotated()) && bin2.addItem(item1.asRotated())) {
                                    neighbours.add(new ArrayList<>(b.stream().map(Bin::clone).toList()));
                                }
                                bin1.removeItemById(item2.getId());
                                bin2.removeItemById(item1.getId());
                                bin1.addItem(item1);
                                bin2.addItem(item2);
                            }
                        }
                    }
                }
            }
        }

        return neighbours;
    }
}
