package mygame.objects;



import com.badlogic.gdx.utils.Pools;

import mygame.MyGame;

public class Extender extends GameObject {

    @Override
    public void act(float delta) {

        super.act(delta);
        setY(getY() + getVelY() * delta);
        if (getY() < 0 - getHeight()) {

            killObject();
//            System.out.println("HEALTH KILLED");

        } else if (getY() > MyGame.HEIGHT + getHeight() + 20) {
            killObject();

//            System.out.println("BOUNDS KILLED");
        }
    }


    private void killObject() {
        remove();
        Pools.free(this);
        System.out.println("killed");
    }
}
