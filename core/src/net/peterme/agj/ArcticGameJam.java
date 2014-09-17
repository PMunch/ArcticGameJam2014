package net.peterme.agj;


import net.peterme.agj.LevelParser.Level;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ArcticGameJam extends Game {
	private Scene currentScene;
	private Scene toDestroy;
	private GameEventListener restartGame;
	private Level level;
	//private AssetManager manager;
	private TextureAtlas textures; 
	private int attempts=0;
	private Sounds sounds;
	//private boolean loaded = false;
	
	@Override
	public void create () {
		level = new LevelParser("level1.js").getLevel();
		/*manager = new AssetManager();
		manager.load("bakgrunn1.png",Texture.class);
		manager.load("fjell1.png",Texture.class);
		manager.load("fjell2.png",Texture.class);
		manager.load("fjell3.png",Texture.class);
		manager.load("fjell4.png",Texture.class);
		manager.load("tre1.png",Texture.class);
		manager.load("tre2.png",Texture.class);
		manager.load("tre3.png",Texture.class);
		manager.load("tre4.png",Texture.class);
		manager.load("tile1.png",Texture.class);
		manager.load("obstacle1.png",Texture.class);
		manager.load("obstacle2.png",Texture.class);
		manager.load("mannbarschwein.png",Texture.class);
		manager.load("pickup1.png",Texture.class);
		manager.load("pickup2.png",Texture.class);
		manager.load("pickup3.png",Texture.class);
		manager.load("sump4.png",Texture.class);
		manager.load("gate.png",Texture.class);
		manager.load("skog.png",Texture.class);
		manager.finishLoading();*/
		sounds=new Sounds();
		sounds.music.play();
		sounds.music.setLooping(true);
		textures = new TextureAtlas("atlas/pack.atlas");
		restartGame = new GameEventListener(){
			@Override
			public boolean onRestartGame(GameEvent event){
				attempts++;
				toDestroy = currentScene;
				currentScene = new GameScene(level,textures,attempts,sounds);
				currentScene.addListener(restartGame);
				Gdx.input.setInputProcessor(currentScene.stage);
				setScreen(currentScene);
				return true;
			}
		};
		currentScene = new GameScene(level,textures,attempts,sounds);
		currentScene.addListener(restartGame);
		Gdx.input.setInputProcessor(currentScene.stage);
		/*currentScene.addListener(new GameEventListener(){
			@Override
			public boolean onStartGame(GameEvent event){
				//currentScene.dispose();
				toDestroy = currentScene;
				currentScene = new GameScene(level,textures);
				currentScene.addListener(restartGame);
				Gdx.input.setInputProcessor(currentScene.stage);
				return true;
			}
		});*/
		setScreen(currentScene);
	}

	@Override
	public void render () {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			getScreen().render(Gdx.graphics.getDeltaTime());
			if(toDestroy!=null){
				toDestroy.dispose();
				toDestroy=null;
			}
	}
}
