package net.peterme.agj;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class Rumble {

  public float time;
  Random random;
  float x, y;
  float current_time;
  float power;
  float current_power;
  private Scene scene;
  private float startX;
  private float startY;

  public Rumble(){
    time = 0;
    current_time = 0;
    power = 0;
    current_power = 0;
  }
  
  // Call this function with the force of the shake 
  // and how long it should last      
  public void rumble(float power, float time, Scene scene) {
    random = new Random();
    this.power = power;
    this.time = time;
    this.current_time = 0;
    this.scene = scene;
    startX = 640;//scene.camera.position.x;
    startY = 360;//scene.camera.position.y;
	Gdx.input.vibrate((int)(time*200));
  }
        
  public void tick(float delta){
      // GameController contains the camera
      // Hero is the character centre screen
    
    if(current_time <= time) {
      scene.camera.translate(x,y,0);
      current_power = power * ((time - current_time) / time);
      // generate random new x and y values taking into account
      // how much force was passed in
      x = (random.nextFloat() - 0.5f) * 2 * current_power;
      y = (random.nextFloat() - 0.5f) * 2 * current_power;
      
      // Set the camera to this new x/y position           
      scene.camera.translate(-x, -y, 0);
      current_time += delta;
    } else {
      // When the shaking is over move the camera back to the hero position
      scene.camera.position.x = startX;
      scene.camera.position.y = startY;
    }
  }      
}
/*
// Creating a new instance of the class
public Rumble  rumble;
this.rumble = new Rumble();

// When there is time left on the shake call the main function
if (gameController.rumble.time  > 0){
  gameController.rumble.tick(delta, gameController, hero);
}

// setting the power and time for a new RUMBLE!
if (line.tension > line.max_tension){	// SNAP
  gc.rumble.rumble(.2f, .1f); 
  state = STATE.IDLE;
}*/

