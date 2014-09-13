package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


public class IntroScene extends Scene{
	Logo logo;
	public IntroScene() {
		addActor(new Background("menu-bg-tile.png"));
		logo = new Logo("logo.png");
		logo.x = (int) (stage.getViewport().getWorldWidth()/2 - logo.texture.getWidth()/2);
		logo.y = (int) (stage.getViewport().getWorldHeight()/2 - logo.texture.getHeight()/2);
		addActor(logo);
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
}
