package net.mgsx.ld42.model;

import com.badlogic.gdx.math.MathUtils;

public class AirEntity extends BaseEntity
{
	public float moveSpeed = 300;
	public float rotateSpeed = 50;
	
	public AirEntity() {
		moveSpeed = MathUtils.random(200, 500);
		rotateSpeed = MathUtils.random(20, 100);
	}
	
	public void update(float delta){
		sprite.setX(sprite.getX() - delta * moveSpeed);
		sprite.rotate(delta * 50);
	}
}
