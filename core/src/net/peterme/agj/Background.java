package net.peterme.agj;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class Background extends GameObject {
	public float bgPos = 0;
	public float currentBGScale = 1;
	public float currentBGScaleDirection = 0.01f;
	
	public Background(String backgroundImage,TextureAtlas textures){
		super(backgroundImage,textures);
	}
	@Override
	public void draw(Batch batch, float alpha){
		for( int x = -1; x < getStage().getViewport().getWorldWidth()/texture.getRegionWidth()+2; x++ ) {
    		for( int y = -1; y < getStage().getViewport().getWorldHeight()/texture.getRegionHeight()+2; y++) {
    			batch.draw(texture,x * texture.getRegionWidth() + bgPos,y * texture.getRegionHeight() + bgPos, texture.getRegionWidth() * currentBGScale, texture.getRegionHeight() * currentBGScale);
    		}
    	}
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
    	bgPos = bgPos % texture.getRegionHeight();
    }
}
