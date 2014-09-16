package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ScoreBoard extends GameObject {

	private BitmapFont font;
	private GameScene scene;
	public ScoreBoard(GameScene scene) {
		super(scene.textures);
		font = new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"),false);
        this.scene = scene;
	}
	@Override
	public void draw(Batch batch, float alpha){
		font.draw(batch, ""+(int) scene.score, 640-(font.getBounds(""+(int) scene.score, 0, (""+(int) scene.score).length()).width)/2, 700);
	}
	@Override
	public void act(float delta){
		super.act(delta);
		if(dead)
			font.dispose();
	}
}
