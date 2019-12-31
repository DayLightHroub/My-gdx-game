package mygame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

import mygame.MyGame;

/**
 * Mainly used as a general object for any *rectangular* objects,
 * This will handle rendering of the object and setting up it's rectangle for the use of collisions
 */
public class GameObject extends Actor implements Pool.Poolable {

    private static ShapeRenderer renderer;
    protected float velX, velY;
    protected Rectangle rectangle;


    public static void setShapeRenderrer(ShapeRenderer renderer) {
        GameObject.renderer = renderer;
    }

    public GameObject() {
        rectangle = new Rectangle();
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        updateRect();
    }

    public void updateRect() {
        rectangle.set(getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());

    }


    public GameObject setVelX(float velX) {
        this.velX = velX;
        return this;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getVelX() {
        return velX;
    }

    public GameObject setVelY(float velY) {
        this.velY = velY;
        return this;
    }

    public float getVelY() {
        return velY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(getColor());
        renderer.rect(getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateRect();
    }


    @Override
    public void reset() {

    }
}
