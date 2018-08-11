package net.mgsx.ld42;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import net.mgsx.ld42.screens.MenuScreen;

public class LD42 extends Game {
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 384; // TODO 512
	
	public static LD42 i(){
		return (LD42) Gdx.app.getApplicationListener();
	}
	
	@Override
	public void create () {
		// setScreen(new GameScreen());
		// setScreen(new IntroScreen());
		// setScreen(new DyingScreen(false));
		setScreen(new MenuScreen());
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}
	
}
