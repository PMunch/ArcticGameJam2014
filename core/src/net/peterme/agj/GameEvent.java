package net.peterme.agj;

import com.badlogic.gdx.scenes.scene2d.Event;

public class GameEvent extends Event{
	public enum Type {
		CREATED,
		ACT,
		KIMEVENT
	}
	public Type type;
	public Object payload;
	
	public GameEvent(Type type, Object payload){
		super();
		this.type = type;
		this.payload = payload;
	}
	public GameEvent(Type type){
		super();
		this.type = type;
		this.payload = null;
	}
	public GameEvent(String type){
		super();
		this.type = GameEvent.Type.KIMEVENT;
		this.payload = type;
	}
}
