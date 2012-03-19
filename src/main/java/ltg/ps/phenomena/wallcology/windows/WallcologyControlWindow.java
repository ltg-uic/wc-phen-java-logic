package ltg.ps.phenomena.wallcology.windows;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.Wallcology;

public class WallcologyControlWindow extends PhenomenaWindow {
	
	private Wallcology wc = null;

	public WallcologyControlWindow(String windowName) {
		super(windowName);
	}

	@Override
	public String toXML() {
		if (wc!=null)
			return wc.toXML();
		return "";
	}

	@Override
	public void update(Phenomena p) {
		this.wc = (Wallcology) p;

	}
}
