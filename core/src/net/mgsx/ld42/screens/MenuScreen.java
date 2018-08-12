package net.mgsx.ld42.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.ld42.LD42;
import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.utils.StageScreen;

public class MenuScreen extends StageScreen
{
	private Batch batch = new SpriteBatch();
	
	private Viewport viewport;
	
	private float time;

	public MenuScreen() 
	{
		skin = GameAssets.i().skin;
		
		Table menu = new Table(skin);
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		root.add(menu).expand();
		
		menu.add("Lost in Deep Space").padBottom(100).row();
		
		TextButton btPlay;
		menu.add(btPlay = new TextButton("PLAY", skin)).padBottom(100);
		
		btPlay.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				LD42.i().setScreen(new IntroScreen());
			}
		});
		
		stage.addActor(root);
		
		batch = new SpriteBatch();
		
		viewport = new FitViewport(LD42.WIDTH, LD42.HEIGHT);
		
		// XXX quick fix GWT ... but doesn't work
		GameAssets.i().playMusic(GameAssets.i().musicTitle);
		GameAssets.i().playMusic(GameAssets.i().musicTitle);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		super.resize(width, height);
	}
	
	@Override
	public void render(float delta) {
		
		time += delta;
		
		viewport.apply(true);
		
		batch.setColor(Color.WHITE);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		
		batch.begin();
		
		float bgMove = time * .1f;
		batch.draw(GameAssets.i().bgStars, 0, 0, 1024, 1024, 0 + bgMove, 0, 1 + bgMove, 1);
		
		batch.end();
		super.render(delta);
	}
}
