
package mygame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pools;

import mygame.MyGame;
import mygame.constants.Constants;

public class BasicEnemy extends GameObject {


    private int damage;
    private int healthUpValue;

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
            setVelY(Constants.MAX_SPEED_ENEMY);


        }
    }

    private void killObject() {
        remove();
        Pools.free(this);
        System.out.println("killed");
    }

}
