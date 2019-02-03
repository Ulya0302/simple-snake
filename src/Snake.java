public class Snake {

    private int[] x;
    private int[] y;
    private int sizeSnake = 4;
    private Direction direction = Direction.DOWN;

    public Snake(int all_dots) {
        x = new int[all_dots];
        y = new int[all_dots];
    }

    public int getSize() {
        return sizeSnake;
    }

    public void incSize() {
        sizeSnake++;
    }

    public int getXi(int i) {
        return x[i];
    }

    public int getYi(int i) {
        return y[i];
    }

    public void setXi(int i, int val) {
        x[i] = val;
    }

    public void setYi(int i, int val) {
        y[i] = val;
    }

    public void changeXi(int i, int diff) {
        x[i] += diff;
    }

    public void changeYi(int i, int diff) {
        y[i] += diff;
    }

    public void setDirection(Direction d) {
        direction = d;

    }

    public Direction getDirection() {
        return direction;
    }
}
