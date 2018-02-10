package io.github.mssjsg.mylittleworld.game.system;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import io.github.mssjsg.mylittleworld.game.Entity;
import io.github.mssjsg.mylittleworld.game.component.DisplayBody;
import io.github.mssjsg.mylittleworld.game.component.Position;
import io.github.mssjsg.mylittleworld.game.shape.Circle;
import io.github.mssjsg.mylittleworld.game.shape.Rectangle;

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

                if (displayBody.shape instanceof Circle) {
                    renderClircle(position, displayBody);
                } else if (displayBody.shape instanceof Rectangle) {
                    renderRectangle(position, displayBody);
                }
            }
        }
        mShapeRenderer.end();
    }

    private void renderClircle(Position position, DisplayBody displayBody) {

        float radius = ((Circle)displayBody.shape).radius;

        mShapeRenderer.circle(position.x - displayBody.centerX, position.y - displayBody.centerY,
                radius, 100);
    }

    private void renderRectangle(Position position, DisplayBody displayBody) {
        Rectangle rectangle = (Rectangle)displayBody.shape;

        float x = position.x - rectangle.width / 2 - displayBody.centerX;
        float y = position.y - rectangle.height / 2 - displayBody.centerY;

        mShapeRenderer.rect(x, y, ((Rectangle)displayBody.shape).width, rectangle.height);
    }
}
