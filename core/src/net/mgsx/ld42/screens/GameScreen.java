package net.mgsx.ld42.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.utils.StageScreen;

public class GameScreen extends StageScreen
{
	private Batch batch = new SpriteBatch();
	
	private OrthographicCamera camera;
	
	private Viewport viewport;
	
	private Sprite planetSprite, heroSprite;
	
	private float time;
	
	private float speed = 0;
	private float altitude = 0;
	
	private static final float worldWidth = 2 * 512;
	private static final float worldHeight = 384;
	
	private Array<Sprite> mountainSprites = new Array<Sprite>();
	private Array<Sprite> flyingSprites = new Array<Sprite>();
	
	private float distanceOut;
	
	public GameScreen() {
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		root.add("Runnng Out of Space").expand().top();
		
		stage.addActor(root);
		
		batch = new SpriteBatch();
		
		planetSprite = new Sprite(GameAssets.i().planet1);
		
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(worldWidth, worldHeight);
		
		heroSprite = new Sprite(GameAssets.i().heroAnimation.getKeyFrame(2));
	}
	
	@Override
	public void render(float delta) {
		
		float targetHeight;
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			targetHeight = 200f;
		}else{
			targetHeight = 0;
		}
		
		altitude = MathUtils.lerp(altitude, targetHeight, delta * 3f);
		
		float targetSpeed = 1;
		if(altitude < 10){
			if(Gdx.input.isKeyPressed(Input.Keys.Q)){
				targetSpeed = .5f;
			}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
				targetSpeed = 4f;
			}
			speed = MathUtils.lerp(speed, targetSpeed, delta * 3f);
		}
		
		distanceOut -= delta * speed;
		
		if(distanceOut < 0){
			distanceOut = 5;
			
			if(MathUtils.randomBoolean() || true){
				Sprite s = new Sprite(GameAssets.i().montainSmall);
				s.setOrigin(s.getWidth()/2, -400);
				s.setBounds((worldWidth - s.getWidth())/2, 100, 64, 64);
				mountainSprites.add(s);
			}else{
				Sprite s = new Sprite(GameAssets.i().montainBig);
				s.setOrigin(s.getWidth()/2, -400);
				s.setBounds((worldWidth - s.getWidth())/2, 100, 64, 128);
				mountainSprites.add(s);
			}
			
			{
				Sprite s = new Sprite(GameAssets.i().ldBlock);
				s.setOrigin(s.getWidth()/2, s.getHeight()/2);
				s.setBounds(worldWidth + s.getWidth(), 150 + MathUtils.random() * 200, s.getRegionWidth(), s.getRegionHeight());
				flyingSprites.add(s);
			}
		}
		
		time += delta * speed;

		viewport.apply(true);
		
		GameAssets.i().heroAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
		
		heroSprite.setRegion(GameAssets.i().heroAnimation.getKeyFrame(time * 8, true));
		
		batch.setProjectionMatrix(viewport.getCamera().combined);
		
		float w = 1024, h = 1024;
		
		float planetOffsetY = -h + worldHeight - 200;
		
		planetSprite.setBounds((worldWidth - w)/2, planetOffsetY, w, h);
		
		planetSprite.setOrigin(planetSprite.getWidth()/2, planetSprite.getHeight()/2);
		planetSprite.setRotation(time * 30);
		
		heroSprite.setBounds((worldWidth - 64)/2, 100 + altitude +  10 * Math.abs(MathUtils.sinDeg(time * 190)), 64, 64);
		
		batch.setColor(Color.WHITE);
		
		batch.begin();
		
		float bgMove = time * .1f;
		batch.draw(GameAssets.i().bgStars, 0, 0, 1024, 1024, 0 + bgMove, 0, 1 + bgMove, 1);
		
		planetSprite.draw(batch);
		heroSprite.draw(batch);
		
		for(Sprite s : flyingSprites){
			s.setX(s.getX() - delta * 300);
			s.rotate(delta * 50);
			s.draw(batch);
		}
		for(int i=0 ; i<flyingSprites.size ; ){
			Sprite s = flyingSprites.get(i);
			if(s.getX() < - s.getWidth()){
				flyingSprites.removeIndex(i);
			}else i++;
		}
		
		for(Sprite s : mountainSprites){
			s.setRotation(time * 30);
			s.draw(batch);
		}
		
		
		batch.end();
		
		super.render(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		super.resize(width, height);
	}
}
