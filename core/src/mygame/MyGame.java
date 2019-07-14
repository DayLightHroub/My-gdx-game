package mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

import mygame.objects.BasicEnemy;
import mygame.objects.Extender;
import mygame.objects.GameObject;
import mygame.objects.Player;

public class MyGame extends ApplicationAdapter {

    public static final int WIDTH = 800, HEIGHT = 600;
    public static boolean isRunning;
    private int MIN_SPEED = 100;


    private int MAX_SPEED = 321 - MIN_SPEED;

    private float MIN_TIME_SPAWN_VALUE = 3;
    private float MAX_TIME_SPAWN_VALUE = 7;


    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Hud hud;
    public static Player mainPlayer;

    private Random rn;
    private int healthValue = 20;


    private boolean mSpawnOE = false;
    private boolean spawnGreen = false;


    float delay;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        rn = new Random();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        //inti shaperendrer in GameObject
        GameObject.setShapeRenderrer(shapeRenderer);

        mainPlayer = new Player();
        mainPlayer.setBounds(WIDTH / 2 - 16, 0, 32, 32);
//        mainPlayer.setOrigin(Align.center);

        hud = new Hud(shapeRenderer);


//        actor.setOriginX(actor.getWidth() / 2);
//        actor.setOriginY(actor.getHeight() / 2);
//        actor.setOrigin(Align.center);
        stage.addActor(mainPlayer);

//        stage.setKeyboardFocus(actor);


//        actor.addAction(scaleTo(5, 1, 3f));
//        myActor.addAction(parallel(moveTo(250, 250, 2, bounceOut), color(Color.RED, 6), delay(0.5f), rotateTo(180, 5, swing)));
//        actor.addAction(forever(sequence(sizeTo(35, 35, 0.2f), sizeTo(32, 32, 0.5f), delay(0.2f))));
//        actor.addAction(forever(sequence(scaleBy(0.3f, 0.3f, 0.4f), scaleBy(-0.3f, -0.3f, 0.4f), delay(0.3f))));


//        hud.addAction(fadeOut(0));
//        hud.addAction(sequence(parallel(fadeIn(0.15f), moveBy(0, 50f, 2f)),  scaleBy(2f, 0.3f, 0.5f)));


