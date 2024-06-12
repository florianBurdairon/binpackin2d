package tp.optimisation;

import tp.optimisation.utils.Guillotine;
import tp.optimisation.utils.Position;

import java.util.*;

public class Bin implements Cloneable {
    private final int width;
    private final int height;

    private final int id;
    private static int nextId = 0;

    private Map<Item, Position> items;
    private ArrayList<Guillotine> guillotines;

    public Bin(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new HashMap<>();
        this.guillotines = new ArrayList<>();
        this.id = nextId++;
    }

    public Bin(List<Item> items, int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new HashMap<>();
        this.guillotines = new ArrayList<>();
        this.id = nextId++;
        for (Item item : items) {
            addItem(item);
        }
    }

    private void addItemAt(Item item, Position position) {
        items.put(item, position);
    }

    // Reorganize the items in the bin each time a new item is added using FFDH algorithm
    public boolean addItem(Item item) {
        ArrayList<Item> items = new ArrayList<>(this.items.keySet());
        items.add(item);
        HashMap<Item, Position> itemsPositions = FFDH(items);
        if (itemsPositions == null) {
            return false;
        } else {
            this.items = itemsPositions;
            return true;
        }
    }

    // First Fit Decreasing Height algorithm
    public HashMap<Item, Position> FFDH(List<Item> items) {
        HashMap<Item, Position> itemPositions = new HashMap<>();

        // Sort by decreasing height
        items.sort(Comparator.comparing(Item::getHeight).reversed());

        // Create a first level of height = maxHeight
        List<Position> levelsPosition = new ArrayList<>();
        int currentMaxHeight = 0;

        // Try to fit as much as possible on the right in this level
        for (Item item : items) {
            // If it fits, add it to the level
            // If it does not fit, check others levels
            boolean placed = false;
            for (Position level : levelsPosition) {
                if (level.getX() + item.width <= width) {
                    itemPositions.put(item, new Position(level.getX(), level.getY()));
                    level.setX(level.getX() + item.width);
                    placed = true;
                    break;
                }
            }
            // If it still does not fit, create new level of height = item.height
            if (!placed) {
                // If sum of all level.height > bin.height = return null
                if (currentMaxHeight + item.getHeight() > height) {
                    return null;
                }
                levelsPosition.add(new Position(item.width, currentMaxHeight));
                itemPositions.put(item, new Position(0, currentMaxHeight));
                currentMaxHeight += item.getHeight();
            }
        }
        // else, return HashMap<Item, Position>
        return itemPositions;
    }

    public void removeItemById(int id) {
        items.keySet().stream().filter(i -> i.getId() == id).findFirst().ifPresent(this::removeItem);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItemHorizontally(Item item) {
        int widthAvailable = width;
        for (Item i : items.keySet()) {
            widthAvailable -= i.getWidth();
        }
        addItemAt(item, new Position(width - widthAvailable, 0));
    }

    public boolean canFitHorizontally(Item item) {
        int widthAvailable = width;
        for (Item i : items.keySet()) {
            widthAvailable -= i.getWidth();
        }
        return item.getWidth() <= widthAvailable;
    }

    public int getId() {
        return id;
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

    public int getItemsCount() {
        return items.size();
    }

    public List<Guillotine> getGuillotines() {
        return guillotines;
    }

    @Override
    public Bin clone() {
        try {
            Bin clone = (Bin) super.clone();
            clone.items = (Map<Item, Position>) ((HashMap<Item, Position>)items).clone();
            clone.guillotines = (ArrayList<Guillotine>) guillotines.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public float getWeight() {
        float surface = width * height;
        float weight = 1;

        for(Item i : items.keySet()) {
            weight -= (i.getWidth() * i.getHeight() / surface);
        }

        weight = (float) Math.sqrt(weight);

        return weight;
    }

    public List<Integer> getItemIdList() {
        List<Integer> ids = new ArrayList<>();
        for (Item i : items.keySet()) {
            ids.add(i.getId());
        }
        return ids;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Bin b)
            return b.id == this.id;
        return false;
    }
}
