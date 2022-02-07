package com.gamefromscratch.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefromscratch.MainGame;

public class MainMenu implements Screen {
    private Stage stage;
    private Game game;
    Texture backTexture;
    SpriteBatch batch;
    Sound button;


    public MainMenu(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        backTexture = new Texture(Gdx.files.internal("ui/mainScreenBackground.png"));

        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Label title = new Label("RTS", MainGame.gameSkin, "default");
        title.setSize(Gdx.graphics.getWidth(), row_height);
        title.setPosition(0, Gdx.graphics.getHeight() - row_height);
        title.setAlignment(Align.center);
        title.setFontScale(2);
        title.setColor(1, 0, 0, 1);
        stage.addActor(title);


        Button button1 = new TextButton("Sandbox", MainGame.gameSkin, "default");
        button1.setSize(col_width * 3, row_height);
        button1.setPosition(col_width, Gdx.graphics.getHeight() - row_height * 2);
        Label text = new Label("", MainGame.gameSkin, "default");
        button1.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SinglePlayerOptions(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        button1.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {


                text.setText("SANDBOX MODE: \n\nPlay alone with control over all units. \nMostly used for practising different " +
                        "build orders and just \ntrying out some new things");

                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setVisible(false);
            }
        });

        stage.addActor(button1);


        text.setSize(Gdx.graphics.getWidth(), row_height);
        text.setPosition(col_width * 6f, Gdx.graphics.getHeight() - row_height * 2);
        text.setColor(Color.BLACK);
        stage.addActor(text);


        Button button2 = new TextButton("Settings", MainGame.gameSkin, "default");
        button2.setSize(col_width * 3, row_height);
        button2.setPosition(col_width, Gdx.graphics.getHeight() - row_height * 6);
        button2.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button2);
        Button button3 = new TextButton("Multiplayer", MainGame.gameSkin, "default");
        button3.setSize(col_width * 3, row_height);
        button3.setPosition(col_width, Gdx.graphics.getHeight() - row_height * 4);
        button3.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MultiPlayerScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        button3.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {


                text.setText("MULTIPLAYER MODE: \n\nPlay against other people. Winner is determined by the last \nstanding town center");

                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setVisible(false);
            }
        });
        stage.addActor(button3);

        Button button4 = new TextButton("Exit", MainGame.gameSkin, "default");
        button4.setSize(col_width * 3, row_height);
        button4.setPosition(col_width * 6f, Gdx.graphics.getHeight() - row_height * 6);
        button4.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button4);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        button.dispose();
        stage.dispose();
    }
}
