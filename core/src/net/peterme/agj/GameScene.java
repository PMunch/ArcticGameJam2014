package net.peterme.agj;

import java.util.ArrayList;

import net.peterme.agj.LevelParser.Entity;
import net.peterme.agj.LevelParser.Level;
import net.peterme.agj.ManBearPig.MorphMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameScene extends Scene {
	private ManBearPig manBearPig;
	//private World world;
	//private Box2DDebugRenderer debugRenderer;
	//private Matrix4 debugMatrix;
	public SkyBackground bg;
	public GameGround ground;
	//private Obstacle obst;
	public ArrayList<Obstacle> obstacles;
	//private Pickup pickup;
	public ArrayList<Pickup> pickups;
	//private Barrier barrier;
	public float score = 0;
	//public float groundSpeed = 1.8f;
	public float groundSpeed = 1.8f;
	//private int levelProgress = 0;
	//private float waitTime = -1f;
	private Preferences prefs = Gdx.app.getPreferences("preferences");
	public int highScore = prefs.getInteger("HIGHSCORE",0);
	private GameTitle title;
	private int attempts;
	public Sounds sounds;
	public GameScene(Level level, TextureAtlas textures, int attempts,Sounds sounds) {
		super(textures);
		this.attempts=attempts;
		this.sounds = sounds;
		//Json json = new Json();
		//Level level = json.fromJson(Level.class, Gdx.files.internal("level1.js"));
	    bg = new SkyBackground("Background/Scenery/bakgrunn1",this);
		//ground = new GameGround(level.layer.get(1).data,level.layer.get(1).width,this);
	    int levelWidth = level.getGround().width;
	    ground = new GameGround(level.getGround().data,levelWidth,this); 
		manBearPig = new ManBearPig("Main/mannbarschwein",this,textures,sounds);
		//manBearPig.x=1092;
		//manBearPig.y=120;
		manBearPig.setPosition(1022, 120);
		//stage.addActor(partSystem);
		addActor(bg);
		addActor(new Clouds(textures,sounds));
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
					gate.setPosition((entity.x+4*45)-(levelWidth*45-1280),
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
					pickup.setPosition((entity.x)-(levelWidth*45-1280),
									(int) (720-(entity.y+pickup.getHeight())));
					pickups.add(pickup);
					addActor(pickup);
				}
			}
		}
		//for(Obstacle obst:obstacles)
		//Gdx.app.log("Obstacles", ""+obstacles.get(0).x);
		stage.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keyCode){
				if(keyCode==Input.Keys.UP){
					manBearPig.willJump=true;
					return true;
				}else if(keyCode==Input.Keys.DOWN){
					manBearPig.morph();
					return true;
				}
				return false;
			}
			@Override
			public boolean keyUp(InputEvent event, int keyCode){
				if(keyCode==Input.Keys.UP){
					manBearPig.willJump=false;
					return true;
				}
				return false;
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				if(button==Input.Buttons.LEFT){
					if(x>stage.getWidth()/2){
						manBearPig.willJump=true;
					}else{
						manBearPig.morph();
					}
				}
				return false;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				if(button==Input.Buttons.LEFT){
					if(x>stage.getWidth()/2){
						manBearPig.willJump=false;
					}
				}
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
		addActor(new ScoreBoard(this,attempts));
		if(attempts==0){
			groundSpeed=0;
		}else{
			groundSpeed=1.8f;
		}
	}
	@Override
	public void render(float delta){
		super.render(delta);
		if(title==null && attempts==0 && delta<0.1){
			title = new GameTitle(textures,this);
			float switchDelay = 0.5f;
			Runnable switchText = new Runnable(){
				@Override
	            public void run() {
	                title.switchText();
	                manBearPig.morph();
	            }
			};
			title.addAction(sequence(new DelayAction(switchDelay),
									run(switchText),
									new DelayAction(switchDelay),
									run(switchText),
									new DelayAction(switchDelay),
									run(new Runnable(){
									@Override
									public void run() {
										title.dead=true;
										groundSpeed=1.8f;
										manBearPig.morph();
									}
			})));
			addActor(title);
		}
		/*ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(manBearPig.grounded?Color.BLACK:Color.RED);
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeType.Line);
        for(Actor actor:stage.getActors()){
        	if(actor instanceof Particle){
        		Particle part = (Particle) actor;
        		sr.rect(part.collisionRect.x,part.collisionRect.y,part.collisionRect.width,part.collisionRect.height);
        	}
        }
        sr.end();*/
        /*sr.rect(manBearPig.collisionRect.x,manBearPig.collisionRect.y,manBearPig.collisionRect.width,manBearPig.collisionRect.height);
        int i=28;
        for(Rectangle[] rectL:ground.colRects){
        	for(Rectangle rect:rectL){
        		if(rect!=null)
        			sr.rect((i+ground.groundOffset)*45,rect.y,rect.width,rect.height);
        	}
        	i--;
        }
        for(int i=3;i<6;i++){
			for(int j=0;j<16;j++){
			//for(Rectangle rect:colRects.get(i)){
				Rectangle rect = ground.colRects.get(i)[j];
				if(rect!=null)
					sr.rect(1280-(i+ground.groundOffset)*45,rect.y,rect.width,rect.height);
			}
        }
        sr.end();*/
		/*if(manBearPig.isAlive){
		Gdx.app.log("Delta", ""+delta);
		}*/
		//float delta = 1/(60*6);
		//float delta = delta2;
		//if(levelProgress!=-1){
		if(delta<0.1f && attempts!=0){
			groundSpeed=1.8f;
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
				score+=delta*groundSpeed*50;
				//levelProgress+=delta*groundSpeed*60*6;
			}else{
				if(score>highScore){
					highScore=(int) score;
					prefs.putInteger("HIGHSCORE", highScore);
					prefs.flush();
				}
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
