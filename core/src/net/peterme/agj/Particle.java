package net.peterme.agj;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Particle extends Actor {
	private TextureRegion texture;
	private int x;
	private int y;
	private float xSpeed;
	private float ySpeed;
	private int originX;
	private int originY;
	private float scaleX;
	private float scaleY;
	private float rotation;
	private int rotationSpeed;
	private float lifeTime;
	private float oLifeTime;
	private float alpha=1f;
	private GameGround ground;
	public Rectangle collisionRect;
	public Particle(String image, TextureAtlas textures, int x, int y,float xSpeed,float ySpeed, float scale, float lifeTime,GameGround ground){
		texture = textures.findRegion(image);
		this.x=x;
		this.y=y;
		this.xSpeed=xSpeed;
		this.ySpeed=ySpeed;
		this.lifeTime=oLifeTime=lifeTime;
		this.ground=ground;
		originX = texture.getRegionWidth()/2;
		originY = texture.getRegionHeight()/2;
		scaleX=scaleY=scale;
		Random rand = new Random();
		rotation = rand.nextFloat()*360;
		rotationSpeed = rand.nextInt(5);
		collisionRect = new Rectangle(x,y,texture.getRegionWidth()*scale,texture.getRegionHeight()*scale);
	}
	@Override
	public void draw(Batch batch, float alpha){
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, this.alpha);
		batch.draw(texture, x, y, originX, originY, texture.getRegionWidth(), texture.getRegionHeight(), scaleX, scaleY, rotation);
		batch.setColor(c);
		//batch.draw(texture, x, y,texture.getRegionWidth(), texture.getRegionHeight());
	}
	@Override
	public void act(float delta){
		lifeTime-=delta;
		if(lifeTime<0){
			clear();
			remove();
		}
		alpha=lifeTime/oLifeTime;
		collisionRect.setPosition(x,y);
		if(ground!=null){
			Rectangle tileRect = new Rectangle();
			Rectangle rect;
			//for(int i=3;i<6;i++){
			int i=(1280-(x+originX))/45+1;
			//if(y<45)
			//	Gdx.app.log("Test","Position: "+collisionRect.x+" tile: "+i);
			if(i>=0 && i<=29){
				for(int j=0;j<16;j++){
				//for(Rectangle rect:colRects.get(i)){
					rect = ground.colRects.get(i)[j];
					if(rect!=null){
						//int offset=(int) (1280-(i-1)*45-(((GameScene)scene).groundSpeed*delta*45*8));//-(1-((GameScene)scene).ground.groundOffset)*22);
						//int offset=1280-(i-1)*45;
						//float speedOffset=((GameScene)scene).groundSpeed*delta*45*8;
						tileRect.set(1280-(i+ground.groundOffset)*45,rect.y,rect.width,rect.height);
						//if(y<45)
						//	Gdx.app.log("Tile", tileRect.x+" "+tileRect.width);
						if(tileRect.overlaps(collisionRect)){
							//Gdx.app.log("Test","collision");
							/*if(tileRect.y+45<collisionRect.y)
								ySpeed=0;
							if(tileRect.y>collisionRect.y+collisionRect.height)
								ySpeed=0;*/
							//ySpeed=0;
							//clear();
							//remove();
							if(ySpeed<0){
								if(tileRect.y+tileRect.height/1.5<collisionRect.y){
									ySpeed=0;
									setPosition(getX(),tileRect.y+tileRect.height);
								}
							}
							//Jumping upwards
							if(ySpeed>0){
								if(tileRect.y>collisionRect.y+collisionRect.height){
									ySpeed=0;
									setPosition(getX(),tileRect.y-collisionRect.height);
								}
							}
							//Sideways collision
							if((int) tileRect.y+tileRect.height>(int) collisionRect.y && (int) tileRect.y<(int) collisionRect.y+collisionRect.height && tileRect.x<collisionRect.x){
								/*if(isAlive)
									Gdx.app.log("test",""+(i+((GameScene)scene).ground.groundOffset)*45);*/
								xSpeed=0;
							}
						}
					}
				}
			}
		}
		
		x+=xSpeed;
		y+=ySpeed;
		if(ySpeed!=0)
			ySpeed-=0.05f;
		rotation += delta*rotationSpeed;
	}
}
