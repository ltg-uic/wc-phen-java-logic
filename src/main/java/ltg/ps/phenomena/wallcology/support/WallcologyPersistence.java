/*
 * Created Nov 18, 2010
 */
package ltg.ps.phenomena.wallcology.support;

import ltg.ps.phenomena.wallcology.Wallcology;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class WallcologyPersistence extends Persistence {

	private Wallcology w = null;
	private boolean recordHistory = false;

	/**
	 * @param fileName
	 */
	public WallcologyPersistence(String fileName) {
		super(fileName);
	}
	
	
	public WallcologyPersistence(Wallcology w) {
		this(w.getInstanceName());
		this.w  = w;
	}
	

	/* (non-Javadoc)
	 * @see ltg.ps.api.Persistence#restore()
	 */
	@Override
	public void restore() {
		readFile();
		Element config = doc.getRootElement().element("config");
		Element wins = doc.getRootElement().element("windows");
		if(config!=null) {
			w.configure(config.asXML());
		}
		if(wins!=null) {
			w.configureWindows(wins.asXML());	
		}
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Persistence#save()
	 */
	@Override
	public void save() {
		try {
			doc = DocumentHelper.parseText(w.toXML());
			writeFile();
			if (recordHistory) {
				writelogFile();
			}
		} catch (DocumentException e) {
			log.info("Impossible to save Wallcology");
		}	
	}
	
	
	public void setRecordHistory(boolean record) {
		this.recordHistory = record;
	}
	
}
