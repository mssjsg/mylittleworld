package io.github.mssjsg.mylittleworld.game.shape;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

import static io.github.mssjsg.mylittleworld.game.system.Box2dSystem.PX_TO_BOX;

/**
 * Created by sing on 1/3/17.
 */

public class Ball implements BodyShape<Ball> {
    public float radius;

    @Override
    public Ball copy() {
        Ball ball = new Ball();
        ball.radius = radius;
        return ball;
    }

    @Override
    public Shape createBox2dShape() {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius * PX_TO_BOX);
        return circleShape;
    }
}
