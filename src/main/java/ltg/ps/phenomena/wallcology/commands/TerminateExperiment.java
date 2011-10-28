package ltg.ps.phenomena.wallcology.commands;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Wallcology;
import ltg.ps.phenomena.wallcology.WallcologyMicroWindow;

import org.dom4j.Element;

public class TerminateExperiment extends PhenomenaCommand {

	
	/**
	 * @param target
	 */
	public TerminateExperiment(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
	}

	@Override
	public void execute() {
		if(this.origin instanceof WallcologyMicroWindow) {
			((Wallcology) target).terminateExperiment(((WallcologyMicroWindow) this.origin).getEnvironment());
		}
	}

	@Override
	public void parse(Element e) {
		if(!(this.origin instanceof WallcologyMicroWindow)) {
			log.warn("Command " + e.getName() + " cannot be invoked from a phenomena window of type " + this.origin.getClass());
		}
	}
}
