package net.peterme.agj;

public class TestScene extends Scene {
	private GameObject image;
	public TestScene(){
		image = new TestActor("badlogic.jpg");
		addActor(image);
	}
}
