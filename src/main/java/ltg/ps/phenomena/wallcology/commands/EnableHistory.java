/*
 * Created Sep 27, 2011
 */
package ltg.ps.phenomena.wallcology.commands;

import org.dom4j.Element;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Wallcology;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class EnableHistory extends PhenomenaCommand {

	/**
	 * @param target
	 * @param origin
	 */
	public EnableHistory(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
	}
	
	// Enabled or disabled
	private boolean value;

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	@Override
	public void execute() {
		((Wallcology)target).setEnabledHistory(value);
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	@Override
	public void parse(Element xml) {
		value = Boolean.parseBoolean(xml.getTextTrim());
	}

}
