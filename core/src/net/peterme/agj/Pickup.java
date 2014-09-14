package net.peterme.agj;

import com.badlogic.gdx.math.Rectangle;

import net.peterme.agj.ManBearPig.MorphMode;

public class Pickup extends GameObject {

	public MorphMode openMode;
	public ManBearPig player;
	public Rectangle collisionRect;
	//private World world;
	//private Body body;
	public Pickup(String image,final MorphMode openMode,final ManBearPig player/*,World world,int x,int y*/) {
		super(image);
		this.openMode=openMode;
		this.player=player;
		collisionRect = new Rectangle(getX(),getY(),getHeight(),getWidth());
	}
	public void step(float delta){
		setPosition(getX()+delta*60*6,getY());
	}
	@Override
	public void act(float delta){
		if(dead==true){
			player.scene.addActor(new ScoreIndicator("+1000",getX(),getY()));
			((GameScene) player.scene).score+=1000;
		}
		super.act(delta);
	}
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		this.x=(int)x;
		this.y=(int)y;
		collisionRect.set(x,y,getWidth(),getHeight());
	}
}
