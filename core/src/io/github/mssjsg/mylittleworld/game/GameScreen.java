package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import io.github.mssjsg.mylittleworld.Screen;
import io.github.mssjsg.mylittleworld.ScreenManager;
import io.github.mssjsg.mylittleworld.Screens;
import io.github.mssjsg.mylittleworld.game.world.WorldScreen;

/**
 * Created by sing on 12/31/16.
 */

public class GameScreen extends Screen {

    private WorldScreen mWorldScreen;

    private InGameScreen mScreen;

    private InGameScreenManager mInGameScreenManager;

    public GameScreen(ScreenManager screenManager) {
        super(screenManager);
        mInGameScreenManager = new GameScreenManager(screenManager);
        mWorldScreen = new WorldScreen(mInGameScreenManager);
        mScreen = mWorldScreen;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        mScreen.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mScreen.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        mScreen.pause();
    }

    @Override
    public void resume() {
        super.resume();
        mScreen.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        mWorldScreen.dispose();
    }

    private void back() {
        setScreen(Screens.createMenuScreen(mScreenManager));
    }

    @Override
    public InputProcessor getInputProcessor() {
        return mScreen;
    }

    private class GameScreenManager extends InGameScreenManager {

        public GameScreenManager(ScreenManager screenManager) {
            super(screenManager);
        }

        @Override
        protected WorldScreen getWorldScreen() {
            return mWorldScreen;
        }

        @Override
        public boolean goBack() {
            if (!mScreen.goBack()) {
                back();
            }

            return true;
        }
    }
}
