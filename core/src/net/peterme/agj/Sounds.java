package net.peterme.agj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
	public Sound[] barrier;
	public Sound[] coin;
	public Sound[] jump;
	public Sound die;
	public Sound shift;
	public Music music;
	public Sounds(){
		barrier = new Sound[3];
		coin = new Sound[3];
		jump = new Sound[3];
		barrier[0]=Gdx.audio.newSound(Gdx.files.internal("sound/barrier1.mp3"));
		barrier[1]=Gdx.audio.newSound(Gdx.files.internal("sound/barrier2.mp3"));
		barrier[2]=Gdx.audio.newSound(Gdx.files.internal("sound/barrier3.mp3"));
		coin[0]=Gdx.audio.newSound(Gdx.files.internal("sound/coin1.mp3"));
		coin[1]=Gdx.audio.newSound(Gdx.files.internal("sound/coin2.mp3"));
		coin[2]=Gdx.audio.newSound(Gdx.files.internal("sound/coin3.mp3"));
		jump[0]=Gdx.audio.newSound(Gdx.files.internal("sound/jump-man.mp3"));
		jump[1]=Gdx.audio.newSound(Gdx.files.internal("sound/jump-bear.mp3"));
		jump[2]=Gdx.audio.newSound(Gdx.files.internal("sound/jump-pig.mp3"));
		die=Gdx.audio.newSound(Gdx.files.internal("sound/die.mp3"));
		shift=Gdx.audio.newSound(Gdx.files.internal("sound/shift.mp3"));
		music=Gdx.audio.newMusic(Gdx.files.internal("sound/music.mp3"));
	}
}
