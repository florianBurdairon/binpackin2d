package neighbours;

import org.junit.jupiter.api.Test;
import tp.optimisation.Dataset;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RotateNeighboursCalculatorTest {
    @Test
    public void testCalcNeighbours() {
//        // Given
//        Dataset dataset = Dataset.fromFile("src/test/java/resources/test-dataset-file.bp2d");
//        AbstractNeighboursCalculator calculator = new NeighboursCalculator();
//
//        // When
//        List<Dataset> neighbours = calculator.calcNeighbours(dataset);
//
//        // Then
//        assertEquals(dataset.getItems().size(), neighbours.size());
//        for (int i = 0; i < neighbours.size(); i++) {
//            assertIsSimilarWithIthItemRotated(neighbours.get(i), dataset, i);
//        }
    }

    private void assertIsSimilarWithIthItemRotated(Dataset expected, Dataset actual, int i)
    {
        for (int j = 0; j < actual.getItems().size(); j++) {
            if (j != i) {
                assertTrue(actual.getItems().get(j).equals(expected.getItems().get(j)));
            } else {
                assertTrue(actual.getItems().get(j).equals(expected.getItems().get(j).asRotated()));
            }
        }
    }
}
