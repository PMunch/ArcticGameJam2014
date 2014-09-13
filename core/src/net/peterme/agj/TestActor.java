package net.peterme.agj;

import javax.sound.midi.Sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;

public class TestActor extends GameObject implements InputProcessor {
    
	public float bgPos = 0;
	public float currentBGScale = 1;
	public float currentBGScaleDirection = 0.01f;
	public TiledMap map;
	public Texture bgTexture;
	
	public OrthogonalTiledMapRenderer mapRenderer;
	//public OrthographicCamera camera;
	
	public TestActor(String image) {
		super(image);
		map = new TmxMapLoader().load("level.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		bgTexture = new Texture(Gdx.files.internal("menu-bg-tile.png"));
		//Gdx.input.setInputProcessor(this);
	}
	@Override
    public void draw(Batch batch, float alpha){
		
    	for( int x = -1; x < getStage().getViewport().getWorldWidth()/bgTexture.getWidth()+2; x++ ) {
    		for( int y = -1; y < getStage().getViewport().getWorldHeight()/bgTexture.getHeight()+2; y++) {
    			batch.draw(bgTexture,x * bgTexture.getWidth() + bgPos,y * bgTexture.getHeight() + bgPos, bgTexture.getWidth() * currentBGScale, bgTexture.getHeight() * currentBGScale);
    		}
    	}
    	//batch.draw(texture, getStage().getViewport().getWorldWidth()/2 - texture.getWidth()/2, getStage().getViewport().getWorldHeight()/2 - getHeight()/2);
    	//mapRenderer.setView();
    	//mapRenderer.render();
    }
    @Override
    public void act(float delta){
    	super.act(delta);
    	bgPos+=3;
    	//if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
    		currentBGScale+=currentBGScaleDirection;
    	
	    	if(currentBGScale > 2) {
	    		currentBGScale = 2;
	    		currentBGScaleDirection = -currentBGScaleDirection;
	    	} else if(currentBGScale <1 ) { 
	    		currentBGScale = 1;
	    		currentBGScaleDirection = -currentBGScaleDirection;
	    	}
    //	}
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
		Gdx.app.log("Test", "touched");
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
