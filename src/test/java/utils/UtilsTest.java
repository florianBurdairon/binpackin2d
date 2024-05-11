package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tp.optimisation.Dataset;
import tp.optimisation.utils.Rectangle;
import tp.optimisation.utils.Utils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    @Test
    public void testMinimumBinPacking() {
        Dataset dataset = Dataset.fromFile("src/test/java/resources/test-dataset-file.bp2d");
        assertEquals(2, Utils.minimumBinPacking(dataset));
    }

    @ParameterizedTest(name = "isItemOverlapping")
    @MethodSource("isItemOverlappingValues")
    public void testIsItemOverlapping(Rectangle rectangle1, Rectangle rectangle2, boolean expected) {
        assertEquals(expected, Utils.isItemOverlapping(rectangle1, rectangle2));
    }

    private static Stream<Arguments> isItemOverlappingValues() {
        return Stream.of(
                // Outside
                Arguments.of(new Rectangle(0, 0, 1, 1), new Rectangle(3, 3, 1, 1), false),
                // Completely Inside
                Arguments.of(new Rectangle(0, 0, 3, 3), new Rectangle(1, 1, 1, 1), true),
                Arguments.of(new Rectangle(1, 1, 1, 1), new Rectangle(0, 0, 3, 3), true),
                // Half Inside
                Arguments.of(new Rectangle(0, 0, 2, 4), new Rectangle(1, 1, 2, 2), true),
                // Quarter Inside
                Arguments.of(new Rectangle(0, 0, 2, 2), new Rectangle(1, 1, 2, 2), true),
                // Same Width
                Arguments.of(new Rectangle(0, 0, 2, 2), new Rectangle(0, 0, 2, 1), true),
                // Same Height
                Arguments.of(new Rectangle(0, 0, 2, 2), new Rectangle(0, 0, 1, 2), true)
        );
    }
}
