package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by sing on 1/2/17.
 */

public class StageInfo {
    public int stageWidth = 600;
    public int stageHeight = 600;

    public final Vector2 mainCharacterPosition = new Vector2();

    public final Array<EnemyInfo> enemies = new Array<EnemyInfo>();
    public final Array<BlockInfo> blocks = new Array<BlockInfo>();

    public static class EnemyInfo {
        public int type;
        public final Vector2 position = new Vector2();
    }

    public static class BlockInfo {
        public int type;
        public final Vector2 position = new Vector2();
    }
}
