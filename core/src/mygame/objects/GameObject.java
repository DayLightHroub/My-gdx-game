package mygame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class GameObject extends Actor {

    private final ShapeRenderer renderer;
    protected GameObjectID id;
    protected float velX, velY;
    protected Rectangle rectangle;


    public GameObject(GameObjectID id, ShapeRenderer renderer, Color color) {

        this.id = id;
        this.renderer = renderer;
        setColor(color);
        rectangle =  new Rectangle();


    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        updateRect();
    }

    public void updateRect() {
     rectangle.set(getX(), getY(), getWidth(), getHeight());

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
    public void setX(float x) {
        super.setX(x);
        rectangle.x = x;
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        rectangle.y = y;
    }
}
