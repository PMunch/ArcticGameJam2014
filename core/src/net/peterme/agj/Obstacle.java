package net.peterme.agj;

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
		TextureRegion[][] tmp = TextureRegion.split(loadImage(image), 140, 280);
		obstacleParts = new TextureRegion[4];
        int index = 0;
        for (int j = 0; j < 4; j++) {
            obstacleParts[index++] = tmp[0][j];
        }
	}
	@Override
	public void draw(Batch batch,float alpha){
		if(player.mode==openMode){
			batch.draw(obstacleParts[3], x+140, y);
			if(player.drawnByObstacle==1)
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.x,player.y);
			batch.draw(obstacleParts[2], x, y);
		}else{
			batch.draw(obstacleParts[1], x+140, y);
			if(player.drawnByObstacle==1){
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.x,player.y);
				if(player.x<=x+140 && player.x>=x){
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
