package mygame.objects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import mygame.MyGame;

/**
 * This is the main player of the game, the main attributes of the player are:
 * 1- it's score which will determine the level of the game
 */
public class Player extends GameObject {
    private float playerBounds;
    private static int animHealth;
    private static int actualHealth;
    private static int score;
    private static int level;


    public Player() {


        animHealth = actualHealth = 100;
        score = 0;
        level = 1;
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        updatePlayerBounds();
    }

    public void updatePlayerBounds() {
        playerBounds = MyGame.WIDTH - getWidth();
    }

    public static void incrementLevel() {
        level++;
    }


    @Override
    public void act(float delta) {
        setX(MyGame.clamp(getX() + velX * delta, 0, MyGame.WIDTH - getWidth() * getScaleX()));
        super.act(delta);


    }

    public static int getAnimationHealth() {
        if (animHealth > actualHealth) {
            animHealth--;
        } else if (animHealth < actualHealth) {
            animHealth++;
        }
        return animHealth;
    }

    public static void setHealth(int health) {

        Player.actualHealth = health;
        if (Player.actualHealth < 0) {
            Player.actualHealth = 0;
        } else if (Player.actualHealth > 100)
            Player.actualHealth = 100;

    }

    public static int getScore() {
        return score;
    }

    public void incScore() {
        score++;
    }

    public static void setScore(int i) {
        score = i;
    }

    public static int getActualHealth() {
        return actualHealth;
    }

    public void stop() {
        velX = 0;
    }

    public void extend() {

        addAction(Actions.scaleTo(2, 1, 0.5f, Interpolation.swingIn));
    }


}
