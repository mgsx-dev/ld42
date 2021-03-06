package net.mgsx.ld42.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.ld42.LD42;

abstract public class StageScreen extends ScreenAdapter
{
	protected Stage stage;
	protected Skin skin;
	protected Viewport viewport;
	
	public StageScreen() 
	{
		this(new FitViewport(LD42.WIDTH, LD42.HEIGHT));
	}
	public StageScreen(Viewport viewport) 
	{
		this.viewport = viewport;
		stage = new Stage(viewport);
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		super.hide();
	}
	
	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}