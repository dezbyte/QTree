package org.dezzPro.app;

import org.dezzPro.app.engine.EntityCircle;
import org.dezzPro.app.engine.Loop;
import org.dezzPro.app.engine.geometry.Bounds2D;
import org.dezzPro.app.engine.geometry.Vector2D;
import org.dezzPro.app.engine.graphics.Colors;
import org.dezzPro.app.gui.MainFrame;
import org.dezzPro.quadtree.QuadTree;

import java.awt.*;
import java.util.Random;

public class TestVectorLoop extends Loop {

  public static float gravity = 30F;
  public static float restitution = 0.9F;

  private static EntityCircle circleA;
  private static EntityCircle circleB;
  private static Vector2D vectorAB;
  private QuadTree<EntityCircle> quadTree;

  private double factor = 0.00D;

  private int[][] points = new int[][]{{0, 15, 17, 33, 41, 13}, {0, 15, 0, 8, 55, 70}};

  public TestVectorLoop() {
    super("vector_test_loop");

    this.quadTree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);

    Bounds2D bounds2D = new Bounds2D(0, MainFrame.WIDTH, 0, MainFrame.HEIGHT);

    circleA = new EntityCircle(10, new Vector2D(100, 150), new Vector2D(0D, 100D), bounds2D);
    circleB = new EntityCircle(10, new Vector2D(150, 100), new Vector2D(0D, 100D), bounds2D);

    int amount = 5;
    Random random = new Random();
    for (int i = 0; i < amount; i++) {
      double vectorX = (double) random.nextInt(MainFrame.WIDTH);
      double vectorY = (double) random.nextInt(MainFrame.HEIGHT);
      EntityCircle circle = new EntityCircle(5, new Vector2D(vectorX, vectorY), new Vector2D(2D * vectorX, 2D * vectorY), bounds2D);
      this.quadTree.add(circle);
    }
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void update(float elapsedTime) {
    quadTree.rootNode().getFlatItems().forEach(entity -> entity.move(elapsedTime));
    quadTree.update();

    vectorAB = Vector2D.subtract(circleA.position(), circleB.position());
    vectorAB.normalize().add(circleA.position());
  }

  @Override
  protected void render() {
    this.mainFrame.clearFrame();

    /*QuadTreeNode<EntityCircle> rootNode = this.quadTree.rootNode();

    rootNode.eachNode((QuadTreeNode treeNode) -> {
      QuadTreeBound nodeBounds = treeNode.getBounds();
      graphics2D.setColor(new Color(Colors.ORANGE.getColor().getRGB() & 0x22ffffff, true));
      graphics2D.drawRect((int) nodeBounds.minX, (int) nodeBounds.minY, (int) nodeBounds.width, (int) nodeBounds.height);
    });

    double vertexX = circleB.x() + circleB.radius();
    double vertexY = circleB.y() + circleB.radius();

    graphics2D.setColor(Colors.CRIMSON.getColor());
    graphics2D.drawLine(
        (int) vectorAB.x() + (int) circleB.radius(),
        (int) vectorAB.y() + (int) circleB.radius(),
        (int) vertexX, (int) vertexY
    );

    this.rotatePolygon(graphics2D, (int) vectorAB.x(), (int) vectorAB.y(), (int) vertexX, (int) vertexY);

    quadTree.rootNode().getFlatItems().forEach(circle -> {
//      graphics2D.fillOval((int) circle.x(), (int) circle.y(), (int) circle.width(), (int) circle.height());
      this.rotatePolygon(graphics2D, (int) vertexX, (int) vertexY, (int) circle.x(), (int) circle.y());
      graphics2D.drawLine((int) vertexX, (int) vertexX, (int) circle.x(), (int) circle.y());
//      quadTree.rootNode().getFlatItems().forEach(entityCircle -> {
//        graphics2D.drawLine(
//            (int) entityCircle.x() + (int) entityCircle.radius(),
//            (int) entityCircle.y() + (int) entityCircle.radius(),
//            (int) circle.x(), (int) circle.y()
//        );
//      });
    });

    graphics2D.setColor(Colors.BLUE.getColor());
    graphics2D.fillOval((int) circleA.x(), (int) circleA.y(), (int) circleA.width(), (int) circleA.height());
    graphics2D.drawLine(0, 0, (int) circleA.x() + (int) circleA.radius(), (int) circleA.y() + (int) circleA.radius());

    graphics2D.setColor(Colors.SPRING.getColor());
    graphics2D.fillOval((int) circleB.x(), (int) circleB.y(), (int) circleB.width(), (int) circleB.height());
    graphics2D.drawLine(0, 0, (int) circleB.x() + (int) circleB.radius(), (int) circleB.y() + (int) circleB.radius());
*/


    this.drawTestVectors();

    this.mainFrame.swapBuffer();
  }

