package net.peterme.agj;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SkyBackground extends GameObject {
	private TextureRegion[] textures;
	public TextureRegion[] backdrops;
	//private BgImage[] bgImages;
	private ArrayList<BgImage> bgImages;
	private Random rand;
	private GameScene scene;
	private float[] weights;
	private class BgImage{
		public int texture;
		public float depth;
		public float scale;
		public float x;
	}
	public SkyBackground(String image,GameScene scene) {
		super(image,scene.textures,scene.sounds);
		this.scene=scene;
		backdrops = new TextureRegion[3];
		backdrops[0]=loadImage("Intro/bakgrunn1");
		backdrops[1]=loadImage("Intro/bakgrunn2");
		backdrops[2]=loadImage("Background/bakgrunn3");
		texture = backdrops[0];
		textures=new TextureRegion[7];
		textures[0]=loadImage("Background/fjell4");
		textures[1]=loadImage("Background/fjell3");
		textures[2]=loadImage("Background/fjell2");
		textures[3]=loadImage("Background/tre1");
		textures[4]=loadImage("Background/tre2");
		textures[5]=loadImage("Background/tre3");
		textures[6]=loadImage("Background/tre4");
		weights = new float[7];
		weights[0]=0.07f;
		weights[1]=0.1f;
		weights[2]=0.1f;
		weights[3]=0.13f;
		weights[4]=0.2f;
		weights[5]=0.2f;
		weights[6]=0.2f;
		bgImages = new ArrayList<BgImage>();
		rand = new Random();
		for(int i=0;i<14;i++){
			addNewBgImage();
		}
		for(int i=0;i<bgImages.size();i++){
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
			batch.draw(reg, bgImage.x, 45f, 0, 0, tex.getRegionWidth(), tex.getRegionHeight(), 1f/bgImage.scale, 1f/bgImage.scale,0);
		}
	}
	private void addNewBgImage(){
		BgImage bgImage = new BgImage();
		float num = rand.nextFloat();
		int i = -1;
		while(num>0f){
			i++;
			num-=weights[i];
		}
		bgImage.texture = i;
		bgImage.depth = bgImage.texture;
		bgImage.scale = 1+rand.nextFloat()*2;
		bgImage.x = -textures[bgImage.texture].getRegionWidth()*(1f/bgImage.scale);//+rand.nextInt(1280);
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
