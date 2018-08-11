package net.mgsx.ld42.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import net.mgsx.ld42.assets.GameAssets;

public class Hero {
	public Sprite sprite;
	private Sprite jetPackSprite;
	
	private float time;
	private float timeWalk;
	private float jetPackTime;
	public int altitudeIndex;
	public float air = 1, gas = 1;
	public int artifacts = 0;
	public int lifes = GameSettings.HERO_MAX_LIFES;
	private float consumption;
	public boolean hurted;
	
	public Hero() {
		sprite = new Sprite(GameAssets.i().heroWalkAnimation.getKeyFrame(2));
		jetPackSprite = new Sprite(GameAssets.i().jetPackAnimation.getKeyFrame(0));
		jetPackSprite.setOriginCenter();
	}
	
	public void jetPackSpawnSlowdown(){
		if(altitudeIndex > 0){
			jetPackSpawn(90);
			consumption = GameSettings.BOOST_GAS_CONSUMPTION;
		}
	}
	public void jetPackSpawnSpeedup(){
		if(altitudeIndex > 0){
			jetPackSpawn(-90);
			consumption = GameSettings.BOOST_GAS_CONSUMPTION;
		}
	}
	public void jetPackSpawnUp(){
		jetPackSpawn(-45);
	}
	public void jetPackSpawnDown(){
		jetPackSpawn(-135);
	}
	private void jetPackSpawn(float angle)
	{
		// gas -= .1f; // remove 10%
		jetPackTime = 1;
		jetPackSprite.setRotation(angle);
	}
	
	public void update(float delta, float runSpeed){
		time += delta;
		timeWalk += delta * runSpeed;
		
		jetPackTime -= delta * 3;
		
		jetPackSprite.setRegion(GameAssets.i().jetPackAnimation.getKeyFrame(time * 25, true));
		jetPackSprite.setOrigin(jetPackSprite.getRegionWidth()/2, jetPackSprite.getRegionHeight() - 10);
		//jetPackSprite.setOriginCenter();;
		
		if(altitudeIndex > 0){
			gas -= delta * .01f * consumption; // 100s gas
		}
		air -= delta * .01f; // 100s air
	}
	
	public void draw(Batch batch) 
	{
		if(jetPackTime > 0){
			jetPackSprite.setBounds(sprite.getX(), sprite.getY() - 32, sprite.getWidth(), sprite.getHeight());
			jetPackSprite.setScale(MathUtils.lerp(.8f, 1.2f, (MathUtils.sin(time * 50)+1)*.5f));
			jetPackSprite.draw(batch);
		}else if(altitudeIndex > 0){
			jetPackSprite.setRotation(0);
			jetPackSprite.setBounds(sprite.getX(), sprite.getY() - 32, sprite.getWidth(), sprite.getHeight());
			jetPackSprite.setScale(MathUtils.lerp(.5f, .8f, (MathUtils.sin(time * 50)+1)*.5f));
			jetPackSprite.draw(batch);
		}
		
		if(hurted){
			sprite.setRegion(GameAssets.i().heroHurted);
		}
		else if(altitudeIndex > 0){
			sprite.setRegion(GameAssets.i().heroFlyAnimation.getKeyFrame(timeWalk * 2, true));
		}else{
			sprite.setRegion(GameAssets.i().heroWalkAnimation.getKeyFrame(timeWalk * 12));
		}
		
		sprite.draw(batch);
		
	}

	public void jetPackOff() {
		jetPackTime = -1;
	}

	public void resetJetPack() {
		consumption = 1;
	}
}
