package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Random;

import io.github.mssjsg.mylittleworld.game.component.Position;
import io.github.mssjsg.mylittleworld.game.data.Entity;
import io.github.mssjsg.mylittleworld.game.data.StageInfo;
import io.github.mssjsg.mylittleworld.game.system.Box2dSystem;
import io.github.mssjsg.mylittleworld.game.system.RenderShapeSystem;
import io.github.mssjsg.mylittleworld.game.system.ResourcesSystem;
import io.github.mssjsg.mylittleworld.game.system.Stages;
import io.github.mssjsg.mylittleworld.game.system.TiledMapSystem;

/**
 * Created by sing on 1/1/17.
 */

public class GameController implements Box2dSystem.OnRacketHitBallListener, ResourcesSystem.ResourcesListener {

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

    private GameView mGameView;

    private float accumulator = 0;

    private Random mRandom = new Random();

    public GameController(GameView gameView) {

        mGameView = gameView;

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

    public void resize(int width, int height) {
        updateCameras(width, height);
    }

    public void render(float delta) {
        stepTime(delta);
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
        mGameView.showGameOver(mGameState.score);
    }

    public void update(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        mSpriteBatch.setProjectionMatrix(mCamera.combined);
//        mSpriteBatch.begin();
//        mSpriteBatch.draw(mLogo, (mStageInfo.stageWidth - mLogo.getWidth()) / 2, (mStageInfo.stageHeight - mLogo.getHeight()) / 2);
//        mSpriteBatch.end();

        if (mResourcesSystem.getState() == ResourcesSystem.LOADING) {
            mResourcesSystem.update(delta);
        } if (mResourcesSystem.getState() == ResourcesSystem.LOADED) {
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
            mTiledMapSystem.update(delta);
            mRenderShapeSystem.update(delta);
            mBox2dSystem.update(delta);
            updateCameraPosition();
        }
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

    public void pause() {
        pauseGame();
    }

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
        mGameView.showScore(mGameState.score);
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

    public interface GameView {
        void showScore(int score);

        void showGameOver(int score);
    }
}
