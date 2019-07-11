package mygame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import mygame.MyGame;

public class BasicEnemy extends GameObject {


//    public BasicEnemy(int x, int y, int width, int height,int speed,  GameObjectID id) {
//        super(x, y, width, height, id);
//        setVelY(speed);
//    }

    private int damage;
    private int healthUpValue;

    public BasicEnemy(ShapeRenderer renderer) {
        super(GameObjectID.Enemy, renderer, Color.RED);
        damage = 8;
        healthUpValue = 4;
    }

    public void initEnemy(Color color, int damage, int healthUpValue) {
        setColor(color);
        this.damage = damage;
        this.healthUpValue = healthUpValue;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        setY(getY() + getVelY() * delta);
        if (getY() < 0 - getHeight()) {
            Player.setHealth(Player.getActualHealth() - damage);
            killObject();
//            System.out.println("HEALTH KILLED");

        } else if (getY() > MyGame.HEIGHT + getHeight() + 20) {
            killObject();
            Player.setHealth(Player.getActualHealth() + healthUpValue);
//            System.out.println("BOUNDS KILLED");
        } else {
            checkCollision();
        }
    }

    private void checkCollision() {

        if (getRectangle().overlaps(MyGame.mainPlayer.getRectangle())) {
            setY(MyGame.mainPlayer.getY() + MyGame.mainPlayer.getHeight());
            setVelY(getVelY() * -6);

//
        }
    }

    private void killObject() {
        remove();
        MyGame.enemyPool.free(this);
        System.out.println("killed");
    }

}
