package net.peterme.agj;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class LevelParser {
	private static class JSONLevel{
		private ArrayList<JSONEntity> entities;
		private ArrayList<JSONLayer> layer;
	}
	private static class JSONEntity{
		private String type;
		private int x;
		private int y;
		private JSONSettings settings;
	}
	private static class JSONSettings{
		private int particleType;
		private int jumpSpeed;
	}
	private static class JSONLayer{
		private String name;
		private int width;
		private int height;
		private boolean linkWithCollision;
		private int visible;
		private String tilesetName;
		private boolean repeat;
		private boolean preRender;
		private int distance;
		private int tilesize;
		private boolean foreground;
		private int[][] data;
	}
	public static class Level{
		//public int[][] data;
		public Ground ground;
		public ArrayList<Entity> entities;
		
		public ArrayList<Entity> getEntities(){
			return entities;
		}
		public Ground getGround(){
			return ground;
		}
	}
	public static class Entity{
		public int type;
		public int subType;
		public int x;
		public int y;
	}
	public static class Ground{
		public int[][] data;
		public int width;
		public int height;
	}
	//JSONLevel level;
	Level level;
	public LevelParser(String levelFile){
		Json json = new Json();
		JSONLevel jsonlevel = json.fromJson(JSONLevel.class, Gdx.files.internal(levelFile));
		level = new Level();
		level.ground=new Ground();
		level.ground.data=jsonlevel.layer.get(1).data;
		level.ground.height=jsonlevel.layer.get(1).height;
		level.ground.width=jsonlevel.layer.get(1).width;
		ArrayList<Entity> entities = new ArrayList<Entity>();
		Entity entity;
		for(JSONEntity jsonentity:jsonlevel.entities){
			entity = new Entity();
			switch(jsonentity.type){
			case "EntityGate":
				entity.type = 0;
				entity.subType=jsonentity.settings.particleType;
				break;
			case "EntityPickup":
				entity.type = 1;
				entity.subType=jsonentity.settings.particleType;
				break;
			default:
				entity.type = -1;
				entity.type=0;
				break;
			}
			//entity.type = jsonentity.type=="EntityGate"?0:1;
			//entity.subType=jsonentity.settings.particleType;
			entity.x=jsonentity.x;
			entity.y=jsonentity.y;
			entities.add(entity);
		}
		level.entities=entities;
	}
	public Level getLevel(){
		return level;
	}
}
