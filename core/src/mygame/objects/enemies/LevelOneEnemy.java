
package mygame.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LevelOneEnemy extends EnemyBasics {


    public void initEnemy(Color color, int damage, int healthUpValue, Stage stage) {
        setColor(color);
        super.init(damage, healthUpValue, stage);

    }

    @Override
    protected void move(float delta) {
        setY(getY() + getVelY() * delta);
    }
}
