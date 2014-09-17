package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class ScoreIndicator extends GameObject {

	private BitmapFont font;
	private String score;
	//private float x;
	//private float y;
	public ScoreIndicator(String score,float x,float y,TextureAtlas textures,Sounds sounds) {
		super(textures,sounds);
		font = new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"),false);
		//font.scale(.7f);
        this.score=score;
        //this.x=x;
        //this.y=y;
        setX(x);
        setY(y);
        MoveToAction act = new MoveToAction();
        act.setPosition(x, y+100);
        act.setDuration(0.2f);
        addAction(sequence(act,
                run(new Runnable(){
                    @Override
                    public void run() {
                        dead=true;
                    }
                    
        })));
	}
	@Override
	public void draw(Batch batch, float alpha){
		font.draw(batch, score,getX()+50,getY());
	}
	@Override
	public void act(float delta){
		super.act(delta);
		if(dead)
			font.dispose();
	}
}
