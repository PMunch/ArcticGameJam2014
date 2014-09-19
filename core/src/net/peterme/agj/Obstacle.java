package net.peterme.agj;

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
	private GameScene scene;
	private boolean playSound=true;
	public TextureRegion lightPillar;
	public Obstacle(String image,MorphMode openMode,ManBearPig player,GameScene scene) {
		super(scene.textures,scene.sounds);
		this.openMode = openMode;
		this.player=player;
		this.scene=scene;
		TextureRegion tex = loadImage(image);
		//Gdx.app.log("Nulltest", ""+tex+" "+tex.getRegionWidth());
		TextureRegion[][] tmp = tex.split( tex.getRegionWidth()/4, tex.getRegionHeight());
		obstacleParts = new TextureRegion[4];
        int index = 0;
        for (int j = 0; j < 4; j++) {
            obstacleParts[index++] = tmp[0][j];
        }
        collisionRect = new Rectangle(getX(),getY(),obstacleParts[0].getRegionWidth()*2,obstacleParts[0].getRegionHeight());
	}
	@Override
	public void draw(Batch batch,float alpha){
		if(lightPillar!=null)
			batch.draw(lightPillar,x+obstacleParts[0].getRegionWidth()-100,openMode==MorphMode.PIG?y+45:y);
		if(player.mode==openMode){
			batch.draw(obstacleParts[3], x+obstacleParts[0].getRegionWidth(), y);
			if(drawPlayer && player.isAlive){
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.getX(),player.getY());
				if(player.getX()<=x+obstacleParts[0].getRegionWidth() && player.getX()>=x+obstacleParts[0].getRegionWidth()-90){
					if(playSound){
						switch(openMode){
						case MAN:
							sounds.barrier[0].play();
							break;
						case BEAR:
							sounds.barrier[1].play();
							break;
						case PIG:
							sounds.barrier[2].play();
							break;
						}
						/*LightPillar pillar = new LightPillar(textures,sounds);
						pillar.x=(int) getX();
						pillar.y=(int) getY();
						scene.addActor(pillar);*/
						lightPillar = loadImage("Main/pillar");
						playSound=false;
					}
				}
			}
			batch.draw(obstacleParts[2], x, y);
		}else{
			batch.draw(obstacleParts[1], x+obstacleParts[0].getRegionWidth(), y);
			if(drawPlayer && player.isAlive){
				batch.draw(player.animation.getKeyFrame(player.elapsedTime),player.getX(),player.getY());
				if(player.getX()<=x+obstacleParts[0].getRegionWidth() && player.getX()>=x+obstacleParts[0].getRegionWidth()-90){
					player.die();
				}
			}
			batch.draw(obstacleParts[0], x, y);
		}
		if(x>1280){
			dead=true;
		}
	}
	public void act(float delta){
		super.act(delta);
		//setX(getX()+6);
		//x+=6;
		setPosition(getX()+delta*360*scene.groundSpeed,getY());
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