  private void drawTestVectors() {
    Graphics2D graphics2D = this.mainFrame.getGraphics2D();

    int cx = 400;
    int cy = 300;

    factor = factor % (Math.PI * 2);
    factor += 0.001D;

    int lx = (int) (cx + Math.sin(this.factor) * 100);
    int ly = (int) (cy + Math.cos(this.factor) * 100);

//    System.out.printf("sin: %s, factor: %s%n", Math.sin(this.factor), this.factor);

    Polygon triangle = new Polygon();
    triangle.addPoint(cx, cy);
    triangle.addPoint(lx, ly);
    triangle.addPoint(cx, cy+100);
    graphics2D.drawPolygon(triangle);

    graphics2D.setColor(Colors.BLUE.getColor());
    graphics2D.drawLine(cx, cy, lx, ly);

    graphics2D.setColor(Colors.ORANGE.getColor());
    this.rotatePolygon(graphics2D, cx, cy, lx, ly);

    Polygon polygon = new Polygon(points[0], points[1], 6);
    graphics2D.fillPolygon(polygon);

    graphics2D.setColor(Colors.SPRING.getColor());
    graphics2D.fillOval(cx, cy, 6, 6);
  }

//  public static void _render() {
//    bufferGraphics.setColor(new Color(0xff0000ff));
//    bufferGraphics.fillOval((int) (350 + (Math.sin(delta)  200)), (int) (-1  (350 + (Math.sin(delta) * 200))), 100, 100);
//    System.out.println(Math.sin(Math.PI));
//    ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//    delta += 0.02f;
//  }

  protected void rotatePolygon(Graphics2D graphics2D, int x1, int y1, int x2, int y2) {
//    Vector2D vectorA = new Vector2D(x1, y1);
//    Vector2D vectorB = new Vector2D(x2, y2);

//    double distance = vectorA.distance(vectorB);

/*
    double distance = vectorA.distance(vectorB);
    double sin = (y2 - y1) / distance;
    double cos = (x2 - x1) / distance;
    double xa = (distance * cos) + x1;
    double ya = (distance * sin) + y1;
*/
    Polygon polygon = new Polygon();

    for (int i = 0; i < 6; i++)
    {
      Vector2D vectorA = new Vector2D(400, 300);
      Vector2D vectorB = new Vector2D(points[0][i], points[1][i]);
      Vector2D vectorC = new Vector2D(x2, y2);

      double distanceA = vectorA.distance(vectorB);
      double distanceB = vectorA.distance(vectorC);

      double cos = (x2 - x1) / distanceB;
      double sin = (y2 - y1) / distanceB;

      double xa = (distanceA * cos);// - (points[0][i] * sin);
      double ya = (distanceA * sin);// + (points[1][i] * cos);

      polygon.addPoint((int) xa, (int) ya);
    }

    graphics2D.fillPolygon(polygon);

    //    double xb = (delta * cos) - (sin * -width) + x1;
    //    double yb = (delta * sin) + (cos * -width) + y1;

    //    Polygon polygon = new Polygon();
    //
    //    polygon.addPoint(x2, y2);
    //    polygon.addPoint((int) xa, (int) ya);
    //    polygon.addPoint((int) xb, (int) yb);

    //    graphics2D.fillPolygon(polygon);

    //    graphics2D.setColor();

//    graphics2D.fillOval((int) xa, (int) ya, 10, 10);
  }

}
