package ltg.ps.phenomena.wallcology;

public class TaggedCreature {
	private String species = null;
	
	private String curWall = null;
	private int curX = 0;
	private int curY = 0;
	
	private String color = null;
	
	public TaggedCreature(String species, String wall, int x, int y, String color) {
		this.species = species;
		this.curWall = wall;
		this.curX = x;
		this.curY = y;
		this.color = color;
	}

	/**
	 * @return the species
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * @param species the species to set
	 */
	public void setSpecies(String species) {
		this.species = species;
	}

	/**
	 * @return the curWall
	 */
	public String getCurWall() {
		return curWall;
	}

	/**
	 * @param curWall the curWall to set
	 */
	public void setCurWall(String curWall) {
		this.curWall = curWall;
	}

	/**
	 * @return the curX
	 */
	public int getCurX() {
		return curX;
	}

	/**
	 * @param curX the curX to set
	 */
	public void setCurX(int curX) {
		this.curX = curX;
	}

	/**
	 * @return the curY
	 */
	public int getCurY() {
		return curY;
	}

	/**
	 * @param curY the curY to set
	 */
	public void setCurY(int curY) {
		this.curY = curY;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
}
