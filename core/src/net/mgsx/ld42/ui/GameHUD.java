package net.mgsx.ld42.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.mgsx.ld42.model.GamePlanet;
import net.mgsx.ld42.model.Hero;

public class GameHUD extends Table
{
	private Label lbGas;
	private Label lbAir;
	private Label lbScrew;
	private Label lbKey;
	private Label lbBolt;
	private Label lbLife;

	public GameHUD(Skin skin) {
		super(skin);
		
		Table tableStats = new Table(skin);
		
		tableStats.add("life").padRight(20);
		tableStats.add(lbLife = new Label("", skin)).width(100);
		tableStats.row();
		
		tableStats.add("gas").padRight(20);
		tableStats.add(lbGas = new Label("", skin)).width(100);
		tableStats.row();
		
		tableStats.add("air").padRight(20);
		tableStats.add(lbAir = new Label("", skin)).width(100);
		tableStats.row();
		
		tableStats.add(lbScrew = new Label("Screw", skin)).width(100);
		tableStats.add().row();
		tableStats.add(lbKey = new Label("Key", skin)).width(100);
		tableStats.add().row();
		tableStats.add(lbBolt = new Label("Bolt", skin)).width(100);
		tableStats.add().row();
		
		lbScrew.setColor(Color.GRAY);
		lbKey.setColor(Color.GRAY);
		lbBolt.setColor(Color.GRAY);
		
		add(tableStats);
		add("Running Out of Space").expandX().right().top();
	}

	public void update(Hero hero, GamePlanet planet) {
		lbGas.setText(String.valueOf(MathUtils.round(Math.max(0, hero.gas) * 100)));
		lbAir.setText(String.valueOf(MathUtils.round(Math.max(0, hero.air) * 100)));
		lbLife.setText(String.valueOf(hero.lifes));
		
		lbScrew.setColor(planet.screwComplete ? Color.GREEN : Color.GRAY);
		lbKey.setColor(planet.keyComplete ? Color.GREEN : Color.GRAY);
		lbBolt.setColor(planet.boltComplete ? Color.GREEN : Color.GRAY);

	}
	
}
