package net.peterme.agj;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Clouds extends GameObject {

	private class Cloud{
		float x;
		float y;
		float rotation;
		float scale;
		public Cloud(float x, float y, float rotation, float scale){
			this.x=x;
			this.y=y;
			this.rotation=rotation;
			this.scale=scale;
		}
	}
	private Cloud[] clouds;
	private float originX;
	private float originY;
	public Clouds(TextureAtlas textures, Sounds sounds) {
		super("Main/sky",textures, sounds);
		originX=texture.getRegionWidth()/2;
		originY=texture.getRegionHeight()/2;
		clouds = new Cloud[8];
		for(int i=0;i<clouds.length;i++){
			clouds[i] = newCloud(i-2);
		}
	}
	@Override
	public void draw(Batch batch,float alpha){
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, 0.3f);
		for(Cloud cloud:clouds)
			batch.draw(texture, cloud.x, cloud.y, originX, originY, texture.getRegionWidth(), texture.getRegionHeight(), cloud.scale, cloud.scale, cloud.rotation);
		batch.setColor(c);
	}
	@Override
	public void act(float delta){
		for(int i=0;i<clouds.length;i++){
			Cloud cloud = clouds[i];
			cloud.x+=delta*20;
			cloud.rotation+=10*(1-cloud.scale)*delta;
			if(cloud.x>1280){
				Gdx.app.log("Gone", "outside");
				clouds[i] = newCloud(-2);
			}
		}
	}
	private Cloud newCloud(int i){
		Random rand = new Random();
		float scale = 0.2f+rand.nextFloat()*0.8f;
		return new Cloud(1280/clouds.length*i+rand.nextInt(30),720-(int)originY-rand.nextInt(40),rand.nextInt(360),scale);
	}
}
