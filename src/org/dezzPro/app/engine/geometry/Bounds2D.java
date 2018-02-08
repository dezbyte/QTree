package org.dezzPro.app.engine.geometry;

public class Bounds2D {

    private int minX = 0;
    private int maxX = 0;

    private int minY = 0;
    private int maxY = 0;

    private int width = 0;
    private int height = 0;

    public Bounds2D(int minX, int maxX, int minY, int maxY)
    {
        this.minX = minX;
        this.maxX = maxX;

        this.minY = minY;
        this.maxY = maxY;

        this.width = maxX - minX;
        this.height = maxY - minY;
    }

    public int getMinX()
    {
        return minX;
    }

    public int getMaxX()
    {
        return maxX;
    }

    public int getMinY()
    {
        return minY;
    }

    public int getMaxY()
    {
        return maxY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean intersects(Bounds2D bound)
    {
        return bound.getMaxX() > this.getMinX() && bound.getMaxY() > this.getMinY() && bound.getMinX() < this.getMaxX() && bound.getMinY() < this.getMaxY();
    }

}
