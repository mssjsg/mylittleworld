package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.graphics.Color;

import io.github.mssjsg.mylittleworld.game.component.DisplayBody;
import io.github.mssjsg.mylittleworld.game.component.HitBody;
import io.github.mssjsg.mylittleworld.game.component.Position;
import io.github.mssjsg.mylittleworld.game.shape.Character;
import io.github.mssjsg.mylittleworld.game.shape.Block;

/**
 * Created by sing on 1/7/17.
 */

public class GameEntityFactory {

    public Entity createStage(float width, float height) {
        //stage
        Block block = new Block();
        block.width = width;
        block.height = height;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = block;
        displayBody.color = Color.CLEAR;

        return new Entity()
                .addComponent(new Position(0, 0))
                .addComponent(displayBody)
                .addComponent(new HitBody(displayBody));
    }

    public Entity createCharacter(int tag, float width, float height, float x, float y, Color color) {
        //create ball
        Character character = new Character();
        character.width = width;
        character.height = height;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = character;
        displayBody.centerX = 0;
        displayBody.centerY = -height / 2f;
        displayBody.color = color;

        return new Entity(tag)
                .addComponent(new Position(x, y))
                .addComponent(displayBody)
                .addComponent(new HitBody(displayBody));

    }

    public Entity createBlock(int tag, float width, float height, float x, float y, Color color) {
        //create ball
        Block block = new Block();
        block.width = width;
        block.height = height;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = block;
        displayBody.centerX = 0;
        displayBody.centerY = -height / 2f;
        displayBody.color = color;

        return new Entity(tag)
                .addComponent(new Position(x, y))
                .addComponent(displayBody)
                .addComponent(new HitBody(displayBody));

    }

    public Entity createMain(float x, float y) {
        return createCharacter(Tags.TAG_MAIN, 40, 60, x, y, Color.RED);
    }

    public Entity createEnemy(float x, float y) {
        return createCharacter(Tags.TAG_ENEMY1, 40, 60, x, y, Color.YELLOW);
    }
}
