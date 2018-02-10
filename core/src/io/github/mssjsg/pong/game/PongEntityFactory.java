package io.github.mssjsg.pong.game;

import com.badlogic.gdx.graphics.Color;

import io.github.mssjsg.pong.game.component.DisplayBody;
import io.github.mssjsg.pong.game.component.HitBody;
import io.github.mssjsg.pong.game.component.Position;
import io.github.mssjsg.pong.game.shape.Circle;
import io.github.mssjsg.pong.game.shape.Rectangle;

/**
 * Created by sing on 1/7/17.
 */

public class PongEntityFactory {

    public enum RacketSide {
        LEFT, TOP, RIGHT, BOTTOM;
    }

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

    public Entity createBall(float radius, float x, float y, Color color) {
        //create ball
        Circle circle = new Circle();
        circle.radius = radius;

        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = circle;
        displayBody.centerX = 0;
        displayBody.centerY = 0;
        displayBody.color = color;

        return new Entity(Tags.TAG_BALL)
                .addComponent(new Position(x, y))
                .addComponent(displayBody)
                .addComponent(new HitBody(displayBody));

    }

    public Entity createRacket(RacketSide racketSide, float length, float thickness, float x, float y, Color color) {
        Rectangle rect = new Rectangle();

        switch (racketSide) {
            case LEFT:
            case RIGHT:
                rect.width = thickness;
                rect.height = length;
                break;
            case TOP:
            case BOTTOM:
                rect.width = length;
                rect.height = thickness;

        }

        //create racket left
        DisplayBody displayBody = new DisplayBody();
        displayBody.shape = rect;

        int tag = Tags.TAG_NONE;

        switch (racketSide) {
            case LEFT:
                displayBody.centerX = -thickness / 2;
                displayBody.centerY = 0;
                tag = Tags.TAG_RACKET_LEFT;
                break;
            case RIGHT:
                displayBody.centerX = thickness / 2;
                displayBody.centerY = 0;
                tag = Tags.TAG_RACKET_RIGHT;
                break;
            case TOP:
                displayBody.centerX = 0;
                displayBody.centerY = thickness / 2;
                tag = Tags.TAG_RACKET_TOP;
                break;
            case BOTTOM:
                displayBody.centerX = 0;
                displayBody.centerY = -thickness / 2;
                tag = Tags.TAG_RACKET_BOTTOM;
                break;
        }


        displayBody.color = color;

        return new Entity(tag)
                .addComponent(displayBody)
                .addComponent(new Position(x, y))
                .addComponent(new HitBody(displayBody));
    }
}
