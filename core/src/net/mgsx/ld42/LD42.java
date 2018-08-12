package net.mgsx.ld42;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import net.mgsx.ld42.model.GameLevels;
import net.mgsx.ld42.screens.DyingScreen;
import net.mgsx.ld42.screens.GameScreen;
import net.mgsx.ld42.screens.IntroScreen;
import net.mgsx.ld42.screens.JumpingScreen;
import net.mgsx.ld42.screens.MenuScreen;
import net.mgsx.ld42.screens.OutroScreen;

public class LD42 extends Game {
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 384;
	
	public static final int WINDOW_WIDTH = WIDTH;
	public static final int WINDOW_HEIGHT = 576;
	
	public static LD42 i(){
		return (LD42) Gdx.app.getApplicationListener();
	}
	
	@Override
	public void create () {
		//setScreen(new GameScreen());
		// setScreen(new IntroScreen());
		// setScreen(new DyingScreen(false));
		setScreen(new MenuScreen());
		// setScreen(new JumpingScreen());
		// setScreen(new OutroScreen());
	}

	@Override
	public void render () {
		
		boolean debug = false;
		if(debug){
			if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
				GameLevels.level = 0;
				setScreen(new MenuScreen());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
				GameLevels.level = 0;
				setScreen(new IntroScreen());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F3)){
				GameLevels.level = 0;
				setScreen(new DyingScreen(true));
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F4)){
				GameLevels.level = 0;
				setScreen(new GameScreen());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F5)){
				GameLevels.level = 1;
				setScreen(new GameScreen());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F6)){
				GameLevels.level = 2;
				setScreen(new GameScreen());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F7)){
				GameLevels.level = 0;
				setScreen(new JumpingScreen());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F8)){
				GameLevels.level = 0;
				setScreen(new OutroScreen());
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.F9)){
				GameLevels.level = 0;
				setScreen(new DyingScreen(false));
			}
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}
	
}
