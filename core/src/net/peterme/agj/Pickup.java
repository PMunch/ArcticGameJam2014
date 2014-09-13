package net.peterme.agj;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import net.peterme.agj.ManBearPig.MorphMode;

public class Pickup extends GameObject {

	public MorphMode openMode;
	public ManBearPig player;
	private World world;
	private Body body;
	public Pickup(String image,final MorphMode openMode,final ManBearPig player,World world,int x,int y) {
		super(image);
		this.openMode=openMode;
		this.player=player;
		
		//setPosition(x, y);
		this.x=x;
		this.y=y;
		this.world=world;
		// First we create a body definition
 		BodyDef bodyDef = new BodyDef();
 		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
 		bodyDef.type = BodyType.KinematicBody;
 		// Set our body's starting position in the world
 		//bodyDef.position.set(1137, 135);
 		bodyDef.position.set(x/100f, y/100f);

 		// Create our body in the world using our body definition
 		body = world.createBody(bodyDef);
 		body.setLinearVelocity(2.5f, 0);

 		// Create a circle shape and set its radius to 6
 		PolygonShape rect = new PolygonShape();
 		rect.setAsBox(40/100f, 40/100f);

 		// Create a fixture definition to apply our shape to
 		FixtureDef fixtureDef = new FixtureDef();
 		fixtureDef.shape = rect;
 		fixtureDef.density = 0f; 
 		fixtureDef.friction = 0f;
 		fixtureDef.restitution = 0;//.6f; // Make it bounce a little bit

 		// Create our fixture and attach it to the body
 		Fixture fixture = body.createFixture(fixtureDef);
 		fixture.setUserData(this);

 		// Remember to dispose of any shapes after you're done with them!
 		// BodyDef and FixtureDef don't need disposing, but shapes do.
 		rect.dispose();
	}
	@Override
	public void act(float delta){
		super.act(delta);
		if(dead){
			world.destroyBody(body);
			body.setUserData(null);
			body=null;
		}
		x+=4;
	}
}
