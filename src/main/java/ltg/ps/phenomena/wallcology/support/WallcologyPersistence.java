/*
 * Created Nov 18, 2010
 */
package ltg.ps.phenomena.wallcology.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import ltg.ps.phenomena.wallcology.Wallcology;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class WallcologyPersistence extends Persistence {

	private Wallcology w = null;

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
			writelogFile();
		} catch (DocumentException e) {
			log.info("Impossible to save Wallcology");
		}	
	}
	
	
	/* Adding the support for a continuous log of the simulation state.
	 * The log file name has the following format: InstanceName_log_YYYY-MM-DD@HH:MM:SS.xml
	 * and will be found in the DB folder.
	 */
	private void writelogFile() {
		//load
		String dbLocation = System.getProperty("DB_LOCATION");
		if (dbLocation==null) {
			dbLocation = "src/main/db";
			log.info("Reset database location to default.");
		}
		dbLocation = dbLocation.concat("/history");
		Calendar calendar = Calendar.getInstance();
		String year = "" + calendar.get(Calendar.YEAR);
		String month = "" + (((calendar.get(Calendar.MONTH)+1)<10)?("0"+(calendar.get(Calendar.MONTH)+1)):(calendar.get(Calendar.MONTH)+1));
		String day = "" + ((calendar.get(Calendar.DAY_OF_MONTH)<10)?("0"+calendar.get(Calendar.DAY_OF_MONTH)):calendar.get(Calendar.DAY_OF_MONTH));
		String hours = "" + ((calendar.get(Calendar.HOUR_OF_DAY)<10)?("0"+calendar.get(Calendar.HOUR_OF_DAY)):calendar.get(Calendar.HOUR_OF_DAY));
		String minutes = "" + ((calendar.get(Calendar.MINUTE)<10)?("0"+calendar.get(Calendar.MINUTE)):calendar.get(Calendar.MINUTE));
		String seconds = "" + ((calendar.get(Calendar.SECOND)<10)?("0"+calendar.get(Calendar.SECOND)):calendar.get(Calendar.SECOND));
		String logfilename = fileName + "_log_" + year + "-" + month + "-" + day + "@" + hours + ":" + minutes + ":" + seconds;
		
		//create
		File logfile = new File(dbLocation + File.separator + logfilename + ".xml");
		try {
			logfile.createNewFile();
		} catch (IOException e) {
			log.error("Impossible to create the persistence log file. Terminating...");
			System.exit(-1);
		}
		
		//write
		XMLWriter out = null;
		try {
			out = new XMLWriter(new FileOutputStream(logfile), OutputFormat.createPrettyPrint());
			out.write(doc);
			out.close();
		} catch (UnsupportedEncodingException e) {
			log.error("Encoding problems...");
		} catch (FileNotFoundException e) {
			log.error("Impossible to create the XML writer");
		} catch (IOException e) {
			log.error("Impossible to write initialization XML on files");
		}
	}
}
