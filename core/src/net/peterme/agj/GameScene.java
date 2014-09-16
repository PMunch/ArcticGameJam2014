package net.peterme.agj;

import java.util.ArrayList;

import net.peterme.agj.LevelParser.Entity;
import net.peterme.agj.LevelParser.Level;
import net.peterme.agj.ManBearPig.MorphMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameScene extends Scene {
	private ManBearPig manBearPig;
	//private World world;
	//private Box2DDebugRenderer debugRenderer;
	//private Matrix4 debugMatrix;
	private SkyBackground bg;
	public GameGround ground;
	//private Obstacle obst;
	public ArrayList<Obstacle> obstacles;
	//private Pickup pickup;
	public ArrayList<Pickup> pickups;
	//private Barrier barrier;
	public float score = 0;
	//public float groundSpeed = 1.8f;
	public float groundSpeed = 1.8f;
	private int levelProgress = 0;
	private float waitTime = -1f;
	//private ParticleSystem partSystem;
	//private ParticleEffectActor partSystem;
	/*public static class Level{
		private ArrayList<Entity> entities;
		private ArrayList<Layer> layer;
	}
	public static class Entity{
		private String type;
		private int x;
		private int y;
		private Settings settings;
	}
	public static class Settings{
		private int particleType;
		private int jumpSpeed;
	}
	public static class Layer{
		private String name;
		private int width;
		private int height;
		private boolean linkWithCollision;
		private int visible;
		private String tilesetName;
		private boolean repeat;
		private boolean preRender;
		private int distance;
		private int tilesize;
		private boolean foreground;
		private int[][] data;
	}*/
	public GameScene(Level level, TextureAtlas textures) {
		super(textures);
		//Json json = new Json();
		//Level level = json.fromJson(Level.class, Gdx.files.internal("level1.js"));
	    bg = new SkyBackground("Background/Scenery/bakgrunn1",this);
		//ground = new GameGround(level.layer.get(1).data,level.layer.get(1).width,this);
	    int levelWidth = level.getGround().width;
	    ground = new GameGround(level.getGround().data,levelWidth,this); 
		manBearPig = new ManBearPig("Main/mannbarschwein",this,textures);
		//manBearPig.x=1092;
		//manBearPig.y=120;
		manBearPig.setPosition(1092, 120);
		//stage.addActor(partSystem);

		addActor(bg);
		addActor(ground);
		obstacles=new ArrayList<Obstacle>();
		pickups = new ArrayList<Pickup>();
		for(Entity entity:level.entities){
			if(entity.type==0){
				Obstacle gate = null;
				switch(entity.subType){
				case 1:
					gate = new Obstacle("Main/gate",MorphMode.MAN,manBearPig,this);
					break;
				case 2:
					gate = new Obstacle("Main/skog",MorphMode.BEAR,manBearPig,this);
					break;
				case 3:
					gate = new Obstacle("Main/sump4",MorphMode.PIG,manBearPig,this);
					break;
				}
				if(gate!=null){
					gate.setPosition(entity.x-(levelWidth*45-1280)+300,
									(int) (720-(entity.y+gate.getHeight())));
					int i=0;
					if(obstacles.size()!=0){
						while(obstacles.get(i).getX()>gate.getX()){
							i++;
							if(i==obstacles.size())
								break;
						}
					}
					obstacles.add(i,gate);
					addActor(gate);
				}
			}else if(entity.type==1){
				Pickup pickup = null;
				switch(entity.subType){
				case 1:
					pickup = new Pickup("Main/pickup1",MorphMode.MAN,manBearPig,this);
					break;
				case 2:
					pickup = new Pickup("Main/pickup2",MorphMode.BEAR,manBearPig,this);
					break;
				case 3:
					pickup = new Pickup("Main/pickup3",MorphMode.PIG,manBearPig,this);
					break;
				}
				if(pickup!=null){
					pickup.setPosition(entity.x-(levelWidth*45-1280),
									(int) (720-(entity.y+pickup.getHeight())));
					pickups.add(pickup);
					addActor(pickup);
				}
			}
		}
		//for(Obstacle obst:obstacles)
		//	Gdx.app.log("Obstacles", ""+obst.x);
		stage.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keyCode){
				if(keyCode==Input.Keys.UP){
					manBearPig.jump();
					return true;
				}else if(keyCode==Input.Keys.DOWN){
					manBearPig.morph();
					return true;
				}
				return false;
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				if(button==Input.Buttons.LEFT){
					if(x>stage.getWidth()/2){
						manBearPig.jump();
					}else{
						manBearPig.morph();
					}
				}
				return false;
			}
		});
		/*stage.addListener(new GameEventListener(){
			@Override
			public boolean onKimEvent(String ev){
				if(ev.equals("explode")){
					//partSystem.explode(manBearPig.x+45, manBearPig.y+45);
				}
				return true;
			}
		});*/
		addActor(manBearPig);
		addActor(new ScoreBoard(this));
	}
	@Override
	public void render(float delta){
		super.render(delta);
		/*ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(manBearPig.grounded?Color.BLACK:Color.RED);
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeType.Line);
        sr.rect(manBearPig.collisionRect.x,manBearPig.collisionRect.y,manBearPig.collisionRect.width,manBearPig.collisionRect.height);
        int i=28;
        for(Rectangle[] rectL:ground.colRects){
        	for(Rectangle rect:rectL){
        		if(rect!=null)
        			sr.rect((i+ground.groundOffset)*45,rect.y,rect.width,rect.height);
        	}
        	i--;
        }
        sr.end();*/
		/*if(manBearPig.isAlive){
		Gdx.app.log("Delta", ""+delta);
		}*/
		//float delta = 1/(60*6);
		//float delta = delta2;
		//if(levelProgress!=-1){
		if(delta<0.1f){
			groundSpeed=1.8f;
		}else{
			//waitTime-=delta;
		}
			if(manBearPig.isAlive){
		        //world.step( delta, 8, 3 );
		        //debugRenderer.render(world, debugMatrix);
				/*for(Obstacle gate:obstacles){
					gate.step(delta*groundSpeed);
				}*/
				/*for(Pickup pickup:pickups){
					pickup.step(delta*groundSpeed);
				}*/
				//bg.step(delta*groundSpeed);
				//ground.step(delta*groundSpeed);
				//pickup.step();
				//barrier.step();
				score+=delta*10;
				levelProgress+=delta*groundSpeed*60*6;
			}else{
				groundSpeed=0;
			}
		/*}else{
			levelProgress=0;
		}*/
	}
	@Override
	public void dispose(){
		super.dispose();
		obstacles.clear();
		pickups.clear();
	}
}
