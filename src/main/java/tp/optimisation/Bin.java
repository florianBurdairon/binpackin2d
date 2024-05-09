package tp.optimisation;

import tp.optimisation.utils.Guillotine;
import tp.optimisation.utils.Position;
import tp.optimisation.utils.Rectangle;
import tp.optimisation.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bin {
    private final int width;
    private final int height;

    private final Map<Item, Position> items;
    private final List<Guillotine> guillotines;
    private final List<Rectangle> emptySpaces;

    public Bin(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new HashMap<>();
        this.guillotines = new ArrayList<>();
        emptySpaces = new ArrayList<>();
    }

    private void addItemAt(Item item, Position position) {
        items.put(item, position);
        Guillotine g = new Guillotine(Guillotine.Direction.Vertical, position.getX() + item.width);

        // Get the empty space where the item is placed and replace it by the new empty spaces
        Rectangle itemRectangle = new Rectangle(position.getX(), position.getY(), item.width, item.height);
        for (Rectangle r : emptySpaces) {
            if (Utils.isItemOverlapping(r, itemRectangle)) {
                emptySpaces.remove(r);
                List<Rectangle> newRectangles = r.cutWithGuillotine(g);
                for (Rectangle newRectangle : newRectangles) {
                    if (Utils.isItemOverlapping(newRectangle, itemRectangle))
                        newRectangle = new Rectangle(newRectangle.getX(), newRectangle.getY() + itemRectangle.getHeight(), newRectangle.getWidth(), newRectangle.getHeight() - itemRectangle.getHeight());
                    emptySpaces.add(newRectangle);
                }
                break;
            }
        }

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
    }

    public void addItemHorizontally(Item item) {
        int widthAvailable = width;
        for (Item i : items.keySet()) {
            widthAvailable -= i.getWidth();
        }
        addItemAt(item, new Position(width - widthAvailable, 0));
    }

    public Position canFit(Item item) {


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

    public Bin duplicate(){
        Bin b = new Bin(width, height);
        for(Map.Entry<Item, Position> item : getItems().entrySet()){
            b.addItemAt(item.getKey(), item.getValue());
        }
        return b;
    }
}
