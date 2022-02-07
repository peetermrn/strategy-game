package com.gamefromscratch.network;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gamefromscratch.network.client.GameBuilder;
import com.gamefromscratch.screen.GameScreen;

import java.io.IOException;

public class GameClient {
    Client client;
    Player player;
    GameScreen map;
    Game game;

    public GameClient(String name, Game game) {
        this.game = game;
        client = new Client();
        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(client.getKryo());
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof GameBuilder && connection.isConnected()) {
                    run(object);
                }
                if (object instanceof Array) {
                    System.out.println("render");
                    run(object);

                }
            }
            @Override
            public void connected (Connection connection) {
                client.sendTCP(player);
            }
        });

        player = new Player();
        player.setName(name);
        client.start();
        // ThreadedListener runs the listener methods on a different thread.
        try {
            client.connect(5000, "localhost", Network.TCPport);
            // Server communication after connection can go here, or in Listener#connected().
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run(Object object) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                if (object instanceof GameBuilder) {
                    map = new GameScreen(game, ((GameBuilder) object).getConnections());
                    game.setScreen(map);
                    client.sendTCP(map.getSortedActions());

                }
                if (object instanceof Array) {
                    System.out.println("RENDER");
                    map.setUpdatedActions((Array<Action>) object);
                    Array<Action> ting = map.getSortedActions();
                    System.out.println("asd");
                    client.sendTCP(ting);
                }
            }

        });
    }
}
