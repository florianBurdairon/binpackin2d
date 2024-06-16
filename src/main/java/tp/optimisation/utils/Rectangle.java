package tp.optimisation.utils;

public class Rectangle extends Position implements Cloneable {
    private final int width;
    private final int height;

    public Rectangle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean equals(Rectangle r){
        return getX() == r.getX() && getY() == r.getY() && width == r.width && height == r.height;
    }

    @Override
    public Rectangle clone() {
        try {
            return (Rectangle) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
