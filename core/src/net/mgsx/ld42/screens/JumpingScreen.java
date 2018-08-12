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
import net.mgsx.ld42.model.GameLevels;
import net.mgsx.ld42.utils.StageScreen;

public class JumpingScreen extends StageScreen
{
private Batch batch = new SpriteBatch();
	
	private Viewport viewport;
	
	private float time;

	private Sprite heroSprite;
	
	private float animTime;
	
	public JumpingScreen() 
	{
		super();
		heroSprite = new Sprite(GameAssets.i().heroFixingAnimation.getKeyFrame(0));
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		root.add("Planet Completed").expand().top().padTop(20);
		
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
		
		animTime += delta * .3f;
		
		time += delta;
		
		viewport.apply(true);
		
		batch.setColor(Color.WHITE);
		
		if(time > 5){
			GameLevels.level++;
			if(GameLevels.level < GameLevels.MAX_LEVEL){
				LD42.i().setScreen(new GameScreen());
			}else{
				LD42.i().setScreen(new OutroScreen());
			}
		}
		
		heroSprite.setRegion(GameAssets.i().heroFlyAnimation.getKeyFrame(time * 2, true));
//		heroSprite.setPosition((LD42.WIDTH - heroSprite.getWidth()) / 2, (LD42.HEIGHT - heroSprite.getHeight()) / 2);
		heroSprite.setPosition(MathUtils.lerp(- heroSprite.getWidth(), LD42.WIDTH + heroSprite.getWidth(), animTime), (LD42.HEIGHT - heroSprite.getHeight()) / 2);
		heroSprite.setOriginCenter();
		heroSprite.setRotation(-time * 60);
		heroSprite.setScale(4);
		
		
		batch.begin();
		
		float bgMove = time * .1f;
		batch.draw(GameAssets.i().bgStars, 0, 0, 1024, 1024, 0 + bgMove, 0, 1 + bgMove, 1);
		
		heroSprite.draw(batch);
		
		batch.end();
		super.render(delta);
	}
}
