package mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

import mygame.common.KillObject;
import mygame.objects.Extender;
import mygame.objects.GameObject;
import mygame.objects.Player;
import mygame.objects.enemies.CircularEnemy;
import mygame.objects.enemies.LevelOneEnemy;
import mygame.objects.enemies.SmartEnemy;
import mygame.objects.enemies.SpiralEnemy;

/**
 * MyGame can be called ManagerOfGame, because it manage the environemt, spawning, level hardisty, and etc....
 * It also define the width and height of the window
 */
public class MyGame extends ApplicationAdapter {

    public static final int WIDTH = 800, HEIGHT = 600;
    private int minSpeed = 100;
    private int maxSpeed = 321 - minSpeed;

    private float minTimeSpawnValue = 3;
    private float maxTimeSpawnValue = 7;

    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Hud hud;
    public static Player mainPlayer;

    private Random rn;
    private int healthValue = 20;

    private boolean mSpawnOE = false;
    private boolean spawnGreen = false;

    private float time = 0.0f;
    float delay;

    //will determine if we are in pause state or not
    private boolean isPaused;

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

        stage.addActor(mainPlayer);
        processObjects.run();
        delay = MathUtils.random(minTimeSpawnValue, maxTimeSpawnValue);

        SmartEnemy smartEnemy = new SmartEnemy();
        smartEnemy.init(2, 2, stage);
        smartEnemy.setX(WIDTH / 2);
        smartEnemy.setVelY(-70);
        smartEnemy.setWidth(40);
        smartEnemy.setHeight(40);
        smartEnemy.setY(HEIGHT - 30);
        stage.addActor(smartEnemy);
        Player.setScore(6800);
//        Player.setScore(6900);

    }


    public void resize(int width, int height) {
        // See below for what true means.
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        //change pause state.
        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            isPaused = !isPaused;
        if (!isPaused) {
            if (Player.getActualHealth() != 0) {
                float delta = Gdx.graphics.getDeltaTime();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    mainPlayer.setVelX(500);
                } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    // System.out.println("pressing A");
                    mainPlayer.setVelX(-500);
                } else
                    mainPlayer.stop();


                //update world

                update(delta);


                //draw shapes
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                stage.draw();


                //inside drawHUd shaperenderer.end() is called
                hud.draw(stage.getBatch());


            } else
                reset();
        }


    }

    /**
     * Reset game, will set every varaibles back to default.
     */
    private void reset() {
        Player.setHealth(100);
        Player.setScore(0);
        mainPlayer.setBounds(WIDTH / 2 - 16, 0, 32, 32);
        mainPlayer.setScale(1);
        minSpeed = 100;
        maxSpeed = 321 - minSpeed;

        minTimeSpawnValue = 3;
        maxTimeSpawnValue = 7;

        healthValue = 20;

        mSpawnOE = false;
        spawnGreen = false;
        time = 0;

        delay = MathUtils.random(minTimeSpawnValue, maxTimeSpawnValue);
        processObjects = new Runnable() {
            @Override
            public void run() {
                addObjectAtLevel();
            }
        };

        //kill all objects
        for (Actor actor : stage.getActors()) {
            if (actor.equals(mainPlayer))
                continue;
            KillObject.killObject((Pool.Poolable) actor);
        }
    }

    private void update(float delta) {
        stage.act(delta);
        mainPlayer.incScore();

        time += delta;
        if (time > delay) {

            delay = MathUtils.random(minTimeSpawnValue, maxTimeSpawnValue);
            time = 0;
            processObjects.run();
        }


        manageScoreLevel(Player.getScore());
    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();

    }

    private void manageScoreLevel(int score) {

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
                minTimeSpawnValue = 1.5f;
                updateTimerValue(3.5f);

                break;
            case 1700:
                updateTimerValue(2.5f);
                break;
            case 1900:
                updateTimerValue(1.75f);
                break;
            case 2200:
                minTimeSpawnValue = .5f;
                updateTimerValue(1f);
                break;
            case 2400:
            case 2900:
                minTimeSpawnValue = .1f;
                updateTimerValue(.3f);
                spawnGreen = true;
                break;
            case 3200:
                spawnGreen = false;
                setMaxSpeed(421);
                minTimeSpawnValue = 4;
                updateTimerValue(6);
                mSpawnOE = true;
                break;
            case 3400:
                minTimeSpawnValue = 2;
                updateTimerValue(4);
                break;
            case 4200:
                minTimeSpawnValue = .5f;
                updateTimerValue(1);
                break;
            case 5200:
                healthValue = 60;
                mSpawnOE = true;
                minTimeSpawnValue = .1f;
                updateTimerValue(.5f);
                spawnGreen = true;
                break;
            case 5700:
                spawnGreen = false;
                minTimeSpawnValue = 4;
                updateTimerValue(8);
                mSpawnOE = false;
                spawnOrange();
                minSpeed = 321;
                setMaxSpeed(700);
                break;
            case 6000:
                minTimeSpawnValue = 1;
                updateTimerValue(3);
                break;
            case 7000:
                updateTimerValue(0.6f);
                processObjects = new Runnable() {
                    @Override
                    public void run() {
                        spiriallEnemySpwaner();
                    }
                };
                break;
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

    private Runnable processObjects = new Runnable() {
        @Override
        public void run() {
            addObjectAtLevel();
        }
    };

    /**
     * Add an object for started levels
     */
    private void addObjectAtLevel() {

        int xLocation = rn.nextInt(WIDTH - 17);
        LevelOneEnemy be = Pools.obtain(LevelOneEnemy.class);
        be.setBounds(xLocation, HEIGHT + 16, 16, 16);
        be.setVelY(-(rn.nextInt(maxSpeed) + minSpeed));


        if (mSpawnOE && rn.nextBoolean())
            be.initEnemy(new Color(0.63921f, 0, 0, 1f), 20, 7, stage);
        else
            be.initEnemy(Color.RED, 8, 4, stage);

        stage.addActor(be);
//


        if (spawnGreen) {//this is a friendly level one enemey with green color and high health amount and low damage
            if (rn.nextBoolean()) {


                LevelOneEnemy healthPotion = Pools.obtain(LevelOneEnemy.class);
                healthPotion.initEnemy(Color.GREEN, 1, healthValue, stage);
                healthPotion.setBounds(rn.nextInt(WIDTH - 17), HEIGHT + 16, 16, 16);
                healthPotion.setVelY(-(rn.nextInt(maxSpeed) + minSpeed));
                stage.addActor(healthPotion);
            }

        }
        Player.incrementLevel();

    }

    /**
     * Start spwaning spirial enemy
     */
    private void spiriallEnemySpwaner() {
        int boundWidth = myRandom(50, WIDTH - SpiralEnemy.WIDTH);
        int startXBound = myRandom(0, WIDTH - boundWidth - SpiralEnemy.WIDTH);
        int ySpeed = myRandom(100, 801);
        int xSpeed = myRandom(300, 1000);

        SpiralEnemy spiralEnemy = Pools.obtain(SpiralEnemy.class);
        spiralEnemy.init(20, 5, stage, startXBound, boundWidth, ySpeed, xSpeed);
        stage.addActor(spiralEnemy);
    }

    public static float clamp(float var, float min, float max) {
        if (var < min) {
            return min;
        } else if (var > max) {
            return max;
        }
        return var;
    }

    private void updateTimerValue(float value) {
        maxTimeSpawnValue = value;
    }

    private void setMaxSpeed(int val) {
        maxSpeed = val - minSpeed;
    }


    /**
     * Generate random number between a range
     *
     * @param from This is the first number from the range
     * @param to   this number will not get back to the result, maximum number to be returned will be *to - 1*
     *             So if you really want to get that number too, just put the to as *to + 1*
     * @return random number ranging from @from to @to
     */
    private int myRandom(int from, int to) {
        return from + rn.nextInt(to - from);
    }
}

