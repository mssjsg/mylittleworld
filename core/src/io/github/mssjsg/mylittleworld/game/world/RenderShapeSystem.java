package io.github.mssjsg.mylittleworld.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.mylittleworld.game.data.Entity;
import io.github.mssjsg.mylittleworld.game.component.DisplayBody;
import io.github.mssjsg.mylittleworld.game.component.Position;
import io.github.mssjsg.mylittleworld.game.shape.Character;
import io.github.mssjsg.mylittleworld.game.shape.Ball;
import io.github.mssjsg.mylittleworld.game.shape.Block;
import io.github.mssjsg.mylittleworld.game.AbstractEntitySystem;

/**
 * Created by sing on 1/2/17.
 */

public class RenderShapeSystem extends AbstractEntitySystem {

    private OrthographicCamera mCamera;
    private ShapeRenderer mShapeRenderer;

    public RenderShapeSystem(OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        mCamera = camera;
        mShapeRenderer = shapeRenderer;
    }

    @Override
    protected void onUpdate(float delta, Array<Entity> components) {
        mShapeRenderer.setProjectionMatrix(mCamera.combined);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity : components) {
            DisplayBody displayBody = entity.getComponent(DisplayBody.class);
            Position position = entity.getComponent(Position.class);
            if (displayBody.color != null) {
                mShapeRenderer.setColor(displayBody.color);

                if (displayBody.shape instanceof Ball) {
                    renderClircle(position, displayBody);
                } else if (displayBody.shape instanceof Block) {
                    renderRectangle(position, displayBody);
                } else if (displayBody.shape instanceof Character) {
                    renderCharacter(position, displayBody);
                }
            }
        }
        mShapeRenderer.end();
    }

    private void renderClircle(Position position, DisplayBody displayBody) {

        float radius = ((Ball)displayBody.shape).radius;

        mShapeRenderer.circle(position.x - displayBody.centerX, position.y - displayBody.centerY,
                radius, 100);
    }

    private void renderRectangle(Position position, DisplayBody displayBody) {
        Block block = (Block)displayBody.shape;

        float x = position.x - block.width / 2 - displayBody.centerX;
        float y = position.y - block.height / 2 - displayBody.centerY;

        mShapeRenderer.rect(x, y, ((Block)displayBody.shape).width, block.height);
    }

    private void renderCharacter(Position position, DisplayBody displayBody) {
        Character rectangle = (Character)displayBody.shape;

        float x = position.x - rectangle.width / 2 - displayBody.centerX;
        float y = position.y - rectangle.height / 2 - displayBody.centerY;

        mShapeRenderer.rect(x, y, ((Character)displayBody.shape).width, rectangle.height);
    }
}
