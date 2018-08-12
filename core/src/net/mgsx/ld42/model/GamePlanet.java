package net.mgsx.ld42.model;

public class GamePlanet {
	
	public boolean keyComplete, screwComplete, boltComplete;
	
	public boolean isComplete(){
		return keyComplete && screwComplete && boltComplete;
	}

	public int getArtifactsCompleted() {
		int n = 0;
		if(keyComplete) n++;
		if(screwComplete) n++;
		if(boltComplete) n++;
		return n;
	}
}
