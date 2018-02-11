package io.github.mssjsg.mylittleworld.game.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;

import static io.github.mssjsg.mylittleworld.game.system.Box2dSystem.PX_TO_BOX;

/**
 * Created by sing on 1/3/17.
 */

public class Block implements BodyShape<Block> {
    public float width;
    public float height;

    @Override
    public Block copy() {
        Block block = new Block();
        block.width = width;
        block.height = height;
        return block;
    }

    @Override
    public Shape createBox2dShape() {
        return createBox(width, height);
    }

    private Shape createBox(float width, float height) {
        width = width * PX_TO_BOX;
        height = height * PX_TO_BOX;

        ChainShape chainShape = new ChainShape();
        Vector2[] vector2s = null;

        if (width == 0 || height == 0) {
            vector2s = new Vector2[2];
        } else {
            vector2s = new Vector2[4];
        }

        int i = 0;
        vector2s[i] = new Vector2(-width / 2f, - height / 2f);
        i++;
        if (width != 0) {
            vector2s[i] = new Vector2(width / 2f, -height / 2f);
            i++;
        }
        if (height != 0) {
            vector2s[i] = new Vector2(width / 2f, height / 2f);
            i++;
            if (width != 0) {
                vector2s[i] = new Vector2(-width / 2f, height / 2f);
            }
        }

        chainShape.createChain(vector2s);
        return chainShape;
    }
}
