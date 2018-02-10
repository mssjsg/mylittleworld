package io.github.mssjsg.pong.game.component;

/**
 * Created by sing on 1/2/17.
 */

public class HitBody extends EntityBody {

    public HitBody() {
    }

    public HitBody(EntityBody body) {
        super(body);
    }

    @Override
    protected EntityBody createObject() {
        return new HitBody();
    }
}
