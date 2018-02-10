package io.github.mssjsg.mylittleworld.game.shape;

/**
 * Created by sing on 1/3/17.
 */

public class Circle extends BodyShape<Circle> {
    public float radius;

    @Override
    public Circle copy() {
        Circle circle = new Circle();
        circle.radius = radius;
        return circle;
    }

    @Override
    public float getWidth() {
        return radius * 2;
    }

    @Override
    public float getHeight() {
        return radius * 2;
    }
}
