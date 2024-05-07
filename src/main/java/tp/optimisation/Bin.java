package tp.optimisation;

import tp.optimisation.utils.Guillotine;
import tp.optimisation.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bin {
    private final int width;
    private final int height;

    private final Map<Item, Position> items;
    private final List<Guillotine> guillotines;

    public Bin(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new HashMap<>();
        this.guillotines = new ArrayList<>();
    }

    public void addItem(Item item, Position position) {
        items.put(item, position);
    }

    public void addItemHorizontally(Item item) {
        int widthAvailable = width;
        for (Item i : items.keySet()) {
            widthAvailable -= i.getWidth();
        }
        items.put(item, new Position(width - widthAvailable, 0));
    }

    public boolean canFit(Item item, Position position) {

        return false;
    }

    public boolean canFitHorizontally(Item item) {
        int widthAvailable = width;
        for (Item i : items.keySet()) {
            widthAvailable -= i.getWidth();
        }
        return item.getWidth() <= widthAvailable;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Item, Position> getItems() {
        return items;
    }

    public List<Guillotine> getGuillotines() {
        return guillotines;
    }
}
