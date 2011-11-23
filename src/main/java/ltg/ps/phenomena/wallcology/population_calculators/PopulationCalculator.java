package ltg.ps.phenomena.wallcology.population_calculators;

import java.util.List;

import ltg.ps.phenomena.wallcology.Wall;
import ltg.ps.phenomena.wallcology.WallcologyPhase;

public interface PopulationCalculator {
	
	public void updatePopulationStable(List<Wall> currentPhaseWalls, WallcologyPhase currentPhase);
	public void updatePopulationTransit(WallcologyPhase nextPhase, List<Wall> walls, List<Wall> prevPhaseWalls, List<Wall> nextPhaseWalls, long totTransTime, long elapsedTransTime);
}
