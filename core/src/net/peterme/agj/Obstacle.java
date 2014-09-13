package net.peterme.agj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.peterme.agj.ManBearPig.MorphMode;

public class Obstacle extends GameObject {

	private MorphMode openMode;
	private TextureRegion[] obstacleParts;
	private ManBearPig player;
	private int playerDraw;
	public Obstacle(String image,MorphMode openMode,ManBearPig player) {
		//super(image);
		this.openMode = openMode;
		this.player=player;
		player.drawnByObstacle++;
		playerDraw = player.drawnByObstacle;
		Texture tex = loadImage(image);
		TextureRegion[][] tmp = TextureRegion.split(tex, tex.getWidth()/4, tex.getHeight());
		obstacleParts = new TextureRegion[4];
        int index = 0;
        for (int j = 0; j < 4; j++) {
            obstacleParts[index++] = tmp[0][j];
        }
	}
	@Override
	public void draw(Batch batch,float alpha){
		if(player.mode==openMode){
			batch.draw(obstacleParts[3], x+obstacleParts[0].getRegionWidth(), y);
			if(player.drawnByObstacle==1){
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.x,player.y);
				if(player.x<=x+obstacleParts[0].getRegionWidth()-40 && player.x>=x && player.y>y+90){
					player.die();
				}
			}
			batch.draw(obstacleParts[2], x, y);
		}else{
			batch.draw(obstacleParts[1], x+obstacleParts[0].getRegionWidth(), y);
			if(player.drawnByObstacle==1){
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.x,player.y);
				if(player.x<=x+obstacleParts[0].getRegionWidth() && player.x>=x){
					player.die();
				}
			}
			batch.draw(obstacleParts[0], x, y);
		}
		if(x>1280){
			player.drawnByObstacle--;
			dead=true;
		}
	}
	public void step(){
		x+=4;
	}
}
