package com.gamefromscratch.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.units.ManAtArms;
import com.gamefromscratch.units.Knight;
import com.gamefromscratch.units.Warrior;
import com.gamefromscratch.units.Worker;

import java.util.Collections;
import java.util.List;

public class InformationUiTable extends Table {
    public Label unitClass;
    private int goldAmount = 0;
    private int woodAmount = 0;

    public InformationUiTable() {
        this.setSize(670, 100);
        this.setPosition(410, 0);
        this.setBounds(410, 0, 670, this.getHeight());
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/Darker_ui_background.png")))));
        this.setSkin(MainGame.gameSkin);
        align(0);

        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


    }

    public void setGoldAndWoodAmount(Worker worker, int gold, int wood) {
        goldAmount = gold;
        woodAmount = wood;
        setTexts(worker);

    }

    public void setTexts(Actor actor) {
        this.clear();
        Container<Table> tableContainer = new Container<Table>();
        tableContainer.setSize(this.getWidth(), getHeight());

        tableContainer.fillX();

        String name = actor.getName();
        List<String> nameList = Collections.singletonList(name);
        String className = nameList.get(0);
        unitClass = new Label(className, MainGame.gameSkin, "default");
        unitClass.setColor(Color.BLACK);

        if (actor instanceof Worker) {
            row().colspan(11);
            this.add(unitClass).colspan(15).center();

            row();
            row().colspan(10);
            Label resources = new Label("Resources", MainGame.gameSkin, "default");
            resources.setColor(Color.BLACK);
            this.add(resources).colspan(3).left();
            Label health = new Label("Health", MainGame.gameSkin, "default");
            health.setColor(Color.BLACK);
            this.add(health).colspan(11).right();
            row();
            row().colspan(10);

            Button wood = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/tree_icon.png")))));
            this.add(wood).size(25, 25).colspan(1).left();

            Label woodCounter = new Label(Integer.toString(woodAmount), MainGame.gameSkin, "default");
            woodCounter.setColor(Color.BLACK);
            this.add(woodCounter).size(30, 30).colspan(1).center();


            Button gold = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/gold_icon.png")))));
            this.add(gold).size(25, 25).colspan(1);

            Label goldCounter = new Label(Integer.toString(goldAmount), MainGame.gameSkin, "default");
            goldCounter.setColor(Color.BLACK);
            this.add(goldCounter).size(30, 30).colspan(1);

            Label healthAmount = new Label("\t" + ((Worker) actor).getHp() + "/" + 50, MainGame.gameSkin, "default");
            healthAmount.setColor(Color.BLACK);
            this.add(healthAmount).colspan(5).right().colspan(9).right();


        } else if (actor instanceof Warrior) {
            row().colspan(11);
            this.add(unitClass).colspan(15).center();

            row();
            row().colspan(10);
            Label resources = new Label("Damage", MainGame.gameSkin, "default");
            resources.setColor(Color.BLACK);
            this.add(resources).colspan(3).left();
            Label health = new Label("Health", MainGame.gameSkin, "default");
            health.setColor(Color.BLACK);
            this.add(health).colspan(11).right();
            row();
            row().colspan(10);

            Label damage = new Label(((Warrior) actor).getAttackDamage().toString(), MainGame.gameSkin, "default");
            damage.setColor(Color.BLACK);
            this.add(damage).size(30, 30).colspan(3).center();

            Label healthAmount = new Label("\t" + ((Warrior) actor).getHp() + "/" + 75, MainGame.gameSkin, "default");
            healthAmount.setColor(Color.BLACK);
            this.add(healthAmount).colspan(7).right().colspan(11).right();

        } else if (actor instanceof ManAtArms) {
            row().colspan(11);
            this.add(unitClass).colspan(15).center();

            row();
            row().colspan(10);
            Label resources = new Label("Damage", MainGame.gameSkin, "default");
            resources.setColor(Color.BLACK);
            this.add(resources).colspan(3).left();
            Label health = new Label("Health", MainGame.gameSkin, "default");
            health.setColor(Color.BLACK);
            this.add(health).colspan(11).right();
            row();
            row().colspan(10);

            Label damage = new Label(((ManAtArms) actor).getAttackDamage().toString(), MainGame.gameSkin, "default");
            damage.setColor(Color.BLACK);
            this.add(damage).size(30, 30).colspan(3).center();

            Label healthAmount = new Label("\t" + ((ManAtArms) actor).getHp() + "/" + 100, MainGame.gameSkin, "default");
            healthAmount.setColor(Color.BLACK);
            this.add(healthAmount).colspan(7).right().colspan(11).right();

        } else if (actor instanceof Knight) {
            row().colspan(11);
            this.add(unitClass).colspan(15).center();

            row();
            row().colspan(10);
            Label resources = new Label("Damage", MainGame.gameSkin, "default");
            resources.setColor(Color.BLACK);
            this.add(resources).colspan(3).left();
            Label health = new Label("Health", MainGame.gameSkin, "default");
            health.setColor(Color.BLACK);
            this.add(health).colspan(11).right();
            row();
            row().colspan(10);

            Label damage = new Label(((Knight) actor).getAttackDamage().toString(), MainGame.gameSkin, "default");
            damage.setColor(Color.BLACK);
            this.add(damage).size(30, 30).colspan(3).center();

            Label healthAmount = new Label("\t" + ((Knight) actor).getHp() + "/" + ((Knight) actor).getMaxHp(), MainGame.gameSkin, "default");
            healthAmount.setColor(Color.BLACK);
            this.add(healthAmount).colspan(7).right().colspan(11).right();
        } else if (actor instanceof Building) {
            this.add(unitClass);
            row();


            Label healthAmount = new Label(((Building) actor).getHp() + "/" + ((Building) actor).getMaxHp(), MainGame.gameSkin, "default");
            healthAmount.setColor(Color.BLACK);
            this.add(healthAmount);


        } else if (actor instanceof Resource) {
            this.add(unitClass);

            row();

            Label resources = new Label(((Resource) actor).getTotalResource() + "/" + ((Resource) actor).getMaxResource(), MainGame.gameSkin, "default");
            resources.setColor(Color.BLACK);
            add(resources);
        }
    }

    public void hideButtons() {
        this.clear();
    }

}
