/*
 * Created Sep 24, 2011
 */
package ltg.ps.phenomena.wallcology.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Environment;
import ltg.ps.phenomena.wallcology.Wallcology;
import ltg.ps.phenomena.wallcology.WallcologyPhase;

import org.dom4j.Element;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class ChangePhase extends PhenomenaCommand {
	
	private WallcologyPhase nextPhase = null;
	private long transitionLength = -1;
	private Map<String, Environment> nextWalls = null;

	/**
	 * @param target
	 * @param origin
	 */
	public ChangePhase(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	@Override
	public void execute() {
		((Wallcology) target).changePhase(nextPhase, nextWalls, transitionLength);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	@Override
	public void parse(Element xml) {
		transitionLength = Long.parseLong(xml.attributeValue("transitionLength"));
		nextPhase = WallcologyPhase.valueOf(xml.elementTextTrim("nextPhase"));
		parseNextWalls(xml);
	}

	private void parseNextWalls(Element xml) {
		nextWalls = new HashMap<String, Environment>();
		@SuppressWarnings("unchecked")
		List<Element> wallXml = xml.element("walls").elements();
		String id = null;
		Environment env = null;
		for(Element e: wallXml) {
			id = e.attributeValue("id");
			env = new Environment(Float.valueOf(e.elementTextTrim("temperature")), 
					Float.valueOf(e.elementTextTrim("humidity")),
					Float.valueOf(e.elementTextTrim("light")));
			nextWalls.put(id, env);
		}
	}

}
