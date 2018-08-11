package net.mgsx.ld42.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class GameAssets {
	private static GameAssets i;
	public static GameAssets i(){
		return i == null ? i = new GameAssets() : i;
	}
	
	public Skin skin;
	public Texture planet1, hero, bgStars;
	public Animation<TextureRegion> heroAnimation;
	
	public TextureRegion montainSmall, montainBig, ldBlock;
	
	public GameAssets() {
		skin = new Skin(Gdx.files.internal("skins/game-skin.json"));
		
		planet1 = new Texture(Gdx.files.internal("planet1.png"));
		
		planet1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		
		hero = new Texture(Gdx.files.internal("hero.png"));
		
		hero.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		for(int i=0 ; i<5 ; i++){
			frames.add(new TextureRegion(hero, 0, 64 * i, 64, 64));
		}
		
		montainSmall = new TextureRegion(hero, 64 * 3, 64 * 1, 64, 64);
		montainBig = new TextureRegion(hero, 64 * 2, 64 * 0, 64, 128);
		
		ldBlock = new TextureRegion(hero, 64 * 2, 64 * 3, 64 * 2, 64 * 2);
		
		heroAnimation = new Animation<TextureRegion>(1, frames);
		
		bgStars = new Texture(Gdx.files.internal("bg.png"));
		
		bgStars.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bgStars.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	}
}
