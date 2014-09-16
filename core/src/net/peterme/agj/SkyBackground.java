package net.peterme.agj;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SkyBackground extends GameObject {
	private TextureRegion[] textures;
	//private BgImage[] bgImages;
	private ArrayList<BgImage> bgImages;
	private Random rand;
	private GameScene scene;
	private class BgImage{
		public int texture;
		public float depth;
		public float x;
	}
	public SkyBackground(String image,GameScene scene) {
		super(image,scene.textures);
		this.scene=scene;
		textures=new TextureRegion[7];
		textures[0]=loadImage("Background/fjell2");
		textures[1]=loadImage("Background/fjell3");
		textures[2]=loadImage("Background/fjell4");
		textures[3]=loadImage("Background/tre1");
		textures[4]=loadImage("Background/tre2");
		textures[5]=loadImage("Background/tre3");
		textures[6]=loadImage("Background/tre4");
		bgImages = new ArrayList<BgImage>();
		rand = new Random();
		for(int i=0;i<4;i++){
			addNewBgImage();
		}
		for(int i=0;i<4;i++){
			bgImages.get(i).x+=rand.nextInt(1280);
		}
	}
	//@Override
	public void act(float delta){
		super.act(delta);
		for(int i=0;i<bgImages.size();i++){
			BgImage bgImage = bgImages.get(i);
			bgImage.x+=bgImage.depth*delta*50*scene.groundSpeed;
			if(bgImage.x>1280){
				bgImages.remove(i);
				addNewBgImage();
			}
		}
		
		/*if(dead){
			//for(int i=0;i<textures.length;i++)
				//textures[i].dispose();
			manager.unload("fjell2.png");
			manager.unload("fjell3.png");
			manager.unload("fjell4.png");
			manager.unload("tre1.png");
			manager.unload("tre2.png");
			manager.unload("tre3.png");
			manager.unload("tre4.png");
		}*/
	}
	@Override
	public void draw(Batch batch, float alpha){
		super.draw(batch, alpha);
		for(int i=0;i<bgImages.size();i++){
			BgImage bgImage = bgImages.get(i);
			TextureRegion tex = textures[bgImage.texture];
			TextureRegion reg = new TextureRegion(tex);
			batch.draw(reg, bgImage.x, 0f, 0, 0, tex.getRegionWidth(), tex.getRegionHeight(), 1f/bgImage.depth, 1f/bgImage.depth,0);
		}
	}
	private void addNewBgImage(){
		BgImage bgImage = new BgImage();
		//bgImages[i]=new BgImage();
		bgImage.texture = rand.nextInt(4);
		bgImage.depth = 1+rand.nextFloat()*2;
		bgImage.x = -textures[bgImage.texture].getRegionWidth()*(1f/bgImage.depth);//+rand.nextInt(1280);
		int j=0;
		if(bgImages.size()>0){
			while(bgImages.get(j).depth<bgImage.depth){
				j++;
				if(j==bgImages.size())
					break;
			}
		}
		bgImages.add(j<bgImages.size()?j:bgImages.size(),bgImage);
	}
	/*public void step(float delta){
		for(int i=0;i<bgImages.size();i++){
			BgImage bgImage = bgImages.get(i);
			bgImage.x+=bgImage.depth*delta*50;
			if(bgImage.x>1280){
				bgImages.remove(i);
				addNewBgImage();
			}
		}
	}*/
}
