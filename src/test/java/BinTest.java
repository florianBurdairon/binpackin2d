import org.junit.jupiter.api.Test;
import tp.optimisation.Bin;
import tp.optimisation.Item;
import tp.optimisation.utils.Position;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinTest {

    @Test
    public void testFFF() {
        Bin bin = new Bin(100, 100);
        Item i1 = new Item(0, 30, 50);

        assertTrue(bin.addItem(i1));

        Map<Item, Position> items = bin.getItems();
        assertTrue(items.containsKey(i1));
        assertTrue(items.get(i1).equals(new Position(0, 0)));

        Item i2 = new Item(1, 30, 45);
        assertTrue(bin.addItem(i2));
        items = bin.getItems();
        assertTrue(items.containsKey(i1) && items.containsKey(i2));
        assertTrue(items.get(i1).equals(new Position(0, 0)));
        assertTrue(items.get(i2).equals(new Position(30, 0)));

        Item i3 = new Item(2, 60, 30);
        assertTrue(bin.addItem(i3));
        items = bin.getItems();
        assertTrue(items.containsKey(i1) && items.containsKey(i2) && items.containsKey(i3));
        assertTrue(items.get(i1).equals(new Position(0, 0)));
        assertTrue(items.get(i2).equals(new Position(30, 0)));
        assertTrue(items.get(i3).equals(new Position(0, 50)));

        Item i4 = new Item(3, 20, 60);
        assertTrue(bin.addItem(i4));
        items = bin.getItems();
        assertTrue(items.containsKey(i1) && items.containsKey(i2) && items.containsKey(i3) && items.containsKey(i4));
        assertTrue(items.get(i1).equals(new Position(20, 0)));
        assertTrue(items.get(i2).equals(new Position(50, 0)));
        assertTrue(items.get(i3).equals(new Position(0, 60)));
        assertTrue(items.get(i4).equals(new Position(0, 0)));

        Item i5 = new Item(4, 60, 20);
        assertFalse(bin.addItem(i5));
        items = bin.getItems();
        assertFalse(items.containsKey(i5));
    }
}
