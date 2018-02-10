package io.github.mssjsg.pong.screen;

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

import io.github.mssjsg.pong.game.GameController;
import io.github.mssjsg.pong.game.GameKeys;

/**
 * Created by sing on 12/31/16.
 */

public class GameScreen extends Screen implements GameController.GameView {

    private static final String STYLE_DEFAULT = "default";
    private static final String STYLE_GAMEOVER = "gameOver";
    private static final int UI_WIDTH = 500;

    private Stage mStage;
    private Skin skin;

    private GameInputProcessor mGameInputProcessor;
    private GameController mGameController;

    private Label mScore;
    private Label mStatus;
    private Label mBtnRetry;

    public GameScreen(ScreenManager screenManager) {
        super(screenManager);

        mStage = new Stage(new ExtendViewport(UI_WIDTH,
                (int)((float)Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth() * UI_WIDTH)));
        mGameInputProcessor = new GameInputProcessor();
        mGameController = new GameController(this);
        buildStage();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return mGameInputProcessor;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        mGameController.render(delta);
        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mGameController.resize(width, height);
        mStage.getViewport().update(UI_WIDTH, (int)((float)height / (float) width * UI_WIDTH), true);
    }

    @Override
    public void pause() {
        super.pause();
        mGameController.pause();
    }

    @Override
    public void resume() {
        super.resume();
        mGameController.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        mGameController.dispose();
    }

    private void back() {
        setScreen(Screens.createMenuScreen(mScreenManager));
    }

    private void buildStage() {
        Table layerControls = buildControlsLayer();

        mStage.clear();
        Stack stack = new Stack();
        mStage.addActor(stack);
        stack.setSize(mStage.getWidth(), mStage.getHeight());
        stack.add(layerControls);
    }

    private Table buildControlsLayer() {
        skin = new Skin(Gdx.files.internal("styles/uiskin.json"), new TextureAtlas(Gdx.files.internal("styles/uiskin.pack.atlas")));

        Table table = new Table();
        table.top();

        mScore = new Label("0", skin, STYLE_DEFAULT);
        mScore.setFontScale(2);
        table.add(mScore).align(Align.top).fillX().padTop(20);

        mStatus = new Label("Game Over", skin, STYLE_GAMEOVER);
        mStatus.setFontScale(2);
        mStatus.setVisible(false);
        table.row();
        table.add(mStatus).fillX().padTop(130);

        mBtnRetry = new Label("Retry", skin, STYLE_GAMEOVER);
        mBtnRetry.setFontScale(1.5f);
        mBtnRetry.setVisible(false);
        table.row();
        table.add(mBtnRetry).align(Align.center).padTop(80);

        mBtnRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resetGame();
            }
        });

        return table;
    }

    @Override
    public void showScore(int score) {
        mScore.setText(String.valueOf(score));
    }

    @Override
    public void showGameOver(int score) {
        mScore.setText(String.valueOf(score));
        mStatus.setText("Game Over");
        mStatus.setVisible(true);
        mBtnRetry.setVisible(true);
    }

    private void resetGame() {
        mStatus.setVisible(false);
        mBtnRetry.setVisible(false);
        mGameController.restartStage();
    }

    private class GameInputProcessor implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.UP:
                    mGameController.press(GameKeys.KEY_UP);
                    return true;
                case Input.Keys.DOWN:
                    mGameController.press(GameKeys.KEY_DOWN);
                    return true;
                case Input.Keys.LEFT:
                    mGameController.press(GameKeys.KEY_LEFT);
                    return true;
                case Input.Keys.RIGHT:
                    mGameController.press(GameKeys.KEY_RIGHT);
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.UP:
                    mGameController.unpress(GameKeys.KEY_UP);
                    return true;
                case Input.Keys.DOWN:
                    mGameController.unpress(GameKeys.KEY_DOWN);
                    return true;
                case Input.Keys.LEFT:
                    mGameController.unpress(GameKeys.KEY_LEFT);
                    return true;
                case Input.Keys.RIGHT:
                    mGameController.unpress(GameKeys.KEY_RIGHT);
                    return true;
                case Input.Keys.BACK:
                case Input.Keys.BACKSPACE:
                    back();
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return mStage.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return mStage.touchUp(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return mStage.touchDragged(screenX, screenY, pointer);
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return mStage.mouseMoved(screenX, screenY);
        }

        @Override
        public boolean scrolled(int amount) {
            return mStage.scrolled(amount);
        }
    }
}
