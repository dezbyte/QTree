package org.dezzPro.app.engine;

import org.dezzPro.app.engine.geometry.Bounds2D;
import org.dezzPro.app.engine.geometry.Vector2D;
import org.dezzPro.quadtree.Object2D;

abstract public class Entity<T extends Entity> implements Object2D, Comparable<T> {

  protected int width = 0;
  protected int height = 0;
  protected Vector2D position;
  protected Vector2D velocity;
  protected Bounds2D bounds;
  protected EntityState state;

  public Entity(int width, int height, Vector2D position, Vector2D velocity, Bounds2D bounds2D) {
    this.width = width;
    this.height = height;
    this.bounds = bounds2D;
    this.position = position;
    this.velocity = velocity;
    this.state = new EntityState();
  }

  @Override
  public double x() {
    return this.position.x();
  }

  @Override
  public void setX(double x) {
    this.position.setX(x);
  }

  @Override
  public double y() {
    return this.position.y();
  }

  @Override
  public void setY(double y) {
    this.position.setY(y);
  }

  @Override
  public double maxX() {
    return this.position.x() + this.width;
  }

  @Override
  public double maxY() {
    return this.position.y() + this.height;
  }

  @Override
  public double width() {
    return this.width;
  }

  @Override
  public double height() {
    return this.height;
  }

  @Override
  public double centreX() {
    return (this.x() + this.maxX()) / 2;
  }

  @Override
  public double centreY() {
    return (this.y() + this.maxY()) / 2;
  }

  public Vector2D position() {
    return this.position;
  }

  public Vector2D velocity() {
    return this.velocity;
  }

  public Bounds2D bounds() {
    return bounds;
  }

  public EntityState state() {
    return this.state;
  }

  @Override
  public int compareTo(T entity) {
    return entity.x() < this.x() ? 1 : entity.x() == this.x() ? 0 : -1;
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof Entity
        && this.velocity().equals(((Entity) object).velocity())
        && this.position().equals(((Entity) object).position());
  }

  abstract public void move(float elapsedTime);

  abstract public boolean colliding(T entity);

  @Override
  public String toString() {
    return String.format("%s: x: %s, y: %s, size: %sx%s", this.getClass().getSimpleName(), x(), y(), width(), height());
  }

}
