package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class GameEventListener implements EventListener{
	@Override
	public boolean handle(Event e) {
		if (!(e instanceof GameEvent)) return false;
		GameEvent event = (GameEvent)e;
		switch(event.type){
		case CREATED:
			return onCreate(event);
		case ACT:
			Gdx.app.log("AGJ", "Actor acting ");
			break;
		case KIMEVENT:
			return onKimEvent((String) event.payload);
		default:
			break;
		}
		return true;
	}
	
	public boolean onKimEvent(String payload) {
		return false;
	}

	public boolean onCreate(GameEvent event) {
		return false;
	}
}
