
package mygame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pools;

import mygame.MyGame;
import mygame.common.KillObject;
import mygame.constants.Constants;

public class BasicEnemy extends GameObject {


    private int damage;
    private int healthUpValue;
    private Stage stage;

    public void initEnemy(Color color, int damage, int healthUpValue, Stage stage) {
        setColor(color);
        this.damage = damage;
        this.healthUpValue = healthUpValue;
        this.stage = stage;

    }

    @Override
    public void act(float delta) {



        setY(getY() + getVelY() * delta);
        if (getY() < 0 - getHeight()) {//when the enemy is below the height of the screen
            Player.setHealth(Player.getActualHealth() - damage);
            KillObject.killObject(this);
            NotificationObject notificationObject = Pools.obtain(NotificationObject.class);
            notificationObject.init("-" + damage, NotificationObject.RED, getX(), 25);
            stage.addActor(notificationObject);

        } else if (getY() > MyGame.HEIGHT + getHeight() + 20) {// when the enemey is above the y axis, meaning the player actually caught it
            KillObject.killObject(this);
            Player.setHealth(Player.getActualHealth() + healthUpValue);
            NotificationObject notificationObject = Pools.obtain(NotificationObject.class);
            notificationObject.init("+" + healthUpValue, NotificationObject.GREEN, getX(), MyGame.HEIGHT - 25);
            stage.addActor(notificationObject);
        }

        super.act(delta);
        checkCollision();

    }

    /**
     * Check if player overlap the enemy
     */
    private void checkCollision() {

        if (getRectangle().overlaps(MyGame.mainPlayer.getRectangle())) {
            setY(MyGame.mainPlayer.getY() + MyGame.mainPlayer.getHeight() * MyGame.mainPlayer.getScaleY());
            setVelY(Constants.MAX_SPEED_ENEMY);


        }
    }

}
