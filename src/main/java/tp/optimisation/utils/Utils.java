package tp.optimisation.utils;

import tp.optimisation.Dataset;
import tp.optimisation.Item;

public class Utils {
    public static int minimumBinPacking(Dataset dataset) {
        int binArea = dataset.getBinWidth() * dataset.getBinHeight();
        int sumItemsArea = 0;
        for (Item item : dataset.getItems()) {
            sumItemsArea += item.getWidth() * item.getHeight();
        }
        return Math.ceilDiv(sumItemsArea, binArea);
    }

    public static boolean isItemOverlapping(Item item1, Position position1, Item item2, Position position2) {
        // Get the highest item
        Item highestItem = position1.getY() > position2.getY() ? item1 : item2;
        Position highestPosition = position1.getY() > position2.getY() ? position1 : position2;

        // Get the lowest item
        Item lowestItem = position1.getY() > position2.getY() ? item2 : item1;
        Position lowestPosition = position1.getY() > position2.getY() ? position2 : position1;

        // Check if the highest item is overlapping the lowest item
        // Check if the items intersect on the y-axis
        if (highestPosition.getY() + highestItem.getHeight() < lowestPosition.getY()) {
            return false;
        }
        // Check if the items intersect on the x-axis
        if (lowestPosition.getX() < highestPosition.getX() && lowestPosition.getX() + lowestItem.getWidth() > highestPosition.getX()) {
            return true;
        }
        if (lowestPosition.getX() < highestPosition.getX() + highestItem.getWidth() && lowestPosition.getX() + lowestItem.getWidth() > highestPosition.getX() + highestItem.getWidth()) {
            return true;
        }
        return false;
    }
}
