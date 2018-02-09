package org.dezzPro.app.engine;

import org.dezzPro.app.TestVectorLoop;
import org.dezzPro.app.engine.geometry.Bounds2D;
import org.dezzPro.app.engine.geometry.Vector2D;

public class EntityCircle extends Entity<EntityCircle> {

    public EntityCircle(int radius, Vector2D position, Vector2D velocity, Bounds2D bounds2D)
    {
        super(radius * 2, radius * 2, position, velocity, bounds2D);
    }

    public double radius()
    {
        return this.width() / 2;
    }

    public void move(float elapsedTime)
    {
        this.setX(this.x() + (this.velocity.x() * elapsedTime));
        this.setY(this.y() + (this.velocity.y() * elapsedTime));
        this.velocity().setY(this.velocity.y() + TestVectorLoop.gravity);

        if (this.maxX() > this.bounds.getMaxX()) {
            this.setX(this.bounds.getMaxX() - this.width());
            this.velocity().setX(-this.velocity().x());
            this.velocity().multiply(TestVectorLoop.restitution);
        } else if (0 > this.x()) {
            this.setX(0.00D);
            this.velocity().setX(this.velocity().x() * -1);
            this.velocity().multiply(TestVectorLoop.restitution);
        }

        if (this.maxY() > this.bounds.getMaxY()) {
            this.setY(this.bounds.getMaxY() - this.height());
            this.velocity().setY(-this.velocity().y());
            this.velocity().multiply(TestVectorLoop.restitution);
        } else if (0 > this.y()) {
            this.setY(0.00D);
            this.velocity().setY(this.velocity().y() * -1);
            this.velocity().multiply(TestVectorLoop.restitution);
        }
    }

    @Override
    public boolean colliding(EntityCircle entity)
    {
        return false;
    }

}
