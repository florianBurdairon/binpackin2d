package tp.optimisation.utils;

public class Guillotine implements Cloneable {

    public enum Direction{
        Vertical,
        Horizontal
    }

    private final Direction direction;
    private final int position;

    public Guillotine(Direction direction, int position) {
        this.direction = direction;
        this.position = position;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public Guillotine clone() {
        try {
            return (Guillotine) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
