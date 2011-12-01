/*
 * Created Apr 26, 2011
 */
package ltg.ps.phenomena.wallcology.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

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
	private String sValue = null;

	// DB stuff
	private Connection conn = null;
	private PreparedStatement ps_log = null;
	private static final String STM_LOG = "INSERT INTO action_log (ts, wall_id, variable, value)" +
			"VALUES (?, ?, ?, ?)";

	/**
	 * @param target
	 */
	public EditEnvironment(Phenomena target, PhenomenaWindow origin) {
		super(target, origin);
		// Setup DB
		try {
			conn = DriverManager.getConnection("jdbc:mysql://carbon.evl.uic.edu/" +
					"wallcology_actions_log?" +
					"user=wallcology&" +
					"password=CAT.evl");
			ps_log = conn.prepareStatement(STM_LOG);
		} catch (SQLException e) {
			log.error("Impossible to setup DB!", e);
		}
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	@Override
	public void execute() {
		if(this.origin instanceof WallcologyWindow) {
			((Wallcology)target).editEnvironment(originalWall, modifiedWall);
			try {
				ps_log.setString(1, new Date().toString());
				ps_log.setString(2, this.origin.getWindowId());
				ps_log.setString(3, variable);
				ps_log.setString(4, sValue);
				ps_log.executeUpdate();
			} catch (SQLException e) {
				log.error("Impossible to update environmental condition! " + e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	@Override
	public void parse(Element e) {
		if(this.origin instanceof WallcologyWindow) {
			variable = e.attributeValue("variable");
			sValue = e.getTextTrim();
			value = Float.valueOf(sValue);
			originalWall = ((Wallcology) target).getWall(((WallcologyWindow) this.origin).getWallId());
			modifiedWall = originalWall.copy();
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
