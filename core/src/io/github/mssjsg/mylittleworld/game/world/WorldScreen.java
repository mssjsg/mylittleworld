package io.github.mssjsg.mylittleworld.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Random;

import io.github.mssjsg.mylittleworld.Screen;
import io.github.mssjsg.mylittleworld.ScreenManager;
import io.github.mssjsg.mylittleworld.game.GameEntityFactory;
import io.github.mssjsg.mylittleworld.game.GameKeys;
import io.github.mssjsg.mylittleworld.game.GameModel;
import io.github.mssjsg.mylittleworld.game.GameScreen;
import io.github.mssjsg.mylittleworld.game.GameState;
import io.github.mssjsg.mylittleworld.game.InGameScreen;
import io.github.mssjsg.mylittleworld.game.InGameScreenManager;
import io.github.mssjsg.mylittleworld.game.Stages;
import io.github.mssjsg.mylittleworld.game.States;
import io.github.mssjsg.mylittleworld.game.Tags;
import io.github.mssjsg.mylittleworld.game.component.Position;
import io.github.mssjsg.mylittleworld.game.data.Entity;
import io.github.mssjsg.mylittleworld.game.data.StageInfo;
import io.github.mssjsg.mylittleworld.game.system.Box2dSystem;
import io.github.mssjsg.mylittleworld.game.system.RenderShapeSystem;
import io.github.mssjsg.mylittleworld.game.system.ResourcesSystem;
import io.github.mssjsg.mylittleworld.game.system.TiledMapSystem;

/**
 * Created by sing on 1/1/17.
 */

public class WorldScreen extends InGameScreen implements Box2dSystem.OnRacketHitBallListener, ResourcesSystem.ResourcesListener {

    private static final int CAMERRA_WIDTH = 400;

    private GameModel mModel;

    private int mKeysPressing;
    private int mKeysPressed;

    private OrthographicCamera mCamera;
    private OrthographicCamera mBoxCamera;

    private SpriteBatch mSpriteBatch;
    private ShapeRenderer mShapeRenderer;

    private StageInfo mStageInfo;
    private Texture mLogo;

    private GameState mGameState;

    private RenderShapeSystem mRenderShapeSystem; //render shapes
    private Box2dSystem mBox2dSystem; //physics
    private TiledMapSystem mTiledMapSystem; //map
    private ResourcesSystem mResourcesSystem;

    private GameEntityFactory mGameEntityFactory;

    private float mTimeStep = 1/60f;

    private float mSpeed = 5f;

    private float accumulator = 0;

    private Random mRandom = new Random();

    private static final String STYLE_DEFAULT = "default";
    private static final String STYLE_GAMEOVER = "gameOver";
    private static final int UI_WIDTH = 500;

    private Stage mStage;
    private Skin skin;

    private GameInputProcessor mGameInputProcessor;

    private Label mScore;
    private Label mStatus;
    private Label mBtnRetry;

