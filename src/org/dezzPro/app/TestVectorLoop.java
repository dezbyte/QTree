package org.dezzPro.app;

import org.dezzPro.app.engine.EntityCircle;
import org.dezzPro.app.engine.Loop;
import org.dezzPro.app.engine.geometry.Bounds2D;
import org.dezzPro.app.engine.geometry.Vector2D;
import org.dezzPro.app.gui.MainFrame;
import org.dezzPro.quadtree.QuadTree;

import java.awt.*;
import java.util.Random;

public class TestVectorLoop extends Loop {

    public static float gravity     = 30F;
    public static float restitution = 0.85F;

    private static EntityCircle circleA;
    private static EntityCircle circleB;
    private static Vector2D vectorAB;
    private QuadTree<EntityCircle> quadTree;

    public TestVectorLoop()
    {
        super("vector_test_loop");

        this.quadTree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);

        Bounds2D bounds2D = new Bounds2D(0, MainFrame.WIDTH, 0, MainFrame.HEIGHT);

        int amount = 100;
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            double vectorX = (double) random.nextInt(MainFrame.WIDTH);
            double vectorY = (double) random.nextInt(MainFrame.HEIGHT);
            EntityCircle circle = new EntityCircle(5, new Vector2D(vectorX, vectorY), new Vector2D(1500D, 1000D), bounds2D);
            this.quadTree.add(circle);
        }
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void update(float elapsedTime)
    {
        this.quadTree.rootNode().leafsAll().forEach(entity -> entity.move(elapsedTime));

//        vectorAB = Vector2D.subtract(circleA.position(), circleB.position());
//        vectorAB.normalize().add(circleA.position());
    }

    @Override
    protected void render()
    {
        this.mainFrame.clearFrame();

        Graphics2D graphics2D = this.mainFrame.getGraphics2D();

        double vertexX = circleB.x() + circleB.radius();
        double vertexY = circleB.y() + circleB.radius();

//        graphics2D.setColor(Colors.DEEP_PINK.color());
        graphics2D.drawLine(
                (int) vectorAB.x() + (int) circleB.radius(),
                (int) vectorAB.y() + (int) circleB.radius(),
                (int) vertexX, (int) vertexY
        );

        this.drawArrow(graphics2D, (int) vectorAB.x(), (int) vectorAB.y(), (int) vertexX, (int) vertexY);

        this.quadTree.rootNode().leafsAll().forEach(circle -> {
            graphics2D.fillOval((int) circle.x(), (int) circle.y(), (int) circle.width(), (int) circle.height());
        });

//        graphics2D.setColor(Colors.BLUE.color());
//        graphics2D.fillOval((int) circleA.x(), (int) circleA.y(), (int) circleA.width(), (int) circleA.height());
//        graphics2D.drawLine(0, 0, (int) circleA.x() + (int) circleA.radius(), (int) circleA.y() + (int) circleA.radius());
//
//        graphics2D.setColor(Colors.SPRING.color());
//        graphics2D.fillOval((int) circleB.x(), (int) circleB.y(), (int) circleB.width(), (int) circleB.height());
//        graphics2D.drawLine(0, 0, (int) circleB.x() + (int) circleB.radius(), (int) circleB.y() + (int) circleB.radius());

        this.mainFrame.swapBuffer();
    }

    protected void drawArrow(Graphics2D graphics2D, int x1, int y1, int x2, int y2)
    {
        Vector2D vectorA = new Vector2D(x1, y1);
        Vector2D vectorB = new Vector2D(x2, y2);

        double distance = vectorA.distance(vectorB);
        double angle    = vectorB.angle(vectorA);
        double width    = 4;
        double length   = 24;

        double sin   = (y2 - y1) / distance;
        double cos   = (x2 - x1) / distance;
        double delta = distance - length;


        double xa = (delta * cos) - (sin * width) + x1;
        double ya = (delta * sin) + (cos * width) + y1;

        double xb = (delta * cos) - (sin * -width) + x1;
        double yb = (delta * sin) + (cos * -width) + y1;

        Polygon polygon = new Polygon();

        polygon.addPoint(x2, y2);
        polygon.addPoint((int) xa, (int) ya);
        polygon.addPoint((int) xb, (int) yb);

        graphics2D.fillPolygon(polygon);
    }

}
