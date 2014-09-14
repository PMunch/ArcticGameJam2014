package net.peterme.agj;

import net.peterme.agj.ManBearPig.MorphMode;

public class PigGate extends Obstacle {

	public PigGate(ManBearPig player) {
		super("sump4.png", MorphMode.PIG, player);
		y=50;
	}
}
