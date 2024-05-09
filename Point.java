public class Point {

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    private float x;
    public float getX() {
        return x;
    }
    
    private float y;
    public float getY() {
        return y;
    }

    public Point sum(Point other) {
        return new Point(
            this.x + other.x,
            this.y + other.y
        );
    }

    public Point mul(Float value) {
        return new Point(
            this.x * value,
            this.y * value
        );
    }
}
