package mygame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;

import java.util.LinkedList;
import java.util.List;

import mygame.common.KillObject;

//TODO Remove actor class and replace with something else.

/**
 * This class is used to display a popup text on the screen with some animations to describe a message
 * Currently the supported colors are RED/GREEN only
 * TODO add some custom function to define RGB color.
 */
public class NotificationObject extends Actor implements Pool.Poolable {
    private String text;

    float fade = 0;
    float scaleTime = 0;
    float scaleValue = 0;
    private float c1, c2, c3;
    private static float SCALE_TIME = 0.7f;
    private BitmapFont font;
    public static int RED = 0;
    public static int GREEN = 1;
    private static float SCALE_TARGET = 0.25f;

    private List<Runnable> animationCalculations;


    public void init(String text, int color, float x, float y) {
        this.text = text;
        font = new BitmapFont();
        font.setColor(Color.RED);

        if (color == RED) {
            c1 = 0.760784f;
            c2 = 0.2f;
            c3 = 0.16078431f;
        } else {//green color default.

            c1 = 0.1137254f;
            c2 = 0.8f;
            c3 = 0.12156f;
        }

        setX(x);
        setY(y);

        animationCalculations = new LinkedList<Runnable>();
        //fade in runnable, this is used to show a fade in once this actor is drawn.
        animationCalculations.add(new Runnable() {
            @Override
            public void run() {
                fade += Gdx.graphics.getDeltaTime() * 2;
                if (fade > 1) {
                    fade = 1;
                    animationCalculations.remove(this);
                }
            }
        });

        //scale size animation, once this object is made it will increase it size by 0.15f
        animationCalculations.add(new Runnable() {
            @Override
            public void run() {
                scaleTime += Gdx.graphics.getDeltaTime();
                scaleValue = (scaleTime * SCALE_TARGET) / SCALE_TIME;
                if (scaleValue > SCALE_TARGET) {
                    scaleValue = SCALE_TARGET;
                    animationCalculations.remove(this);
                }
            }
        });


        //after this the defined amount of time remove this object.
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                animationCalculations.add(new Runnable() {
                    @Override
                    public void run() {
                        fade -= Gdx.graphics.getDeltaTime() * 2;
                        if (fade < 0) {
                            fade = 0;
                            animationCalculations.remove(this);
                            KillObject.killObject(NotificationObject.this);
                        }
                    }
                });
            }
        }, 2.5f);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        //check if there is any animation that need to be updated/recalcuated.
        if (!animationCalculations.isEmpty()) {
            for (Runnable runnable : animationCalculations)
                runnable.run();
        }

        font.setColor(c1, c2, c3, fade);
        font.getData().setScale(scaleValue + 1f);
        font.draw(batch, text, getX(), getY());
    }


    @Override
    public void reset() {
        fade = 0;
        scaleTime = 0;
        scaleValue = 0;
    }
}
