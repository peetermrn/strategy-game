package com.gamefromscratch.network;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.IndexBufferObjectSubData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryo.Kryo;
import com.gamefromscratch.buildings.Barracks;
import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.buildings.House;
import com.gamefromscratch.buildings.TownCenter;
import com.gamefromscratch.map.MiniMap;
import com.gamefromscratch.network.client.GameBuilder;
import com.gamefromscratch.resources.BigGold;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.resources.SmallGold;
import com.gamefromscratch.resources.Tree;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.ui.InformationUiTable;
import com.gamefromscratch.ui.ResourceUiTable;
import com.gamefromscratch.ui.TownCenterUiTable;
import com.gamefromscratch.ui.WorkerUiTable;
import com.gamefromscratch.units.Worker;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Network {
    static public final int TCPport = 5001;
    static public final int UDPport = 5002;
    static public final String serverIP = "193.40.255.30";

    static public void register (Kryo kryo) {
        kryo.register(GameBuilder.class);
        kryo.register(GameScreen.class);
        kryo.register(Player.class);
        kryo.register(Random.class);
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(Worker.class);
        kryo.register(WorkerUiTable.class);
        kryo.register(TownCenterUiTable.class);
        kryo.register(ResourceUiTable.class);
        kryo.register(InformationUiTable.class);
        kryo.register(Tree.class);
        kryo.register(SmallGold.class);
        kryo.register(Resource.class);
        kryo.register(BigGold.class);
        kryo.register(MiniMap.class);
        kryo.register(Building.class);
        kryo.register(TownCenter.class);
        kryo.register(House.class);
        kryo.register(Building.class);
        kryo.register(Barracks.class);
        kryo.register(Viewport.class);
        kryo.register(StretchViewport.class);
        kryo.register(ClickListener.class);
        kryo.register(Stage.class);
        kryo.register(InputEvent.class);
        kryo.register(Actor.class);
        kryo.register(Vector3.class);
        kryo.register(Vector2.class);
        kryo.register(OrthogonalTiledMapRenderer.class);
        kryo.register(TmxMapLoader.class);
        kryo.register(TiledMap.class);
        kryo.register(MapProperties.class);
        kryo.register(Sprite.class);
        kryo.register(OrthographicCamera.class);
        kryo.register(GL20.class);
        kryo.register(Camera.class);
        kryo.register(Gdx.class);
        kryo.register(Screen.class);
        kryo.register(InputProcessor.class);
        kryo.register(InputMultiplexer.class);
        kryo.register(Input.class);
        kryo.register(Array.class);
        kryo.register(Object[].class);
        kryo.register(Rectangle.class);
        kryo.register(DelayedRemovalArray.class);
        kryo.register(IntArray.class);
        kryo.register(int[].class);
        kryo.register(Color.class);
        kryo.register(Touchable.class);
        kryo.register(Batch.class);
        kryo.register(Texture.class);
        kryo.register(ObjectSet.class);
        kryo.register(SpriteBatch.class);
        kryo.register(Matrix4.class);
        kryo.register(float[].class);
        kryo.register(Mesh.class);
        kryo.register(IndexBufferObjectSubData.class);
        kryo.register(Null.class);
        kryo.register(Application.ApplicationType.class);
        kryo.register(InputAdapter.class);
        kryo.register(ShapeRenderer.class);
        kryo.register(InputEvent.Type.class);
        kryo.register(Table.class);
        kryo.register(Table.Debug.class);
        kryo.register(FocusListener.class);
        kryo.register(FocusListener.FocusEvent.class);
        kryo.register(ScissorStack.class);
        kryo.register(Application.ApplicationType.class);
        kryo.register(Disposable.class);
        kryo.register(Pool.Poolable.class);
        kryo.register(Pools.class);
        kryo.register(Scaling.class);
        kryo.register(SnapshotArray.class);
        kryo.register(ScalingViewport.class);
        kryo.register(ShortBuffer.class);
        kryo.register(Actor[].class);
        kryo.register(HashMap.class);
        kryo.register(FileTextureData.class);
        kryo.register(FileHandle.class);
        kryo.register(ShapeRenderer.ShapeType.class);
        kryo.register(MathUtils.class);
        kryo.register(ActorGestureListener.class);
        kryo.register(Align.class);
        kryo.register(ClassReflection.class);
        kryo.register(SequenceAction.class);
        kryo.register(MoveToAction.class);
    }
}