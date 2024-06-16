package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tp.optimisation.utils.Rectangle;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

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
}
