package net.peterme.agj;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;


public class Ground extends GameObject {
	private List<Integer> ground;
	private Texture underGround;
	private float groundSpeed = 0.2f;
	private float groundOffset=1f;
	private int lastHeight = 8;
	private Random rand = new Random();
	public Ground(String image) {
		super(image);
		underGround = loadImage("tile2.png");
		ground = new ArrayList();//new int[41];
		for(int i = 0; i<41; i++){
			//ground[i]=lastHeight;
			ground.add(getNewHeight());
		}
	}
	@Override
	public void draw(Batch batch, float alpha){
		for(int i=0;i<ground.size();i++){
			batch.draw(texture,(i-groundOffset)*32,ground.get(i)*32);
			for(int j=0;j<ground.get(i);j++){
				batch.draw(underGround,(i-groundOffset)*32,j*32);
			}
		}
	}
	@Override
	public void act(float delta){
		super.act(delta);
		groundOffset-=groundSpeed;
		if(groundOffset<0){
			groundOffset+=1;
			ground.remove(ground.size()-1);
			ground.add(0, getNewHeight());
		}
	}
	private int getNewHeight(){
		if(rand.nextInt(100)<20){
			if(rand.nextBoolean()){
				if(lastHeight<20)
					lastHeight++;
				else
					lastHeight--;
			}else{
				if(lastHeight>1)
					lastHeight--;
				else
					lastHeight++;
			}
		}
		return lastHeight;
	}
}
