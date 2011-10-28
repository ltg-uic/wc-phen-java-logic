/*
 * Created Sep 20, 2011
 */
package ltg.ps.phenomena.wallcology.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltg.ps.phenomena.wallcology.Wall;
import ltg.ps.phenomena.wallcology.Wallcology;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class DBCalculator {
	
	// Logger
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

	// Queries
	private static final String STM_DATA = "select dp99 FROM data where id=?";
	private static final String STM_STABLE4 = "SELECT scum_data, fuzz_data, scum_eaters_data, " +
			"fuzz_eaters_data, predator_data FROM experiments WHERE " +
			"scum<>0 AND fuzz<>0 AND scum_eaters<>0 AND fuzz_eaters<>0 AND predator=0 AND " +
			"temperature=? AND light=? AND humidity=?";
	private static final String STM_STABLE5 = "SELECT scum_data, fuzz_data, scum_eaters_data, " +
			"fuzz_eaters_data, predator_data FROM experiments WHERE " +
			"scum<>0 AND fuzz<>0 AND scum_eaters<>0 AND fuzz_eaters<>0 AND predator<>0 AND " +
			"temperature=? AND light=? AND humidity=?";
	//DB connection & stuff
	private Connection conn = null;
	private PreparedStatement ps_data = null;
	private PreparedStatement ps_exp4 = null;
	private PreparedStatement ps_exp5 = null;
	// Popupation stuff
	private double noisePercent = 0.04; 

	public DBCalculator() {
		// Setup DB
		try {
			conn = DriverManager.getConnection("jdbc:mysql://carbon.evl.uic.edu/" +
					"wallcology_microworlds?" +
					"user=wallcology&" +
					"password=CAT.evl");
			ps_data = conn.prepareStatement(STM_DATA);
			ps_exp4 = conn.prepareStatement(STM_STABLE4);
			ps_exp5 = conn.prepareStatement(STM_STABLE5);
		} catch (SQLException e) {
			log.error("Impossible to setup DB!", e);
		}
	}
	
	
	/**
	 * Called whenever we are updating a stable phase
	 */
	public void updateStableEnvironment(List<Wall> currentPhaseWalls, String currentPhase) {
		for (Wall w: currentPhaseWalls) {
			// Stable phase with 4 creatures
			if (currentPhase.equals(Wallcology.STABLE4_PHASE)) {
				updateWallStable(w, 4);
			}
			// Stable phase during the hiccup
			if (currentPhase.equals(Wallcology.HICCUP_PHASE)) {
				// Uses same function because data is coded into walls!
				updateWallStable(w, 4);
			}
			// Stable phase during predator
			if (currentPhase.equals(Wallcology.STABLE5_PHASE)) {
				updateWallStable(w, 5);
			}
		}
	}


	/**
	 * Called whenever we are transiting from a phase to another
	 */
	public void updateTransEnvironment(String nextPhase, List<Wall> walls, List<Wall> prevPhaseWalls, List<Wall> nextPhaseWalls, long totTransTime, long elapsedTransTime) {
		for (int i=0;i<walls.size();i++) {
				if (totTransTime==-1 || elapsedTransTime== -1) {
					log.error("Total and/or elapsed transition times are -1");
				}
				updateWallTransition(nextPhase, walls.get(i), prevPhaseWalls.get(i), nextPhaseWalls.get(i), totTransTime, elapsedTransTime);
		}
	}


	
	private void updateWallStable(Wall w, int wn) {
		// Get "experiment" data
		int[] ca = new int[5];
		if (wn==4)
			ca = getExperiment(w, 4);
		if (wn==5)
			ca = getExperiment(w, 5);
		// Add noise
		ca = addNoise(ca);
		// Set values
		w.setPopulationsFromModel(ca);
	}



	private void updateWallTransition(String nextPhase, Wall cw, Wall pw, Wall nw, long totTransTime, long elapsedTransTime) {
		// Get "experiment" data for previous wall
		int[] pp = new int[5];
		if(pw.getPopulation().get("fuzzPredator_s1")==0)
			pp = getExperiment(pw, 4);
		else
			pp = getExperiment(pw, 5);
		// Get "experiment" data for next wall
		int[] np = new int[5];
		if(nextPhase.equals(Wallcology.HICCUP_PHASE)) {
			np = getExperiment(nw, 4);
		}
		if(nextPhase.equals(Wallcology.STABLE4_PHASE)) {
			np = getExperiment(nw, 4);
		}
		if(nextPhase.equals(Wallcology.STABLE5_PHASE)) {
			np = getExperiment(nw, 5);
		}	
		// Interpolate
		int[] ca = new int[5];
		double progressRatio = ((double) elapsedTransTime / (double) totTransTime); 
		for (int i=0; i<5;i++) {
			double delta = (double) (np[i] - pp[i]); 
			ca[i] = pp[i] + (int) Math.round(progressRatio * delta);
		}
		// However... if time elapsed is 0 we want to return the population  
		// of the previous phase since the transition didn't start yet...
		if (elapsedTransTime==0)
			ca = pp;
		// Add noise
		ca = addNoise(ca);
		// Set values
		cw.setPopulationsFromModel(ca);
	}



	private int[] getExperiment(Wall w, int cn) {
		ResultSet rs = null;
		ResultSet rs1 = null;
		int[] ca = new int[5];
		try {
			if(cn==4)
				rs = execStm(ps_exp4, w);
			else if (cn==5) 
				rs = execStm(ps_exp5, w);
			rs.next();
			for(int i=1; i<=5; i++) {
				// Get amount of each creature
				ps_data.setInt(1, rs.getInt(i));
				rs1 = ps_data.executeQuery();
				rs1.next();
				ca[i-1] = rs1.getInt("dp99");
			}
		} catch (SQLException e) {
			log.error("Impossible to assign value to preapred statement");
		}
		return ca;
	}
	
	
//	private int[] getExperiment(int t, int l, int h, int cn) {
//		ResultSet rs = null;
//		ResultSet rs1 = null;
//		int[] ca = new int[5];
//		try {
//			if(cn==4) {
//				ps_exp4.setInt(1, t);
//				ps_exp4.setInt(2, l);
//				ps_exp4.setInt(3, h);
//				rs = ps_exp4.executeQuery();
//			} else if (cn==5) { 
//				ps_exp5.setInt(1, t);
//				ps_exp5.setInt(2, l);
//				ps_exp5.setInt(3, h);
//				rs = ps_exp5.executeQuery();
//			}
//			rs.next();
//			for(int i=1; i<=5; i++) {
//				// Get amount of each creature
//				ps_data.setInt(1, rs.getInt(i));
//				rs1 = ps_data.executeQuery();
//				rs1.next();
//				ca[i-1] = rs1.getInt("dp99");
//			}
//		} catch (SQLException e) {
//			log.error("Impossible to assign value to preapred statement");
//		}
//		return ca;
//	}
	
	
	private ResultSet execStm(PreparedStatement ps_exp4, Wall w) throws SQLException {
		if(((int) w.getTemperature()) == Wall.HIGH_TEMP)
			ps_exp4.setInt(1, 1);
		else
			ps_exp4.setInt(1, 0);

		if(((int) w.getLight()) == Wall.HIGH_LIGHT)
			ps_exp4.setInt(2, 1);
		else
			ps_exp4.setInt(2, 0);

		if(((int) w.getHumidity()) == Wall.HIGH_HUM)
			ps_exp4.setInt(3, 1);
		else
			ps_exp4.setInt(3, 0);
		return ps_exp4.executeQuery();
	}



	



	private int[] addNoise(int[] ca) {
		// Randomize +/- noisePercent
		double dev;
		for(int i=0; i<5; i++) {
			dev = Math.random()*((double)ca[i])*noisePercent;
			if(Math.random()<.5) {
				ca[i] = (int) Math.round((((double)ca[i]) + dev));
			} else {
				ca[i] = (int) Math.round((((double)ca[i]) - dev));
			}
		}
		return ca;
	}

}
