package net.peterme.agj;

import java.util.ArrayList;

import net.peterme.agj.ManBearPig.MorphMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class GameScene extends Scene {
	private ManBearPig manBearPig;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;
	private SkyBackground bg;
	private GameGround ground;
	private Obstacle obst;
	private Pickup pickup;
	private Barrier barrier;
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
		//JsonValue root = new JsonReader().parse(Gdx.files.internal("level1.js"));
		Level level = json.fromJson(Level.class, Gdx.files.internal("level1peter.js"));
		//addActor(new Ground("tile.png"));
		world = new World(new Vector2(0, -17), true); 
		debugRenderer = new Box2DDebugRenderer();
	    //debugMatrix=camera.combined.cpy();//new Matrix4(camera.combined);
	    //debugMatrix.scale(1/1000000f, 1/1000000f, 1f);
	    debugMatrix = new OrthographicCamera( 1280/100f, 720/100f ).combined;
	    debugMatrix.setTranslation(new Vector3(-1f,-1f,0));
	    //ParticleEffect bombEffect = new ParticleEffect();
		//bombEffect.load(Gdx.files.internal("particle1.p"), Gdx.files.internal("."));

	    //partSystem = new ParticleEffectActor(bombEffect);
	    bg = new SkyBackground("bakgrunn1.png");
		addActor(bg);
		ground = new GameGround(world,level.layer.get(1).data,level.layer.get(1).width);
		addActor(ground);
		manBearPig = new ManBearPig("mannbarschwein.png",world,this); 
		manBearPig.x=1092;
		manBearPig.y=120;

		//stage.addActor(partSystem);
		//obst = new Obstacle("sump4.png", MorphMode.PIG, manBearPig);
		//obst = new PigGate(manBearPig);
		//obst.x=-280;
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
	        world.step( delta, 8, 3 );
	        debugRenderer.render(world, debugMatrix);
			//obst.step();
			bg.step();
			ground.step(delta);
			//pickup.step();
			//barrier.step();
			score++;
		}
	}
}
