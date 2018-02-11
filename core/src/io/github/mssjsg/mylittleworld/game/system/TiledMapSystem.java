package io.github.mssjsg.mylittleworld.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by Sing on 11/2/2018.
 */

public class TiledMapSystem extends BaseSystem {

    private OrthogonalTiledMapRenderer mMapRenderer;
    private OrthographicCamera mCamera;
    private OnLoadMapListener mOnLoadMapListener;

    public void setOnLoadMapListener(OnLoadMapListener onLoadMapListener) {
        mOnLoadMapListener = onLoadMapListener;
    }

    public void setCamera(OrthographicCamera camera) {
        mCamera = camera;
    }

    public void load(String map) {
        TiledMap tiledMap = new TmxMapLoader().load(map);
        mMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        if (mOnLoadMapListener != null) {
            mOnLoadMapListener.onLoadMap(tiledMap);
        }
    }

    @Override
    public void update(float delta) {
        if (mMapRenderer != null && mCamera != null) {
            mMapRenderer.setView(mCamera);
            mMapRenderer.render();
        }
    }

    public interface OnLoadMapListener {
        void onLoadMap(TiledMap tiledMap);
    }
}
