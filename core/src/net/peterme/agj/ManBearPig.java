package net.peterme.agj;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
	private boolean grounded = false;
	private Body body;
	private Animation manimation;
	private Animation bearimation;
	private Animation pigimation;
	public int drawnByObstacle = 0;
	public MorphMode mode;
	private Rumble rumble;
	public Scene scene;
	public boolean isAlive = true;
	private Action action;
	private ParticleEffect bombEffect;
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
	public ManBearPig(String image,World world,Scene scene) {
		super();
		rumble = new Rumble();
		this.scene = scene;
		//rand = new Random();
		//particleTexture = loadImage("partikkel1.png");
		//particles = new Particle[15];
		bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("particle1.p"), Gdx.files.internal("."));
		Texture texture = loadImage(image);
		TextureRegion[][] tmp = TextureRegion.split(texture, 90, 90);              // #10
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
        
        	// First we create a body definition
     		BodyDef bodyDef = new BodyDef();
     		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
     		bodyDef.type = BodyType.DynamicBody;
     		// Set our body's starting position in the world
     		//bodyDef.position.set(1137, 135);
     		bodyDef.position.set(1137/100f, 200/100f);

     		// Create our body in the world using our body definition
     		body = world.createBody(bodyDef);

     		// Create a circle shape and set its radius to 6
     		PolygonShape rect = new PolygonShape();
     		rect.setAsBox(45/100f, 45/100f);

     		// Create a fixture definition to apply our shape to
     		FixtureDef fixtureDef = new FixtureDef();
     		fixtureDef.shape = rect;
     		fixtureDef.density = 0.5f; 
     		fixtureDef.friction = 0.1f;
     		fixtureDef.restitution = 0;//.6f; // Make it bounce a little bit

     		// Create our fixture and attach it to the body
     		Fixture fixture = body.createFixture(fixtureDef);
     		fixture.setUserData("player");

     		// Remember to dispose of any shapes after you're done with them!
     		// BodyDef and FixtureDef don't need disposing, but shapes do.
     		rect.dispose();
     		
     		world.setContactListener(new ContactListener(){

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
			    			 if(pickup.openMode==mode)
			    				 pickup.dead=true;
			    			 else
			    				 contact.setEnabled(false);
			    		  }
			    		  if(contact.getFixtureB().getUserData() != null &&contact.getFixtureB().getUserData() instanceof Pickup){
			    			  Pickup pickup = ((Pickup) contact.getFixtureB().getUserData());
			    			  if(pickup.openMode==mode)
			    				  pickup.dead=true;
			    			  else
			    				  contact.setEnabled(false);
			    		  }
			    	  }
				}

				@Override
				public void postSolve(Contact contact, ContactImpulse impulse) {
					// TODO Auto-generated method stub
					
				}
     			
     		});
	}
	public void jump(){
		if(grounded){
			grounded=false;
			body.applyForce(new Vector2(0,180), body.getPosition(), true);
		}
	}
	public void morph(){
		Gdx.input.vibrate(40);
		rumble.rumble(10f, .2f,scene);
		if(animation==manimation){
			animation=bearimation;
			mode=MorphMode.BEAR;
			return;
		}else if(animation==bearimation){
			animation=pigimation;
			mode=MorphMode.PIG;
			return;
		}else if(animation==pigimation){
			animation=manimation;
			mode=MorphMode.MAN;
			return;
		}
	}
	@Override
	public void act(float delta){
		super.act(delta);
		//x=(int) (body.getPosition().x*100f-45);
		if(action!=null){
			//action.act(delta);
			action.act(delta);
		}else{
			y=(int) (body.getPosition().y*100f-45);
		}
		if (rumble.time  > 0){
			rumble.tick(delta);
		}
		bombEffect.update(delta);
		/*for(int i=0;i<15;i++){
			if(particles[i]==null){
				if(rand.nextInt(100)>80){
					particles[i]= new Particle();
					particles[i].life=1f;
					particles[i].texture=particleTexture;
					particles[i].x=x+45;
					particles[i].y=y;
				}
			}else{
				particles[i].life-=0.03;
				particles[i].x+=4;
				particles[i].y+=rand.nextFloat();
				if(particles[i].life<0f){
					particles[i]=null;
				}
			}
		}*/
	}
	@Override
	public void draw(Batch batch,float alpha){
		bombEffect.draw(batch);
		if(drawnByObstacle==0)
			super.draw(batch,alpha);
	}
	public void die(){
		if(isAlive){
			isAlive=false;
			fire(new GameEvent("explode"));
			bombEffect.reset();
			bombEffect.start();
			bombEffect.setPosition(x+45, y+45);
			Timer.schedule(new Task(){
                @Override
                public void run() {
                    fire(new GameEvent(GameEvent.Type.RESTART));
                }
            }
            ,1);
		}
	}
}
