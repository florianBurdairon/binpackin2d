package tp.optimisation.utils;

import tp.optimisation.Bin;
import tp.optimisation.Dataset;
import tp.optimisation.Item;

import java.util.List;

public class Utils {
    public static int minimumBinPacking(Dataset dataset) {
        int binArea = dataset.getBinWidth() * dataset.getBinHeight();
        int sumItemsArea = 0;
        for (Item item : dataset.getItems()) {
            sumItemsArea += item.getWidth() * item.getHeight();
        }
        return Math.ceilDiv(sumItemsArea, binArea);
    }

    public static boolean isItemOverlapping(Rectangle rectangle1, Rectangle rectangle2) {
        // Check if the items have the same position and width
        if (rectangle1.getX() == rectangle2.getX() && rectangle1.getY() == rectangle2.getY() && rectangle1.getWidth() == rectangle2.getWidth()) {
            return true;
        }
        // Get the highest item
        Rectangle highestItem = rectangle1.getY() > rectangle2.getY() ? rectangle1 : rectangle2;

        // Get the lowest item
        Rectangle lowestItem = rectangle1.getY() > rectangle2.getY() ? rectangle2 : rectangle1;

        // Check if the highest item is overlapping the lowest item
        // Check if the items intersect on the y-axis
        if (highestItem.getY() + highestItem.getHeight() < lowestItem.getY()) {
            return false;
        }
        // Check if the items intersect on the x-axis
        if (lowestItem.getX() < highestItem.getX() && lowestItem.getX() + lowestItem.getWidth() > highestItem.getX()) {
            return true;
        }
        if (lowestItem.getX() < highestItem.getX() + highestItem.getWidth() && lowestItem.getX() + lowestItem.getWidth() > highestItem.getX() + highestItem.getWidth()) {
            return true;
        }
        return false;
    }

    public static float getBinPackingWeight(List<Bin> bins) {
        float sum = 0;
        for (Bin bin : bins) {
            sum += bin.getWeight();
        }
        return sum;
    }
}
