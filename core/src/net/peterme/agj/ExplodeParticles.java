package net.peterme.agj;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ExplodeParticles extends Actor {
	public ExplodeParticles(GameScene scene,TextureAtlas textures, String image, int x, int y,int xSpeed, int ySpeed,GameGround ground){
		Random rand = new Random();
		int particles = 40+rand.nextInt(10);
		for(int i=0;i<particles;i++){
			//scene.stage.addActor(new Particle(image, textures, x, y,2+5-rand.nextInt(10),5-rand.nextInt(10), 0.6f+rand.nextFloat(),rand.nextFloat()/1.5f));
			scene.stage.addActor(new Particle(image, textures, x, y,xSpeed+(int)(Math.cos(6.28f/particles*i)*(3+rand.nextInt(4))),ySpeed+(int)(Math.sin(6.28f/particles*i)*(3+rand.nextInt(4))), 0.6f+rand.nextFloat(),rand.nextFloat()/1.5f,ground));
		}
		clear();
		remove();
	}
}
