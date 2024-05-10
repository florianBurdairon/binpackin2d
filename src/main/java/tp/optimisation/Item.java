package tp.optimisation;

import java.util.Random;

public class Item {
    int id;
    int width;
    int height;

    final float red;
    final float green;
    final float blue;

    public Item(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;

        Random r = new Random();
        red = r.nextFloat();
        green = r.nextFloat();
        blue = r.nextFloat();
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void rotate() {
        int temp = width;
        width = height;
        height = temp;
    }

    public Item asRotated() {
        return new Item(id, height, width);
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public String toString() {
        return id + " " + width + " " + height;
    }

    public boolean equals(Item i){
        return this.id == i.id && this.width == i.width && this.height == i.height;
    }
}
