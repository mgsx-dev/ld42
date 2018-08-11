package net.mgsx.ld42.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.ld42.LD42;
import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.utils.StageScreen;

public class DyingScreen extends StageScreen
{
private Batch batch = new SpriteBatch();
	
	private Viewport viewport;
	
	private float time;

	private Sprite heroSprite;

	private boolean transitToGame;
	
	private float heroScale = 16;
	
	public DyingScreen(boolean transitToGame) 
	{
		super();
		this.transitToGame = transitToGame;
		heroSprite = new Sprite(GameAssets.i().heroFixingAnimation.getKeyFrame(0));
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		if(!transitToGame){
			
			root.add("GAME OVER").expand();
		}
		
		stage.addActor(root);
		
		batch = new SpriteBatch();
		
		viewport = new FitViewport(LD42.WIDTH, LD42.HEIGHT);
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
		
		if(time > 5){
			LD42.i().setScreen(transitToGame ? new GameScreen() : new MenuScreen());
		}
		
		heroScale = MathUtils.lerp(heroScale, 0, delta);
		
		heroSprite.setRegion(GameAssets.i().heroDying);
		heroSprite.setPosition((LD42.WIDTH - heroSprite.getWidth()) / 2, (LD42.HEIGHT - heroSprite.getHeight()) / 2);
		heroSprite.setOriginCenter();
		heroSprite.setRotation(time * 120);
		heroSprite.setScale(heroScale);
		
		batch.begin();
		
		float bgMove = time * .1f;
		batch.draw(GameAssets.i().bgStars, 0, 0, 1024, 1024, 0 + bgMove, 0, 1 + bgMove, 1);
		
		heroSprite.draw(batch);
		
		batch.end();
		super.render(delta);
	}
}
