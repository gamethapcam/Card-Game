package Screens;

import cards.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.garth.CardGame;
import hud.hud;

public class GameScreen extends ScreenAdapter {
    private CardGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private Texture backgroundTexture;
    private Texture backgroundTexture2;
    private int backgroundOffsetY;
    private int backgroundOffsetX;
    private final float CAMERA_SPEED = 150f;

    private TextureRegion backCard;
    private TextureRegion joker;
    private TextureRegion club1;
    private TextureRegion club2;
    private TextureRegion club3;

    private Stage stage;

    private float DISTANCE_BETWEEN = 50f;
    private Array<Card> cards = new Array<>();
    public GameScreen(CardGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(hud.WORLD_WIDTH, hud.WORLD_HEIGHT, camera);
//        viewport = new ScreenViewport(camera);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        TextureAtlas textureAtlas = game.getAssetManager().get("Card-Game.atlas");
        backCard = textureAtlas.findRegion("back-256");
        club1 = textureAtlas.findRegion("club-1-256");
        club2 = textureAtlas.findRegion("club-2-256");
        club3 = textureAtlas.findRegion("club-3-256");
        joker = textureAtlas.findRegion("joker-256");
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        cards.add(new Card(backCard, club1));
        cards.add(new Card(backCard, club2));
        cards.add(new Card(backCard, club3));
        cards.add(new Card(backCard, joker));
        for (Card c : cards) {
            c.addActor(stage);
        }

        cards.first().setPosition((hud.WORLD_WIDTH / 2) - (cards.first().getWidth() * 2),
                hud.WORLD_HEIGHT - 200);
        for (int i = 1; i < 4; i++) {
            Card c = cards.get(i - 1);
            float cX = c.getPositionX();
            float cY = c.getPositionY();
            float width = c.getWidth();
            float height = c.getHeight();

            cards.get(i).setPosition((cX + width) + DISTANCE_BETWEEN, cY);
        }

        backgroundTexture = new Texture(Gdx.files.internal("dungeon_bg.png"));
        backgroundTexture2 = new Texture(Gdx.files.internal("dungeon_bg.png"));

    }

    @Override
    public void render(float delta) {
        stage.act();
        clearScreen();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        batch.end();
        stage.draw();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawRects();
        shapeRenderer.end();
    }

    private void drawRects() {
        for (Card c : cards) {
            c.drawRectangle(shapeRenderer);
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
//        backgroundSprite.draw(batch);
        // scrolling background implementation
        backgroundOffsetY += CAMERA_SPEED * Gdx.graphics.getDeltaTime();
        backgroundOffsetX += CAMERA_SPEED * Gdx.graphics.getDeltaTime();
        if (backgroundOffsetY >= hud.WORLD_HEIGHT) {
            backgroundOffsetY = 0;
        }
        if (backgroundOffsetX >= hud.WORLD_WIDTH) {
            backgroundOffsetX = 0;
        }

        batch.draw(backgroundTexture, -backgroundOffsetX, -backgroundOffsetY);
        batch.draw(backgroundTexture2, -backgroundOffsetX + hud.WORLD_WIDTH, -backgroundOffsetY + hud.WORLD_HEIGHT);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
//        stage.getViewport().update(width, height, true);
//        System.out.println("w= " + width + "   height= " + height);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
