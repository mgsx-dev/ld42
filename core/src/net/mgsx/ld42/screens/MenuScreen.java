package net.mgsx.ld42.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import net.mgsx.ld42.LD42;
import net.mgsx.ld42.assets.GameAssets;
import net.mgsx.ld42.utils.StageScreen;

public class MenuScreen extends StageScreen
{
	public MenuScreen() {
		
		skin = GameAssets.i().skin;
		
		Table root = new Table(skin);
		
		root.setFillParent(true);
		
		stage.addActor(root);
		
		Table menu = new Table(skin);
		
		root.add(menu).expand();
		
		menu.add("Running Out of Space").padBottom(100).row();
		
		TextButton btPlay;
		menu.add(btPlay = new TextButton("PLAY", skin)).padBottom(100);
		
		btPlay.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				LD42.i().setScreen(new IntroScreen());
			}
		});
	}
}
