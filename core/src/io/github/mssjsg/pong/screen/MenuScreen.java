package io.github.mssjsg.pong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by sing on 12/31/16.
 */

public class MenuScreen extends Screen {

    private Stage stage;
    private Skin skin;

    public MenuScreen(ScreenManager screenManager) {
        super(screenManager);
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        buildStage();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    private void buildStage() {
        Table layerControls = buildControlsLayer();

        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stack.add(layerControls);
    }

    private Table buildControlsLayer() {
        skin = new Skin(Gdx.files.internal("styles/uiskin.json"), new TextureAtlas(Gdx.files.internal("styles/uiskin.pack.atlas")));

        Table table = new Table();

        Label label = new Label("Pong!", skin, "default");
        label.setFontScale(2);
        table.add(label).expand();
        table.row().space(20);
        TextButton textButton = new TextButton("PLAY", skin, "default");
        table.add(textButton).padBottom(100);

        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mScreenManager.setScreen(new GameScreen(mScreenManager));
            }
        });

        return table;
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }
}
