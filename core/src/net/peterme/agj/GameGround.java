package net.peterme.agj;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class GameGround extends GameObject {
	//private ArrayList<Integer> ground;
	//private ArrayList<Body> bodies;
	public float groundOffset = 0f;
	private float groundSpeed = 0.134f;
	private Texture[] textures;
	//private ArrayList<Body[]> bodies;
	public ArrayList<Rectangle[]> colRects;
	public ArrayList<int[]> tiles;
	//private int[][] tiles;
	private int totalWidth;
	private int currentWidth;
	private int[][] data;
	//private World world;
	
	public GameGround(/*World world,*/int[][] data,int totalWidth) {
		super();
		this.totalWidth = totalWidth;
		this.data=data;
		//this.world=world;
		textures = new Texture[3];
		textures[0]=loadImage("tile1.png");
		textures[1]=loadImage("obstacle1.png");
		textures[2]=loadImage("obstacle2.png");
		//ground = new ArrayList<Integer>();//new int[41];
		//bodies = new ArrayList<Body>();
		//bodies =new ArrayList<Body[]>(30);
		colRects =new ArrayList<Rectangle[]>(30);
		tiles=new ArrayList<int[]>(30);
		for(int i=0;i<30;i++){
			tiles.add(i, new int[16]);
			colRects.add(i,new Rectangle[16]);
		}
		//tiles = new int[29][16];
		for(int i=0;i<30;i++){
			currentWidth++;
			for(int j=0;j<16;j++){
				tiles.get(29-i)[j]=data[15-j][totalWidth-31+i];
				if(tiles.get(29-i)[j]!=0){
		     		//body.setLinearVelocity(7f, 0);
		     		//bodies.get(29-i)[j]=createBody(i,j);
					colRects.get(29-i)[j]=createRect(i,j);
				}
			}
		}
		/*for(int i = 0; i<30; i++){
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
		}*/
	}
	@Override
	public void draw(Batch batch,float alpha){
		/*for(int i=0;i<ground.size();i++){
			batch.draw(texture,(i-groundOffset)*45,ground.get(i)*45);
			for(int j=0;j<ground.get(i);j++){
				batch.draw(texture,(i-groundOffset)*45,j*45);
			}
		}*/
		for(int i=0;i<30;i++){
			for(int j=0;j<16;j++){
				if(tiles.get(29-i)[j]!=0)
					batch.draw(textures[tiles.get(29-i)[j]-1],(i-1+groundOffset)*45,j*45);
			}
		}
	}
	/*@Override
	public void act(float delta){
		super.act(delta);
		
	}*/
	public void step(float delta){
		groundOffset+=delta*8;
		if(groundOffset>1){
			groundOffset-=1f;
			tiles.remove(0);
			int[] row = new int[16];
			tiles.add(29,row);
			Body temp;
			for(int i=0;i<16;i++){
				tiles.get(29)[i]=data[15-i][totalWidth-currentWidth];
				//temp=bodies.get(29)[i];
				//world.destroyBody(temp);
				//temp.setUserData(null);
			}
			colRects.remove(0);
			Rectangle[] bRow = new Rectangle[16];
			colRects.add(29,bRow);
			for(int i=0;i<16;i++){
				if(tiles.get(29)[i]!=0)
					colRects.get(29)[i]=createRect(0,i);
			}
			currentWidth++;
			//ground.remove(ground.size()-1);
			//ground.add(0, 1);
		}
	}
	public Rectangle createRect(int i, int j){
		return new Rectangle(0,j*45,45,45);
	}
}
