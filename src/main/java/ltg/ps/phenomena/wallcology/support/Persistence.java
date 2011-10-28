/*
 * Created Jun 15, 2010
 */
package ltg.ps.phenomena.wallcology.support;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage the configuration file of a particular phenomena pod.
 *
 * @author Gugo
 */
public abstract class Persistence {

	protected Logger log = LoggerFactory.getLogger(Persistence.class);
	protected String fileName = null;
	private File file = null;
	protected Document doc = null;


	public Persistence(String fileName) {
		this.fileName = fileName;
		String dbLocation = System.getProperty("DB_LOCATION");
		if (dbLocation==null) {
			dbLocation = "src/main/db";
			log.info("Reset database location to default.");
		}
		file = new File(dbLocation + File.separator + fileName + ".xml");
		loadFile();
	}

	private void loadFile() {
		if (checkForFile()) {
			// File exists
			log.info("Persistence file "+getFileName()+" found.");
		} else {
			createFile();
			initFile();
			log.info("The persistence file doesn't exist. " +
			"We created a brand new one for you.");
		}
	}

	private void createFile() {
		try {
			file.createNewFile();
		} catch (IOException e) {
			log.error("Impossible to create the persistence file. Terminating...");
			System.exit(-1);
		}
	}
	
	private void initFile() {
		doc = DocumentHelper.createDocument();
		doc.addElement(fileName);
		writeFile();
	}

	private boolean checkForFile() {
		return file.canRead()&&file.canWrite();
	}

	private String getFileName() {
		return fileName;
	}
	
	
	protected void readFile() {
		SAXReader r = new SAXReader();
		try {
			doc = r.read(file);
		} catch (DocumentException e) {
			log.error("Impossible to parse " + fileName);
		}
	}
	
	
	protected void writeFile() {
		XMLWriter out = null;
		try {
			out = new XMLWriter(new FileOutputStream(file), OutputFormat.createPrettyPrint());
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
	
	
	public void cleanup() {
		file.delete();
	}


	public abstract void restore();

	public abstract void save();

}
