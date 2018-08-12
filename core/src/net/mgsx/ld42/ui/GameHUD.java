package net.mgsx.ld42.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.mgsx.ld42.model.GameLevels;
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
		
		
		add(tableStats).expand().top().left();
		row();
		add(GameLevels.planetNames[GameLevels.level]).expandX().center().getActor().setColor(Color.DARK_GRAY);;
		// add(GameLevels.planetNames[GameLevels.level]).expandX().right().top().getActor().setColor(Color.GRAY);;
	}

	public void update(Hero hero, GamePlanet planet) {
		lbGas.setText(String.valueOf(MathUtils.round(Math.max(0, hero.gas) * 100)));
		lbAir.setText(String.valueOf(MathUtils.round(Math.max(0, hero.air) * 100)));
		lbLife.setText(String.valueOf(hero.lifes));
		
		lbGas.setColor(hero.gas < .5f ? Color.ORANGE : hero.gas < .1f ? Color.RED : Color.WHITE);
		lbAir.setColor(hero.air < .5f ? Color.ORANGE : hero.air < .1f ? Color.RED : Color.WHITE);
		lbLife.setColor(hero.lifes <= 1 ? Color.RED : Color.WHITE);
		
		lbScrew.setColor(planet.screwComplete ? Color.GREEN : Color.GRAY);
		lbKey.setColor(planet.keyComplete ? Color.GREEN : Color.GRAY);
		lbBolt.setColor(planet.boltComplete ? Color.GREEN : Color.GRAY);

	}
	
}
