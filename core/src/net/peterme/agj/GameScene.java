package net.peterme.agj;

import java.util.ArrayList;

import net.peterme.agj.ManBearPig.MorphMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Json;

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
	public int score = 0;
	//private ParticleSystem partSystem;
	//private ParticleEffectActor partSystem;
	public static class Level{
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
	}
	public GameScene() {
		super();
		Json json = new Json();
		Level level = json.fromJson(Level.class, Gdx.files.internal("level1.js"));
	    bg = new SkyBackground("bakgrunn1.png");
		addActor(bg);
		ground = new GameGround(level.layer.get(1).data,level.layer.get(1).width);
		addActor(ground);
		manBearPig = new ManBearPig("mannbarschwein.png",this);
		//manBearPig.x=1092;
		//manBearPig.y=120;
		manBearPig.setPosition(1092, 120);

		//stage.addActor(partSystem);
		obstacles=new ArrayList<Obstacle>();
		pickups = new ArrayList<Pickup>();
		for(Entity entity:level.entities){
			if(entity.type.equals("EntityGate")){
				Obstacle gate = null;
				switch(entity.settings.particleType){
				case 1:
					gate = new Obstacle("gate.png",MorphMode.MAN,manBearPig);
					break;
				case 2:
					gate = new Obstacle("skog.png",MorphMode.BEAR,manBearPig);
					break;
				case 3:
					gate = new Obstacle("sump4.png",MorphMode.PIG,manBearPig);
					break;
				}
				if(gate!=null){
					gate.setPosition(entity.x-(level.layer.get(1).width*45-1280),
									(int) (720-(entity.y+gate.getHeight())));
					obstacles.add(gate);
					addActor(gate);
				}
			}else if(entity.type.equals("EntityPickup")){
				Pickup pickup = null;
				switch(entity.settings.particleType){
				case 1:
					pickup = new Pickup("pickup1.png",MorphMode.MAN,manBearPig);
					break;
				case 2:
					pickup = new Pickup("pickup2.png",MorphMode.BEAR,manBearPig);
					break;
				case 3:
					pickup = new Pickup("pickup3.png",MorphMode.PIG,manBearPig);
					break;
				}
				if(pickup!=null){
					pickup.setPosition(entity.x-(level.layer.get(1).width*45-1280),
									(int) (720-(entity.y+pickup.getHeight())));
					pickups.add(pickup);
					addActor(pickup);
				}
			}
		}
		/*obst = new Obstacle("gate.png", MorphMode.MAN, manBearPig);
		//obst = new PigGate(manBearPig);
		obst.x=-400;
		obst.y=45;*/
		//barrier = new Barrier("obstacle1.png",world,-800,200);
		//addActor(barrier);
		//obst.y=50;
		//addActor(obst);
		//pickup = new Pickup("pickup1.png", MorphMode.MAN, manBearPig, world, -600, 200);
		//addActor(pickup);
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
		if(manBearPig.isAlive){
	        //world.step( delta, 8, 3 );
	        //debugRenderer.render(world, debugMatrix);
			for(Obstacle gate:obstacles){
				gate.step(delta);
			}
			for(Pickup pickup:pickups){
				pickup.step(delta);
			}
			bg.step(delta);
			ground.step(delta);
			//pickup.step();
			//barrier.step();
			score++;
		}
	}
}
