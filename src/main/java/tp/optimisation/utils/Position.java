package tp.optimisation.utils;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position add(Position pos) {
        return new Position(this.x + pos.getX(), this.y + pos.getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "[" + x + ";" + y + "]";
    }

    public boolean equals(Position pos) {
        return x == pos.getX() && y == pos.getY();
    }
}
