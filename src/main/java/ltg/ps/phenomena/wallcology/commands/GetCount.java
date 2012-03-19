/*
 * Created Mar 19, 2012
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
public class GetCount extends PhenomenaCommand {
	
	private String wallId = null;
	private String reqId = null;

	public GetCount(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	@Override
	public void execute() {
		((Wallcology)target).setGetCountWallId(wallId, reqId);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	@Override
	public void parse(Element e) {
		wallId = e.attributeValue("wall");
		reqId = e.attributeValue("reqId");
	}

}
