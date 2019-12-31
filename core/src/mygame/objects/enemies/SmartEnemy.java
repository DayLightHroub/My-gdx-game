package mygame.objects.enemies;

import mygame.MyGame;

/**
 * The smart enemy will try to avoid collisions with the *player*
 * The idea is this enemy will keep on checking whether he is at the same x axes of the player
 * If that was the case then this enemy will try to dodge right or left depends on the x position related to the player x position
 */
public class SmartEnemy extends EnemyBasics {

    private float targetX;
    private Runnable dodge;
    private int speedX = 100;
    private int dodgeDirection;
    public SmartEnemy() {
        setVelX(speedX);
    }

    @Override
    protected void move(final float delta) {
        if (dodge != null)
            dodge.run();
        setY(getY() + getVelY() * delta);
        //Check are we above the player? meaning are  smartEnemy.x >= player.x && smartEnemy.x + smartEnemy.width <= player.x + player.width?
        float rightPlayerX = MyGame.mainPlayer.getX() + MyGame.mainPlayer.getWidth();
        float rightEnemyX = getX() + getWidth();
        if ((rightEnemyX >= MyGame.mainPlayer.getX() && rightEnemyX <= rightPlayerX) || (getX() >= MyGame.mainPlayer.getX() && getX() <= rightPlayerX)) {
            //check whether to dodge to left side or right side
            //first get the half position of the enemy
            float enemyXHalf = getX() + getWidth() / 2;
            float playerXHalf = MyGame.mainPlayer.getX() + MyGame.mainPlayer.getWidth() / 2;
            if (enemyXHalf >= playerXHalf) {
                dodgeDirection = 1;//dodge to right
                //the new position of the obstcle has to be Player.x + Player.width + 12 <== the 12 is just an extended position
                targetX = MyGame.mainPlayer.getX() + MyGame.mainPlayer.getWidth() + 12;
                //check is target exceed width of the screen
                if (targetX > MyGame.WIDTH - getWidth())
                    targetX = MyGame.WIDTH - getWidth();
            } else {
                dodgeDirection = -1;//dodge to left
                //the new position of the obstacle has to be Player.x + Player.width + 12 <== the 12 is just an extended position
                targetX = MyGame.mainPlayer.getX() - getWidth() - 12;
                //check is target exceed width of the screen
                if (targetX < 0)
                    targetX = 0;
            }
            dodge = new Runnable() {
                @Override
                public void run() {
                    //first dodge the player
                    setX(getX() + dodgeDirection * getVelX() * delta);
                    //second check did we just pass the target?
                    if (dodgeDirection == 1) {
                        if (getX() >= targetX) {//only if we are dodge to the right
                            setX(targetX);
                            dodge = null;//just to stop executing this.
                        }
                    } else if (getX() <= targetX) {//only if we are dodge to the right
                        setX(targetX);
                        dodge = null;//just to stop executing this.
                    }
                }
            };
        }
    }
}
