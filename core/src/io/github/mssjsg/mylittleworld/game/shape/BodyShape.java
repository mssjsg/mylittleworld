package io.github.mssjsg.mylittleworld.game.shape;

import io.github.mssjsg.mylittleworld.util.Copyable;

/**
 * Created by sing on 1/3/17.
 */

public abstract class BodyShape<S> implements Copyable<S> {
    public abstract float getWidth();

    public abstract float getHeight();
}
