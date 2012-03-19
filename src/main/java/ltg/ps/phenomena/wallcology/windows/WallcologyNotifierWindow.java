package ltg.ps.phenomena.wallcology.windows;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Wall;
import ltg.ps.phenomena.wallcology.Wallcology;

public class WallcologyNotifierWindow extends PhenomenaWindow {
	
	private String wallId = null;
	private Wall w = null;
	
	public WallcologyNotifierWindow(String windowName) {
		super(windowName);
	}
	
	public void setWallId(String w) {
		this.wallId = w;
	}

	@Override
	public String toXML() {
		String xml = null;
		if (w==null)
			return xml;
		int bluebugs = w.getPopulation().get("blueBug_s1") + w.getPopulation().get("blueBug_s2") 
				+ w.getPopulation().get("blueBug_s3") + w.getPopulation().get("blueBug_s4");
		int greenbugs = w.getPopulation().get("greenBug_s1") + w.getPopulation().get("greenBug_s1");
		int predator = w.getPopulation().get("fuzzPredator_s1") + w.getPopulation().get("fuzzPredator_s2"); 
		xml =	"<getCount wall=\"" + wallId + "\" >" + 
					"<temperature>" + w.getTemperature() + "</temperature>" +
					"<humidity>" + w.getHumidity() +"</humidity>" +
					"<light>" + w.getLight() + "</light>" +
					"<greenScum>" +
						"<amount>" + w.getPopulation().get("greenScum") + "</amount>" +
					"</greenScum>" +
					"<fluffyMold>" +
						"<amount>" + w.getPopulation().get("fluffyMold") + "</amount>" +
					"</fluffyMold>" +
					"<blueBug>" +
						"<amount>" + bluebugs + "</amount>" +
					"</blueBug>" +
					"<greenBug>" +
						"<amount>" + greenbugs + "</amount>" +
					"</greenBug>" +
					"<fuzzPredator>" +
						"<amount>" + predator + "</amount>" +
					"</fuzzPredator>" +
				"</getCount>";
		return xml;
	}

	
	@Override
	public void update(Phenomena p) {
		if (wallId!=null)
			w = ((Wallcology)p).getWall(wallId);
	}
}
