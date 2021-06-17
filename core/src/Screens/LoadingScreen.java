package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.garth.CardGame;
import hud.hud;
import jdk.javadoc.internal.tool.Start;

public class LoadingScreen extends ScreenAdapter {

    private Camera camera;
    private Viewport viewport;

    private CardGame game;
    private Texture bg;

    private SpriteBatch batch;

    public LoadingScreen(CardGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(hud.WORLD_WIDTH, hud.WORLD_HEIGHT, camera);
        game.getAssetManager().getLogger().setLevel((Logger.DEBUG));
        game.getAssetManager().load("Card-Game.atlas", TextureAtlas.class);
        game.getAssetManager().setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(
                new InternalFileHandleResolver()
        ));
        game.getAssetManager().setLoader(BitmapFont.class, ".ttf",
                new FreetypeFontLoader(new InternalFileHandleResolver()));

        FreetypeFontLoader.FreeTypeFontLoaderParameter params =
                new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "pixel_regular.ttf";
        params.fontParameters.size = 60;
        game.getAssetManager().load(params.fontFileName, BitmapFont.class, params);
        game.getAssetManager().load("menu_bg.png", Texture.class);
        bg = new Texture(Gdx.files.internal("dungeon_bg.png"));
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.getAssetManager().update()) {
            game.setScreen(new StartScreen(game));
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
    }
}
