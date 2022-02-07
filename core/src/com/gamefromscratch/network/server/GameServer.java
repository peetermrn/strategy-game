package com.gamefromscratch.network.server;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.gamefromscratch.network.Network;
import com.gamefromscratch.network.Player;
import com.gamefromscratch.network.client.GameBuilder;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.ui.MultyplayerLobby;


import java.io.IOException;

public class GameServer {
    static Server server;
    MultyplayerLobby lobby;
    GameScreen map;
    Array<Action> serverCollection = new Array<>();

    public GameServer(MultyplayerLobby lobby1) throws IOException {
        lobby = lobby1;
        server = new Server(16384, 4096);
        server.bind(5001);
        Network.register(server.getKryo());
        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Player) {
                    lobby.addPlayer(((Player) object).getPlayerName());
                }
                if (object instanceof Array) {
                    System.out.println("Server");
                    for (Action a: ((Array<Action>) object)) {
                        if (serverCollection.contains(a, true)) {

                        } else {
                            serverCollection.add(a);
                        }
                    }
                    server.sendToAllTCP(serverCollection);
                }
            }
        });
        server.start();
        server.update(11000);
    }

    public void startGame() {
        GameBuilder gamebuilder = new GameBuilder(server.getConnections().length);
        server.sendToAllTCP(gamebuilder);
    }
}
