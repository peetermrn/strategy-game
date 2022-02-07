package com.gamefromscratch.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.network.GameClient;
import com.gamefromscratch.network.server.GameServer;
import com.gamefromscratch.ui.MultyplayerLobby;

import java.io.IOException;

public class MultiPlayerScreen implements Screen {
    private Stage stage;
    private Game game;
    private GameServer server;
    private GameClient client;
    Texture backTexture;
    SpriteBatch batch;
    MultyplayerLobby lobby;
    String gameName = "default";

    public MultiPlayerScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        backTexture = new Texture(Gdx.files.internal("ui/MultyplayerBackground.png"));
        lobby = new MultyplayerLobby();
        int row_height = Gdx.graphics.getHeight() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        Label title = new Label("Lobby", MainGame.gameSkin,"default");
        title.setSize(Gdx.graphics.getWidth(),row_height);
        title.setPosition(0,Gdx.graphics.getHeight()-row_height);
        title.setAlignment(Align.center);
        title.setFontScale(2);
        title.setColor(1, 0, 0, 1);
        stage.addActor(title);
        TextField field = new TextField("username", MainGame.gameSkin, "default");
        field.setSize(col_width*3, row_height);
        field.setPosition(col_width*3, row_height*10);
        field.addListener(new InputListener(){
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return true;
            }
        });
        stage.addActor(field);
        Button button1 = new TextButton("Create Game",MainGame.gameSkin,"default");
        button1.setSize(col_width*3,row_height);
        button1.setPosition(col_width,Gdx.graphics.getHeight()-row_height*4);
        button1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                try {
                    server = new GameServer(lobby);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button1);
        Button button2 = new TextButton("Active Games",MainGame.gameSkin,"default");
        button2.setSize(col_width*3,row_height);
        button2.setPosition(col_width,Gdx.graphics.getHeight()-row_height*6);
        button2.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                stage.addActor(lobby);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button2);
        Button button3 = new TextButton("Start game",MainGame.gameSkin,"default");
        button3.setSize(col_width*3,row_height);
        button3.setPosition(col_width*5,row_height);
        button3.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                server.startGame();
                System.out.println("start");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button3);
        Button button4 = new TextButton("Join Lobby",MainGame.gameSkin,"default");
        button4.setSize(col_width*3,row_height);
        button4.setPosition(col_width,Gdx.graphics.getHeight()-row_height*6);
        button4.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                TextField field2 = new TextField("port", MainGame.gameSkin, "default");
                field2.setSize(col_width*3, row_height-30);
                field2.setPosition(col_width, row_height*2);
                field2.addListener(new InputListener(){
                    @Override
                    public boolean keyTyped(InputEvent event, char character) {
                        return true;
                    }
                });
                stage.addActor(field2);
                TextField field3 = new TextField("ip address", MainGame.gameSkin, "default");
                field3.setSize(col_width*2, row_height- 30);
                field3.setPosition(col_width, row_height*3);
                field3.addListener(new InputListener(){
                    @Override
                    public boolean keyTyped(InputEvent event, char character) {
                        return true;
                    }
                });
                stage.addActor(field3);
                client = new GameClient(field.getText(), game);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
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
        stage.dispose();
    }
}
