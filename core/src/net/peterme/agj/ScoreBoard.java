package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class ScoreBoard extends GameObject {

	private BitmapFont font;
	private GameScene scene;
	private boolean showAttempts;
	private int attempts;
	public ScoreBoard(GameScene scene,int attempts) {
		super(scene.textures,scene.sounds);
		font = new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"),false);
        this.scene = scene;
        this.attempts=attempts;
        if(attempts!=0){
        	showAttempts=true;
	        addAction(sequence(new DelayAction(3),
					run(new Runnable(){
					@Override
					public void run() {
						showAttempts=false;
					}
			})));
        }
	}
	@Override
	public void draw(Batch batch, float alpha){
		font.draw(batch, ""+(int) scene.score, 640-(font.getBounds(""+(int) scene.score, 0, (""+(int) scene.score).length()).width)/2, 700);
		font.draw(batch, "["+(int) scene.highScore+"]", 640-(font.getBounds("["+(int) scene.highScore+"]", 0, ("["+(int) scene.highScore+"]").length()).width)/2, 665);
		if(showAttempts)
			font.draw(batch, "Attempts: "+attempts, 640-(font.getBounds("Attempts: "+attempts, 0, ("Attempts: "+attempts).length()).width)/2, 615);
	}
	@Override
	public void act(float delta){
		super.act(delta);
		if(dead)
			font.dispose();
	}
}
