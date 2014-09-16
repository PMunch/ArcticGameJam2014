package net.peterme.agj;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ExampleActor extends GameObject {
	public Texture texture;
	public ExampleActor(String image,TextureAtlas textures){
		super(image, textures);
	}
}