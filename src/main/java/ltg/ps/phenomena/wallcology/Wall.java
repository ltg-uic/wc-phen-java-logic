package ltg.ps.phenomena.wallcology;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wall {
	
	public static final int HIGH_TEMP 	= 30;
	public static final int	LOW_TEMP 	= 20;
	public static final int	HIGH_LIGHT 	= 80;
	public static final int	LOW_LIGHT	= 35;
	public static final int	HIGH_HUM 	= 90;
	public static final int	LOW_HUM 	= 50;
	
	private String id = null;
	
	private int width			= 0;
	private int height			= 0;
	
	private float temperature 	= 0.0f;
	private float humidity 		= 0.0f;
	private float light 		= 0.0f;
	private Map<String, Integer> population = null;
	private List<Wall> adjacents = null;
	
	public Wall (String id) {
		this.id = id;
		population = new HashMap<String, Integer>();
	}
	
	public Wall(String id, int w, int h, float temperature, float humidity, float light, Map<String, Integer> population) {
		this.id = id;
		this.width = w;
		this.height = h;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		this.population = population;
	}
	
	public Wall(String id, float temperature, float humidity, float light) {
		this.id = id;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
		population = new HashMap<String, Integer>();
	}
	
	/**
	 * @return the wall id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the width of the wall
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @param width the width of the wall
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return the height of the wall
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @param height the height of the wall
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the temperature
	 */
	public float getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the humidity
	 */
	public float getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	/**
	 * @return the light
	 */
	public float getLight() {
		return light;
	}

	/**
	 * @param light the light to set
	 */
	public void setLight(float light) {
		this.light = light;
	}

	/**
	 * @return the population
	 */
	public Map<String, Integer> getPopulation() {
		return population;
	}

	/**
	 * @param population the population to set
	 */
	public void setPopulations(Map<String, Integer> population) {
		this.population = population;
	}
	
	/**
	 * @return the list of adjacent walls
	 */
	public List<Wall> getAdjacents() {
		return adjacents;
	}
	
	/**
	 * @param adjacents adjacent walls to set
	 */
	public void setAdjacents(List<Wall> adjacents) {
		this.adjacents = adjacents;
	}
	
	public String toString() {
		String str = "";
		str += this.id + "\n";
		str += "width: " + this.width + "\n";
		str += "height: " + this.height + "\n";
		str += "temperature " + this.temperature + "\n";
		str += "humidity: " + this.humidity + "\n";
		str += "light: " + this.light + "\n";
		for (Map.Entry<String, Integer> e : this.population.entrySet())
			str += e.getKey() + ": " + e.getValue() + "\n";
		for(Wall w : this.adjacents)
			str += "adj: " + w.getId() + "\n";		
		
		return str;
	}

	
	public void setPopulationsFromModel(int[] ca) {
		// Scum
		population.put("greenScum", ca[0]);
		// Fuzz
		population.put("fluffyMold", ca[1]);
		// Blue bugs
		int s1 = 0, s2 = 0, s3 = 0, s4 = 0;
		for(int i=0; i<ca[2]; i++) {
			switch ((int)(Math.random()*4)) {
				case 0:
					s1++;
					break;
				case 1:
					s2++;
					break;
				case 2:
					s3++;
					break;
				case 3:
					s4++;
					break;
			}
		}
		population.put("blueBug_s1", s1);
		population.put("blueBug_s2", s2);
		population.put("blueBug_s3", s3);
		population.put("blueBug_s4", s4);
		// Green bugs
		s1 = 0; s2 = 0;
		for(int i=0; i<ca[3]; i++) {
			switch ((int)(Math.random()*2)) {
			case 0:
				s1++;
				break;
			case 1:
				s2++;
				break;
			}
		}
		population.put("greenBug_s1", s1);
		population.put("greenBug_s2", s2);
		// Fuzz predator
		s1 = 0; s2 = 0;
		for(int i=0; i<ca[4]; i++) {
			switch ((int)(Math.random()*2)) {
			case 0:
				s1++;
				break;
			case 1:
				s2++;
				break;
			}
		}
		population.put("fuzzPredator_s1", s1);
		population.put("fuzzPredator_s2", s2);
	}

	public Wall copy() {
		Wall nw = new Wall(id, width, height, temperature, humidity, light, copyPopulation());
		return nw;
	}

	private Map<String, Integer> copyPopulation() {
		Map <String, Integer> popCop = new HashMap<String, Integer>();
		popCop.putAll(population);
		return popCop;
	}
	

}
