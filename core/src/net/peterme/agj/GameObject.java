package net.peterme.agj;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;

public class GameObject extends Actor {
	Event created;
	public GameObject(){
		created = new GameEvent(GameEvent.Type.CREATED);
		fire(created);
		//textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
        //animation = new Animation(1/15f, textureAtlas.getRegions());
	}
	@Override
	public void draw(Batch batch, float alpha){
        //batch.draw(texture,0,0);
    }
    @Override
    public void act(float delta){
		fire(new GameEvent(GameEvent.Type.ACT));
		fire(new GameEvent("acting"));
    }
}
