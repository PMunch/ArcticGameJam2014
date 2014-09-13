package net.peterme.agj;

public class TestScene extends Scene {
	private GameObject image;
	public TestScene(){
		image = new ExampleActor("badlogic.jpg");
		addActor(image);
	}
}
