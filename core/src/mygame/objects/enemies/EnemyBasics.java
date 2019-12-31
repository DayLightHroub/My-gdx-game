package mygame.objects.enemies;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pools;

import mygame.MyGame;
import mygame.common.KillObject;
import mygame.common.MoveRunnable;
import mygame.constants.Constants;
import mygame.objects.GameObject;
import mygame.objects.NotificationObject;
import mygame.objects.Player;

/**
 * This will handle the most important events for any enemy.
 * Like collisions with main player, kill the object if it went below the screen.
 * If a collision happen enemy will reverse it self.
 */
abstract public class EnemyBasics extends GameObject {

    protected int damage;
    protected int healthUpValue;
    protected Stage stage;

    @Override
    public void act(float delta) {

        moveRunnable.run(delta);
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
            moveRunnable = new MoveRunnable() {
                @Override
                public void run(float delta) {
                    setY(getY() + getVelY() * delta);
                }
            };


        }
    }

    /**
     * Define the normal behaviour for this enemy.
     * Please note that once a collision happen with main player this behaviour won't be used.
     * Instead the collision behaviour will start to execute
     *
     * @param delta
     */
    protected abstract void move(float delta);

    public void init(int damage, int healthUpValue, Stage stage) {
        this.damage = damage;
        this.healthUpValue = healthUpValue;
        this.stage = stage;
    }

    private MoveRunnable moveRunnable = new MoveRunnable() {
        @Override
        public void run(float delta) {
            move(delta);
        }
    };

    @Override
    public void reset() {
        moveRunnable = new MoveRunnable() {
            @Override
            public void run(float delta) {
                move(delta);
            }
        };
    }
}
