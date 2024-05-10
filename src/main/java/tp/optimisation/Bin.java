package tp.optimisation;

import tp.optimisation.utils.Guillotine;
import tp.optimisation.utils.Position;
import tp.optimisation.utils.Rectangle;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Guillotine g = new Guillotine(Guillotine.Direction.Vertical, position.getX() + item.width);

        // Get the empty space where the item is placed and replace it by the new empty spaces
        Rectangle itemRectangle = new Rectangle(position.getX(), position.getY(), item.width, item.height);
        List<Rectangle> emptySpacesToRemove = new ArrayList<>();
        List<Rectangle> emptySpacesToAdd = new ArrayList<>();
        for (Rectangle r : emptySpaces) {
            if (Utils.isItemOverlapping(r, itemRectangle)) {
                emptySpacesToRemove.add(r);
                List<Rectangle> newRectangles = r.cutWithGuillotine(g);
                Rectangle rectangleWithoutItem = new Rectangle(newRectangles.get(0).getX(), newRectangles.get(0).getY() + itemRectangle.getHeight(), newRectangles.get(0).getWidth(), newRectangles.get(0).getHeight() - itemRectangle.getHeight());
                emptySpacesToAdd.add(rectangleWithoutItem);
                emptySpacesToAdd.add(newRectangles.get(1));
                break;
            }
        }
        emptySpaces.removeAll(emptySpacesToRemove);
        emptySpaces.addAll(emptySpacesToAdd);

        guillotines.add(g);
    }

    public boolean addItem(Item item) {
        Position position = canFit(item);
        if (position != null) {
            addItemAt(item, position);
            return true;
        }
        return false;
    }

    public void removeItem(Item item) {
        items.remove(item);
        Bin reorganizedBin = new Bin(width, height);
        for (Item i : items.keySet()) {
            reorganizedBin.addItem(i);
        }
        items.clear();
        guillotines.clear();
        emptySpaces.clear();
        emptySpaces.addAll(reorganizedBin.emptySpaces);
        items.putAll(reorganizedBin.items);
        guillotines.addAll(reorganizedBin.guillotines);
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

    @Override
    public Bin clone() {
        try {
            Bin clone = (Bin) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
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

        weight = Math.abs(weight - 0.5f);

        if(items.size() == 2) {
            weight *= 0.9f;
        } else if (items.size() == 1) {
            weight *= 0.5f;
        }

        return weight;
    }
}
