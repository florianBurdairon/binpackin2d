import org.junit.jupiter.api.Test;
import tp.optimisation.Dataset;
import tp.optimisation.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatasetTest {

    private final String datasetString = """
                NAME: test-dataset.bp2d
                COMMENT: testing dataset
                NB_ITEMS: 3
                BIN_WIDTH: 250
                BIN_HEIGHT: 250
                
                ITEMS [id width height]:
                1 167 184
                2 114 118
                3 167 152""";

    private final String[] datasetStringValues = new String[]{"test-dataset.bp2d", "testing dataset", "3", "250", "250"};
    private final Item[] datasetStringItems = new Item[]{
            new Item(1, 167, 184),
            new Item(2, 114, 118),
            new Item(3, 167, 152),
    };

    private final String[] datasetFileValues = new String[]{"test-dataset-file.bp2d", "testing dataset from file", "4", "150", "250"};
    private final Item[] datasetFileItems = new Item[]{
            new Item(4, 83, 140),
            new Item(5, 70, 86),
            new Item(6, 143, 166),
            new Item(7, 120, 160),
    };

    @Test
    public void testFromString() {
        // When
        Dataset dataset = Dataset.fromString(datasetString);

        // Then
        this.assertDatasetValues(dataset, datasetStringValues);
        this.assertDatasetItems(dataset, datasetStringItems);
        assertEquals(datasetString, dataset.toString());
    }

    @Test
    public void testFromFile() {
        // When
        String datasetFilename = "src/test/java/resources/test-dataset-file.bp2d";
        Dataset dataset = Dataset.fromFile(datasetFilename);

        // Then
        this.assertDatasetValues(dataset, datasetFileValues);
        this.assertDatasetItems(dataset, datasetFileItems);
        try {
            assertEquals(new String(Files.readAllBytes(Paths.get(datasetFilename))), dataset.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assertDatasetValues(Dataset dataset, String[] values) {
        assertEquals(values[0], dataset.getName());
        assertEquals(values[1], dataset.getComment());
        assertEquals(Integer.parseInt(values[2]), dataset.getNbItems());
        assertEquals(Integer.parseInt(values[3]), dataset.getBinWidth());
        assertEquals(Integer.parseInt(values[4]), dataset.getBinHeight());
    }

    private void assertDatasetItems(Dataset dataset, Item[] expectedItems) {
        List<Item> items = dataset.getItems();
        assertEquals(expectedItems.length, items.size());
        for(int i = 0; i < items.size(); i++) {
            assertEquals(expectedItems[i].getId(), items.get(i).getId());
            assertEquals(expectedItems[i].getWidth(), items.get(i).getWidth());
            assertEquals(expectedItems[i].getHeight(), items.get(i).getHeight());
        }
    }
}
