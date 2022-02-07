package com.gamefromscratch.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamefromscratch.buildings.*;
import com.gamefromscratch.database.PopulationBank;
import com.gamefromscratch.map.MiniMap;
import com.gamefromscratch.other.ActorComparator;
import com.gamefromscratch.other.BuildingPlaceChecker;
import com.gamefromscratch.other.BuildingPlacer;
import com.gamefromscratch.other.CollisionChecker;
import com.gamefromscratch.path.PathFinder;
import com.gamefromscratch.path.Station;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.other.ResourceGenerator;
import com.gamefromscratch.ui.BarracksUiTable;
import com.gamefromscratch.ui.BlacksmithUiTable;
import com.gamefromscratch.ui.InformationUiTable;
import com.gamefromscratch.ui.MarketUiTable;
import com.gamefromscratch.ui.OptionsUiTable;
import com.gamefromscratch.ui.ResourceUiTable;
import com.gamefromscratch.ui.ScoreUiTable;
import com.gamefromscratch.ui.TownCenterUiTable;
import com.gamefromscratch.ui.WarriorUiTable;
import com.gamefromscratch.ui.WorkerUiTable;
import com.gamefromscratch.units.Unit;
import com.gamefromscratch.units.Worker;
import com.gamefromscratch.database.ResourceBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen implements Screen, InputProcessor {
    public Stage stage;
    OrthographicCamera camera;
    int mapWidth = 100;
    int mapHeight = 100;
    public MiniMap miniMap;
    public Actor selectedActor;
    public TiledMap tiledMap;
    public List<Resource> resourceList = new ArrayList<>();
    public List<Resource> goldList = new ArrayList<>();
    public List<Resource> treeList = new ArrayList<>();
    public List<Worker> workerList = new ArrayList<>();
    public List<Building> buildingList = new ArrayList<>();
    int numberOfEnemies;
    SpriteBatch sb;
    List<Sprite> sprites = new ArrayList<>();
    float currentZoom = 0;
    public Map<Integer, TownCenter> tcMap = new HashMap<>();
    public List<Unit> warriors = new ArrayList<>();
    public BlacksmithUiTable blacksmithUiTable;
    public List<Sprite> graves = new ArrayList<>();
    OrthogonalTiledMapRenderer renderer;
    private Viewport viewport;
    public List<Unit> unitList = new ArrayList<>();
    public WorkerUiTable workerUiTable;
    Stage uiStage;
    public ResourceUiTable resourceUiTable;
    public InformationUiTable informationUiTable;
    TownCenterUiTable townCenterUiTable;
    public PathFinder path;
    BarracksUiTable barracksUiTable;
    MarketUiTable marketUiTable;
    WarriorUiTable warriorUiTable;
    public ResourceBank reBank;
    public PopulationBank popBank;
    Sprite sprite;
    public List<Building> resourceDropOf = new ArrayList<>();
    public float marketCof = 1;
    Game game;
    public OptionsUiTable ot;
    Texture arrow;
    Texture flag;
    float arrowTime;
    TextureRegion arrowAnimation;
    Animation<TextureRegion> arrowMove;
    float flagTime;
    TextureRegion flagAnimation;
    Animation<TextureRegion> flagMove;
    ScoreUiTable scoreUiTable;
    CollisionChecker collisionChecker;
    public BuildingPlaceChecker buildingPlaceChecker;
    BuildingPlacer buildingPlacer;
    public GameScreen(){

    }

    public GameScreen(Game aGame, int numberOfEnemies) {
        game = aGame;
        this.numberOfEnemies = numberOfEnemies;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 30);
        viewport = new StretchViewport(camera.viewportWidth, camera.viewportHeight, camera);
        //AutoTiler autoTiler = new AutoTiler(100, 100, Gdx.files.internal("tileset.json"));
        //TiledMap map = autoTiler.generateMap();
        TiledMap map = new TmxMapLoader().load("other/SmallMap.tmx");
        tiledMap = map;
        stage = new Stage(viewport);
        renderer = new OrthogonalTiledMapRenderer(map);
        sb = new SpriteBatch();
        camera.update();
        reBank = new ResourceBank(numberOfEnemies + 1);
        popBank = new PopulationBank(numberOfEnemies + 1);
        miniMap = new MiniMap(map, camera, this, stage);
        SpriteBatch sb;
        createIndicators();
        uiStage = new Stage();
        InputMultiplexer multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        workerUiTable = new WorkerUiTable();
        resourceUiTable = new ResourceUiTable(reBank, popBank);
        uiStage.addActor(workerUiTable);
        uiStage.addActor(resourceUiTable);
        informationUiTable = new InformationUiTable();
        collisionChecker = new CollisionChecker(this);
        uiStage.addActor(informationUiTable);
        path = new PathFinder(this);
        scoreUiTable = new ScoreUiTable(reBank, popBank, this);
        uiStage.addActor(scoreUiTable);
        buildingPlacer = new BuildingPlacer(this, reBank);
        stage.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedActor instanceof Unit) {
                    ((Unit) selectedActor).moveTo(new Vector2(x, y), true);
                }
            }
        });
        stage.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /*debugPath(); //for debugging*/
                if (selectedActor instanceof Worker && workerUiTable.selectedUIBuilding != 0) {
                    buildingPlacer.placeBuilding(x, y);
                }
                if (stage.hit(x, y, true) == null) {
                    selectedActor = null;
                    clearAllUiTables();
                } else {
                    workerUiTable.unmakeSelectedBuilding();
                    selectedActor = stage.hit(x, y, true);
                    if (selectedActor instanceof Resource || selectedActor == null || selectedActor instanceof Building) {
                        for (Unit actor : unitList) {
                            if (calculateEuclideanDistance(x, y, actor.getX() + actor.getWidth() / 2, actor.getY() + actor.getHeight() / 2) < 70) {
                                selectedActor = actor;
                                break;
                            }
                        }
                    }
                    clearAllUiTables();
                    if (selectedActor != null) {
                        setUi();
                    }
                }
            }
        });
        buildingPlaceChecker = new BuildingPlaceChecker(this);
        createInitialTownCenters();
        ResourceGenerator resourceGenerator = new ResourceGenerator(this);
        resourceGenerator.generateTreesAndGold();
        miniMap.createDots();
        path.calculateMap();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                stage.getActors().sort(new ActorComparator());
            }
        }, 0, 500);
    }

    public void setUi() {
        if (selectedActor instanceof Unit && !(selectedActor instanceof Worker)) {
            warriorUiTable = new WarriorUiTable((Unit) selectedActor);
            uiStage.addActor(warriorUiTable);
        }
        if (selectedActor instanceof Worker) {
            workerUiTable.showButtons();
            if (barracksUiTable != null) {
                barracksUiTable.clearTable();
            }
        }
        if (selectedActor instanceof Market && ((Market) selectedActor).isReady()) {
            marketUiTable = new MarketUiTable(reBank, numberOfEnemies + 1, (Market) selectedActor, marketCof, GameScreen.this);
            uiStage.addActor(marketUiTable);
        }
        if (selectedActor instanceof Blacksmith && ((Blacksmith) selectedActor).isReady()) {
            blacksmithUiTable = new BlacksmithUiTable(tcMap.get(((Blacksmith) selectedActor).playerID), reBank);
            uiStage.addActor(blacksmithUiTable);
        }
        if (selectedActor instanceof TownCenter) {
            townCenterUiTable = new TownCenterUiTable((TownCenter) selectedActor);
            uiStage.addActor(townCenterUiTable);
        } else if (selectedActor instanceof Barracks && ((Barracks) selectedActor).isReady()) {
            barracksUiTable = new BarracksUiTable((Barracks) selectedActor);
            uiStage.addActor(barracksUiTable);
        }
        informationUiTable.setTexts(selectedActor);
    }

    public void clearAllUiTables() {
        workerUiTable.hideButtons();
        if (townCenterUiTable != null) {
            townCenterUiTable.clearTable();
        }
        if (warriorUiTable != null) {
            warriorUiTable.clearTable();
        }
        if (marketUiTable != null) {
            marketUiTable.clearTable();
        }
        if (blacksmithUiTable != null) {
            blacksmithUiTable.clearTable();
        }

        if (barracksUiTable != null) {
            barracksUiTable.clearTable();
        }
        informationUiTable.hideButtons();
    }


    private void createInitialTownCenters() {
        TownCenter tc = new TownCenter(this, 700, 700, 1);
        buildingList.add(tc);
        tcMap.put(tc.playerID, tc);
        TownCenter tc2 = new TownCenter(this, 2500, 2700, 2);
        buildingList.add(tc2);
        tcMap.put(tc2.playerID, tc2);
        if (numberOfEnemies > 1) {
            TownCenter tc3 = new TownCenter(this, 700, 2700, 3);
            buildingList.add(tc3);
            tcMap.put(tc3.playerID, tc3);
            resourceDropOf.add(tc3);
        }
        if (numberOfEnemies > 2) {
            TownCenter tc4 = new TownCenter(this, 2500, 700, 4);
            buildingList.add(tc4);
            tcMap.put(tc4.playerID, tc4);
            resourceDropOf.add(tc4);
        }
        resourceDropOf.add(tc);
        resourceDropOf.add(tc2);
    }


    public Array<Action> getSortedActions() {
        Array<Action> actions = new Array<>();
        for (Actor a : stage.getActors()) {
            if (a.getActions().size != 0) {
                actions.add(a.getActions().first());
            }
        }
        System.out.println(actions);
        return actions;
    }

    public void setUpdatedActions(Array<Action> happenings) {
        System.out.println(happenings);
        for (Action a : happenings) {
            if (a.getActor() != null) {
                if (!stage.getActors().contains(a.getActor(), true)) {
                    stage.addActor(a.getActor());
                }
            }
        }
    }

    @Override
    public void show() {

    }

    public void debugPath() {
        sprites.clear();
        for (Station station : path.stations) {
            Sprite newSprite = new Sprite(new Texture(Gdx.files.internal("minimap/yellow_dot.png")));
            newSprite.setPosition((float) station.getX() * 32 - newSprite.getWidth() / 2 + 16, (float) station.getY() * 32 - newSprite.getHeight() / 2 + 16);
            sprites.add(newSprite);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        sb.begin();
        for (Sprite sprite : graves) {
            sprite.draw(sb);
        }
        sb.end();
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
        camera.update();
        stage.getViewport().getCamera().update();
        scoreUiTable.setLabelText();
        moveCamera();
        collisionChecker.checkCollisions();
        resourceUiTable.goldCounter.setText(Integer.toString(resourceUiTable.goldAmount));
        resourceUiTable.woodCounter.setText(Integer.toString(resourceUiTable.woodAmount));
        if (selectedActor != null) {
            informationUiTable.setTexts(selectedActor);
        }
        miniMap.update(0, 0);
        miniMap.render();
        resourceUiTable.setAmounts();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        if (selectedActor != null && selectedActor instanceof Unit) {
            setFlag();
        }
        if (workerUiTable.selectedUIBuilding != 0 && selectedActor instanceof Worker) {
            setTempBuilding();
        }
        /*for (Sprite station : sprites) { //for debugging
            station.draw(sb);
        }*/
        sb.end();
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();
    }

    public void setFlag() {
        if (selectedActor.getActions().size > 0 && ((Unit) selectedActor).destVector != null) {
            flagTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFlag = flagMove.getKeyFrame(flagTime, true);
            sb.draw(currentFlag, ((Unit) selectedActor).destVector.x - 45, ((Unit) selectedActor).destVector.y, currentFlag.getRegionWidth() / 2f, currentFlag.getRegionHeight() / 2f);
        }
        arrowTime += Gdx.graphics.getDeltaTime();
        TextureRegion current = arrowMove.getKeyFrame(arrowTime, true);
        sb.draw(current, selectedActor.getX() + selectedActor.getWidth() / 2 - 28, selectedActor.getHeight() + selectedActor.getY() + 20);
    }

    public void setTempBuilding() {
        sprite = null;
        int playerId = ((Worker) selectedActor).playerID;
        if (workerUiTable.selectedUIBuilding == 1 && reBank.getGold(playerId) >= House.getGoldBuildAmount() && reBank.getWood(playerId) >= House.getWoodBuildAmount()) {
            sprite = House.getBuildingSprite(playerId);
        }
        if (workerUiTable.selectedUIBuilding == 2 && reBank.getGold(playerId) >= Barracks.getGoldBuildAmount() && reBank.getWood(playerId) >= Barracks.getWoodBuildAmount()) {
            sprite = Barracks.getBuildingSprite(playerId);
        }
        if (workerUiTable.selectedUIBuilding == 3 && reBank.getGold(playerId) >= Market.getGoldBuildAmount() && reBank.getWood(playerId) >= Market.getWoodBuildAmount()) {
            sprite = Market.getBuildingSprite(playerId);
        }
        if (workerUiTable.selectedUIBuilding == 4 && reBank.getGold(playerId) >= Camp.getGoldBuildAmount() && reBank.getWood(playerId) >= Camp.getWoodBuildAmount()) {
            sprite = Camp.getBuildingSprite(playerId);
        }
        if (workerUiTable.selectedUIBuilding == 5 && reBank.getGold(playerId) >= Blacksmith.getGoldBuildAmount() && reBank.getWood(playerId) >= Blacksmith.getWoodBuildAmount()) {
            sprite = Blacksmith.getBuildingSprite(playerId);
        }
        if (sprite != null) {
            sprite.setPosition(camera.position.x - camera.viewportWidth / 2 + Gdx.input.getX() - sprite.getWidth() / 2, camera.position.y + camera.viewportHeight / 2 - Gdx.input.getY() - sprite.getHeight() / 2);
            if (!buildingPlaceChecker.checkBuildingPlace(sprite, sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2)) {
                sprite.setColor(Color.BLACK);
            }
            sprite.draw(sb);
        }
    }

    public void createIndicators() {
        arrow = new Texture(Gdx.files.internal("Icons/arrow2.png"));
        flag = new Texture(Gdx.files.internal("Icons/flag2.png"));
        TextureRegion[][] temp = TextureRegion.split(flag, flag.getWidth() / 10, flag.getHeight());
        TextureRegion[] frames = new TextureRegion[10];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 10; j++) {
                frames[index++] = temp[i][j];
            }
        }
        flagMove = new Animation<TextureRegion>(0.09f, frames);
        flagTime = 0f;
        flagAnimation = flagMove.getKeyFrame(0);
        TextureRegion[][] temp2 = TextureRegion.split(arrow, arrow.getWidth() / 26, arrow.getHeight());
        TextureRegion[] frames2 = new TextureRegion[26];
        int index2 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 26; j++) {
                frames2[index2++] = temp2[i][j];
            }
        }
        arrowMove = new Animation<TextureRegion>(0.05f, frames2);
        arrowTime = 0f;
        arrowAnimation = arrowMove.getKeyFrame(0);

    }

    private float calculateEuclideanDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void moveCamera() {
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        if (x <= 35) {
            x = Gdx.input.getX();
            camera.translate(-20, 0);
            stage.getViewport().getCamera().translate(-20, 0, 0);
            miniMap.update(x - 20, y);
        }
        if (x >= (Gdx.graphics.getWidth() - 35)) {
            x = Gdx.input.getX();
            camera.translate(20, 0);
            stage.getViewport().getCamera().translate(20, 0, 0);
            miniMap.update(x + 20, y);
        }
        if (y <= 35) {
            y = Gdx.input.getY();
            camera.translate(0, 20);
            stage.getViewport().getCamera().translate(0, 20, 0);
            miniMap.update(x, 20 + y);
        }
        if (y >= (Gdx.graphics.getHeight() - 35)) {
            y = Gdx.input.getY();
            camera.translate(0, -10);
            stage.getViewport().getCamera().translate(0, -20, 0);
            miniMap.update(x, -20 + y);
        }
        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;
        boundaries(camera, startX, startY, mapWidth * 32 - startX * 2, mapHeight * 32 - 2 * startY);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT)
            camera.translate(-32, 0);
        if (keycode == Input.Keys.FORWARD_DEL) {
            if (selectedActor != null) {
                if (selectedActor instanceof Building) {
                    ((Building) selectedActor).hp = 0;
                    ((Building) selectedActor).death();
                }
                if (selectedActor instanceof Unit) {
                    ((Unit) selectedActor).hp = 0;
                    ((Unit) selectedActor).death();
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (workerUiTable.selectedUIBuilding != 0) {
                workerUiTable.unmakeSelectedBuilding();
            } else {
                selectedActor = null;
                workerUiTable.hideButtons();
                informationUiTable.hideButtons();
                clearAllUiTables();
                if (ot == null) {
                    ot = new OptionsUiTable(this, game, numberOfEnemies + 1, reBank);
                    uiStage.addActor(ot);
                } else {
                    ot.clearTable();
                }
            }
        }
        if (keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if (keycode == Input.Keys.UP)
            camera.translate(0, 32);
        if (keycode == Input.Keys.DOWN)
            camera.translate(0, -32);
        return true;
    }

    public static void boundaries(Camera cam, float startX, float startY, float width, float height) {
        Vector3 position = cam.position;
        if (position.x < startX) {
            position.x = startX - 30;
        }
        if (position.y < startY) {
            position.y = startY - 30;
        }
        if (position.x > startX + width) {
            position.x = startX + width + 30;
        }
        if (position.y > startY + height) {
            position.y = startY + height + 30;
        }
        cam.position.set(position);
        cam.update();
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
        currentZoom += amountX * 0.5;
        currentZoom += amountY * 0.5;
        float maxZoom = 5f;
        if (currentZoom >= maxZoom) currentZoom = maxZoom;
        float minZoom = 1f;
        if (currentZoom <= minZoom) currentZoom = minZoom;
        camera.zoom = currentZoom;
        camera.update();
        return false;
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
        uiStage.dispose();
    }
}
