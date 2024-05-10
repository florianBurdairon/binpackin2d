package tp.optimisation.utils;

import tp.optimisation.Item;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Position implements Cloneable {
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public Rectangle(Item item, Position position) {
        super(position.getX(), position.getY());
        this.width = item.getWidth();
        this.height = item.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCutByGuillotine(Guillotine g){
        if (g.getDirection() == Guillotine.Direction.Vertical) {
            return this.getX() < g.getPosition() && this.getX() + this.width > g.getPosition();
        } else {
            return this.getY() < g.getPosition() && this.getY() + this.height > g.getPosition();
        }
    }

    public List<Rectangle> cutWithGuillotine(Guillotine g) {
        List<Rectangle> cutParts = new ArrayList<>();

        if (g.getDirection() == Guillotine.Direction.Vertical) {
            cutParts.add(new Rectangle(getX(), getY(), g.getPosition() - getX(), height));
            cutParts.add(new Rectangle(g.getPosition(), getY(), width - (g.getPosition() - getX()), height));
        } else {
            cutParts.add(new Rectangle(getX(), getY(), width, g.getPosition() - getY()));
            cutParts.add(new Rectangle(getX(), g.getPosition(), width, height- (g.getPosition() - getY())));
        }

        return cutParts;
    }

    public float getArea() {
        return width * height;
    }

    public boolean equals(Rectangle r){
        return getX() == r.getX() && getY() == r.getY() && width == r.width && height == r.height;
    }

    @Override
    public Rectangle clone() {
        try {
            Rectangle clone = (Rectangle) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
