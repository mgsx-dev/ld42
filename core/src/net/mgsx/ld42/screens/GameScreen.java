package net.mgsx.ld42.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.ld42.LD42;
import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.model.AirEntity;
import net.mgsx.ld42.model.BaseEntity;
import net.mgsx.ld42.model.EntityType;
import net.mgsx.ld42.model.GameSettings;
import net.mgsx.ld42.model.Hero;
import net.mgsx.ld42.model.LandEntity;
import net.mgsx.ld42.ui.GameHUD;
import net.mgsx.ld42.utils.StageScreen;

public class GameScreen extends StageScreen
{
	private Batch batch = new SpriteBatch();
	
	private Viewport viewport;
	
	private Sprite planetSprite;
	
	private float time;
	
	private float speed = 0;
	private float altitude = 0;
	
	private float targetAltitude = 0, sourceAltitude = 0;
	
	private static final float worldWidth = LD42.WIDTH;
	private static final float worldHeight = LD42.HEIGHT;
	
	private Array<LandEntity> landEntities = new Array<LandEntity>();
	private Array<AirEntity> airSprites = new Array<AirEntity>();
	
	private float distanceOut, airTimeout;
	private float altitudeTime;
	
	private BaseEntity collidingEntity;
	
	private Hero hero;

	private GameHUD gameHUD;
	
	public GameScreen() {
		
		hero = new Hero();
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		root.add(gameHUD = new GameHUD(skin)).expand().fillX().top();
		
		stage.addActor(root);
		
		batch = new SpriteBatch();
		
		planetSprite = new Sprite(GameAssets.i().planet1);
		
		viewport = new FitViewport(worldWidth, worldHeight);
	}
	
	@Override
	public void render(float delta) {
		
		// check collisions
		
		// collidingEntity = null;
		if(collidingEntity == null){
			for(AirEntity ae : airSprites){
				if(ae.type != EntityType.NONE){
					float re = ae.sprite.getWidth() / 3f; // XXX reduced zone
					float rh = 12; // XX reduced zone
					float hx = hero.sprite.getX() + hero.sprite.getWidth()/2;
					float hy = hero.sprite.getY() + hero.sprite.getHeight()/2;
					float ex = ae.sprite.getX() + ae.sprite.getWidth()/2;
					float ey = ae.sprite.getY() + ae.sprite.getHeight()/2;
					
					float dx = hx - ex;
					float dy = hy - ey;
					
					float d = re + rh;
					
					if(dx * dx + dy * dy < d * d){
						handleCollision(ae);
					}
				}
			}
			for(LandEntity le : landEntities){
				if(le.type != EntityType.NONE){
					if(Math.abs(le.landAngle) < 3 && altitude < 64){
						handleCollision(le);
					}
				}
			}
		}
		// XXX debug
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			collidingEntity = null;
		}		

		
		
		if(altitudeTime >= 1){
			if(targetAltitude < 64 * 5 && Gdx.input.isKeyPressed(Input.Keys.Z)){
				targetAltitude = sourceAltitude + 64;
				altitudeTime = 0;
				hero.jetPackSpawnUp();
			}
			if(targetAltitude > 0 && Gdx.input.isKeyPressed(Input.Keys.S)){
				targetAltitude = sourceAltitude - 64;
				altitudeTime = 0;
				hero.jetPackSpawnDown();
			}
		}else{
			altitudeTime += delta * 4;
			altitude = MathUtils.lerp(sourceAltitude, targetAltitude, Interpolation.sine.apply(MathUtils.clamp(altitudeTime, 0, 1)));
			if(altitudeTime >= 1){
				sourceAltitude = targetAltitude;
			}
		}
		hero.altitudeIndex = MathUtils.round(altitude / 64); // TODO inverse index to altitude ...
		
