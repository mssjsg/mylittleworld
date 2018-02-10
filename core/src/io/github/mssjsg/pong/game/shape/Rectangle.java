package io.github.mssjsg.pong.game.shape;

/**
 * Created by sing on 1/3/17.
 */

public class Rectangle extends BodyShape<Rectangle> {
    public float width;
    public float height;

    @Override
    public Rectangle copy() {
        Rectangle rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;
        return rectangle;
    }
}
