package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.mylittleworld.game.data.Entity;

/**
 * Created by sing on 1/2/17.
 */

public abstract class AbstractEntitySystem extends BaseSystem {

    private Array<Entity> mEntities = new Array<Entity>();

    @Override
    public final void update(float delta) {
        onUpdate(delta, mEntities);
    }

    public void addEntity(Entity entity) {
        mEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        mEntities.removeValue(entity, true);
    }

    protected abstract void onUpdate(float delta, Array<Entity> entities);
}
