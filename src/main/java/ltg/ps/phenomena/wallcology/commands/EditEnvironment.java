/*
 * Created Apr 26, 2011
 */
package ltg.ps.phenomena.wallcology.commands;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Wall;
import ltg.ps.phenomena.wallcology.Wallcology;
import ltg.ps.phenomena.wallcology.WallcologyWindow;

import org.dom4j.Element;

/**
 * EditEnvironment is the implementation of PhenomenaCommand that allows to change 
 * the temperature, humidity and light settings of Wall associated with the PhenomenaWindow
 * which issues the command itself.
 * At this time, EditEnviroment can be fired just by the wallscope clients.
 *
 * @author Gugo
 */
public class EditEnvironment extends PhenomenaCommand {
	
	private Wall modifiedWall = null;
	private Wall originalWall = null;
	private String variable  = null;
	private float value;

	/**
	 * @param target
	 */
	public EditEnvironment(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	@Override
	public void execute() {
		if(this.origin instanceof WallcologyWindow) {
			((Wallcology)target).editEnvironment(originalWall, modifiedWall);
		}
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	@Override
	public void parse(Element e) {
		if(this.origin instanceof WallcologyWindow) {
			originalWall = ((Wallcology) target).getWall(((WallcologyWindow) this.origin).getWallId());
			modifiedWall = originalWall.copy();
			variable = e.attributeValue("variable");
			value = Float.valueOf(e.getTextTrim());
			if(variable.equals("temperature")) {
				modifiedWall.setTemperature(value);
			}
			if(variable.equals("humidity")) {
				modifiedWall.setHumidity(value);
			}
			if(variable.equals("light")) {
				modifiedWall.setLight(value);
			}
		}
		else {
			log.warn("Command " + e.getName() + " cannot be invoked from a phenomena window of type " + this.origin.getClass());
		}
	}

}
