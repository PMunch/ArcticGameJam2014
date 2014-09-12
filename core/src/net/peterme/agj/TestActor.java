package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TestActor extends Actor {
	Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
	//TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
    //Animation animation = new Animation(1/15f, textureAtlas.getRegions());
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,0,0);
    }
    @Override
    public void act(float delta){
    	
    }
}
