package ltg.ps.phenomena.wallcology.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Wall;
import ltg.ps.phenomena.wallcology.Wallcology;
import ltg.ps.phenomena.wallcology.WallcologyMicroWindow;

import org.dom4j.Element;

public class StartExperiment extends PhenomenaCommand {
	
	private Wall microworld = null;

	
	/**
	 * @param target
	 */
	public StartExperiment(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
	}

	@Override
	public void execute() {
		if(this.origin instanceof WallcologyMicroWindow) {
			((Wallcology) target).startExperiment(microworld);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void parse(Element e) {
		if(this.origin instanceof WallcologyMicroWindow) {
			microworld = ((WallcologyMicroWindow) this.origin).getEnvironment();
			Map<String, Integer> wallpopulation = new HashMap<String, Integer>();
			List<Element> creatures = e.element("population").elements();
			for (Element el2: creatures) {
				wallpopulation.put(
					el2.getName(),
					Integer.valueOf(el2.getTextTrim())
					);
			}
			
			microworld.setTemperature(Float.valueOf(e.element("environment").elementTextTrim("temperature")));
			microworld.setHumidity(Float.valueOf(e.element("environment").elementTextTrim("humidity")));
			microworld.setLight(Float.valueOf(e.element("environment").elementTextTrim("light")));
			microworld.setPopulations(wallpopulation);

		}
		else {
			log.warn("Command " + e.getName() + " cannot be invoked from a phenomena window of type " + this.origin.getClass());
		}
	}
}
