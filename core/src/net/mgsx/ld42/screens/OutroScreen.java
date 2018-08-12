package net.mgsx.ld42.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.ld42.LD42;
import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.model.GameLevels;
import net.mgsx.ld42.utils.StageScreen;

public class OutroScreen extends StageScreen
{
	private Batch batch = new SpriteBatch();
	
	private Viewport viewport;
	
	private Sprite planetSprite;
	
	private float time;

	private Sprite heroSprite;
	
	private int state = 0;
	private float stateTime;

	private Label lbComplete;
	
	public OutroScreen() 
	{
		GameAssets.i().playMusic(GameAssets.i().musicOutro);
		
		heroSprite = new Sprite(GameAssets.i().heroFixingAnimation.getKeyFrame(0));
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		root.add(lbComplete = new Label("Game Completed", skin)).expand();
		
		lbComplete.getColor().a = 0;
		
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
		batch.setProjectionMatrix(viewport.getCamera().combined);
		
		batch.setColor(Color.WHITE);
		
		stateTime += delta;
		
		float ix = -64;
		float iy = 512;
		
		if(state == 0){
			heroSprite.setPosition(ix, iy);
			state++;
			stateTime = 0;
		}else if(state == 1){
			if(stateTime > 1){ // time waiting
				stateTime = 0;
				state++;
				GameAssets.i().playSFX(GameAssets.i().sndRotation);
			}
		}else if(state == 2){
			float t = stateTime / 2;
			float tx = 670;
			float ty = 108;
			if(t >= 1){
				heroSprite.setPosition(tx, ty);
				stateTime = 0;
				state++;
				GameAssets.i().playSFX(GameAssets.i().sndCrush);
			}else{
				heroSprite.setX(MathUtils.lerp(ix, tx, t));
				heroSprite.setY(MathUtils.lerp(iy, ty, t));
			}
			heroSprite.setRegion(GameAssets.i().heroFlyAnimation.getKeyFrame(time * 2, true));
		}else if(state == 3){
			heroSprite.setRegion(GameAssets.i().heroOK);
			if(stateTime > 1){ // time waiting dumby
				stateTime = 0;
				state++;
				
				GameAssets.i().playSFX(GameAssets.i().sndGameComplete);
				
				lbComplete.addAction(Actions.sequence(Actions.alpha(1, .3f), Actions.delay(5)));
			}
		}else if(state == 4){
			if(!lbComplete.hasActions()){
				LD42.i().setScreen(new MenuScreen());
				GameLevels.level = 0;
			}
		}
		
		
		batch.begin();
		
		float bgMove = time * .1f;
		batch.draw(GameAssets.i().bgStars, 0, 0, 1024, 1024, 0 + bgMove, 0, 1 + bgMove, 1);
		
		planetSprite.setPosition(0, 0);
		planetSprite.draw(batch);
		
		heroSprite.draw(batch);
		
		batch.end();
		super.render(delta);
	}
}
