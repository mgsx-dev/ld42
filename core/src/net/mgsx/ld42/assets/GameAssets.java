package net.mgsx.ld42.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
	public Texture planet1, planet2, planet3, hero, bgStars;
	public Animation<TextureRegion> heroWalkAnimation;
	
	public TextureRegion ldBlock;
	public TextureRegion [] montainSmall, montainBig, montainSmallUp;
	public Animation<TextureRegion> jetPackAnimation;
	public TextureRegion bonusAir;
	public TextureRegion bonusGas;
	public TextureRegion artifactKey;
	public Array<TextureRegion> asteroidsOne;
	public Animation<TextureRegion> heroFlyAnimation;
	public Animation<TextureRegion> heroFixingAnimation;
	public TextureRegion heroQuestioning;
	public TextureRegion heroDying;
	public TextureRegion heroHurted;
	public Texture planet0;
	public TextureRegion artifactBolt;
	public TextureRegion artifactScrew;
	public TextureRegion bonusLife;
	public TextureRegion heroOK;
	public Music musicTitle;
	public Music musicLevel1;
	public Music musicLevel2;
	public Music musicLevel3;
	public Music musicIntro;
	public Music musicOutro;
	public Music musicGameOver;
	public Music musicTransition;

	public Music currentMusic;
	public Sound sndBonus;
	public Sound sndCrush;
	public Sound sndGameComplete;
	public Sound sndJetPack;
	public Sound sndRadio;
	public Sound sndLevelComplete;
	public Sound sndRotation;
	
	public GameAssets() {
		skin = new Skin(Gdx.files.internal("skins/game-skin.json"));
		
		planet0 = new Texture(Gdx.files.internal("planet0.png"));
		planet1 = new Texture(Gdx.files.internal("planet1.png"));
		planet2 = new Texture(Gdx.files.internal("planet2.png"));
		planet3 = new Texture(Gdx.files.internal("planet3.png"));
		
		
		hero = new Texture(Gdx.files.internal("hero.png"));
		
		hero.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		for(int i=0 ; i<5 ; i++){
			frames.add(new TextureRegion(hero, 0, 64 * i, 64, 64));
		}
		
		montainSmall = new TextureRegion[3];
		montainBig = new TextureRegion[3];
		montainSmallUp = new TextureRegion[3];
		for(int i=0 ; i<3 ; i++){
			montainSmall[i] =   getCell(hero, 3, 9 + i * 3, 1, 1);
			montainBig[i] =     getCell(hero, 2, 8 + i * 3, 1, 2);
			montainSmallUp[i] = getCell(hero, 4, 8 + i * 3, 1, 2);
		}
		
		bonusAir = getCell(hero, 5, 0, 1, 1);
		bonusGas = getCell(hero, 5, 1, 1, 1);
		
		
		artifactKey = getCell(hero, 5, 2, 1, 1);
		artifactBolt = getCell(hero, 5, 3, 1, 1);
		artifactScrew = getCell(hero, 5, 4, 1, 1);
		bonusLife = getCell(hero, 5, 5, 1, 1);
		
		asteroidsOne = new Array<TextureRegion>();
		asteroidsOne.add(getCell(hero, 2, 5, 1, 1));
		asteroidsOne.add(getCell(hero, 3, 5, 1, 1));
		asteroidsOne.add(getCell(hero, 2, 6, 1, 1));
		asteroidsOne.add(getCell(hero, 3, 6, 1, 1));
		
		ldBlock = new TextureRegion(hero, 64 * 2, 64 * 3, 64 * 2, 64 * 2);
		
		heroWalkAnimation = new Animation<TextureRegion>(1, frames);
		heroFlyAnimation = getAnimation(hero, 0, 8, 1, 1, 4);
		heroFixingAnimation = getAnimation(hero, 0, 5, 1, 1, 2);
		heroQuestioning = getCell(hero, 0, 7, 1, 1);
		heroDying = getCell(hero, 0, 12, 1, 1);
		heroHurted = getCell(hero, 0, 13, 1, 1);
		heroOK = getCell(hero, 0, 14, 1, 1);
		
		bgStars = new Texture(Gdx.files.internal("bg.png"));
		
		bgStars.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bgStars.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		jetPackAnimation = getAnimation(hero, 1, 0, 1, 1, 4);
		
		musicTitle = Gdx.audio.newMusic(Gdx.files.internal("music/title.ogg"));
		musicLevel1 = Gdx.audio.newMusic(Gdx.files.internal("music/level1.ogg"));
		musicLevel2 = Gdx.audio.newMusic(Gdx.files.internal("music/level2.ogg"));
		musicLevel3 = Gdx.audio.newMusic(Gdx.files.internal("music/level3.ogg"));
		musicIntro = musicOutro = Gdx.audio.newMusic(Gdx.files.internal("music/intro.ogg"));
		musicGameOver = musicTransition = Gdx.audio.newMusic(Gdx.files.internal("music/deep-fx.ogg"));

		
		sndBonus =  Gdx.audio.newSound(Gdx.files.internal("sfx/bonus.wav"));
		sndCrush =  Gdx.audio.newSound(Gdx.files.internal("sfx/crush-short.wav"));
		sndGameComplete =  Gdx.audio.newSound(Gdx.files.internal("sfx/game-complete.wav"));
		sndJetPack =  Gdx.audio.newSound(Gdx.files.internal("sfx/jetpack.wav"));
		sndRadio =  Gdx.audio.newSound(Gdx.files.internal("sfx/ld-radio.wav"));
		sndLevelComplete =  Gdx.audio.newSound(Gdx.files.internal("sfx/level-end.wav"));
		sndRotation =  Gdx.audio.newSound(Gdx.files.internal("sfx/rotating.wav"));
		
		
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
	
	public void playMusic(Music music){
		if(currentMusic != null){
			currentMusic.stop();
		}
		currentMusic = music;
		if(currentMusic != null){
			currentMusic.setLooping(true);
			currentMusic.play();
		}
	}
	
	public void playMusicOnce(Music music){
		if(currentMusic != null){
			currentMusic.stop();
		}
		currentMusic = music;
		if(currentMusic != null){
			currentMusic.setLooping(false);
			currentMusic.play();
		}
	}
	
	public void playSFX(Sound snd){
		snd.play(.2f); // TODO mix
	}
}
