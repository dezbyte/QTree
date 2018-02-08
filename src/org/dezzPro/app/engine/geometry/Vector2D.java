package org.dezzPro.app.engine.geometry;

public class Vector2D {

    protected double x = 0.00D;
    protected double y = 0.00D;

    public Vector2D(double d)
    {
        this(d, d);
    }

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double y() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2D add(Vector2D vector2D)
    {
        this.setX(this.x + vector2D.x());
        this.setY(this.y + vector2D.y());

        return this;
    }

    public static Vector2D add(Vector2D vectorA, Vector2D vectorB)
    {
        return new Vector2D(vectorA.x() + vectorB.x(), vectorB.y() + vectorB.y());
    }

    public Vector2D subtract(Vector2D vector2D)
    {
        this.setX(this.x - vector2D.x());
        this.setY(this.y - vector2D.y());

        return this;
    }

    public static Vector2D subtract(Vector2D vectorA, Vector2D vectorB)
    {
        return new Vector2D(vectorA.x() - vectorB.x(), vectorA.y() - vectorB.y());
    }

    public Vector2D multiply(double scalar)
    {
        this.setX(this.x * scalar);
        this.setY(this.y * scalar);

        return this;
    }

    public Vector2D multiply(Vector2D vector2D)
    {
        this.setX(this.x * vector2D.x());
        this.setY(this.y * vector2D.y());

        return this;
    }

    public static Vector2D multiply(Vector2D vectorA, Vector2D vectorB)
    {
        return new Vector2D(vectorA.x() * vectorB.x(), vectorB.y() * vectorB.y());
    }

    public Vector2D normalize()
    {
        double length = this.length();

        if(length != 0.00D) {
            this.setX(this.x / length);
            this.setY(this.y / length);
        }

        return this;
    }

    public double length()
    {
        return Math.hypot(this.x, this.y);
    }

    public double distance(Vector2D vector2D)
    {
        double dx = this.x - vector2D.x();
        double dy = this.y - vector2D.y();

        return Math.hypot(dx, dy);
    }

    public double dot(Vector2D vector2D)
    {
        return this.x * vector2D.y() + this.y * vector2D.x();
    }

    public double det(Vector2D vector2D)
    {
        return this.x * vector2D.y() - this.y * vector2D.x();
    }

    public double angle(Vector2D vector2D)
    {
        return Math.atan2(this.det(vector2D), this.dot(vector2D));
    }

    public String toString()
    {
        return String.format("%s {%s, %s}", this.getClass().getName(), this.x, this.y);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Vector2D && this.x() == ((Vector2D) object).x() && this.y() == ((Vector2D) object).y();
    }
}
