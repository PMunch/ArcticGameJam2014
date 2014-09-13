package net.peterme.agj;

import net.peterme.agj.ManBearPig.MorphMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameScene extends Scene {
	private ManBearPig manBearPig;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;
	private SkyBackground bg;
	private GameGround ground;
	private Obstacle obst;
	public GameScene() {
		super();
		//addActor(new Ground("tile.png"));
		world = new World(new Vector2(0, -17), true); 
		debugRenderer = new Box2DDebugRenderer();
	    //debugMatrix=camera.combined.cpy();//new Matrix4(camera.combined);
	    //debugMatrix.scale(1/1000000f, 1/1000000f, 1f);
	    debugMatrix = new OrthographicCamera( 1280/100f, 720/100f ).combined;

	    bg = new SkyBackground("bakgrunn1.png");
		addActor(bg);
		ground = new GameGround("tile1.png",world);
		addActor(ground);
		manBearPig = new ManBearPig("mannbarschwein.png",world,this); 
		manBearPig.x=1092;
		manBearPig.y=200;
		addActor(manBearPig);
		obst = new Obstacle("skog.png", MorphMode.BEAR, manBearPig);
		obst.x=-280;
		obst.y=90;
		addActor(obst);
		Pickup pickup = new Pickup("pickup1.png", MorphMode.MAN, manBearPig, world, -600, 150);
		addActor(pickup);
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
	}
	@Override
	public void render(float delta){
		super.render(delta);
		if(manBearPig.isAlive){
	        world.step( delta, 8, 3 );
	        //debugRenderer.render(world, debugMatrix);
			obst.step();
			bg.step();
			ground.step();
		}
	}
}
