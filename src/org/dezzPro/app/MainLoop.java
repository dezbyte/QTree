package org.dezzPro.app;

import org.dezzPro.app.engine.Entity;
import org.dezzPro.app.engine.EntityCircle;
import org.dezzPro.app.engine.Loop;
import org.dezzPro.app.engine.geometry.Bounds2D;
import org.dezzPro.app.gui.MainFrame;
import org.dezzPro.quadtree.Object2D;
import org.dezzPro.quadtree.QuadTree;
import org.dezzPro.quadtree.QuadTreeNode;

import java.awt.*;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

public class MainLoop extends Loop implements QuadTree.EachLeaf {

    private Random random;

    private QuadTree<EntityCircle>            tree;
    private Map<Layer, HashSet<EntityCircle>> entities;

    public MainLoop()
    {
        super("main_loop");

        this.entities = new EnumMap<>(Layer.class);
        this.entities.put(Layer.LAYER1, new HashSet<>());
        this.entities.put(Layer.LAYER2, new HashSet<>());
        this.entities.put(Layer.LAYER3, new HashSet<>());

        this.tree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
        this.random = new Random();

        int amount = 32;

        Bounds2D bounds2D = new Bounds2D(0, MainFrame.WIDTH, 0, MainFrame.HEIGHT);

        double rangeMin = 1D;
        double rangeMax = -1D;

//        Entity entityA = new EntityCircle(5, new Vector2D(0D, 250D), new Vector2D(1.5D, 1.6D), bounds2D);
//        this.entities.get(Layer.LAYER1).add(entityA);
//        this.tree.add(entityA);
//
//        Entity entityB = new EntityCircle(5, new Vector2D(MainFrame.WIDTH, 250D), new Vector2D(1.5D, 1.5D), bounds2D);
//        this.entities.get(Layer.LAYER1).add(entityB);
//        this.tree.add(entityB);

//        for (int i = 0; i < amount; i++) {
//
//            int x = this.random.nextInt(MainFrame.WIDTH);
//            int y = this.random.nextInt(MainFrame.HEIGHT);
//
//            double vectorX = (rangeMax - rangeMin) + rangeMin * this.random.nextDouble();
//            double vectorY = (rangeMax - rangeMin) + rangeMin * this.random.nextDouble();
//
//            Vector2D vector2D = new Vector2D(vectorX, vectorY);
//
//            Entity entity = new EntityCircle(4, x, y, vector2D, bounds2D);
//
//            this.entities.get(Layer.LAYER1).add(entity);
//            this.tree.add(entity);
//        }

    }

    @Override
    protected void initialize()
    {

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void update(float elapsedTime)
    {
//        System.out.println(elapsedTime / Loop.ONE_NANO_SECOND);
        this.tree.rootNode().getFlatItems().forEach(entity -> entity.move(elapsedTime));
        this.tree.update();

        this.tree.rootNode().eachNode(treeNode -> {
            if (!treeNode.hasChildren() && treeNode.getLeafs().size() > 0) {

                for (Entity entityA : (Iterable<Entity>) treeNode.getLeafs()) {
                    for (Entity entityB : (Iterable<Entity>) treeNode.getLeafs()) {
                        System.out.println(entityA.velocity().normalize().distance(entityB.velocity().normalize()));
                    }
                }

            }
        });

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void render()
    {
        this.mainFrame.clearFrame();

        Graphics2D graphics2D = this.mainFrame.getGraphics2D();

        graphics2D.setFont(new Font("courier", Font.PLAIN, 8));

        graphics2D.setColor(Color.DARK_GRAY);

        BiConsumer<BiConsumer, QuadTreeNode> nodeRecursive = (consumer, node) -> {
            if(node.hasChildren()) {
                node.nodes().forEach((nodeType, innerNode) -> consumer.accept(consumer, innerNode));
            } else {
                int x = (int) node.getBounds().minX;
                int y = (int) node.getBounds().minY;

                int width  = (int) node.getBounds().width;
                int height = (int) node.getBounds().height;

                graphics2D.drawString(String.valueOf(node.getLeafs().size()), x + 2, y + 10);
                graphics2D.drawRect(x, y, width, height);
            }
        };

        nodeRecursive.accept(nodeRecursive, this.tree.rootNode());

        graphics2D.setColor(Color.GREEN);

        this.tree.rootNode().getFlatItems().forEach(entity -> {

//            if(entity.getState().collision == EntityState.Collision.YES) {
//                graphics2D.setColor(Color.RED);
//            } else {
//                graphics2D.setColor(Color.WHITE);
//            }

            int x      = (int) entity.x();
            int y      = (int) entity.y();
            int width  = (int) entity.width();
            int height = (int) entity.height();

            graphics2D.fillOval(x, y, width, height);
            graphics2D.setColor(Color.RED);

        });

//        this.tree.rootNode().eachLeaf(leafA -> {
//            this.tree.rootNode().eachLeaf(leafB -> {
//                graphics2D.drawLine((int) leafA.centreX(), (int) leafA.centreY(), (int) leafB.centreX(), (int) leafB.centreY());
//            });
//        });

        this.mainFrame.swapBuffer();
    }

    @Override
    public void execute(Object2D object2D)
    {

    }

}