    public WorldScreen(InGameScreenManager screenManager) {
        super(screenManager);

        mModel = new GameModel();

        mGameEntityFactory = new GameEntityFactory();
        mGameState = new GameState();
        mSpriteBatch = new SpriteBatch();
        mShapeRenderer = new ShapeRenderer();

        mLogo = new Texture("badlogic.jpg");

        mCamera = new OrthographicCamera();
        mBoxCamera = new OrthographicCamera();

        updateCameras(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //init systems
        mRenderShapeSystem = new RenderShapeSystem(mCamera, mShapeRenderer);
        mTiledMapSystem = new TiledMapSystem();
        mTiledMapSystem.setCamera(mCamera);
        mBox2dSystem = new Box2dSystem(mBoxCamera, this);
        mResourcesSystem = new ResourcesSystem();
        mResourcesSystem.addResourcesListener(this);

        mKeysPressing = 0;

        mGameState.state = States.PLAYING;

        //init stage
        mStageInfo = Stages.createStage(0);
        startGame(mStageInfo);


        mStage = new Stage(new ExtendViewport(UI_WIDTH,
                (int)((float)Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth() * UI_WIDTH)));
        mGameInputProcessor = new GameInputProcessor();

        buildStage();
    }

    private void startGame(StageInfo stageInfo) {
        mResourcesSystem.loadStage(stageInfo);
    }

    private void setupStage(StageInfo stageInfo) {
        mModel.stage = mGameEntityFactory.createStage(stageInfo.stageWidth, stageInfo.stageHeight);

        Vector2 mainPosition = stageInfo.mainCharacterPosition;
        mModel.main = mGameEntityFactory.createMain(mainPosition.x, mainPosition.y);
        addEntity(mModel.main);

        for (StageInfo.EnemyInfo enemyInfo : stageInfo.enemies) {
            Vector2 position = enemyInfo.position;
            Entity enemy = mGameEntityFactory.createEnemy(position.x, position.y);
            mModel.enemies.add(enemy);
            addEntity(enemy);
        }

        for (StageInfo.BlockInfo blockInfo : stageInfo.blocks) {
            Vector2 position = blockInfo.position;
            Entity block = mGameEntityFactory.createBlock(Tags.TAG_BLOCK1, Stages.TILE_SIZE,
                    Stages.TILE_SIZE, position.x, position.y, Color.GREEN);
            mModel.blocks.add(block);
            addBlock(block);
        }

        //add top boundary
        Entity boundaryTop = mGameEntityFactory.createBlock(Tags.TAG_BLOCK1, mStageInfo.stageWidth,
                0, mStageInfo.stageWidth / 2f, mStageInfo.stageHeight, null);
        addBlock(boundaryTop);

        //add left boundary
        Entity boundaryLeft = mGameEntityFactory.createBlock(Tags.TAG_BLOCK1, 0,
                mStageInfo.stageHeight, 0, 0, null);
        addBlock(boundaryLeft);

        //add right boundary
        Entity boundaryRight = mGameEntityFactory.createBlock(Tags.TAG_BLOCK1, 0,
                mStageInfo.stageHeight, mStageInfo.stageWidth, 0, null);
        addBlock(boundaryRight);

        //add bottom boundary
        Entity boundaryBottom = mGameEntityFactory.createBlock(Tags.TAG_BLOCK1, mStageInfo.stageWidth,
                0, mStageInfo.stageWidth / 2f, 0, null);
        addBlock(boundaryBottom);
    }

    private void addEntity(Entity entity) {
        mBox2dSystem.addEntity(entity, true);
        mRenderShapeSystem.addEntity(entity);
    }

    private void addBlock(Entity entity) {
        mBox2dSystem.addEntity(entity, false);
        mRenderShapeSystem.addEntity(entity);
    }

    private void updateCameras(int screenWidth, int screenHeight) {

        float width = CAMERRA_WIDTH;
        float height = (float)screenHeight / (float)screenWidth * width;

        mCamera.viewportWidth = width;
        mCamera.viewportHeight = height;
        mCamera.update();

        mBoxCamera.viewportWidth = width * Box2dSystem.PX_TO_BOX;
        mBoxCamera.viewportHeight = height * Box2dSystem.PX_TO_BOX;
        mBoxCamera.update();
    }

    private void updateCameraPosition() {
        Position position = mModel.main.getComponent(Position.class);

        float cameraPosX = position.x;
        float cameraPosY = position.y;

        mCamera.position.set(cameraPosX, cameraPosY, 0);
        mCamera.update();

        mBoxCamera.position.set(cameraPosX * Box2dSystem.PX_TO_BOX, cameraPosY * Box2dSystem.PX_TO_BOX, 0);
        mBoxCamera.update();
    }

    private void resumeGame() {
        mGameState.state = States.PLAYING;
    }

    private void pauseGame() {
        mGameState.state = States.PAUSED;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return mGameInputProcessor;
    }

    @Override
    public void resize(int width, int height) {
        updateCameras(width, height);
        mStage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //        mSpriteBatch.setProjectionMatrix(mCamera.combined);
//        mSpriteBatch.begin();
//        mSpriteBatch.draw(mLogo, (mStageInfo.stageWidth - mLogo.getWidth()) / 2, (mStageInfo.stageHeight - mLogo.getHeight()) / 2);
//        mSpriteBatch.end();


        if (mResourcesSystem.getState() == ResourcesSystem.LOADING) {
            mResourcesSystem.update(delta);
        } else if (mResourcesSystem.getState() == ResourcesSystem.LOADED) {
            stepTime(delta);
            mTiledMapSystem.update(delta);
            mRenderShapeSystem.update(delta);
        }

        mStage.act(delta);
        mStage.draw();
    }

    private void updateState(float velocityX, float velocityY) {
        Body mainBody = mBox2dSystem.getBody(mModel.main);

        mainBody.setLinearVelocity(velocityX, velocityY);

        for (Entity entity : mModel.enemies) {
            Body body = mBox2dSystem.getBody(entity);
            Vector2 velocity = body.getLinearVelocity();
            body.setLinearVelocity(velocity.x * 0.9f, velocity.y * 0.9f);
        }

//        if (mGameState.state == States.PLAYING && !mStageBound.contains(ball.getPosition())) {
//            gameOver();
//        }
    }

    private void gameOver() {
        mGameState.state = States.GAME_OVER;
        showGameOver(mGameState.score);
    }

    public void update(float delta) {
            float velocityX = 0;
            float velocityY = 0;

            mKeysPressed = 0;

            if (isPressingKey(GameKeys.KEY_RIGHT)) {
                velocityX += mSpeed;
            }

            if (isPressingKey(GameKeys.KEY_LEFT)) {
                velocityX -= mSpeed;
            }

            if (isPressingKey(GameKeys.KEY_UP)) {
                velocityY += mSpeed;
            }

            if (isPressingKey(GameKeys.KEY_DOWN)) {
                velocityY -= mSpeed;
            }

            updateState(velocityX, velocityY);
            mBox2dSystem.update(delta);
            updateCameraPosition();
    }

    private void stepTime(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= mTimeStep) {
            update(mTimeStep);
            accumulator -= mTimeStep;
        }
    }

