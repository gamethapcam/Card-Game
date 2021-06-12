package cards;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.w3c.dom.css.Rect;

public class Card {

    private float x, y;
    private ImageButton backImageButton;
    private ImageButton frontImage;
    private float width = 100;
    private float height = 130;
    private Rectangle cardRectangleBorder;
    private boolean allowDraw = false;

    public Card(TextureRegion back, TextureRegion front) {
        this.backImageButton = new ImageButton(new TextureRegionDrawable(back));
        this.frontImage = new ImageButton(new TextureRegionDrawable(front));
        x = y = 0;
        flipButtonListener();
        backImageButton.setSize(width, height);
        frontImage.setSize(width, height);
        cardRectangleBorder = new Rectangle(x, y, width, height );
    }


    public void drawRectangle(ShapeRenderer shapeRenderer) {
        // only allow rectangle to be drawn if the front Image is shown.
        // check flipButtonListener method for flag control.
        if (allowDraw)
            shapeRenderer.rect(x, y, cardRectangleBorder.getWidth(), cardRectangleBorder.height);
    }

    private void flipButtonListener() {
        this.backImageButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button)
            {
                super.tap(event, x, y, count, button);
                backImageButton.setVisible(false);
                allowDraw = true;
            }
        });

        this.frontImage.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button)
            {
                super.tap(event, x, y, count, button);
                if (!backImageButton.isVisible()) {
                    backImageButton.setVisible(true);
                }
                allowDraw = false;
            }
        });
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        backImageButton.setPosition(this.x, this.y);
        frontImage.setPosition(this.x, this.y);

        cardRectangleBorder.setPosition(this.x, this.y);
    }

    public void addActor(Stage stage) {
        stage.addActor(frontImage);
        stage.addActor(backImageButton);
    }
    public float getPositionX() {
        return this.x;
    }
    public float getPositionY() {
        return this.y;
    }
    public float getWidth() {
        return frontImage.getWidth();
    }
    public float getHeight() {
        return frontImage.getHeight();
    }
}
