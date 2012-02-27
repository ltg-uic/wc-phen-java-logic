package ltg.ps.phenomena.wallcology.domain;

import java.util.List;

public class Species {
	private String id = null;
	private double idealTemperature = 0;
	private int idealHumidity = 0;
	private double idealLight = 0;
	private List<String> food = null;
	private float temperatureSpeed = 0;
	private float humiditySpeed = 0;
	private float lightSpeed = 0;
	private float predatorSpeed = 0;
	private float noFoodSpeed = 0;
	
	
	public Species(String id, 
			double idealTemperature, 
			int idealHumidity, 
			double idealLight, 
			List<String> food, 
			float temperatureSpeed,	
			float humiditySpeed, 
			float lightSpeed, 
			float predatorSpeed, 
			float noFoodSpeed) {
		
		this.id = id;
		this.idealTemperature = idealTemperature;
		this.idealHumidity = idealHumidity;
		this.idealLight = idealLight;
		this.food = food;
		this.temperatureSpeed = temperatureSpeed;
		this.humiditySpeed = humiditySpeed;
		this.lightSpeed = lightSpeed;
		this.predatorSpeed = predatorSpeed;
		this.noFoodSpeed = noFoodSpeed;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the idealTemperature
	 */
	public double getIdealTemperature() {
		return idealTemperature;
	}


	/**
	 * @param idealTemperature the idealTemperature to set
	 */
	public void setIdealTemperature(double idealTemperature) {
		this.idealTemperature = idealTemperature;
	}


	/**
	 * @return the idealHumidity
	 */
	public int getIdealHumidity() {
		return idealHumidity;
	}


	/**
	 * @param idealHumidity the idealHumidity to set
	 */
	public void setIdealHumidity(int idealHumidity) {
		this.idealHumidity = idealHumidity;
	}


	/**
	 * @return the idealLight
	 */
	public double getIdealLight() {
		return idealLight;
	}


	/**
	 * @param idealLight the idealLight to set
	 */
	public void setIdealLight(double idealLight) {
		this.idealLight = idealLight;
	}


	/**
	 * @return the food
	 */
	public List<String> getFood() {
		return food;
	}


	/**
	 * @param food the food to set
	 */
	public void setFood(List<String> food) {
		this.food = food;
	}


	/**
	 * @return the temperatureSpeed
	 */
	public float getTemperatureSpeed() {
		return temperatureSpeed;
	}


	/**
	 * @param temperatureSpeed the temperatureSpeed to set
	 */
	public void setTemperatureSpeed(float temperatureSpeed) {
		this.temperatureSpeed = temperatureSpeed;
	}


	/**
	 * @return the humiditySpeed
	 */
	public float getHumiditySpeed() {
		return humiditySpeed;
	}


	/**
	 * @param humiditySpeed the humiditySpeed to set
	 */
	public void setHumiditySpeed(float humiditySpeed) {
		this.humiditySpeed = humiditySpeed;
	}


	/**
	 * @return the lightSpeed
	 */
	public float getLightSpeed() {
		return lightSpeed;
	}


	/**
	 * @param lightSpeed the lightSpeed to set
	 */
	public void setLightSpeed(float lightSpeed) {
		this.lightSpeed = lightSpeed;
	}


	/**
	 * @return the predatorSpeed
	 */
	public float getPredatorSpeed() {
		return predatorSpeed;
	}


	/**
	 * @param predatorSpeed the predatorSpeed to set
	 */
	public void setPredatorSpeed(float predatorSpeed) {
		this.predatorSpeed = predatorSpeed;
	}


	/**
	 * @return the noFoodSpeed
	 */
	public float getNoFoodSpeed() {
		return noFoodSpeed;
	}


	/**
	 * @param noFoodSpeed the noFoodSpeed to set
	 */
	public void setNoFoodSpeed(float noFoodSpeed) {
		this.noFoodSpeed = noFoodSpeed;
	}	
	
}
