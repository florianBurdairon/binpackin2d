package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tp.optimisation.Dataset;
import tp.optimisation.Item;
import tp.optimisation.utils.Position;
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
    public void testIsItemOverlapping(Item item1, Position<Integer> position1, Item item2, Position<Integer> position2, boolean expected) {
        assertEquals(expected, Utils.isItemOverlapping(item1, position1, item2, position2));
    }

    private static Stream<Arguments> isItemOverlappingValues() {
        return Stream.of(
                // Outside
                Arguments.of(new Item(0, 1, 1), new Position<>(0, 0), new Item(0, 1, 1), new Position<>(3, 3), false),
                // Completely Inside
                Arguments.of(new Item(0, 3, 3), new Position<>(0, 0), new Item(0, 1, 1), new Position<>(1, 1), true),
                Arguments.of(new Item(0, 1, 1), new Position<>(1, 1), new Item(0, 3, 3), new Position<>(0, 0), true),
                // Half Inside
                Arguments.of(new Item(0, 2, 4), new Position<>(0, 0), new Item(0, 2, 2), new Position<>(1, 1), true),
                // Quarter Inside
                Arguments.of(new Item(0, 2, 2), new Position<>(0, 0), new Item(0, 2, 2), new Position<>(1, 1), true)
        );
    }
}
