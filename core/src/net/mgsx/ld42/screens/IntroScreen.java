package net.mgsx.ld42.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.ld42.LD42;
import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.utils.StageScreen;

public class IntroScreen extends StageScreen
{
	private Batch batch = new SpriteBatch();
	
	private Viewport viewport;
	
	private Sprite planetSprite;
	
	private float time;

	private Sprite heroSprite;
	
	private Sprite explosionSprite;
	
	private int state = 0;
	private float stateTime;
	
	public IntroScreen() 
	{
		GameAssets.i().playMusic(GameAssets.i().musicIntro);
		
		heroSprite = new Sprite(GameAssets.i().heroFixingAnimation.getKeyFrame(0));
		
		explosionSprite = new Sprite(GameAssets.i().jetPackAnimation.getKeyFrame(0));
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		root.add("Somewhere in the galaxy ...").expand().top().left();
		
		stage.addActor(root);
		
		batch = new SpriteBatch();
		
		planetSprite = new Sprite(GameAssets.i().planet0);
		
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
		batch.setProjectionMatrix(viewport.getCamera().combined);
		
		stateTime += delta;
		
		float pikesScale = 1;
		
		boolean drawPikes = false;
		if(state == 0){
			heroSprite.setPosition(670, 108);
			state++;
			stateTime = 0;
		}else if(state == 1){
			if(stateTime > 3){ // time fixing
				stateTime = 0;
				state++;
			}
			heroSprite.setRegion(GameAssets.i().heroFixingAnimation.getKeyFrame(time * 4, true));
		}else if(state == 2){
			drawPikes = true;
			if(stateTime > 1){ // time buzzing
				stateTime = 0;
				state++;
			}
		}else if(state == 3){
			if(stateTime > 2){ // time waiting dumby
				stateTime = 0;
				state++;
			}
			heroSprite.setRegion(GameAssets.i().heroQuestioning);
		}else if(state == 4){
			drawPikes = true;
			pikesScale = 4;
			if(stateTime > 1){ // time ejection
				stateTime = 0;
				state++;
			}
			heroSprite.setRegion(GameAssets.i().heroDying);
			heroSprite.setX(heroSprite.getX() - delta * 1000);
			heroSprite.setY(heroSprite.getY() + delta * 300);
			heroSprite.setOriginCenter();
			heroSprite.setRotation(stateTime * 720);
		}else if(state == 5){
			LD42.i().setScreen(new DyingScreen(true));
		}
		
		
		batch.begin();
		
		float bgMove = time * .1f;
		batch.draw(GameAssets.i().bgStars, 0, 0, 1024, 1024, 0 + bgMove, 0, 1 + bgMove, 1);
		
		planetSprite.setPosition(0, 0);
		planetSprite.draw(batch);
		
		heroSprite.draw(batch);
		
		if(drawPikes && (time * 15f) % 2f < 1f){
			explosionSprite.setRotation(-90);
			explosionSprite.setPosition(672, 108);
			explosionSprite.draw(batch);
		}
		
		batch.end();
		super.render(delta);
	}
}
