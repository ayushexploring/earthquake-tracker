package practice;

//Java utilities libraries
import java.util.ArrayList;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

public class EarthquakeCityMapPractice extends PApplet {
	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;
	private static final boolean offline = false;
	public static final float THRESHOLD_MODERATE = 5;
	public static final float THRESHOLD_LIGHT = 4;
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	private UnfoldingMap map;
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	private int yellow = color(255, 255, 0);
	private int red = color(255, 0, 0);
	private int blue = color(0, 0, 255);
	private int black = color(0, 0, 0);
	private int white = color(255, 255, 255);
	
	public void setup() {
		size(1400, 900, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";
		}
		else {
			map = new UnfoldingMap(this, 300, 50, 1050, 800, new Microsoft.HybridProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
	    List<Marker> markers = new ArrayList<Marker>();
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    for(PointFeature pf: earthquakes) {
	    	markers.add(createMarker(pf));
	    }
	    map.addMarkers(markers);
	}
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		System.out.println(feature.getProperties());
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
	    if(mag <= THRESHOLD_LIGHT) {
	    	marker.setColor(blue);
	    	marker.setRadius(7);
	    }
	    else if(mag > THRESHOLD_LIGHT && mag < THRESHOLD_MODERATE) {
	    	marker.setColor(yellow);
	    	marker.setRadius(10);
	    }
	    else if(mag >= THRESHOLD_MODERATE) {
	    	marker.setColor(red);
	    	marker.setRadius(15);
	    }
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}
	private void addKey() {	
		fill(white);
		rect(30, 50, 250, 500, 10);
		
		fill(black);
		textSize(25);
		text("Earthquake Key", 60, 90);
		
		fill(red);
		ellipse(60, 190, 15, 15);
		
		fill(black);
		textSize(20);
		text("Magnitude > 5.0", 80, 195);
		
		fill(yellow);
		ellipse(60, 290, 11, 11);
		
		fill(black);
		textSize(20);
		text("Between 4.0 & 4.9", 80, 295);
		
		fill(blue);
		ellipse(60, 390, 8, 8);
		
		fill(black);
		textSize(20);
		text("Magnitude < 4.0", 80, 395);
	}
}