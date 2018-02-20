package io.github.mssjsg.mylittleworld.game;

/**
 * Created by sing on 1/7/17.
 */

public abstract class BaseSystem {

    public abstract void update(float delta);

    public void dispose() {};

    public void onPause() {}

    public void resume() {}
}
