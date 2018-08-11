package net.mgsx.ld42.ui;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.mgsx.ld42.model.Hero;

public class GameHUD extends Table
{
	private Label lbGas;
	private Label lbAir;

	public GameHUD(Skin skin) {
		super(skin);
		
		Table tableStats = new Table(skin);
		
		tableStats.add("gas").padRight(20);
		tableStats.add(lbGas = new Label("", skin)).width(100);
		tableStats.row();
		
		tableStats.add("air").padRight(20);
		tableStats.add(lbAir = new Label("", skin)).width(100);
		tableStats.row();
		
		add(tableStats);
		add("Runnng Out of Space").expandX().right();
	}

	public void update(Hero hero) {
		lbGas.setText(String.valueOf(MathUtils.round(Math.max(0, hero.gas) * 100)));
		lbAir.setText(String.valueOf(MathUtils.round(Math.max(0, hero.air) * 100)));
	}
	
}
