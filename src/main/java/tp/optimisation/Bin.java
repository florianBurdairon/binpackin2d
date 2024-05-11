package tp.optimisation;

import tp.optimisation.utils.Guillotine;
import tp.optimisation.utils.Position;
import tp.optimisation.utils.Rectangle;

import java.util.*;

public class Bin implements Cloneable {
    private final int width;
    private final int height;

    private Map<Item, Position> items;
    private ArrayList<Guillotine> guillotines;
    private ArrayList<Rectangle> emptySpaces;

    public Bin(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new HashMap<>();
        this.guillotines = new ArrayList<>();
        emptySpaces = new ArrayList<>();
        emptySpaces.add(new Rectangle(0, 0, width, height));
    }

    private void addItemAt(Item item, Position position) {
        items.put(item, position);
//        Guillotine g = new Guillotine(Guillotine.Direction.Vertical, position.getX() + item.width);
//
//        // Get the empty space where the item is placed and replace it by the new empty spaces
//        Rectangle itemRectangle = new Rectangle(position.getX(), position.getY(), item.width, item.height);
//        List<Rectangle> emptySpacesToRemove = new ArrayList<>();
//        List<Rectangle> emptySpacesToAdd = new ArrayList<>();
//        for (Rectangle r : emptySpaces) {
//            if (Utils.isItemOverlapping(r, itemRectangle)) {
//                emptySpacesToRemove.add(r);
//                List<Rectangle> newRectangles = r.cutWithGuillotine(g);
//                Rectangle rectangleWithoutItem = new Rectangle(newRectangles.get(0).getX(), newRectangles.get(0).getY() + itemRectangle.getHeight(), newRectangles.get(0).getWidth(), newRectangles.get(0).getHeight() - itemRectangle.getHeight());
//                emptySpacesToAdd.add(rectangleWithoutItem);
//                emptySpacesToAdd.add(newRectangles.get(1));
//                break;
//            }
//        }
//        emptySpaces.removeAll(emptySpacesToRemove);
//        emptySpaces.addAll(emptySpacesToAdd);
//
//        guillotines.add(g);
    }

    /*public boolean addItem(Item item) {
        Position position = canFit(item);
        if (position != null) {
            addItemAt(item, position);
            return true;
        }
        return false;
    }*/

    // Reorganize the items in the bin each time a new item is added using FFF algorithm
    public boolean addItem(Item item) {
        ArrayList<Item> items = new ArrayList<>(this.items.keySet());
        items.add(item);
        HashMap<Item, Position> itemsPositions = FFF(items);
        if (itemsPositions == null) {
            return false;
        } else {
            this.items = itemsPositions;
            return true;
        }
    }

    // Finite First Fit algorithm
    public HashMap<Item, Position> FFF(List<Item> items) {
        List<Bin> bins = new ArrayList<>();
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
                    itemPositions.put(item, level);
                    level.setX(level.getX() + item.width);
                    placed = true;
                    break;
                }
            }
            // If it still does not fit, create new level of height = item.height
            if (!placed) {
                // If sum of all level.height > bin.height = return null
                currentMaxHeight += item.getHeight();
                if (currentMaxHeight > height) {
                    return null;
                }
                levelsPosition.add(new Position(0, currentMaxHeight));
            }
        }
        // else, return HashMap<Item, Position>
        return itemPositions;
    }

    public void removeItem(Item item) {
        items.remove(item);
//        Bin reorganizedBin = new Bin(width, height);
//        for (Item i : items.keySet()) {
//            reorganizedBin.addItemAt(i, items.get(i));
//        }
//        items.clear();
//        guillotines.clear();
//        emptySpaces.clear();
//        emptySpaces.addAll(reorganizedBin.emptySpaces);
//        items.putAll(reorganizedBin.items);
//        guillotines.addAll(reorganizedBin.guillotines);
    }

    public void addItemHorizontally(Item item) {
        int widthAvailable = width;
        for (Item i : items.keySet()) {
            widthAvailable -= i.getWidth();
        }
        addItemAt(item, new Position(width - widthAvailable, 0));
    }

    public Position canFit(Item item) {
        for (Rectangle r : emptySpaces) {
            if (r.getWidth() >= item.width && r.getHeight() >= item.height) {
                return new Position(r.getX(), r.getY());
            }
        }
        return null;
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

    public List<Rectangle> getEmptySpaces() {
        return emptySpaces;
    }

    @Override
    public Bin clone() {
        try {
            Bin clone = (Bin) super.clone();
            clone.items = (Map<Item, Position>) ((HashMap<Item, Position>)items).clone();
            clone.guillotines = (ArrayList<Guillotine>) guillotines.clone();
            clone.emptySpaces = (ArrayList<Rectangle>) emptySpaces.clone();
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

        for(Rectangle r : emptySpaces) {
            weight -= (r.getArea() / surface);
        }

        weight = Math.abs(weight - 0.5f) * 2;

        if(items.size() == 2) {
            weight *= 0.9f;
        } else if (items.size() == 1) {
            weight *= 0.5f;
        }

        return weight;
    }
}
