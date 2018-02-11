package org.dezzPro.quadtree;

import java.util.Set;

@SuppressWarnings("unused")
public class QuadTree<T extends Object2D & Comparable<T>> {

  public static QuadTreeNode rootNode;

  private QuadTreeNode<T> root;
  private Set<T> items;

  public QuadTree(double minX, double minY, double maxX, double maxY) {
    root = new QuadTreeNode<>(new QuadTreeBound(minX, minY, maxX, maxY), 0);
    rootNode = root;
  }

  public void add(T treeLeaf) {
    this.root.insert(treeLeaf);
  }

  public void clear() {
    this.root.clear();
  }

  public void execute(EachLeaf executor) {
    this.root.eachLeaf(executor);
  }

  public Set<T> leafsAll() {
    if (this.items == null) {
      this.items = this.root.getFlatItems();
    }

    return this.items;
  }

  public QuadTreeNode<T> rootNode() {
    return this.root;
  }

  public static QuadTreeNode root() {
    return null;
  }

  public void update() {
    this.root.updateBelongs();
    this.root.updateNodes();
  }

  public String toString() {
    return String.format("QuadTree{ rootNode: %s }", root);
  }

  @FunctionalInterface
  public interface EachLeaf {
    void execute(Object2D object2D);
  }

  @FunctionalInterface
  public interface EachNode {
    void execute(QuadTreeNode treeNode);
  }

}
