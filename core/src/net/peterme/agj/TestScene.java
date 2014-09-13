package net.peterme.agj;

public class TestScene extends Scene {
	private GameObject image;
	public TestScene(){
		image = new TestActor("logo.png");
		addActor(image);
	}
}
