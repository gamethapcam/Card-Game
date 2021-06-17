package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.garth.CardGame;
import hud.hud;

public class StartScreen extends ScreenAdapter implements InputProcessor {
    private BitmapFont bitmapFont;
    private CardGame game;
    private GlyphLayout glyphLayout;
    private final String welcome = "WELCOME TO MY SHITTY GAME!";
    private final String pressSpace = "PRESS SPACE TO START";
    private Camera camera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;
    private Texture backgroundTexture2;

    private int backgroundOffsetY;
    private int backgroundOffsetX;
    private final float CAMERA_SPEED = 150f;

    public StartScreen(CardGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        viewport = new FitViewport(hud.WORLD_WIDTH, hud.WORLD_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        bitmapFont = game.getAssetManager().get("pixel_regular.ttf");
        backgroundTexture = game.getAssetManager().get("menu_bg.png");
        backgroundTexture2 = game.getAssetManager().get("menu_bg.png");

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen();
        draw();
    }

    private void update(float delta) {
        // scrolling background implementation
        backgroundOffsetY += CAMERA_SPEED * delta;
        backgroundOffsetX += CAMERA_SPEED * delta;
        if (backgroundOffsetY >= hud.WORLD_HEIGHT) {
            backgroundOffsetY = 0;
        }
        if (backgroundOffsetX >= hud.WORLD_WIDTH) {
            backgroundOffsetX = 0;
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(backgroundTexture, -backgroundOffsetX, -backgroundOffsetY);
        spriteBatch.draw(backgroundTexture2, -backgroundOffsetX + hud.WORLD_WIDTH,
                -backgroundOffsetY + hud.WORLD_HEIGHT);

        glyphLayout = new GlyphLayout(bitmapFont, welcome);
        bitmapFont.draw(spriteBatch, welcome,(hud.WORLD_WIDTH / 2) - (glyphLayout.width / 2),
                hud.WORLD_HEIGHT / 1.25f);

        glyphLayout.setText(bitmapFont, pressSpace);
        bitmapFont.draw(spriteBatch, pressSpace, (hud.WORLD_WIDTH / 2) - (glyphLayout.width / 2),
                hud.WORLD_HEIGHT / 2f);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        backgroundTexture2.dispose();
        bitmapFont.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            game.setScreen(new GameScreen(game));
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
