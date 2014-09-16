package net.peterme.agj;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class TextureHandler {
	//private ArrayList<String> handledTextures;
	private Map<String, HandledTexture> handledTextures;
	public class HandledTexture extends Texture{
		int count;
		public HandledTexture(FileHandle file) {
			super(file);
			count=1;
		}
		@Override
		public void dispose(){
			count--;
			if(count==0){
				super.dispose();
			}
		}
	}
	public HandledTexture loadTexture(String filename){
		if(handledTextures.containsKey(filename)){
			handledTextures.get(filename).count++;
		} else {
			handledTextures.put(filename, new HandledTexture(Gdx.files.internal(filename)));
		}
		return handledTextures.get(filename);
	}
}
