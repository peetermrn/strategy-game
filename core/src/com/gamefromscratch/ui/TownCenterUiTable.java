package com.gamefromscratch.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.gamefromscratch.buildings.TownCenter;
import com.gamefromscratch.MainGame;


public class TownCenterUiTable extends Table {
    TownCenter tc;

    public TownCenterUiTable(TownCenter townCenter) {
        tc = townCenter;
        this.setSize(410, 200);
        this.setPosition(0, 500);
        this.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setTouchable(Touchable.enabled);
        this.align(Align.topLeft);
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/Darker_ui_background.png")))));
        this.setSkin(MainGame.gameSkin);
        Label resources2 = new Label("asd", MainGame.gameSkin, "default");
        resources2.setVisible(false);
        add(resources2);
        row();
        Label resources = new Label("asd", MainGame.gameSkin, "default");
        resources.setVisible(false);
        add(resources);
        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });



        Button workerButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/worker.png")))));
        this.add(workerButton).size(80, 80);

        workerButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tc.CreateWorker();

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });




        row();
        row().colspan(10);

        Label wood = new Label("    Worker: \n    Builds houses and gathers resources\n    Wood: " + townCenter.workerCost + " Gold: 0", MainGame.gameSkin, "default");
        wood.setVisible(false);
        wood.setFontScale(0.85f);
        wood.setColor(Color.BLACK);
        this.add(wood);

        workerButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                wood.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                wood.setVisible(false);
            }
        });


    }

    public void clearTable() {
        this.remove();
    }
}
