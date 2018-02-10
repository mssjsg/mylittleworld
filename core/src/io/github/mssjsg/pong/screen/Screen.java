package io.github.mssjsg.pong.screen;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by sing on 12/31/16.
 */

public abstract class Screen {

    protected ScreenManager mScreenManager;

    public Screen(ScreenManager screenManager) {
        mScreenManager = screenManager;
    }

    public abstract InputProcessor getInputProcessor();

    public void resize(int width, int height) {

    }

    public void render(float delta) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }

    protected void setScreen(Screen screen) {
        mScreenManager.setScreen(screen);
    }
}
