package customactions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActions {

    public static Action showForThreeSeconds(final float duration, final Actor other) {
        return new Action() {
            float left = duration;
            @Override
            public boolean act(float delta) {
                left -= delta;
                if (left <= 0) {
                    actor.setVisible(false);
                    other.setVisible(true);
                    return true;
                }
                return false;
            }
        };
    }
    public static Action hide(final float duration) {
        return new Action() {
            @Override
            public boolean act(float delta) {

                return true;
            }
        };
    }
}
