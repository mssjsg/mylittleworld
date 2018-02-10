package io.github.mssjsg.mylittleworld.game;

import com.badlogic.gdx.graphics.Color;

import io.github.mssjsg.mylittleworld.game.component.DisplayBody;
import io.github.mssjsg.mylittleworld.game.component.HitBody;
import io.github.mssjsg.mylittleworld.game.component.Position;
import io.github.mssjsg.mylittleworld.game.shape.Circle;
import io.github.mssjsg.mylittleworld.game.shape.Rectangle;

/**
 * Created by sing on 1/7/17.
 */

public class GameEntityFactory {

    public Entity createStage(float width, float height) {
        //stage
        Rectangle rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = rectangle;
        displayBody.color = Color.CLEAR;

        return new Entity()
                .addComponent(new Position(0, 0))
                .addComponent(displayBody)
                .addComponent(new HitBody(displayBody));
    }

    public Entity createCharacter(int tag, float width, float height, float x, float y, Color color) {
        //create ball
        Rectangle rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = rectangle;
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
        Rectangle rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = rectangle;
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
