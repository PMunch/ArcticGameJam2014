package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;


public class IntroScene extends Scene{
	Logo logo;
	public IntroScene(TextureAtlas textures) {
		super(textures);
		addActor(new Background("menu-bg-tile.png", textures));
		/*logo = new Logo("logo.png", textures);
		logo.x = (int) (stage.getViewport().getWorldWidth()/2 - logo.texture.getWidth()/2);
		logo.y = (int) (stage.getViewport().getWorldHeight()/2 - logo.texture.getHeight()/2);
		addActor(logo);
		logo.setBounds();
		logo.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
	                int pointer, int button){
				logo.fire(new GameEvent(GameEvent.Type.STARTGAME));
				return true;
			}
		});*/
	}

	/*@Override
	public boolean touchDown(Event ev,int screenX, int screenY, int pointer, int button) {
		//Gdx.app.log("Tag", "sx: "+screenX+" sy: "+screenY+" lx: "+logo.x+" ly: "+logo.y+" w: "+logo.getWidth()+" h: "+logo.getHeight());
		Gdx.app.log("Touched","Touched: "+logo.hit(screenX,screenY,false));
		
		/*if(sceneGroup.hit(screenX, screenY, true)==logo){
			Gdx.app.log("Touched", "Touching logo");
		}
		if(logo.touched(screenX, screenY)){
			//fire(new GameEvent(GameEvent.Type.STARTGAME));
			return true;
		}
		return false;
	}*/
}
