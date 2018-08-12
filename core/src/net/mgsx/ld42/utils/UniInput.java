package net.mgsx.ld42.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class UniInput {
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	public static boolean isKeyPressed(int code)
	{
		if(code == UP){
			return Gdx.input.isKeyPressed(Input.Keys.UP) 
					||  Gdx.input.isKeyPressed(Input.Keys.W) 
					||  Gdx.input.isKeyPressed(Input.Keys.Z); 
		}else if(code == DOWN){
			return Gdx.input.isKeyPressed(Input.Keys.DOWN) 
					||  Gdx.input.isKeyPressed(Input.Keys.S); 
		}else if(code == LEFT){
			return Gdx.input.isKeyPressed(Input.Keys.LEFT) 
					||  Gdx.input.isKeyPressed(Input.Keys.A) 
					||  Gdx.input.isKeyPressed(Input.Keys.Q);
		}else if(code == RIGHT){
			return Gdx.input.isKeyPressed(Input.Keys.RIGHT) 
					||  Gdx.input.isKeyPressed(Input.Keys.D); 
		}
		return false;
	}
}
