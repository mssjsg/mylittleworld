package io.github.mssjsg.mylittleworld.game.world;

import com.badlogic.gdx.Gdx;
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

import io.github.mssjsg.mylittleworld.game.BaseSystem;

/**
 * Created by Sing on 19/2/2018.
 */

public class WorldPanelSystem extends BaseSystem {

    private static final int UI_WIDTH = 500;
    private static final String STYLE_DEFAULT = "default";
    private static final String STYLE_GAMEOVER = "gameOver";

    private Label mScore;
    private Label mStatus;
    private Label mBtnRetry;

    private Stage mStage;
    private Skin skin;

    public WorldPanelSystem() {
        mStage = new Stage(new ExtendViewport(UI_WIDTH,
                (int)((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth() * UI_WIDTH)));

        buildStage();
    }

    @Override
    public void update(float delta) {
        mStage.act(delta);
        mStage.draw();
    }

    public void resize(int width, int height) {
        mStage.getViewport().update(width, height, true);
    }

    public InputProcessor getInputProcessor() {
        return mStage;
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

    public void showScore(int score) {
        mScore.setText(String.valueOf(score));
    }

    public void showGameOver(int score) {
        mScore.setText(String.valueOf(score));
        mStatus.setText("Game Over");
        mStatus.setVisible(true);
        mBtnRetry.setVisible(true);
    }

    private void resetGame() {
        mStatus.setVisible(false);
        mBtnRetry.setVisible(false);
    }
}
