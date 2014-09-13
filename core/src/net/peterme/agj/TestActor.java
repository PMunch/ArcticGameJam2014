package net.peterme.agj;

import javax.sound.midi.Sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;

public class TestActor extends GameObject implements InputProcessor {
	//TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
    //Animation animation = new Animation(1/15f, textureAtlas.getRegions());
    
	public float bgPos = 0;
	public float currentBGScale = 1;
	public Texture bgTexture;
	public TestActor(String image) {
		super(image);
		//Texture texture = new Texture(Gdx.files.internal("logo.png"));
		bgTexture = new Texture(Gdx.files.internal("menu-bg-tile.png"));
		//this.setBounds(0,0, texture.getWidth(), texture.getHeight());
		this.setTouchable(Touchable.enabled);
		Gdx.input.setInputProcessor(this);
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
		// TODO Auto-generated method stub
		fire(new GameEvent(GameEvent.Type.STARTGAME));
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
