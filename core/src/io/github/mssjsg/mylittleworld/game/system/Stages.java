package io.github.mssjsg.mylittleworld.game.system;

import io.github.mssjsg.mylittleworld.game.data.StageInfo;
import io.github.mssjsg.mylittleworld.game.data.TextureAtlasInfo;

/**
 * Created by Sing on 10/2/2018.
 */

public class Stages {

    public static final int TILE_SIZE = 32;

    public static StageInfo createStage(int stageId) {
        StageInfo stageInfo = new StageInfo();

        stageInfo.mapPath = "map/stage1.tmx";

        stageInfo.stageHeight = 16 * TILE_SIZE;
        stageInfo.stageWidth = 16 * TILE_SIZE;
        stageInfo.mainCharacterPosition.x = 6 * TILE_SIZE;
        stageInfo.mainCharacterPosition.y = 6 * TILE_SIZE;

        StageInfo.EnemyInfo enemyInfo = new StageInfo.EnemyInfo();
        enemyInfo.position.x = 2 * TILE_SIZE;
        enemyInfo.position.y = 2 * TILE_SIZE;

        stageInfo.enemies.add(enemyInfo);

        StageInfo.BlockInfo blockInfo = new StageInfo.BlockInfo();
        blockInfo.position.x = 5 * TILE_SIZE;
        blockInfo.position.y = 5 * TILE_SIZE;
        stageInfo.blocks.add(blockInfo);

//        TextureAtlasInfo textureAtlasInfo = new TextureAtlasInfo("");
//        stageInfo.textureAtlasInfos.add();

        return stageInfo;
    }
}
