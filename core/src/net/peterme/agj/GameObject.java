package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {
	public Boolean dead = false;
	public Texture texture;
	public TextureAtlas textureAtlas;
	public Animation animation;
	public int x;
	public int y;
	private int elapsedTime;
	
	public GameObject(){
		
	}
	public GameObject(String image){
		texture = new Texture(Gdx.files.internal(image));
	}
	public GameObject(String atlas,String sprite,float framerate){
		textureAtlas = new TextureAtlas(Gdx.files.internal("atlas"));
        animation = new Animation(1f/framerate, textureAtlas.findRegions(sprite));
	}
	
		//textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
        //animation = new Animation(1/15f, textureAtlas.getRegions());
	//}
	public Texture loadImage(String image){
		return new Texture(Gdx.files.internal(image));
	}
	@Override
	public void draw(Batch batch, float alpha){
        //batch.draw(texture.region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
		if(texture != null)
			batch.draw(texture,x,y,texture.getWidth(),texture.getHeight());
		if(textureAtlas != null)
			batch.draw(animation.getKeyFrame(elapsedTime),x,y);
    }
    @Override
    public void act(float delta){
    	super.act(delta);
    	elapsedTime+=Gdx.graphics.getDeltaTime();
		if(dead){
			if(texture!=null)
				texture.dispose();
			if(textureAtlas!=null)
				textureAtlas.dispose();
			clear();
			remove();
		}
    }
    @Override
    public float getWidth(){
    	if(texture!=null)
    		return texture.getWidth();
    	else
    		return 0;
    }
    @Override
    public float getHeight(){
    	if(texture!=null)
    		return texture.getHeight();
    	else
    		return 0;
    }
    public boolean touched(int screenX, int screenY){
    	int tapX;
    	int tapY;
    	tapX=(int) (screenX/(getStage().getViewport().getScreenWidth()/getStage().getViewport().getWorldWidth()));
    	tapY=(int) (screenY/(getStage().getViewport().getScreenHeight()/getStage().getViewport().getWorldHeight()));
    	if(tapX>x && tapX<x+getWidth() && tapY<y && tapY>y-getHeight()){
			return true;
		}
    	return false;
    }
}
