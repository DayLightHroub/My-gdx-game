package mygame.objects;


import com.badlogic.gdx.graphics.Color;

import mygame.MyGame;
import mygame.common.KillObject;

/**
 * The extender isn't an enemy, it's a friendly item that will extend the width of the player.
 */
public class Extender extends GameObject {

    public void initiExtender() {
        setColor(Color.ORANGE);
    }

    @Override
    public void act(float delta) {


        setY(getY() + getVelY() * delta);
        if (getY() < 0 - getHeight()) {

            KillObject.killObject(this);
//            System.out.println("HEALTH KILLED");

        } else if (getY() > MyGame.HEIGHT + getHeight() + 20) {
            KillObject.killObject(this);

//            System.out.println("BOUNDS KILLED");
        }



        super.act(delta);
        checkCollision();
    }

    private void checkCollision() {

        if (getRectangle().overlaps(MyGame.mainPlayer.getRectangle())) {
            remove();
            MyGame.mainPlayer.extend();
        }
    }


}
