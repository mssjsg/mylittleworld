package io.github.mssjsg.mylittleworld;

import io.github.mssjsg.mylittleworld.game.GameScreen;
import io.github.mssjsg.mylittleworld.main.MenuScreen;

/**
 * Created by sing on 1/1/17.
 */

public final class Screens {

    private Screens() {}

    public static Screen createMenuScreen(ScreenManager manager) {
        return new MenuScreen(manager);
    }

    public static Screen createGameScreen(ScreenManager manager) {
        return new GameScreen(manager);
    }
}
