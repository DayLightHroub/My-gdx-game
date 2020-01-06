package mygame.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import mygame.MyGame;

/**
 * SpiralEnemy: Is an enemy with the following behaviour:
 * This enemy has new attributes as for leftBoundX, rightBoundX.
 * LeftBoundX: is the left bound of the area the enemy can move on the x axis.
 * RightBoundX: is the right bound of the area the enemy can move on the x axis.
 * The enemy will move between the left and right boundX, once the enemy hit left bound or right bound it will reverse it vel x speed
 * It also important to mention that the velocity in the x axis is releated to the relative x position in the distance of left and right boundX
 * by saying that, the velocity of the x axis will be at it's maximum only if the enemey is in the middle of the distance, going away from the distance will
 * Decrease the velocity to it's minimum value.
 */
public class SpiralEnemy extends EnemyBasics {
    private float leftBoundX;
    private float rightBoundX;

    private float maxXSpeed;
    private static float minXSpeed = 30;

    private float maxYSpeed;
    private static float minYSpeed = 10;
    private int direction;

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    public void init(int damage, int healthUpValue, Stage stage, float leftBoundX, float boundWidth, float ySpeed, float xSpeed) {
        super.init(damage, healthUpValue, stage);
        this.maxYSpeed = ySpeed;
        this.maxXSpeed = xSpeed;
        this.leftBoundX = leftBoundX;
        rightBoundX = leftBoundX + boundWidth;

        setColor(Color.LIME);
        setVelY(-minXSpeed);
        setVelX(minXSpeed);
        setHeight(WIDTH);
        setWidth(HEIGHT);
        setY(MyGame.HEIGHT);
        setX(leftBoundX);


        direction = 1;


    }

    @Override
    public void move(float delta) {
        setY(getY() + getVelY() * delta);
        setX(getX() + getVelX() * delta);
        if (getX() > rightBoundX) {
            setX(rightBoundX);
            direction = -1;

        } else if (getX() < leftBoundX) {
            setX(leftBoundX);
            direction = 1;

        }

        //manage the velocity of x by incrementing or decrementing
        setVelX(currentSpeed(getX(), minXSpeed, maxXSpeed, leftBoundX, rightBoundX) * direction);
        setVelY(-1 * currentSpeed(getX(), minYSpeed, maxYSpeed, leftBoundX, rightBoundX));
    }


    /**
     * This function should return the current speed of the object, the rules
     * for the speed as follows: if current position was at leftBound or
     * rightBound then currentSpeed should equal to minSpeed if current position
     * was in half leftBound and rightBound then currentSpeed should equal to
     * maxSpeed current speed would increase from leftBound to half the
     * distance, and decrease from half the distance to right bound
     *
     * @param currentPosition always between left bound and right bound.
     * @throws IndexOutOfBoundsException will be thrown in two cases, case 1 is when minSpeed greater than maxSpeed, Case 2 when left bound is greater than right bound
     * @return
     */
    private static float currentSpeed(float currentPosition, float minSpeed, float maxSpeed, float leftBound, float rightBound) {
        if (minSpeed > maxSpeed) {
            throw new IndexOutOfBoundsException("Min speed can't be greater than max speed");
        }
        if (leftBound > rightBound) {
            throw new IndexOutOfBoundsException("LeftBound  can't be greater than rightBound");
        }

        float relatedPosition = currentPosition - leftBound;
        float halfRelatedPosition = (rightBound - leftBound) / 2;
        if (relatedPosition > halfRelatedPosition) {
            relatedPosition = halfRelatedPosition - (relatedPosition - halfRelatedPosition);
        }
        float percentage = relatedPosition / halfRelatedPosition;
        float speedDifferences = maxSpeed - minSpeed;
        float cSpeed = minSpeed + speedDifferences * percentage;
        return cSpeed;
    }

}
