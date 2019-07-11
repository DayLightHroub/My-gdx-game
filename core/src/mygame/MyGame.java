package mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

import mygame.objects.BasicEnemy;
import mygame.objects.GameObject;
import mygame.objects.Player;

public class MyGame extends ApplicationAdapter {

    public static final int WIDTH = 800, HEIGHT = 600;
    public static boolean isRunning;
    private int MIN_SPEED = 100;
    //300 is the maximum speed!
    private int MAX_SPEED = 321 - MIN_SPEED;

    private int MIN_TIME_SLEEP = 3000;
    private int MAX_TIME_VALUE = 7000;
    private int MAX_TIME_SLEEP = MAX_TIME_VALUE + 1 - MIN_TIME_SLEEP;


    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Hud hud;
    public static Player mainPlayer;

    private Random rn;
    private int healthValue = 20;


    private boolean mSpawnOE = false;
    private boolean spawnGreen = false;
    private boolean isDrawing = false;
    private boolean isUpdating = false;

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


        //Enemy spawn thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    try {
                        int delay = rn.nextInt(MAX_TIME_SLEEP) + MIN_TIME_SLEEP;
//                        System.out.println("dleay timer is " + delay);
                        Thread.sleep(delay);
                        while (isDrawing && isUpdating) ;
                        addObjectAtLevel();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }).start();


    }

    public void resize(int width, int height) {
        // See below for what true means.
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        if (Player.getActualHealth() != 0) {


            float delta = Gdx.graphics.getDeltaTime();
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
            isUpdating = true;
            update(delta);
            isUpdating = false;


            //draw shapes
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            isDrawing = true;
            stage.draw();
            isDrawing = false;

            //inside drawHUd shaperenderer.end() is called
            hud.draw(stage.getBatch());

        }


    }

    private void update(float delta) {
        stage.act(delta);
        mainPlayer.incScore();
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
                updateTimerValue(6000);
                break;
            case 600:
                updateTimerValue(5500);
                break;
            case 700:

                updateTimerValue(4500);
                break;
            case 1200:
                updateTimerValue(3500);
                break;
            case 1500:
                MIN_TIME_SLEEP = 1500;
                updateTimerValue(3500);

                break;
            case 1700:
                updateTimerValue(2500);
                break;
            case 1900:
                updateTimerValue(1750);
                break;
            case 2200:

                MIN_TIME_SLEEP = 500;
                updateTimerValue(1000);


                break;
            case 2400:


            case 2900:

                MIN_TIME_SLEEP = 100;
                updateTimerValue(300);
                spawnGreen = true;
                break;
            case 3200:
                spawnGreen = false;
                setMAX_SPEED(421);
                MIN_TIME_SLEEP = 4000;
                updateTimerValue(6000);
                mSpawnOE = true;
                break;

            case 3400:
                MIN_TIME_SLEEP = 2000;
                updateTimerValue(4000);

                break;

            case 4200:
                MIN_TIME_SLEEP = 500;
                updateTimerValue(1000);

                break;

            case 4800:

                healthValue = 60;
                mSpawnOE = true;
                MIN_TIME_SLEEP = 100;
                updateTimerValue(500);
                spawnGreen = true;
                break;

            case 5500:
                spawnGreen = false;
                MIN_TIME_SLEEP = 4000;
                updateTimerValue(8000);
                mSpawnOE = false;
                break;


        }


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


    private void updateTimerValue(int value) {
        MAX_TIME_SLEEP = value + 1 - MIN_TIME_SLEEP;
    }

    private void setMAX_SPEED(int val) {
        MAX_SPEED = val - MIN_SPEED;
    }
}

