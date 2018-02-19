package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.InputProcessor;

import io.github.mssjsg.mylittleworld.Screen;
import io.github.mssjsg.mylittleworld.ScreenManager;

/**
 * Created by Sing on 18/2/2018.
 */

public abstract class InGameScreen extends Screen {

    protected InGameScreenManager mInGameScreenManager;

    public InGameScreen(InGameScreenManager screenManager) {
        super(screenManager);
        mInGameScreenManager = screenManager;
    }
}
