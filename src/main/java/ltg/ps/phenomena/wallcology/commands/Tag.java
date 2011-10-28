package ltg.ps.phenomena.wallcology.commands;

import org.dom4j.Element;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.wallcology.TaggedCreature;
import ltg.ps.phenomena.wallcology.Wallcology;
import ltg.ps.phenomena.wallcology.WallcologyWindow;

public class Tag extends PhenomenaCommand {
	
	private TaggedCreature tagged = null;

	/**
	 * @param target
	 */
	public Tag(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
	}
	
	@Override
	public void execute() {
		if(this.origin instanceof WallcologyWindow) {
			((Wallcology) target).tagCreature(tagged);
		}
	}

	@Override
	public void parse(Element e) {
		if(this.origin instanceof WallcologyWindow) {
			String color = e.attributeValue("color");
			String wall = ((WallcologyWindow) this.origin).getWallId();
			int x = ((WallcologyWindow) this.origin).getX();
			int y = ((WallcologyWindow) this.origin).getY();
			String species = e.getTextTrim();
			tagged = new TaggedCreature(species, wall, x, y, color);
		}
		else {
			log.warn("Command " + e.getName() + " cannot be invoked from a phenomena window of type " + this.origin.getClass());
		}
	}

}
