import org.junit.jupiter.api.Test;
import tp.optimisation.Dataset;
import tp.optimisation.utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    @Test
    public void testMinimumBinPacking() {
        Dataset dataset = Dataset.fromFile("src/test/java/resources/test-dataset-file.bp2d");
        assertEquals(2, Utils.minimumBinPacking(dataset));
    }
}
