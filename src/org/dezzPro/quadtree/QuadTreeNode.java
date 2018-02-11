package org.dezzPro.quadtree;

import java.awt.*;
import java.util.*;
import java.util.function.BiConsumer;

public class QuadTreeNode<T extends Object2D> {

  private static final int MAX_OBJECTS_PER_NODE = 2;
  private static final int MAX_DEPTH = 6;
  private Map<NodeType, QuadTreeNode<T>> nodes;
  private int depth = 0;
  private boolean hasChildren = false;
  private QuadTreeBound bounds;
  private Set<T> leafs;
  private QuadTreeNode<T> parentNode;

  public QuadTreeNode(QuadTreeNode<T> parentNode, QuadTreeBound bounds, int depth) {
    this.parentNode = parentNode;
    this.bounds = bounds;
    this.leafs = new HashSet<>();
    this.nodes = new EnumMap<>(NodeType.class);
    this.depth = depth;
  }

  public QuadTreeNode(QuadTreeBound bounds, int depth) {
    this(null, bounds, depth);
  }

  public QuadTreeNode(double minX, double minY, double maxX, double maxY, int depth) {
    this(null, minX, minY, maxX, maxY, depth);
  }

  public QuadTreeNode(QuadTreeNode<T> parentNode, double minX, double minY, double maxX, double maxY, int depth) {
    this(parentNode, new QuadTreeBound(minX, minY, maxX, maxY), depth);
  }

  public void eachLeaf(QuadTree.EachLeaf eachLeaf) {
    this.getFlatItems().forEach(eachLeaf::execute);
  }

  @SuppressWarnings("unchecked")
  public void eachNode(QuadTree.EachNode eachNode) {
    BiConsumer<BiConsumer, QuadTreeNode> nodeExecute = (consumer, node) -> {
      eachNode.execute(node);
      node.nodes().forEach((nodeType, innerNode) -> consumer.accept(consumer, innerNode));
    };
    nodeExecute.accept(nodeExecute, this);
  }

  public Set<T> getFlatItems() {
    Set<T> items = new HashSet<>(this.leafs);

    if (this.hasChildren) {
      this.nodes().forEach((nodeType, node) -> items.addAll(node.getFlatItems()));
    }

    return items;
  }

  public Set<T> getLeafs() {
    return this.leafs;
  }

  @SuppressWarnings("unchecked")
  public void updateBelongs() {
    if (this.hasChildren) {
      if (this.getFlatItems().size() > 0) {
        this.nodes.forEach((nodeType, node) -> node.updateBelongs());
      }
    } else {
      Iterator<T> iterator = this.leafs.iterator();
      while (iterator.hasNext()) {
        T leaf = iterator.next();
        if (!this.isBelong(leaf)) {
          iterator.remove();
          this.insertToParent(leaf);
        }
      }
    }
  }

  public void updateNodes() {
    if (this.hasChildren) {
      if (this.getFlatItems().size() == 0) {
        this.nodes.forEach(this.nodes::remove);
        this.hasChildren = false;
      } else {
        this.nodes.forEach((nodeType, node) -> node.updateNodes());
      }
    }
  }

  public boolean isBelong(T leaf) {
    return this.getBounds().contains(leaf.x(), leaf.y());
  }

  public void clear() {
    this.leafs.clear();

    if (this.hasChildren) {
      for (NodeType nodeType : NodeType.values()) {
        this.nodes.get(nodeType).clear();
      }
    }

    this.hasChildren = false;
    this.nodes.clear();
  }

  public Set<T> search(QuadTreeBound treeBound) {
    treeBound.intersects(this.bounds);
    // not implemented yet
    return null;
  }

  public Set<T> search(Rectangle rectangle) {
    return this.search(new QuadTreeBound(rectangle.x, rectangle.y, rectangle.getMaxX(), rectangle.getMaxY()));
  }

  public void insertToParent(T object2D) throws RuntimeException {
    QuadTreeNode<T> node = this.getParentNode();

    if (!this.isRoot()) {
      if (node.isBelong(object2D)) {
        node.insert(object2D);
      } else {
        node.insertToParent(object2D);
      }
    }

    throw new RuntimeException(String.format("Leaf was left root node bounds. Leaf: %s", object2D));
  }

  public void insert(T object2D) {
    if (this.hasChildren) {
      this.nodes.get(this.detectNodeType(object2D)).insert(object2D);
    } else {
      if (MAX_OBJECTS_PER_NODE > this.leafs.size()) {
        this.leafs.add(object2D);
      } else if (MAX_DEPTH > this.getDepth() && this.leafs.size() >= MAX_OBJECTS_PER_NODE) {

        this.splitNode();
        this.nodes.get(this.detectNodeType(object2D)).insert(object2D);

        Iterator<T> iterator = this.leafs.iterator();
        while (iterator.hasNext()) {
          T leaf = iterator.next();
          this.nodes.get(this.detectNodeType(leaf)).insert(leaf);
          iterator.remove();
        }
      } else {
        this.leafs.add(object2D);
      }
    }
  }

  public void splitNode() {
    QuadTreeNode<T> nodeNorthWest = new QuadTreeNode<>(this.bounds.minX, this.bounds.minY, this.bounds.centreX,
        this.bounds.centreY, this.depth + 1);
    QuadTreeNode<T> nodeNorthEast = new QuadTreeNode<>(this.bounds.centreX, this.bounds.minY, this.bounds.maxX,
        this.bounds.centreY, this.depth + 1);
    QuadTreeNode<T> nodeSouthEast = new QuadTreeNode<>(this.bounds.centreX, this.bounds.centreY, this.bounds.maxX,
        this.bounds.maxY, this.depth + 1);
    QuadTreeNode<T> nodeSouthWest = new QuadTreeNode<>(this.bounds.minX, this.bounds.centreY, this.bounds.centreX,
        this.bounds.maxY, this.depth + 1);

    this.nodes.put(NodeType.NW, nodeNorthWest);
    this.nodes.put(NodeType.NE, nodeNorthEast);
    this.nodes.put(NodeType.SE, nodeSouthEast);
    this.nodes.put(NodeType.SW, nodeSouthWest);

    this.hasChildren = true;
  }

  public NodeType detectNodeType(Rectangle rectangle) {
    int x = (int) rectangle.getCenterX();
    int y = (int) rectangle.getCenterY();

    return this.detectNodeType(x, y);
  }

  public NodeType detectNodeType(T object2D) {
    return this.detectNodeType(object2D.x(), object2D.y());
  }

  public NodeType detectNodeType(double x, double y) {
    NodeType nodeType;

    if (x > this.bounds.centreX) {
      nodeType = y > this.bounds.centreY ? NodeType.SE : NodeType.NE;
    } else {
      nodeType = y > this.bounds.centreY ? NodeType.SW : NodeType.NW;
    }

    return nodeType;
  }

  public boolean isRoot() {
    return null == this.parentNode;
  }

  public QuadTreeNode<T> getParentNode() {
    return this.parentNode;
  }

  public int getDepth() {
    return this.depth;
  }

  public QuadTreeBound getBounds() {
    return this.bounds;
  }

  public boolean hasChildren() {
    return this.hasChildren;
  }

  public Map<NodeType, QuadTreeNode<T>> nodes() {
    return this.nodes;
  }

  public String toString() {
    return String.format("QuadTreeNode{ bounds: %s, \ngetLeafs: %s,\n nodes: %s }", this.bounds, this.leafs.size(),
        this.nodes);
  }
}
