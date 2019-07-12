package mygame.common;

import com.badlogic.gdx.utils.Pools;

import mygame.objects.GameObject;

public class KillObject {


    private KillObject()
    {

    }

    public static void killObject(GameObject object) {
        object.remove();
        Pools.free(object);
        System.out.println("killed");

    }
}
