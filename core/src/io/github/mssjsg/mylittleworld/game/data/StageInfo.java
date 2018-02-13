package io.github.mssjsg.mylittleworld.game.data;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sing on 1/2/17.
 */

public class StageInfo {
    public int stageWidth;
    public int stageHeight;
    public String mapPath;
    public List<TextureAtlasInfo> textureAtlasInfos = new ArrayList<TextureAtlasInfo>();

    public final Vector2 mainCharacterPosition = new Vector2();

    public final List<EnemyInfo> enemies = new ArrayList<EnemyInfo>();
    public final List<BlockInfo> blocks = new ArrayList<BlockInfo>();

    public static class EnemyInfo {
        public int type;
        public final Vector2 position = new Vector2();
    }

    public static class BlockInfo {
        public int type;
        public final Vector2 position = new Vector2();
    }
}