        addObjectAtLevel();


//        delay = rn.nextFloat(MAX_TIME_SPAWN) + MIN_TIME_SPAWN_VALUE;
        delay = MathUtils.random(MIN_TIME_SPAWN_VALUE, MAX_TIME_SPAWN_VALUE);
        System.out.println("first" + delay);


    }


    private float time = 0.0f;

    public void resize(int width, int height) {
        // See below for what true means.
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        if (Player.getActualHealth() != 0) {


            float delta = Gdx.graphics.getDeltaTime();

//            System.out.println(time += delta);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //handlerObjects.tick(delta);
            //hud.tick(delta)
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                // System.out.println("pressing D");
                mainPlayer.setVelX(500);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                // System.out.println("pressing A");
                mainPlayer.setVelX(-500);
            } else
                mainPlayer.stop();
//            mainPlayer.setVelX(-500);


            //update world

            update(delta);


            //draw shapes
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            stage.draw();


            //inside drawHUd shaperenderer.end() is called
            hud.draw(stage.getBatch());


        }


    }

    private void update(float delta) {
        stage.act(delta);
        mainPlayer.incScore();


        //add a new object (enemy, healthpotion, extender) when timer is finished

        time += delta;
        if (time > delay) {

            delay = MathUtils.random(MIN_TIME_SPAWN_VALUE, MAX_TIME_SPAWN_VALUE);
            System.out.println("new delay : " + delay);
            time = 0;
            addObjectAtLevel();
        }


        manageScoreLevel(Player.getScore());
    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();

    }

    private void manageScoreLevel(int score) {


//
        switch (score) {
            case 100:
                updateTimerValue(6);
                break;
            case 600:
                updateTimerValue(5.5f);
                break;
            case 700:

                updateTimerValue(4.5f);
                break;
            case 1200:
                updateTimerValue(3.5f);
                break;
            case 1500:
                MIN_TIME_SPAWN_VALUE = 1.5f;
                updateTimerValue(3.5f);

                break;
            case 1700:
                updateTimerValue(2.5f);
                break;
            case 1900:
                updateTimerValue(1.75f);
                break;
            case 2200:

                MIN_TIME_SPAWN_VALUE = .5f;
                updateTimerValue(1f);


                break;
            case 2400:


            case 2900:

                MIN_TIME_SPAWN_VALUE = .1f;
                updateTimerValue(.3f);
                spawnGreen = true;
                break;
            case 3200:
                spawnGreen = false;
                setMAX_SPEED(421);
                MIN_TIME_SPAWN_VALUE = 4;
                updateTimerValue(6);
                mSpawnOE = true;
                break;

            case 3400:
                MIN_TIME_SPAWN_VALUE = 2;
                updateTimerValue(4);

                break;

            case 4200:
                MIN_TIME_SPAWN_VALUE = .5f;
                updateTimerValue(1);

                break;

            case 5200:

                healthValue = 60;
                mSpawnOE = true;
                MIN_TIME_SPAWN_VALUE = .1f;
                updateTimerValue(.5f);
                spawnGreen = true;
                break;

            case 5700:
                spawnGreen = false;
                MIN_TIME_SPAWN_VALUE = 4;
                updateTimerValue(8);
                mSpawnOE = false;
                spawnOrange();
                MIN_SPEED = 321;
                setMAX_SPEED(700);
                break;


            case 6000:
                MIN_TIME_SPAWN_VALUE = 1;
                updateTimerValue(3);
                //testLine




        }


    }

    private void spawnOrange() {

        //test
        Extender extender = Pools.obtain(Extender.class);
        extender.initiExtender();
        extender.setBounds(300, 500, 16, 16);
        extender.setVelX(0);
        extender.setVelY(-150);
        stage.addActor(extender);
        //test
    }

    private void addObjectAtLevel() {

        int xLocation = rn.nextInt(WIDTH - 17);
        BasicEnemy be = Pools.obtain(BasicEnemy.class);
        be.setBounds(xLocation, HEIGHT + 16, 16, 16);
        be.setVelY(-(rn.nextInt(MAX_SPEED) + MIN_SPEED));


        if (mSpawnOE && rn.nextBoolean())
            be.initEnemy(new Color(0.63921f, 0, 0, 1f), 20, 7);
        else
            be.initEnemy(Color.RED, 8, 4);

        stage.addActor(be);
//


        if (spawnGreen) {
            if (rn.nextBoolean()) {


                BasicEnemy healthPotion = Pools.obtain(BasicEnemy.class);
                healthPotion.initEnemy(Color.GREEN, 1, healthValue);
                healthPotion.setBounds(rn.nextInt(WIDTH - 17), HEIGHT + 16, 16, 16);
                healthPotion.setVelY(-(rn.nextInt(MAX_SPEED) + MIN_SPEED));
                stage.addActor(healthPotion);
            }

        }
        Player.incrementLevel();

        // BasicEnemy(xLocation, HEIGHT + 16, 16, 16, rn.nextInt(MAX_SPEED) + MIN_SPEED, GameObjectID.Enemy);
    }

    public static float clamp(float var, float min, float max) {
        if (var < min) {
            return min;
        } else if (var > max) {
            return max;
        }
        return var;
    }


//    private void collisionCalculation(BasicEnemy basicEnemy) {
//
//        if (basicEnemy.getPoly().overlaps(mainPlayer.getPoly())) {
//            basicEnemy.setY(mainPlayer.getY() + mainPlayer.getHeight());
//            basicEnemy.setVelY(basicEnemy.getVelY() * -6);
//        }
//    }


    private void updateTimerValue(float value) {
        MAX_TIME_SPAWN_VALUE = value;
    }

    private void setMAX_SPEED(int val) {
        MAX_SPEED = val - MIN_SPEED;
    }
}

