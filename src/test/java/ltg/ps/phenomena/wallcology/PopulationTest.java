/*
 * Created Apr 19, 2012
 */
package ltg.ps.phenomena.wallcology;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import ltg.ps.phenomena.wallcology.population_calculators.HardCodedCalculator;
import ltg.ps.phenomena.wallcology.population_calculators.PopulationCalculator;

import org.junit.Test;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class PopulationTest {

	@Test
	public final void driftTestDay() {
		int minInADay = 60 * 24;
		List <Wall> w = simulateWalls(minInADay);
		// Check that scum and fuzz values are within boundaries
		for (int k=0; k < 4; k++) {
			assertTrue(w.get(k).getPopulation().get("greenScum") <= (1+(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][0]);
			assertTrue(w.get(k).getPopulation().get("greenScum") >= (1-(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][0]);
			assertTrue(w.get(k).getPopulation().get("fluffyMold") <= (1+(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][1]);
			assertTrue(w.get(k).getPopulation().get("fluffyMold") >= (1-(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][1]);
		}
	}


	@Test
	public final void driftTestMonth() {
		int minInAMonth = 60 * 24 * 30;
		List <Wall> w = simulateWalls(minInAMonth);
		// Check that scum and fuzz values are within boundaries
		for (int k=0; k < 4; k++) {
			assertTrue(w.get(k).getPopulation().get("greenScum") <= (1+(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][0]);
			assertTrue(w.get(k).getPopulation().get("greenScum") >= (1-(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][0]);
			assertTrue(w.get(k).getPopulation().get("fluffyMold") <= (1+(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][1]);
			assertTrue(w.get(k).getPopulation().get("fluffyMold") >= (1-(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][1]);
		}
	}


	@Test
	public final void driftTestYear() {
		int minInAYear = 60 * 24 * 365;
		List <Wall> w = simulateWalls(minInAYear);
		// Check that scum and fuzz values are within boundaries
		for (int k=0; k < 4; k++) {
			assertTrue(w.get(k).getPopulation().get("greenScum") <= (1+(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][0]);
			assertTrue(w.get(k).getPopulation().get("greenScum") >= (1-(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][0]);
			assertTrue(w.get(k).getPopulation().get("fluffyMold") <= (1+(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][1]);
			assertTrue(w.get(k).getPopulation().get("fluffyMold") >= (1-(PopulationCalculator.noisePercent+0.05)) * HardCodedCalculator.wall_population[k][1]);
		}
	}



	private List<Wall> simulateWalls(int minutes) {
		List<Wall> walls = new ArrayList<Wall>();
		HardCodedCalculator pc = new HardCodedCalculator();
		walls.add(new Wall("1"));
		walls.add(new Wall("2"));
		walls.add(new Wall("3"));
		walls.add(new Wall("4"));
		// Run the population update for a whole day 
		for(int i=0; i<minutes; i++) {
			pc.updatePopulationStable(walls, WallcologyPhase.STABLE_4_PHASE);
			// Print the calculated values
//			System.out.println(	walls.get(0).getPopulation().get("greenScum") + " " + walls.get(0).getPopulation().get("fluffyMold") + " - " +
//					walls.get(1).getPopulation().get("greenScum") + " " + walls.get(1).getPopulation().get("fluffyMold") + " - " +
//					walls.get(2).getPopulation().get("greenScum") + " " + walls.get(2).getPopulation().get("fluffyMold") + " - " +
//					walls.get(3).getPopulation().get("greenScum") + " " + walls.get(3).getPopulation().get("fluffyMold"));
		}
		// Return the wall values
		return walls;
	}

}
