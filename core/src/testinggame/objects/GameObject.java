package testinggame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class GameObject extends Actor {

    private final ShapeRenderer renderer;
    protected GameObjectID id;
    protected float velX, velY;

    protected Polygon polygon;


    public GameObject(GameObjectID id, ShapeRenderer renderer, Color color) {

        this.id = id;
        this.renderer = renderer;
        setColor(color);
        polygon = new Polygon();


    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        updatePoly();
    }

    public void updatePoly() {

        polygon.setVertices(new float[]{0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});

    }


    public GameObject setVelX(float velX) {
        this.velX = velX;
        return this;
    }

    public Polygon getPoly() {
        return polygon;
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
//        renderer.setColor(Color.GREEN);
//        renderer.polygon(polygon.getTransformedVertices());



    }


    @Override
    public void setX(float x) {
        super.setX(x);
        polygon.setPosition(getX(), getY());
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        polygon.setPosition(getX(), getY());

    }
}
