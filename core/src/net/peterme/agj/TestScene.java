package net.peterme.agj;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TestScene extends Scene {
	private GameObject image;
	public TestScene(TextureAtlas textures){
		super(textures);
		image = new TestActor("logo.png",textures);
		addActor(image);
	}
}
