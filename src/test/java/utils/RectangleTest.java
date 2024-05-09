package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tp.optimisation.utils.Guillotine;
import tp.optimisation.utils.Rectangle;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    public static Stream<Arguments> testIsCutByGuillotineValues() {
        return Stream.of(
                Arguments.of(new Rectangle(0, 0, 10, 10), new Guillotine(Guillotine.Direction.Vertical, 5), true),
                Arguments.of(new Rectangle(10, 0, 10, 10), new Guillotine(Guillotine.Direction.Vertical, 5), false),
                Arguments.of(new Rectangle(0, 0, 4, 10), new Guillotine(Guillotine.Direction.Vertical, 5), false),
                Arguments.of(new Rectangle(0, 0, 5, 10), new Guillotine(Guillotine.Direction.Vertical, 5), false),
                Arguments.of(new Rectangle(5, 0, 10, 10), new Guillotine(Guillotine.Direction.Vertical, 5), false),

                Arguments.of(new Rectangle(0, 0, 10, 10), new Guillotine(Guillotine.Direction.Horizontal, 5), true),
                Arguments.of(new Rectangle(0, 10, 10, 10), new Guillotine(Guillotine.Direction.Horizontal, 5), false),
                Arguments.of(new Rectangle(0, 0, 10, 4), new Guillotine(Guillotine.Direction.Horizontal, 5), false),
                Arguments.of(new Rectangle(0, 0, 10, 5), new Guillotine(Guillotine.Direction.Horizontal, 5), false),
                Arguments.of(new Rectangle(0, 5, 10, 10), new Guillotine(Guillotine.Direction.Horizontal, 5), false)
        );
    }



    @ParameterizedTest(name = "testIsCutByGuillotine")
    @MethodSource("testIsCutByGuillotineValues")
    public void testIsCutByGuillotine(Rectangle r, Guillotine g, boolean result){
        assertEquals(r.isCutByGuillotine(g), result);
    }

    public static Stream<Arguments> testEqualsValues() {
        return Stream.of(
                Arguments.of(new Rectangle(0, 0, 10, 10), true),
                Arguments.of(new Rectangle(1, 0, 10, 10), false),
                Arguments.of(new Rectangle(0, 1, 10, 10), false),
                Arguments.of(new Rectangle(0, 0, 9, 10), false),
                Arguments.of(new Rectangle(0, 0, 10, 9), false),
                Arguments.of(new Rectangle(1, 1, 9, 9), false)
        );
    }

    @ParameterizedTest(name = "testEquals")
    @MethodSource("testEqualsValues")
    public void testEquals(Rectangle r, boolean result) {
        Rectangle r1 = new Rectangle(0, 0, 10, 10);
        assertEquals(r.equals(r1), result);
        assertNotEquals(r, r1);
    }

    public static Stream<Arguments> testCutWithGuillotineValues() {
        return Stream.of(
                Arguments.of(new Guillotine(Guillotine.Direction.Vertical, 5), new Rectangle(0, 0, 5, 10), new Rectangle(5, 0, 5, 10)),
                Arguments.of(new Guillotine(Guillotine.Direction.Vertical, 8), new Rectangle(0, 0, 8, 10), new Rectangle(8, 0, 2, 10)),
                Arguments.of(new Guillotine(Guillotine.Direction.Horizontal, 5), new Rectangle(0, 0, 10, 5), new Rectangle(0, 5, 10, 5)),
                Arguments.of(new Guillotine(Guillotine.Direction.Horizontal, 8), new Rectangle(0, 0, 10, 8), new Rectangle(0, 8, 10, 2))
        );
    }

    @ParameterizedTest(name = "testCutWithGuillotine")
    @MethodSource("testCutWithGuillotineValues")
    public void testCutWithGuillotine(Guillotine g, Rectangle r1, Rectangle r2){
        Rectangle r = new Rectangle(0, 0, 10, 10);

        List<Rectangle> cuts = r.cutWithGuillotine(g);

        assertTrue(cuts.getFirst().equals(r1));
        assertTrue(cuts.getLast().equals(r2));
    }
}
