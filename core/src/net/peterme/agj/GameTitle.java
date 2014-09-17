package net.peterme.agj;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameTitle extends GameObject {
	public TextureRegion[] titles;
	private GameScene scene;
	private int currentTexture;

	public GameTitle(TextureAtlas textures, GameScene scene) {
		super(textures,scene.sounds);
		this.scene = scene;
		titles = new TextureRegion[3];
		titles[0] = loadImage("Intro/MANN");
		titles[1] = loadImage("Intro/BAR");
		titles[2] = loadImage("Intro/SCHWEIN");
		texture = titles[0];
		currentTexture = 0;
		//scaleX=scaleY=0.8f;
		x=(int) (scene.viewport.getWorldWidth()/2-texture.getRegionWidth()*scaleX/2);
		y=(int) (scene.viewport.getWorldHeight()/2-texture.getRegionHeight()*scaleY/2);
	}
	public void switchText(){
		texture = titles[++currentTexture%3];
		x=(int) (scene.viewport.getWorldWidth()/2-texture.getRegionWidth()*scaleX/2);
		y=(int) (scene.viewport.getWorldHeight()/2-texture.getRegionHeight()*scaleY/2);
	}
}
