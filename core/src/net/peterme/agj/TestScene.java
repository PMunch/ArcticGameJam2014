package net.peterme.agj;

import com.badlogic.gdx.Gdx;

public class TestScene extends Scene {
	private GameObject image;
	public TestScene(){
		image = new TestActor("logo.png");
		addActor(image);
	}
}
