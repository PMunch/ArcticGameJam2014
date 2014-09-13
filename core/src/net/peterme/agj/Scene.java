package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Scene extends Group implements Screen {
	public Stage stage;
	public Camera camera;
	public Viewport viewport;
	
	public Scene(){
		camera = new OrthographicCamera();
	    viewport = new FitViewport(1280, 720, camera);
		stage = new Stage(viewport);
		stage.addActor(this);
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

	@Override
	public void dispose() {
		stage.dispose();
		this.clear();
		this.dispose();
	}

}
