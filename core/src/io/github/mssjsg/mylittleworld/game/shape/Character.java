package io.github.mssjsg.mylittleworld.game.shape;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import static io.github.mssjsg.mylittleworld.game.system.Box2dSystem.PX_TO_BOX;

/**
 * Created by Sing on 11/2/2018.
 */

public class Character implements BodyShape<Character> {
    public float width;
    public float height;

    @Override
    public Character copy() {
        Character character = new Character();
        character.width = width;
        character.height = height;
        return character;
    }

    @Override
    public Shape createBox2dShape() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2 * PX_TO_BOX, height / 2 * PX_TO_BOX);
        return polygonShape;
    }
}
