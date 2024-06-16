package tp.optimisation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private final String name;
    private final String comment;
    private final int nbItems;
    private final int binWidth;
    private final int binHeight;

    private final ArrayList<Item> items;

    public Dataset(String name, String comment, int nbItems, int binWidth, int binHeight) {
        this.name = name;
        this.comment = comment;
        this.nbItems = nbItems;
        this.binWidth = binWidth;
        this.binHeight = binHeight;

        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getBinHeight() {
        return binHeight;
    }

    public int getBinWidth() {
        return binWidth;
    }

    public int getNbItems() {
        return nbItems;
    }

    public String getComment() {
        return comment;
    }

    public void setItems(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public static Dataset fromString(String s){
        s = s.replace("\r", "");
        String[] lines = s.split("\n");
        String name = lines[0].split(": ")[1];
        String comment = lines[1].split(": ")[1];
        int nbItems = Integer.parseInt(lines[2].split(": ")[1]);
        int binWidth = Integer.parseInt(lines[3].split(": ")[1]);
        int binHeight = Integer.parseInt(lines[4].split(": ")[1]);

        Dataset dataset = new Dataset(name, comment, nbItems, binWidth, binHeight);

        for (int i = 7; i < lines.length; i++) {
            String[] item = lines[i].split(" ");
            int id = Integer.parseInt(item[0]);
            int width = Integer.parseInt(item[1]);
            int height = Integer.parseInt(item[2]);

            dataset.items.add(new Item(id, width, height));
        }

        return dataset;
    }

    public static Dataset fromFile(String filePath) {
        String fileString = "";
        try {
            fileString = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException ignored) {}
        return fromString(fileString);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NAME: ").append(name).append("\n");
        sb.append("COMMENT: ").append(comment).append("\n");
        sb.append("NB_ITEMS: ").append(nbItems).append("\n");
        sb.append("BIN_WIDTH: ").append(binWidth).append("\n");
        sb.append("BIN_HEIGHT: ").append(binHeight).append("\n");
        sb.append("\n");
        sb.append("ITEMS [id width height]:");
        for (Item item : items) {
            sb.append("\n").append(item.toString());
        }
        return sb.toString();
    }
}
