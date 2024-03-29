/*
 * Created Jan 18, 2011
 */
package ltg.ps.phenomena.wallcology.windows;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Wall;
import ltg.ps.phenomena.wallcology.Wallcology;

/**
 * WallcologyWindow implements the abstract class PhenomenaWindow to represent the wallscope client in the simulation
 *
 * @author Gugo
 */
public class WallcologyWindow extends PhenomenaWindow { //implements Observer
	
	private Wallcology wc = null;
	private Wall wallData = null;
	private String wallId = null;
	private int x = 0;
	private int y = 0;
	
	public WallcologyWindow(String windowName, int x, int y, String wall) {
		super(windowName);	
		this.x = x;
		this.y = y;
		this.wallId = wall;
	}
	
	
	public WallcologyWindow(String windowName, String wallId, int x, int y) {
		super(windowName);	
		this.wallId = wallId;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void update(Phenomena p) {
		this.wc = (Wallcology) p;
		wallData = wc.getWall(wallId);
	}

	
	@Override
	public String toXML() {
		Element root = DocumentHelper.createElement("wallcology");
		Element env = DocumentHelper.createElement("environment");
			env.addElement("temperature").addText(String.valueOf(wallData.getTemperature()));
			env.addElement("humidity").addText(String.valueOf(wallData.getHumidity()));
			env.addElement("light").addText(String.valueOf(wallData.getLight()));
			env.addAttribute("enableEdit", String.valueOf(wc.getEditEnabled()));
		Element pop = DocumentHelper.createElement("population");
		for(Map.Entry<String, Integer> m : wallData.getPopulation().entrySet()) {
			pop.addElement(m.getKey()).addText(String.valueOf(m.getValue()));
		}
		root.add(env);
		root.add(pop);
		if (wc!=null) 
			return removeXMLDeclaration(DocumentHelper.createDocument(root));
		return "";	
	}

	
	public String getWallId() {
		return this.wallId;
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	
	private String removeXMLDeclaration(Document doc) {
		StringWriter w = new StringWriter();
		OutputFormat f =  OutputFormat.createPrettyPrint();
		f.setSuppressDeclaration(true);
		XMLWriter xw = new XMLWriter(w, f);
		try {
			xw.write(doc);
		} catch (IOException e1) {
			log.error("Unable to print to an XML string? Really?");
		}
		return w.toString();
	}
	
}
