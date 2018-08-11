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
	public Animation<TextureRegion> jetPackAnimation;
	public TextureRegion montainSmallUp;
	public TextureRegion bonusAir;
	public TextureRegion bonusGas;
	public TextureRegion bonusKey;
	public Array<TextureRegion> asteroidsOne;
	
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
		
		montainSmallUp = getCell(hero, 4, 0, 1, 2);
		bonusAir = getCell(hero, 5, 0, 1, 1);
		bonusGas = getCell(hero, 5, 1, 1, 1);
		bonusKey = getCell(hero, 5, 2, 1, 1);
		
		asteroidsOne = new Array<TextureRegion>();
		asteroidsOne.add(getCell(hero, 2, 5, 1, 1));
		asteroidsOne.add(getCell(hero, 3, 5, 1, 1));
		asteroidsOne.add(getCell(hero, 2, 6, 1, 1));
		asteroidsOne.add(getCell(hero, 3, 6, 1, 1));
		
		ldBlock = new TextureRegion(hero, 64 * 2, 64 * 3, 64 * 2, 64 * 2);
		
		heroAnimation = new Animation<TextureRegion>(1, frames);
		
		bgStars = new Texture(Gdx.files.internal("bg.png"));
		
		bgStars.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bgStars.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		jetPackAnimation = getAnimation(hero, 1, 0, 1, 1, 4);
		
		
	}
	
	private TextureRegion getCell(Texture texture, int col, int row, int cols, int rows)
	{
		int s = 64;
		return new TextureRegion(texture, col * s, row *s, cols * s, rows * s);
	}
	
	private Animation<TextureRegion> getAnimation(Texture texture, int col, int row, int cols, int rows, int frames)
	{
		Array<TextureRegion> regions = new Array<TextureRegion>();
		for(int i=0 ; i<frames ; i++){
			regions.add(getCell(texture, col, row + i * rows, cols, rows));
		}
		return new Animation<TextureRegion>(1, regions);
	}
}
