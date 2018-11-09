package practice;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MyLocationPractice extends PApplet {
	UnfoldingMap map;
	Location loc = new Location(28.599268, 77.304056);
	int red = color(255, 0, 0);
	SimplePointMarker mk;
	
	public void setup() {
		size(1000, 800, OPENGL);
		int zoomLevel = 17;
		map = new UnfoldingMap(this, 50, 50, 900, 700, new Microsoft.HybridProvider());
		map.zoomAndPanTo(zoomLevel, loc);
		MapUtils.createDefaultEventDispatcher(this, map);
		mk = new SimplePointMarker(loc);
		mk.setColor(red);
		mk.setRadius(10);
		map.addMarker(mk);
	}
	
	public void draw() {
		map.draw();
		drawButtons();
	}
	
	private void drawButtons() {
		fill(255,255,255);
		rect(100,100,25,25);
		
		fill(100,100,100);
		rect(100,150,25,25);
	}
	
	public void mouseReleased() {
		if(mouseX > 100 && mouseX < 125 && mouseY > 100 && mouseY < 125)
			background(255, 255, 255);
		else if(mouseX > 100 && mouseX < 125 && mouseY > 150 && mouseY < 175)
			background(100, 100, 100);
	}
}