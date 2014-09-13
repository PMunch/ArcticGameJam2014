package net.peterme.agj;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
   ParticleEffect effect;

   public ParticleEffectActor(ParticleEffect effect) {
      this.effect = effect;
      this.effect.start();
      this.effect.setPosition(700+45, 200+45);
   }

   public void draw(SpriteBatch batch, float parentAlpha) {
	   effect.draw(batch);
   }

   public void act(float delta) {
      //super.act(delta);
      effect.update(delta);
   }

   public ParticleEffect getEffect() {
      return effect;
   }
}