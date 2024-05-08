import org.junit.jupiter.api.Test;
import tp.optimisation.Item;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    public void testRotate(){
        Item item = new Item(0, 2, 5);
        item.rotate();
        assertEquals(5, item.getWidth());
        assertEquals(2, item.getHeight());
    }

    @Test
    public void testAsRotated() {
        Item item = new Item(0, 2, 5);
        Item rotated = item.asRotated();
        assertEquals(5, rotated.getWidth());
        assertEquals(2, rotated.getHeight());
        assertNotEquals(item, rotated); // Different instances
        assertFalse(item.equals(rotated)); // with different values
    }
}