    @Override
    public void pause() {
        pauseGame();
    }

    @Override
    public void resume() {
        resumeGame();
    }

    public void dispose() {
        mSpriteBatch.dispose();
        mShapeRenderer.dispose();
        mBox2dSystem.dispose();

        mLogo.dispose();
    }

    private boolean isPressingKey(int key) {
        return (mKeysPressing & key) > 0;
    }

    public void press(int key) {
        mKeysPressing |= key;
    }

    public void unpress(int key) {
        mKeysPressing &= ~key;
        mKeysPressed |= key;
    }

    @Override
    public void onRacketHitBall(int tag) {
        mGameState.score++;
        showScore(mGameState.score);
    }

    private void setEntityPosition(Entity entity, float x, float y) {
        mBox2dSystem.getBody(entity).setTransform(x * Box2dSystem.PX_TO_BOX, y * Box2dSystem.PX_TO_BOX, 0);
    }

    public void restartStage() {
        mGameState.score = 0;
        mGameState.state = States.IDLE;

        float centerX = mStageInfo.stageWidth / 2f;
        float centerY = mStageInfo.stageHeight / 2f;

        setEntityPosition(mModel.main, centerX, centerY);

        resumeGame();
    }

    @Override
    public void onResourcesLoaded() {
        mTiledMapSystem.setMap(mResourcesSystem.getStageMap());
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) mResourcesSystem.getStageMap().getLayers().get(0);
        for (int x = 0; x < tiledMapTileLayer.getWidth(); x++) {
            for (int y = 0; y < tiledMapTileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(x, y);
                if (cell.getTile().getProperties().get("type").equals("1")) {
                    Vector2 position = new Vector2();
                    position.x = (0.5f + x) * Stages.TILE_SIZE;
                    position.y = y * Stages.TILE_SIZE;
                    Entity block = mGameEntityFactory.createBlock(Tags.TAG_BLOCK1, Stages.TILE_SIZE,
                            Stages.TILE_SIZE, position.x, position.y, Color.PURPLE);
                    mModel.blocks.add(block);
                    addBlock(block);
                }
            }
        }

        setupStage(mStageInfo);
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
        restartStage();
    }

    private class GameInputProcessor implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.UP:
                    press(GameKeys.KEY_UP);
                    return true;
                case Input.Keys.DOWN:
                    press(GameKeys.KEY_DOWN);
                    return true;
                case Input.Keys.LEFT:
                    press(GameKeys.KEY_LEFT);
                    return true;
                case Input.Keys.RIGHT:
                    press(GameKeys.KEY_RIGHT);
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.UP:
                    unpress(GameKeys.KEY_UP);
                    return true;
                case Input.Keys.DOWN:
                    unpress(GameKeys.KEY_DOWN);
                    return true;
                case Input.Keys.LEFT:
                    unpress(GameKeys.KEY_LEFT);
                    return true;
                case Input.Keys.RIGHT:
                    unpress(GameKeys.KEY_RIGHT);
                    return true;
                case Input.Keys.BACK:
                case Input.Keys.BACKSPACE:
                    mScreenManager.goBack();
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
