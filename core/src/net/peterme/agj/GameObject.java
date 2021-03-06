package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {
	public Boolean dead = false;
	public TextureRegion texture;
	//public TextureAtlas textureAtlas;
	public Animation animation;
	public int x;
	public int y;
	public int originX = 0;
	public int originY = 0;
	public float rotation = 0;
	public float scaleX = 1;
	public float scaleY = 1;
	public float elapsedTime;
	public TextureAtlas textures;
	public Sounds sounds;
	
	public GameObject(TextureAtlas textures,Sounds sounds){
		this.textures=textures;
		this.sounds=sounds;
	}
	public GameObject(String image,TextureAtlas textures,Sounds sounds){
		this.textures=textures;
		texture = loadImage(image);
		this.sounds=sounds;
	}
	/*public GameObject(String atlas,String sprite,float framerate,TextureAtlas textures,Sounds sounds){
		this.textures=textures;
		this.sounds=sounds;
		textureAtlas = new TextureAtlas(Gdx.files.internal(atlas));
        animation = new Animation(1f/framerate, textureAtlas.findRegions(sprite));
	}*/
	
		//textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
        //animation = new Animation(1/15f, textureAtlas.getRegions());
	//}
	public TextureRegion loadImage(String image){
		//return new HandledTexture(image);
		//return TextureHandler.loadTexture(image);
		//if(image.equals("pickup1.png"))
		//	Gdx.app.log("Test", ""+manager.getReferenceCount("pickup1.png")); 
		//manager.setReferenceCount(image,manager.getReferenceCount(image)+1);
		//return manager.get(image,Texture.class);
		return textures.findRegion(image);//.getTexture();
	}
	@Override
	public void draw(Batch batch, float alpha){
        //batch.draw(texture.region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
		if(texture != null)
			batch.draw(texture, x, y, originX, originY, texture.getRegionWidth(), texture.getRegionHeight(), scaleX, scaleY, rotation);
			//batch.draw(texture,x,y,texture.getRegionWidth(),texture.getRegionHeight());
			//batch.draw(texture,x,y,texture.getRegionWidth(),texture.getRegionHeight());
		if(animation != null){
			batch.draw(animation.getKeyFrame(elapsedTime),getX(),getY());
			//Gdx.app.log("Animation", "ElapsedTime: "+elapsedTime);
		}
    }
    @Override
    public void act(float delta){
    	super.act(delta);
    	elapsedTime+=Gdx.graphics.getDeltaTime();
		if(dead){
			/*if(textureName!=null){
				manager.unload(textureName);
				manager.setReferenceCount(textureName,manager.getReferenceCount(textureName)-1);
				textureName=null;
			}*/
				//texture.dispose();
			//if(textureAtlas!=null)
			//	textureAtlas.dispose();
			clear();
			remove();
		}
    }
    @Override
    public float getWidth(){
    	if(texture!=null)
    		return texture.getRegionWidth();
    	else
    		return 0;
    }
    @Override
    public float getHeight(){
    	if(texture!=null)
    		return texture.getRegionHeight();
    	else
    		return 0;
    }
    /*public boolean touched(int screenX, int screenY){
    	int tapX;
    	int tapY;
    	tapX=(int) (screenX/(getStage().getViewport().getScreenWidth()/getStage().getViewport().getWorldWidth()));
    	tapY=(int) (screenY/(getStage().getViewport().getScreenHeight()/getStage().getViewport().getWorldHeight()));
    	//Gdx.app.log("Test", "tx: "+tapX+" ty: "+tapY+" x: "+x+" y: "+y+" h: "+getHeight()+" w: "+getWidth());
    	if(tapX>x && tapX<x+getWidth() && tapY>y && tapY<y+getHeight()){
			return true;
		}
    	return false;
    }*/
    public void setBounds(){
		setBounds(x, y, getWidth(), getHeight());
    }
}
