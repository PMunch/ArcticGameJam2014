package net.peterme.agj;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SkyBackground extends GameObject {
	private Texture[] textures;
	//private BgImage[] bgImages;
	private ArrayList<BgImage> bgImages;
	private Random rand;
	private class BgImage{
		public int texture;
		public float depth;
		public float x;
	}
	public SkyBackground(String image) {
		super(image);
		textures=new Texture[4];
		textures[0]=loadImage("fjell2.png");
		textures[1]=loadImage("fjell3.png");
		textures[2]=loadImage("fjell4.png");
		textures[3]=loadImage("tre1.png");
		bgImages = new ArrayList<BgImage>();
		rand = new Random();
		for(int i=0;i<4;i++){
			addNewBgImage();
		}
		for(int i=0;i<4;i++){
			bgImages.get(i).x+=rand.nextInt(1280);
		}
	}
	@Override
	public void act(float delta){
		super.act(delta);
		if(dead){
			for(int i=0;i<textures.length;i++)
				textures[i].dispose();
		}
	}
	@Override
	public void draw(Batch batch, float alpha){
		super.draw(batch, alpha);
		for(int i=0;i<bgImages.size();i++){
			BgImage bgImage = bgImages.get(i);
			Texture tex = textures[bgImage.texture];
			TextureRegion reg = new TextureRegion(tex);
			batch.draw(reg, bgImage.x, 0f, 0, 0, tex.getWidth(), tex.getHeight(), 1f/bgImage.depth, 1f/bgImage.depth,1);
		}
	}
	private void addNewBgImage(){
		BgImage bgImage = new BgImage();
		//bgImages[i]=new BgImage();
		bgImage.texture = 0;//rand.nextInt(4);
		bgImage.depth = 1+rand.nextFloat()*2;
		bgImage.x = -textures[bgImage.texture].getWidth()*(1f/bgImage.depth);//+rand.nextInt(1280);
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
	public void step(){
		for(int i=0;i<bgImages.size();i++){
			BgImage bgImage = bgImages.get(i);
			bgImage.x+=bgImage.depth;
			if(bgImage.x>1280){
				bgImages.remove(i);
				addNewBgImage();
			}
		}
	}
}
