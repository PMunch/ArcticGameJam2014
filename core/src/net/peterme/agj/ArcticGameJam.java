package net.peterme.agj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ArcticGameJam extends ApplicationAdapter {
	private Actor image;
	private Stage stage;
	private Viewport viewport;
	private Camera camera;
	
	@Override
	public void create () {
		image = new TestActor();
		camera = new OrthographicCamera();
	    viewport = new FitViewport(1280, 720, camera);
		stage = new Stage(viewport);
		stage.addActor(image);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}
