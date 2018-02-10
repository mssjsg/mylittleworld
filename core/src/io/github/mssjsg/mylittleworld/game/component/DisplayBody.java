package io.github.mssjsg.mylittleworld.game.component;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by sing on 1/2/17.
 */

public class DisplayBody extends EntityBody<DisplayBody> {

    public Color color;

    public DisplayBody() {
    }

    public DisplayBody(EntityBody body) {
        super(body);
    }

    @Override
    protected DisplayBody createObject() {
        return new DisplayBody();
    }

    @Override
    public DisplayBody copy() {
        DisplayBody displayBody = super.copy();
        displayBody.color = new Color(color);
        return displayBody;
    }
}
