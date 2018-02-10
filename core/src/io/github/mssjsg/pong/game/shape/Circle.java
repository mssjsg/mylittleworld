package io.github.mssjsg.pong.game.shape;

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
}
