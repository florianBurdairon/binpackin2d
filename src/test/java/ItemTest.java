import org.junit.jupiter.api.Test;
import tp.optimisation.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    @Test
    public void testRotate(){
        Item item = new Item(0, 2, 5);
        item.rotate();
        assertEquals(5, item.getWidth());
        assertEquals(2, item.getHeight());
    }
}
