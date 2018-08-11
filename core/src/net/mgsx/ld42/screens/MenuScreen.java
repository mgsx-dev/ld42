package net.mgsx.ld42.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.utils.StageScreen;

public class MenuScreen extends StageScreen
{
	public MenuScreen() {
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		root.add("Runnng Out of Space").expand().top();
		
		stage.addActor(root);
	}
}
