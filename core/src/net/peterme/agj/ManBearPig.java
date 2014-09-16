package net.peterme.agj;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class ManBearPig extends GameObject {
	//private Animation walkAnimation;
	public boolean grounded = false;
	private Body body;
	private Animation manimation;
	private Animation bearimation;
	private Animation pigimation;
	public boolean drawnByObstacle = false;
	public MorphMode mode;
	private Rumble rumble;
	public Scene scene;
	public boolean isAlive = true;
	private Action action;
	private ParticleEffect deathEffect;
	private ParticleEffect bombEffect;
	private ParticleEffect[] bombEffects;
	private ParticleEffect runEffect;
	private ParticleEffect[] runEffects;
	private float bombX;
	public Rectangle collisionRect;
	private float ySpd=0;
	private float gravity=.5f;
	//private Random rand;
	//private Texture particleTexture;
	//private Particle[] particles;
	/*private class Particle{
		float x;
		float y;
		Texture texture;
		float life = 0f;
		float scale;
		float alpha;
	}*/
	public enum MorphMode {
		MAN,
		BEAR,
		PIG
	};
	public ManBearPig(String image/*,World world*/,final Scene scene,TextureAtlas textures) {
		super(textures);
		rumble = new Rumble();
		this.scene = scene;
		//rand = new Random();
		//particleTexture = loadImage("partikkel1.png");
		//particles = new Particle[15];
		//bombEffect = new ParticleEffect();
		//bombEffect.load(Gdx.files.internal("particle1.p"), Gdx.files.internal(""));
		/*runEffects = new ParticleEffect[3];
		runEffects[0] = new ParticleEffect();
		runEffects[0].load(Gdx.files.internal("particleRun1.p"), Gdx.files.internal(""));
		runEffects[1] = new ParticleEffect();
		runEffects[1].load(Gdx.files.internal("particleRun2.p"), Gdx.files.internal(""));
		runEffects[2] = new ParticleEffect();
		runEffects[2].load(Gdx.files.internal("particleRun3.p"), Gdx.files.internal(""));*/
		
		bombEffects = new ParticleEffect[3];
		bombEffects[0] = new ParticleEffect();
		bombEffects[0].load(Gdx.files.internal("particle1.p"), Gdx.files.internal(""));
		bombEffects[1] = new ParticleEffect();
		bombEffects[1].load(Gdx.files.internal("particle2.p"), Gdx.files.internal(""));
		bombEffects[2] = new ParticleEffect();
		bombEffects[2].load(Gdx.files.internal("particle3.p"), Gdx.files.internal(""));
	
		deathEffect = new ParticleEffect();
		deathEffect.load(Gdx.files.internal("particleDie.p"), Gdx.files.internal(""));
		

		/*runEffect = runEffects[0];
		runEffect.start();
		runEffect.setPosition(x+90, y+10);*/
		
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
     		
     	/*	world.setContactListener(new ContactListener(){

				@Override
				public void beginContact(Contact contact) {
					WorldManifold manifold = contact.getWorldManifold();
			    	  for(int j = 0; j < manifold.getNumberOfContactPoints(); j++){
			    		  if(contact.getFixtureA().getUserData() != null &&contact.getFixtureA().getUserData().equals("ground"))
			    			  grounded=true;
			    		  if(contact.getFixtureB().getUserData() != null &&contact.getFixtureB().getUserData().equals("ground"))
			    			  grounded=true;
			    	  }
				}

				@Override
				public void endContact(Contact contact) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void preSolve(Contact contact, Manifold oldManifold) {
					WorldManifold manifold = contact.getWorldManifold();
			    	  for(int j = 0; j < manifold.getNumberOfContactPoints(); j++){
			    		  if(contact.getFixtureA().getUserData() != null &&contact.getFixtureA().getUserData() instanceof Pickup){
			    			 Pickup pickup = ((Pickup) contact.getFixtureA().getUserData());
			    			 if(pickup.openMode==mode){
			    				 pickup.dead=true;
			    				 ((GameScene) scene).score+=1000;
			    				 scene.addActor(new ScoreIndicator("+1000",pickup.x,pickup.y));
			    			 }
			    			 contact.setEnabled(false);
			    		  }
			    		  if(contact.getFixtureB().getUserData() != null &&contact.getFixtureB().getUserData() instanceof Pickup){
			    			  Pickup pickup = ((Pickup) contact.getFixtureB().getUserData());
			    			  if(pickup.openMode==mode){
			    				  pickup.dead=true;
			    				 scene.addActor(new ScoreIndicator("+1000",pickup.x,pickup.y));
			    				 ((GameScene) scene).score+=1000;
			    			  }
			    			  contact.setEnabled(false);
			    		  }
			    	  }
				}

				@Override
				public void postSolve(Contact contact, ContactImpulse impulse) {
					// TODO Auto-generated method stub
					
				}
     			
     		});*/
	}
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		collisionRect.setPosition(x+10, y);
		//collisionRect.setY(y);
	}
	public void jump(){
		if(grounded){
			grounded=false;
			ySpd=-11;
			//setPosition(getX(),getY()+2);
			Gdx.app.log("test", "jump");
		}
	}
	public void morph(){
		Gdx.input.vibrate(40);
		rumble.rumble(10f, .2f,scene);
		if(animation==manimation){
			animation=bearimation;
			mode=MorphMode.BEAR;
			explode();
			return;
		}else if(animation==bearimation){
			animation=pigimation;
			mode=MorphMode.PIG;
			explode();
			return;
		}else if(animation==pigimation){
			animation=manimation;
			mode=MorphMode.MAN;
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
		for(int i=0;i<6;i++){
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
						if((int) tileRect.y+tileRect.height>(int) collisionRect.y && (int) tileRect.y<(int) collisionRect.y+collisionRect.height){
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
		if(bombEffect!=null){
			bombEffect.setPosition(getX()+45+bombX, getY()+45-ySpd);
			bombEffect.update(delta);
			if(isAlive)
				bombX+=12;
		}
		if(deathEffect!=null){
			deathEffect.setPosition(getX()+45, getY());
			deathEffect.update(delta);
		}
	}
	@Override
	public void draw(Batch batch,float alpha){
		if(bombEffect!=null)
			bombEffect.draw(batch);
		if(runEffect!=null)
			runEffect.draw(batch);
		if(deathEffect!=null)
			deathEffect.draw(batch);
		if(isAlive){
			if(!drawnByObstacle)
				super.draw(batch,alpha);
		}
	}
	public void die(){
		if(isAlive){
			Gdx.app.log("Dead", "Game over");
			isAlive=false;
			//explode();
			deathEffect.reset();
			deathEffect.start();
			deathEffect.setPosition(getX()+45, getY());
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
			bombEffect=bombEffects[0];
			//runEffect=runEffects[0];
			break;
		case BEAR:
			bombEffect=bombEffects[1];
			//runEffect=runEffects[1];
			break;
		case PIG:
			bombEffect=bombEffects[2];
			//runEffect=runEffects[2];
			break;
		}

		bombX=0;
		bombEffect.reset();
		bombEffect.start();
		bombEffect.setPosition(getX()+45, getY()+45);
		/*runEffect.reset();
		runEffect.start();
		runEffect.setPosition(x+90, y+10);*/
	}
}
