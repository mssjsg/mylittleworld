package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.mssjsg.mylittleworld.game.data.StageInfo;
import io.github.mssjsg.mylittleworld.game.data.TextureAtlasInfo;

/**
 * Created by Sing on 12/2/2018.
 */

public class ResourcesSystem extends BaseSystem {

    public static final int IDLE = 0;
    public static final int LOADING = 1;
    public static final int LOADED = 2;

    //sources
    private String mStagePath;
    private List<TextureAtlasInfo> mTextureAtlasInfos;

    //loaded resources
    private TiledMap mStageMap;
    private Map<String, Map<String, TextureRegion[][]>> mTextureRegionMap
            = new HashMap<String, Map<String, TextureRegion[][]>>();

    private AssetManager mAssetManager;
    private int mState = IDLE;

    private Set<ResourcesListener> mResourcesListeners = new HashSet<ResourcesListener>();

    public ResourcesSystem() {
        mAssetManager = new AssetManager();
        mAssetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
    }

    public void loadStage(StageInfo stageInfo) {
        if (mState != LOADING) {
            mState = LOADING;
            mTextureAtlasInfos = new ArrayList<TextureAtlasInfo>();
            mTextureAtlasInfos.addAll(stageInfo.textureAtlasInfos);

            //load texture atlas
            for (TextureAtlasInfo textureAtlasInfo : mTextureAtlasInfos) {
                mAssetManager.load(textureAtlasInfo.path, TextureAtlas.class);
            }

            mStagePath = stageInfo.mapPath;

            mAssetManager.load(mStagePath, TiledMap.class);
        }
    }

    @Override
    public void update(float delta) {
        if (mState == LOADING) {
            if (mAssetManager.update()) {
                mState = LOADED;
                initAssets();
                for (ResourcesListener resourcesListener : mResourcesListeners) {
                    resourcesListener.onResourcesLoaded();
                }
            }
        }
    }

    public TiledMap getStageMap() {
        return mStageMap;
    }

    private void initAssets() {
        // enable texture filtering for pixel smoothing
        for (TextureAtlasInfo textureAtlasInfo : mTextureAtlasInfos) {
            TextureAtlas atlas = mAssetManager.get(textureAtlasInfo.path, TextureAtlas.class);
            Map<String, TextureRegion[][]> regionMap = new HashMap<String, TextureRegion[][]>();
            mTextureRegionMap.put(textureAtlasInfo.path, regionMap);
            for (TextureAtlas.AtlasRegion atlasRegion : atlas.getRegions()) {
                regionMap.put(atlasRegion.name, atlasRegion.split(textureAtlasInfo.tileWidth, textureAtlasInfo.tileHeight));
            }

            for (Texture t : atlas.getTextures()) {
                t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            }
        }

        mStageMap = mAssetManager.get(mStagePath, TiledMap.class);
    }

    public TextureRegion getTextureRegion(String atlasPath, String textureName, int textureIndex) {
        Map<String, TextureRegion[][]> regionMap = mTextureRegionMap.get(atlasPath);
        TextureRegion[][] regions = regionMap.get(textureName);
        int columns = regions[0].length;
        return regions[textureIndex / columns][textureIndex % columns];
    }

    public int getState() {
        return mState;
    }

    public void addResourcesListener(ResourcesListener resourcesListener) {
        mResourcesListeners.add(resourcesListener);
    }

    public void removeResourcesListener(ResourcesListener resourcesListener) {
        mResourcesListeners.remove(resourcesListener);
    }

    public interface ResourcesListener {
        void onResourcesLoaded();
    }
}
