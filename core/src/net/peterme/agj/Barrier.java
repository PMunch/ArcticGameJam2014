package net.peterme.agj;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Barrier extends GameObject {

	private World world;
	private Body body;
	public Barrier(String image,World world,int x,int y,TextureAtlas textures) {
		super(image,textures);
		this.world=world;
		this.x=x;
		this.y=y;
		// First we create a body definition
 		BodyDef bodyDef = new BodyDef();
 		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
 		bodyDef.type = BodyType.KinematicBody;
 		// Set our body's starting position in the world
 		//bodyDef.position.set(1137, 135);
 		bodyDef.position.set((x+14)/100f, (y+22)/100f);

 		// Create our body in the world using our body definition
 		body = world.createBody(bodyDef);
 		body.setLinearVelocity(3.6f, 0);

 		// Create a circle shape and set its radius to 6
 		PolygonShape rect = new PolygonShape();
 		rect.setAsBox(45/200f, 45/200f);

 		// Create a fixture definition to apply our shape to
 		FixtureDef fixtureDef = new FixtureDef();
 		fixtureDef.shape = rect;
 		fixtureDef.density = 0f; 
 		fixtureDef.friction = 0f;
 		fixtureDef.restitution = 0;//.6f; // Make it bounce a little bit

 		// Create our fixture and attach it to the body
 		Fixture fixture = body.createFixture(fixtureDef);
 		fixture.setUserData("barrier");

 		// Remember to dispose of any shapes after you're done with them!
 		// BodyDef and FixtureDef don't need disposing, but shapes do.
 		rect.dispose();
	}
	public void step(){
		x+=6;
	}
}
