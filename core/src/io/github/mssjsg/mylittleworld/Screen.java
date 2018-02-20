package io.github.mssjsg.mylittleworld;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by sing on 12/31/16.
 */

public abstract class Screen implements InputProcessor {

    private static InputProcessor EMPTY_INPUT_PROCESSOR = new EmptyInputProcessor();

    protected ScreenManager mScreenManager;

    public Screen(ScreenManager screenManager) {
        mScreenManager = screenManager;
    }

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

    public boolean goBack() {
        return false;
    }

    public InputProcessor getInputProcessor() {
        return EMPTY_INPUT_PROCESSOR;
    }

    @Override
    public boolean keyDown(int keycode) {
        return getInputProcessor().keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return getInputProcessor().keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return getInputProcessor().keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return getInputProcessor().touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return getInputProcessor().touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return getInputProcessor().touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return getInputProcessor().mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return getInputProcessor().scrolled(amount);
    }

    private static class EmptyInputProcessor implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
