package io.github.mssjsg.mylittleworld.game.shape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;

import io.github.mssjsg.mylittleworld.util.Copyable;

/**
 * Created by sing on 1/3/17.
 */

public interface BodyShape<S> extends Copyable<S> {
    Shape createBox2dShape();
}
