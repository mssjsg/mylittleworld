package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.utils.ArrayMap;

import io.github.mssjsg.mylittleworld.game.component.Component;

/**
 * Created by sing on 1/2/17.
 */

public class Entity {
    public int tag;
    public ArrayMap<Class<? extends Component>, Component> mComponentArrayMap;

    public Entity() {
        this(Tags.TAG_NONE);
    }

    public Entity(int tag) {
        this.tag = tag;
        mComponentArrayMap = new ArrayMap<Class<? extends Component>, Component>();
    }

    public Entity addComponent(Component component) {
        mComponentArrayMap.put(component.getClass(), component);
        return this;
    }

    public void removeComponent(Class<? extends Component> type) {
        mComponentArrayMap.removeKey(type);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        return (T)mComponentArrayMap.get(type);
    }
}
