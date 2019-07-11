package mygame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import mygame.MyGame;

public class Extender extends GameObject {

    public Extender(GameObjectID id, ShapeRenderer renderer, Color color) {
        super(id, renderer, color);
    }

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
        MyGame.enemyPool.free(this);
        System.out.println("killed");
    }
}
