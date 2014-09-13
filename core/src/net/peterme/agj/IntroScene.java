package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


public class IntroScene extends Scene implements InputProcessor {
	Logo logo;
	public IntroScene() {
		addActor(new Background("menu-bg-tile.png"));
		logo = new Logo("logo.png");
		logo.x = (int) (getStage().getViewport().getWorldWidth()/2 - logo.texture.getWidth()/2);
		logo.y = (int) (getStage().getViewport().getWorldHeight()/2 - getHeight()/2);
		addActor(logo);
		Gdx.input.setInputProcessor(this);
	}

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
		//Gdx.app.log("Tag", "sx: "+screenX+" sy: "+screenY+" lx: "+logo.x+" ly: "+logo.y+" w: "+logo.getWidth()+" h: "+logo.getHeight());
		if(logo.touched(screenX, screenY)){
			fire(new GameEvent(GameEvent.Type.STARTGAME));
			return true;
		}
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
	}
}
