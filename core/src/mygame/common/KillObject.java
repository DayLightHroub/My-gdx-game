package mygame.common;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class KillObject {


    private KillObject() {

    }

    public static void killObject(Pool.Poolable object) {
        ((Actor) object).remove();
        Pools.free(object);

    }
}
