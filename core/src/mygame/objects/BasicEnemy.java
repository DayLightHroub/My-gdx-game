
package mygame.objects;

import com.badlogic.gdx.graphics.Color;

import mygame.MyGame;
import mygame.common.KillObject;
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


        setY(getY() + getVelY() * delta);
        if (getY() < 0 - getHeight()) {
            Player.setHealth(Player.getActualHealth() - damage);
            KillObject.killObject(this);
//            System.out.println("HEALTH KILLED");

        } else if (getY() > MyGame.HEIGHT + getHeight() + 20) {
            KillObject.killObject(this);
            Player.setHealth(Player.getActualHealth() + healthUpValue);
//            System.out.println("BOUNDS KILLED");
        }

        super.act(delta);
        checkCollision();

    }

    private void checkCollision() {

        if (getRectangle().overlaps(MyGame.mainPlayer.getRectangle())) {
            setY(MyGame.mainPlayer.getY() + MyGame.mainPlayer.getHeight() * MyGame.mainPlayer.getScaleY());
            setVelY(Constants.MAX_SPEED_ENEMY);


        }
    }

}
