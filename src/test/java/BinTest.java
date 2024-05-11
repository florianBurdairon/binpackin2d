import org.junit.jupiter.api.Test;
import tp.optimisation.Bin;
import tp.optimisation.Item;
import tp.optimisation.utils.Position;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinTest {

    @Test
    public void testFFF() {
        Bin bin = new Bin(200, 200);
        Item i1 = new Item(0, 100, 50);
        bin.addItem(i1);

        //TODO: Do this test
        Map<Item, Position> items = bin.getItems();
        assertTrue(items.containsKey(i1));
        //assertTrue(items.get(i1).equals(new Position(0, 0)));
        //Item i2 = new Item(1, 100, 45);
        //bin.addItem(i2);
    }
}
