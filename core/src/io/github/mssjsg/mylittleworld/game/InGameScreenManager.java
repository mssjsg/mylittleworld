package io.github.mssjsg.mylittleworld.game;

import io.github.mssjsg.mylittleworld.Screen;
import io.github.mssjsg.mylittleworld.ScreenManager;
import io.github.mssjsg.mylittleworld.game.world.WorldScreen;

/**
 * Created by Sing on 18/2/2018.
 */

public abstract class InGameScreenManager implements ScreenManager {

    private ScreenManager mScreenManager;

    public InGameScreenManager(ScreenManager screenManager) {
        mScreenManager = screenManager;
    }

    @Override
    public void setScreen(Screen screen) {

    }

    protected abstract WorldScreen getWorldScreen();

    @Override
    public boolean goBack() {
        return false;
    }
}
