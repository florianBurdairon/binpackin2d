package tp.optimisation;

public class Item {
    int id;
    int width;
    int height;

    public Item(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
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

    public String toString() {
        return id + " " + width + " " + height;
    }
}
