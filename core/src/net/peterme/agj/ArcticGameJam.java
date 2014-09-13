package net.peterme.agj;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ArcticGameJam extends Game {
	private Scene currentScene;
	private Scene toDestroy;
	
	@Override
	public void create () {
		currentScene = new GameScene();
		Gdx.input.setInputProcessor(currentScene.stage);
		currentScene.addListener(new GameEventListener(){
			@Override
			public boolean onStartGame(GameEvent event){
				//currentScene.dispose();
				toDestroy = currentScene;
				currentScene = new GameScene();
				setScreen(currentScene);
				Gdx.input.setInputProcessor(currentScene.stage);
				return true;
			}
		});
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
