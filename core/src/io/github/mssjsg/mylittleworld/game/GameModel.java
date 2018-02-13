package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.mylittleworld.game.data.Entity;

/**
 * Created by Sing on 10/2/2018.
 */

public class GameModel {
    public Entity stage;
    public Entity main;
    public Array<Entity> blocks;
    public Array<Entity> enemies;

    public GameModel() {
        blocks = new Array<Entity>();
        enemies = new Array<Entity>();
    }
}
