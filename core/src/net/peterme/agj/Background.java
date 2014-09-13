package net.peterme.agj;
import com.badlogic.gdx.graphics.g2d.Batch;


public class Background extends GameObject {
	public float bgPos = 0;
	public float currentBGScale = 1;
	public float currentBGScaleDirection = 0.01f;
	
	public Background(String backgroundImage){
		super(backgroundImage);
	}
	@Override
	public void draw(Batch batch, float alpha){
		for( int x = -1; x < getStage().getViewport().getWorldWidth()/texture.getWidth()+2; x++ ) {
    		for( int y = -1; y < getStage().getViewport().getWorldHeight()/texture.getHeight()+2; y++) {
    			batch.draw(texture,x * texture.getWidth() + bgPos,y * texture.getHeight() + bgPos, texture.getWidth() * currentBGScale, texture.getHeight() * currentBGScale);
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
    	bgPos = bgPos % texture.getHeight();
    }
}
