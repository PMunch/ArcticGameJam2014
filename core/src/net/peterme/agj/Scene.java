package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Scene /*extends Group*/ implements Screen {
	public Stage stage;
	public Camera camera;
	public Viewport viewport;
	//public Group sceneGroup;
	
	public Scene(){
		camera = new OrthographicCamera();
	    viewport = new FitViewport(1280, 720, camera);
		stage = new Stage(viewport);
		//sceneGroup = new Group();
		//stage.addActor(sceneGroup);
		/*sceneGroup.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
	                int pointer, int button){
				Gdx.app.log("Test","Event: "+event.getTarget());
				return true;
			}
		});*/
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
/*
	public void dispose() {
		stage.dispose();
		for(Actor child:getChildren()){
			((GameObject)child).dead=true;
		}
		//this.clear();
		//this.dispose();
	}
*/

	@Override
	public void dispose() {
		/*for(Actor child:stage.getActors()){
			((GameObject)child).dead=true;
		}*/
		stage.dispose();
	}
	
	public void addActor(GameObject actor){
		//sceneGroup.addActor(actor);
		stage.addActor(actor);
	}
	public Stage getStage(){
		return stage;
	}
	/*public void fire(Event event){
		//sceneGroup.fire(event);
	}*/
	public void addListener(GameEventListener gameEventListener){
		//sceneGroup.addListener(gameEventListener);
		stage.addListener(gameEventListener);
	}
/*
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}*/
}
