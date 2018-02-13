package io.github.mssjsg.mylittleworld.game.data;

/**
 * Created by maksing on 25/2/2016.
 */
public class TextureAtlasInfo {
    public final String path;
    public final int tileWidth;
    public final int tileHeight;

    public TextureAtlasInfo(String path, int tileWidth, int tileHeight) {
        this.path = path;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }
}