		hero.resetJetPack();
		float targetSpeed = 2;
		if(altitude < 10 || true){
			if(Gdx.input.isKeyPressed(Input.Keys.Q)){
				hero.jetPackSpawnSlowdown();
				targetSpeed = .5f;
			}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
				hero.jetPackSpawnSpeedup();
				targetSpeed = 3f;
			}
			speed = MathUtils.lerp(speed, targetSpeed, delta * 3f);
		}
		if(collidingEntity != null) speed = 3;
		
		distanceOut -= delta * speed;
		
		if(distanceOut < 0){
			distanceOut = MathUtils.random(5, 10); // XXX MathUtils.random(1, 3);
			
			LandEntity entity = new LandEntity();
			entity.type = EntityType.OBSTACLE;
			entity.landAngle = -90;
			Sprite s;
			if(MathUtils.randomBoolean()){
				s = new Sprite(GameAssets.i().montainSmall);
				s.setOrigin(s.getWidth()/2, -400);
				s.setBounds((worldWidth - s.getWidth())/2, 100, 64, 64);
			}else{
				s = new Sprite(GameAssets.i().montainBig);
				s.setOrigin(s.getWidth()/2, -400);
				s.setBounds((worldWidth - s.getWidth())/2, 100, 64, 128);
			}
			
			entity.sprite = s;
			
			landEntities.add(entity);
		}
		
		airTimeout -= delta;
		
		if(airTimeout < 0){
			airTimeout = MathUtils.random(1, 3);
			{
				AirEntity ae = new AirEntity();
				
				if(MathUtils.randomBoolean()){
					ae.type = EntityType.OBSTACLE;
					ae.sprite = new Sprite(GameAssets.i().asteroidsOne.random());
				}else{
					if(MathUtils.randomBoolean()){
						ae.type = EntityType.GAS;
						ae.sprite = new Sprite(GameAssets.i().bonusGas);
					}else{
						if(MathUtils.randomBoolean()){
							ae.type = EntityType.AIR;
							ae.sprite = new Sprite(GameAssets.i().bonusAir);
						}else{
							ae.type = EntityType.ARTIFACT;
							ae.sprite = new Sprite(GameAssets.i().bonusKey);
						}
						// TODO life
					}
				}
				
				
				Sprite s = ae.sprite;
				s.setOrigin(s.getWidth()/2, s.getHeight()/2);
				s.setBounds(worldWidth + s.getWidth(), 150 + MathUtils.random() * 200, s.getRegionWidth(), s.getRegionHeight());
				airSprites.add(ae);
			}
		}
		
//		((OrthographicCamera)viewport.getCamera()).zoom = 5f;
//		viewport.getCamera().update();
		
		time += delta * speed;

		viewport.apply(true);
		
		GameAssets.i().heroAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
		
		hero.update(delta, speed);
		
		batch.setProjectionMatrix(viewport.getCamera().combined);
		
		float w = 1024, h = 1024;
		
		float planetOffsetY = -h + worldHeight - 200;
		
		planetSprite.setBounds((worldWidth - w)/2, planetOffsetY, w, h);
		
		planetSprite.setOrigin(planetSprite.getWidth()/2, planetSprite.getHeight()/2);
		planetSprite.setRotation(time * 30);
		
		if(collidingEntity != null){
			hero.jetPackOff();
			hero.sprite.setPosition(collidingEntity.sprite.getX(), collidingEntity.sprite.getY());
			hero.sprite.setRotation(collidingEntity.sprite.getRotation());
			hero.sprite.setOrigin(collidingEntity.sprite.getOriginX(), collidingEntity.sprite.getOriginY());
		}else{
			hero.sprite.setBounds((worldWidth - 64)/2, 100 + altitude +  10 * Math.abs(MathUtils.sinDeg(time * 190)), 64, 64);
			hero.sprite.setRotation(0);
			hero.sprite.setOrigin(0, 0);
		}
		
		
		
		
		batch.setColor(Color.WHITE);
		
		batch.begin();
		
		float bgMove = time * .1f;
		batch.draw(GameAssets.i().bgStars, 0, 0, 1024, 1024, 0 + bgMove, 0, 1 + bgMove, 1);
		
		planetSprite.draw(batch);
		
		float airSpeed = (speed - 1) / 1.3f + 1;
		
		for(AirEntity ae : airSprites){
			Sprite s = ae.sprite;
			ae.update(delta * airSpeed);
			s.draw(batch);
			if(s.getX() < - s.getWidth()){
				ae.toRemove = true;
			}
		}
		for(int i=0 ; i<airSprites.size ; ){
			AirEntity ae = airSprites.get(i);
			Sprite s = ae.sprite;
			if(ae.toRemove){
				airSprites.removeIndex(i);
			}else i++;
		}
		
		for(LandEntity le : landEntities){
			le.landAngle += delta * speed * 30;
			Sprite s = le.sprite;
			s.setRotation(le.landAngle);
			s.draw(batch);
			if(le.landAngle > 90){ // TODO
				le.toRemove = true;
			}
		}
		for(int i=0 ; i<landEntities.size ; ){
			LandEntity e = landEntities.get(i);
			if(e.toRemove){
				landEntities.removeIndex(i);
			}else i++;
		}
		
		hero.draw(batch);
		
		
		batch.end();
		
		// update UI
		gameHUD.update(hero);
		
		
		super.render(delta);
	}
	
	private void handleCollision(BaseEntity e) {
		switch(e.type){
		case AIR:
			hero.air = 1; // air max
			e.toRemove = true;
			break;
		case ARTIFACT:
			hero.artifacts++;
			e.toRemove = true;
			break;
		case GAS:
			hero.gas = 1; // gas max
			e.toRemove = true;
			break;
		case LIFE:
			hero.lifes = Math.min(GameSettings.HERO_MAX_LIFES, hero.lifes + 1);
			e.toRemove = true;
			break;
		case NONE:
			break;
		case OBSTACLE:
			collidingEntity = e;
			break;
		default:
			break;
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		super.resize(width, height);
	}
}
