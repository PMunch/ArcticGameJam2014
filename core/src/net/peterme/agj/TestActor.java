package net.peterme.agj;

import javax.sound.midi.Sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;

public class TestActor extends Actor {
	Texture texture = new Texture(Gdx.files.internal("logo.png"));
	Texture bgTexture = new Texture(Gdx.files.internal("menu-bg-tile.png"));
	//TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
    //Animation animation = new Animation(1/15f, textureAtlas.getRegions());
    
	public float bgPos = 0;
	public float currentBGScale = 1;
	public TestActor() {
		
	}
	@Override
    public void draw(Batch batch, float alpha){
    	for( int x = -1; x < getStage().getWidth()/bgTexture.getWidth()+2; x++ ) {
    		for( int y = -1; y < getStage().getHeight()/bgTexture.getHeight()+2; y++) {
    			batch.draw(bgTexture,x * bgTexture.getWidth() + bgPos,y * bgTexture.getHeight() + bgPos, bgTexture.getWidth() * currentBGScale, bgTexture.getHeight() * currentBGScale);
    		}
    	}
    	batch.draw(texture, getStage().getWidth()/2 - texture.getWidth()/2, getStage().getHeight()/2 - getHeight()/2);
    }
    @Override
    public void act(float delta){
    	super.act(delta);
    	bgPos+=3;
    	
    	bgPos = bgPos % bgTexture.getHeight();
    }
}
