package mygame.objects;

import mygame.MyGame;


public class Player extends GameObject {
    private float playerBounds;
    //    private final int playerBoundsy;
    private static int animHealth;
    private static int actualHealth;
    private static int score;
    private static int level;
    private static int PLAYER_SPEED = 600;

    private int ax;


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
        super.act(delta);
        setX(getX() + velX * delta);
        setX(MyGame.clamp(getX(), 0, playerBounds));


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

        System.out.println("new health is " + actualHealth);

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

    public static int getLevel() {
        return level;
    }


    public void setVelXNew(int ax, float delta) {


        if (ax > 0) {
            if (velX < 0)
                velX += 3800 * delta;
            else {
                velX += ax * delta;
                if (velX > PLAYER_SPEED) {
                    velX = PLAYER_SPEED;
                }
            }

        } else {
            if (velX > 0)
                velX -= 3800 * delta;
            else {
                velX += ax * delta;
                if (velX < -PLAYER_SPEED) {
                    velX += ax * delta;
                    velX = -PLAYER_SPEED;
                }
            }
        }

//        System.out.println(velX);
    }

    public void stop(float delta) {
        if (velX > 0) {
            velX -= 3800 * delta;
            if (velX < 0) velX = 0;
        } else {
            velX += 3800 * delta;
            if (velX > 0) velX = 0;
        }


    }

    public void stop() {
        velX = 0;
    }

    public static int getPlayerSpeed() {
        return PLAYER_SPEED;
    }
}
