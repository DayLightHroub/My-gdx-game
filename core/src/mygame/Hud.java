package mygame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import mygame.objects.Player;

public class Hud {
    int x, y;
    private BitmapFont font;
    private final int xText = 10;
    private final int yText = MyGame.HEIGHT - 70;
    private final ShapeRenderer shapeRenderer;

    public Hud(ShapeRenderer shapeRenderer) {
        x = 10;
        y = MyGame.HEIGHT - 40;
        font = new BitmapFont();
        font.setColor(Color.GRAY);
        this.shapeRenderer = shapeRenderer;
    }





    public void draw(Batch batch) {

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        drawHealthRect(shapeRenderer, Color.GRAY, 200);
        //HP will be filled in the grey box
        drawHealthRect(shapeRenderer, new Color(0.5882f, (Player.getAnimationHealth() / 100f), 0, 1f), Player.getAnimationHealth() * 2);


        //white boarder
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, 200, 32);


        //end calls here
        shapeRenderer.end();

        drawText(batch);

    }

    public void drawText(Batch batch) {

        batch.begin();
        font.draw(batch, "Score: " + Player.getScore(), xText, yText);

        batch.end();
    }


    private void drawHealthRect(ShapeRenderer shapeRenderer, Color color, int healthAmount) {

        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, healthAmount, 32);
    }
}
