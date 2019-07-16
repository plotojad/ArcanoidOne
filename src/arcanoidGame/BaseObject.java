package arcanoidGame;

abstract class BaseObject {

    //координаты
    double x;
    double y;
    //радиус объекта
    double radius;

    BaseObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Метод рисует свой объект на "канвасе".
     */
    public abstract void draw(Canvas canvas);

    /**
     * Двигаем себя на один ход.
     */
    public abstract void move();

    /**
     * Проверяем - не выходит ли (x,y) за границы.
     */
    void checkBorders(double minx, double maxx, double miny, double maxy) {
        if (x < minx) {
            x = minx;
        }
        if (x > maxx) {
            x = maxx;
        }
        if (y < miny) {
            y = miny;
        }
        if (y > maxy) {
            y = maxy;
        }
    }

    /**
     * Проверяем - пересекаются ли переданный(o) и наш(this) объекты.
     */
    @SuppressWarnings("Duplicates")
    public boolean isIntersec(BaseObject o) {
        double dx = x - o.x;
        double dy = y - o.y;
        double destination = Math.sqrt(dx * dx + dy * dy);
        double destination2 = Math.max(radius, o.radius);
        return destination <= destination2;
    }
}