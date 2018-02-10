package io.github.mssjsg.mylittleworld.game.component;

import io.github.mssjsg.mylittleworld.game.shape.BodyShape;
import io.github.mssjsg.mylittleworld.util.Copyable;

/**
 * Created by sing on 1/3/17.
 */

public abstract class EntityBody<B extends EntityBody<B>> implements Component, Copyable<B> {
    public float centerX;
    public float centerY;
    public BodyShape<?> shape;

    public EntityBody() {
    }

    public EntityBody(EntityBody body) {
        centerX = body.centerX;
        centerY = body.centerY;
        shape = (BodyShape<?>)body.shape.copy();
    }

    protected abstract B createObject();

    @Override
    public B copy() {
        B body = createObject();
        body.centerX = centerX;
        body.centerY = centerY;
        body.shape = (BodyShape<?>)shape.copy();
        return body;
    }
}
