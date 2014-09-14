package net.peterme.agj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import net.peterme.agj.ManBearPig.MorphMode;

public class Obstacle extends GameObject {

	private MorphMode openMode;
	private TextureRegion[] obstacleParts;
	private ManBearPig player;
	public boolean drawPlayer;
	public Rectangle collisionRect;
	public Obstacle(String image,MorphMode openMode,ManBearPig player) {
		//super(image);
		this.openMode = openMode;
		this.player=player;
		Texture tex = loadImage(image);
		TextureRegion[][] tmp = TextureRegion.split(tex, tex.getWidth()/4, tex.getHeight());
		obstacleParts = new TextureRegion[4];
        int index = 0;
        for (int j = 0; j < 4; j++) {
            obstacleParts[index++] = tmp[0][j];
        }
        collisionRect = new Rectangle(getX(),getY(),obstacleParts[0].getRegionWidth()*2,obstacleParts[0].getRegionHeight());
	}
	@Override
	public void draw(Batch batch,float alpha){
		if(player.mode==openMode){
			batch.draw(obstacleParts[3], x+obstacleParts[0].getRegionWidth(), y);
			if(drawPlayer && player.isAlive){
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.getX(),player.getY());
				if(player.getX()<=x+obstacleParts[0].getRegionWidth()-40 && player.getX()>=x && player.getY()>y+90){
					player.die();
				}
			}
			batch.draw(obstacleParts[2], x, y);
		}else{
			batch.draw(obstacleParts[1], x+obstacleParts[0].getRegionWidth(), y);
			if(drawPlayer && player.isAlive){
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.getX(),player.getY());
				if(player.getX()<=x+obstacleParts[0].getRegionWidth() && player.getX()>=x){
					player.die();
				}
			}
			batch.draw(obstacleParts[0], x, y);
		}
		if(x>1280){
			dead=true;
		}
	}
	public void step(){
		//setX(getX()+6);
		//x+=6;
		setPosition(getX()+6,getY());
	}
	@Override
	public float getHeight(){
		return obstacleParts[0].getRegionHeight();
	}
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		this.x=(int)x;
		this.y=(int)y;
		//collisionRect = new Rectangle(x,y,obstacleParts[0].getRegionWidth()*2,obstacleParts[0].getRegionHeight());
		collisionRect.set(x,y,obstacleParts[0].getRegionWidth()*2,obstacleParts[0].getRegionHeight());
	}
}
