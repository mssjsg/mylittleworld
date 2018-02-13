package io.github.mssjsg.mylittleworld.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by Sing on 11/2/2018.
 */

public class TiledMapSystem extends BaseSystem {

    private OrthogonalTiledMapRenderer mMapRenderer;
    private OrthographicCamera mCamera;

    public void setCamera(OrthographicCamera camera) {
        mCamera = camera;
    }

    public void setMap(TiledMap map) {
        mMapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void update(float delta) {
        if (mMapRenderer != null && mCamera != null) {
            mMapRenderer.setView(mCamera);
            mMapRenderer.render();
        }
    }
}
