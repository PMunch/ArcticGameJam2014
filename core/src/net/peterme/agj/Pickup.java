package net.peterme.agj;

import com.badlogic.gdx.math.Rectangle;

import net.peterme.agj.ManBearPig.MorphMode;

public class Pickup extends GameObject {

	public MorphMode openMode;
	public ManBearPig player;
	public Rectangle collisionRect;
	private GameScene scene;
	//private World world;
	//private Body body;
	public Pickup(String image,final MorphMode openMode,final ManBearPig player,GameScene scene) {
		super(image,scene.textures);
		this.openMode=openMode;
		this.player=player;
		this.scene=scene;
		collisionRect = new Rectangle(getX(),getY(),getHeight(),getWidth());
	}
	/*public void step(float delta){
		setPosition(getX()+delta*60*6,getY());
	}*/
	@Override
	public void act(float delta){
		super.act(delta);
		if(dead==true){
			player.scene.addActor(new ScoreIndicator("+1000",getX(),getY(),textures));
			scene.score+=1000;
		}
		setPosition(getX()+delta*360*scene.groundSpeed,getY());
	}
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		this.x=(int)x;
		this.y=(int)y;
		collisionRect.set(x,y,getWidth(),getHeight());
	}
}
