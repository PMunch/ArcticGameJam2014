package net.peterme.agj;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class GameGround extends GameObject {
	private ArrayList<Integer> ground;
	private ArrayList<Body> bodies;
	private float groundOffset = 1f;
	private float groundSpeed = 0.1f;
	
	public GameGround(String image,World world) {
		super(image);
		ground = new ArrayList<Integer>();//new int[41];
		bodies = new ArrayList<Body>();
		
		for(int i = 0; i<30; i++){
			//ground[i]=lastHeight;
			ground.add(1);
			
			// First we create a body definition
     		BodyDef bodyDef = new BodyDef();
     		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
     		bodyDef.type = BodyType.KinematicBody;
     		// Set our body's starting position in the world
     		//bodyDef.position.set(1137, 135);
     		bodyDef.position.set((i*45)/100f, 45*1.5f/100f);

     		// Create our body in the world using our body definition
     		Body body = world.createBody(bodyDef);

     		// Create a circle shape and set its radius to 6
     		PolygonShape rect = new PolygonShape();
     		rect.setAsBox(45/200f, 45/200f);

     		// Create a fixture definition to apply our shape to
     		FixtureDef fixtureDef = new FixtureDef();
     		fixtureDef.shape = rect;
     		fixtureDef.density = 0.5f; 
     		fixtureDef.friction = 0.1f;
     		fixtureDef.restitution = 0;//.6f; // Make it bounce a little bit

     		// Create our fixture and attach it to the body
     		Fixture fixture = body.createFixture(fixtureDef);
     		fixture.setUserData("ground");

     		// Remember to dispose of any shapes after you're done with them!
     		// BodyDef and FixtureDef don't need disposing, but shapes do.
     		rect.dispose();
     		
     		//body.setLinearVelocity(7f, 0);
     		bodies.add(body);
		}
	}
	@Override
	public void draw(Batch batch,float alpha){
		for(int i=0;i<ground.size();i++){
			batch.draw(texture,(i-groundOffset)*45,ground.get(i)*45);
			for(int j=0;j<ground.get(i);j++){
				batch.draw(texture,(i-groundOffset)*45,j*45);
			}
		}
	}
	@Override
	public void act(float delta){
		super.act(delta);
		groundOffset-=groundSpeed;
		if(groundOffset<0){
			groundOffset+=1;
			ground.remove(ground.size()-1);
			ground.add(0, 1);
		}
	}
}
