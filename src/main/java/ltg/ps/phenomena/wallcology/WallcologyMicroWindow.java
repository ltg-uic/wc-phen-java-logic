/*
 * Created Apr 26, 2011
 */
package ltg.ps.phenomena.wallcology;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaWindow;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class WallcologyMicroWindow extends PhenomenaWindow {
	
	private Wallcology wc = null;
	@SuppressWarnings("unused")
	private Wall wall = null;
	
	public WallcologyMicroWindow(String windowName) {
		super(windowName);
	}

	
	@Override
	public String toXML() {
		if (wc!=null) 
			return "<updatePhenomena>" + wc.toXML() + "</updatePhenomena>";	
		return "";
	}

	
	@Override
	public void update(Phenomena p) {
		this.wc = (Wallcology) p;
	}
	
	public Wall getEnvironment() {
		for(Wall w : this.wc.getMicroworlds()) {
			if(w.getId().equals(this.windowId))
				return w;
		}
		return null;
	}
	
	public void setWall(Wall w) {
		this.wall = w;
	}
}
