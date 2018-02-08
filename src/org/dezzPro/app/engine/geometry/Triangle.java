package org.dezzPro.app.engine.geometry;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class Triangle {

    protected Vector2D position;
    protected int width;
    protected int height;
    protected Map<Vertex, Vector2D> points;

    public enum Vertex{
        A, B, C
    }

    public Triangle(Vector2D position, int width, int height)
    {
        this.position = position;
        this.width = width;
        this.height = height;

        this.points = new EnumMap<>(Vertex.class);
        this.points.put(Vertex.A, new Vector2D(0D, 0D));
        this.points.put(Vertex.B, new Vector2D(0D, 0D));
        this.points.put(Vertex.C, new Vector2D(0D, 0D));
    }

    public Triangle()
    {
        this(new Vector2D(0D, 0D), 10, 10);
    }

    public Vector2D position()
    {
        return this.position;
    }

    public double x()
    {
        return this.position.x();
    }

    public double y()
    {
        return this.position.y();
    }

    public int width()
    {
        return this.width;
    }

    public int height()
    {
        return this.height;
    }

    public Vector2D center()
    {
        return new Vector2D(this.position.x() + (this.width() / 2), this.position.y() + (this.height() / 2));
    }

    public Map<Vertex, Vector2D> points()
    {
        return this.points;
    }

    public Vector2D points(Vertex vertex)
    {
        return this.points.get(vertex);
    }

    public Polygon polygon()
    {
        Polygon polygon = new Polygon();

        this.points().forEach((vertex, vector2D) -> polygon.addPoint((int) vector2D.x(), (int) vector2D.y()));

        return polygon;
    }

}
