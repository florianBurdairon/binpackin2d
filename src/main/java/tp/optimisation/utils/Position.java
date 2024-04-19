package tp.optimisation.utils;

public class Position<T> {
    private T x;
    private T y;

    public Position(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(T y) {
        this.y = y;
    }

    public String toString() {
        return "[" + x + ";" + y + "]";
    }
}
