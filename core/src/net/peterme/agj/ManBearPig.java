package net.peterme.agj;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;


public class ManBearPig extends GameObject {
	public boolean grounded = false;
	private Animation manimation;
	private Animation bearimation;
	private Animation pigimation;
	public boolean drawnByObstacle = false;
	public MorphMode mode;
	private Rumble rumble;
	public GameScene scene;
	public boolean isAlive = true;
	private Action action;
	public Rectangle collisionRect;
	private float ySpd=0;
	private float gravity=.5f;
	private boolean willJump = false;
	private Random rand;
	public enum MorphMode {
		MAN,
		BEAR,
		PIG
	};
	public ManBearPig(String image/*,World world*/,final GameScene scene,TextureAtlas textures,Sounds sounds) {
		super(textures,sounds);
		rumble = new Rumble();
		this.scene = scene;
		rand = new Random();
		
		TextureRegion texture = loadImage(image);
		TextureRegion[][] tmp = texture.split( 90, 90);              // #10
        TextureRegion[] manFrames = new TextureRegion[4];
        int index = 0;
        for (int j = 0; j < 4; j++) {
            manFrames[index++] = tmp[0][j];
        }
        manimation = new Animation(1/10f, manFrames);
        manimation.setPlayMode(PlayMode.LOOP_PINGPONG);
        TextureRegion[] bearFrames = new TextureRegion[4];
        index = 0;
        for (int j = 4; j < 8; j++) {
            bearFrames[index++] = tmp[0][j];
        }
        bearimation = new Animation(1/10f, bearFrames);
        bearimation.setPlayMode(PlayMode.LOOP_PINGPONG);
        TextureRegion[] pigFrames = new TextureRegion[4];
        index = 0;
        for (int j = 8; j < 12; j++) {
            pigFrames[index++] = tmp[0][j];
        }
        pigimation = new Animation(1/10f, pigFrames);
        pigimation.setPlayMode(PlayMode.LOOP_PINGPONG);
        animation = manimation;
        mode=MorphMode.MAN;
        
        collisionRect = new Rectangle(getX()+10,getY(), texture.getRegionWidth()/12-20, texture.getRegionHeight());
	}
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		collisionRect.setPosition(x+10, y);
		//collisionRect.setY(y);
	}
	public void jump(){
		willJump=true;
	}
	public void performJump(){
		//if(grounded){
			grounded=false;
			ySpd=-11;
			//setPosition(getX(),getY()+2);
			//Gdx.app.log("test", "jump");
		//}
	}
	public void morph(){
		rumble.rumble(10f, .2f,scene);
		sounds.shift.play();
		if(animation==manimation){
			animation=bearimation;
			mode=MorphMode.BEAR;
			scene.bg.texture=scene.bg.backdrops[1];
			explode();
			return;
		}else if(animation==bearimation){
			animation=pigimation;
			mode=MorphMode.PIG;
			scene.bg.texture=scene.bg.backdrops[2];
			explode();
			return;
		}else if(animation==pigimation){
			animation=manimation;
			mode=MorphMode.MAN;
			scene.bg.texture=scene.bg.backdrops[0];
			explode();
			return;
		}
	}
	@Override
	public void act(float delta){
		super.act(delta);
		ySpd+=gravity;
		grounded=false;
		setPosition(getX(),getY()-ySpd);
		Rectangle tileRect = new Rectangle();
		ArrayList<Rectangle[]> colRects = ((GameScene)scene).ground.colRects;
		
		//collisionRect.set(collisionRect.x,collisionRect.y-ySpd,collisionRect.width,collisionRect.height+ySpd);
		for(int i=3;i<6;i++){
			for(int j=0;j<16;j++){
			//for(Rectangle rect:colRects.get(i)){
				Rectangle rect = colRects.get(i)[j];
				if(rect!=null){
					//int offset=(int) (1280-(i-1)*45-(((GameScene)scene).groundSpeed*delta*45*8));//-(1-((GameScene)scene).ground.groundOffset)*22);
					//int offset=1280-(i-1)*45;
					//float speedOffset=((GameScene)scene).groundSpeed*delta*45*8;
					tileRect.set(1280-(i+((GameScene)scene).ground.groundOffset)*45,rect.y,rect.width,rect.height);
					//Gdx.app.log("Tile", tileRect.x+" "+tileRect.width);
					if(tileRect.overlaps(collisionRect)){
						//Always die if tile is spike
						if(((GameScene)scene).ground.tiles.get(i)[j]==3){
							die();
						}
						//Falling from above
						if(ySpd>0){
							if(tileRect.y+tileRect.height/1.5<collisionRect.y){
								ySpd=0;
								setPosition(getX(),tileRect.y+tileRect.height);
								grounded=true;
							}
						}
						//Jumping upwards
						if(ySpd<0){
							if(tileRect.y>collisionRect.y+collisionRect.height){
								ySpd=0;
								setPosition(getX(),tileRect.y-collisionRect.height);
							}
						}
						//Sideways collision
						if((int) tileRect.y+tileRect.height>(int) collisionRect.y && (int) tileRect.y<(int) collisionRect.y+collisionRect.height && tileRect.x<collisionRect.x){
							/*if(isAlive)
								Gdx.app.log("test",""+(i+((GameScene)scene).ground.groundOffset)*45);*/
							die();
							ySpd=0;
						}
						/*if(tileRect.y+tileRect.height<collisionRect.y){
							if(ySpd>0){
								ySpd=0;
								setPosition(getX(),tileRect.y+tileRect.height+ySpd);
								grounded=true;
								if(((GameScene)scene).ground.tiles.get(i)[j]==3){
									die();
								}
							}
						}else if(tileRect.y>collisionRect.y+collisionRect.height){
							setPosition(getX(),tileRect.y-collisionRect.height);
							ySpd=0;
						}else{
							die();
							ySpd=0;
						}*/
					}
				}
			}
		}
		ArrayList<Obstacle> obstacles = ((GameScene)scene).obstacles;
		drawnByObstacle=false;
		for(int i=0;i<obstacles.size();i++){
			if(obstacles.get(i).collisionRect.overlaps(collisionRect)){
				obstacles.get(i).drawPlayer=true;
				drawnByObstacle=true;
			}else{
				obstacles.get(i).drawPlayer=false;
			}
		}
		ArrayList<Pickup> pickups = ((GameScene)scene).pickups;
		for(int i=0;i<pickups.size();i++){
			Pickup pickup = pickups.get(i);
			if(pickup.collisionRect.overlaps(collisionRect)){
				if(pickup.openMode==mode){
  				  pickup.dead=true;
  			  }
			}
		}
		//collisionRect = oldColRect;
		if(action!=null){
			action.act(delta);
		}
		if (rumble.time  > 0){
			rumble.tick(delta);
		}
		/*if(bombEffect!=null){
			bombEffect.setPosition(getX()+45+bombX, getY()+45-ySpd);
			bombEffect.update(delta);
			if(isAlive)
				bombX+=12;
		}
		if(deathEffect!=null){
			deathEffect.setPosition(getX()+45, getY());
			deathEffect.update(delta);
		}*/
		if(willJump){
			if(grounded)
				performJump();
			willJump=false;
		}
		if(grounded && isAlive && scene.groundSpeed!=0){
			switch(mode){
			case MAN:
				scene.stage.addActor(new Particle("Main/partikkel1", textures, (int)getX()+45, (int)getY()-12,3+rand.nextInt(4),rand.nextInt(2), 0.2f+(rand.nextFloat()-0.5f), 0.3f,null));
				break;
			case BEAR:
				scene.stage.addActor(new Particle("Main/partikkel2", textures, (int)getX()+45, (int)getY()-12,3+rand.nextInt(4),rand.nextInt(2), 0.2f+(rand.nextFloat()-0.5f), 0.3f,null));
				break;
			case PIG:
				scene.stage.addActor(new Particle("Main/partikkel3", textures, (int)getX()+45, (int)getY()-12,3+rand.nextInt(4),rand.nextInt(2), 0.2f+(rand.nextFloat()-0.5f), 0.3f,null));
				break;
			}
		}
	}
	@Override
	public void draw(Batch batch,float alpha){
		/*if(bombEffect!=null)
			bombEffect.draw(batch);
		if(runEffect!=null)
			runEffect.draw(batch);
		if(deathEffect!=null)
			deathEffect.draw(batch);*/
		if(isAlive){
			if(!drawnByObstacle)
				super.draw(batch,alpha);
		}
	}
	public void die(){
		if(isAlive){
			rumble.rumble(15f, .3f,scene);
			sounds.die.play();
			Gdx.app.log("Dead", "Game over");
			isAlive=false;
			//explode();
			/*deathEffect.reset();
			deathEffect.start();
			deathEffect.setPosition(getX()+45, getY());*/
			scene.stage.addActor(new ExplodeParticles(scene, textures, "Main/partikkelBlod", (int)getX()+40, (int)getY()+50,0,0,scene.ground));
			Timer.schedule(new Task(){
                @Override
                public void run() {
                    fire(new GameEvent(GameEvent.Type.RESTART));
                }
            }
            ,1);
		}
	}
	public void explode(){
		switch(mode){
		case MAN:
			scene.stage.addActor(new ExplodeParticles(scene, textures, "Main/partikkel1", (int)getX()+30, (int)getY()+30,3,0,scene.ground));
			//bombEffect=bombEffects[0];
			//runEffect=runEffects[0];
			break;
		case BEAR:
			scene.stage.addActor(new ExplodeParticles(scene, textures, "Main/partikkel2", (int)getX()+30, (int)getY()+30,3,0,scene.ground));
			//bombEffect=bombEffects[1];
			//runEffect=runEffects[1];
			break;
		case PIG:
			scene.stage.addActor(new ExplodeParticles(scene, textures, "Main/partikkel3", (int)getX()+30, (int)getY()+30,3,0,scene.ground));
			//bombEffect=bombEffects[2];
			//runEffect=runEffects[2];
			break;
		}

		/*bombX=0;
		bombEffect.reset();
		bombEffect.start();
		bombEffect.setPosition(getX()+45, getY()+45);*/
		/*runEffect.reset();
		runEffect.start();
		runEffect.setPosition(x+90, y+10);*/
	}
}
