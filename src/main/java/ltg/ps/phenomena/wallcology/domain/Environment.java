/*
 * Created Sep 25, 2011
 */
package ltg.ps.phenomena.wallcology.domain;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class Environment {
	
	public static final int HIGH_TEMP 	= 25;
	public static final int	LOW_TEMP 	= 20;
	public static final int	HIGH_LIGHT 	= 60;
	public static final int	LOW_LIGHT	= 45;
	public static final int	HIGH_HUM 	= 98;
	public static final int	LOW_HUM 	= 50;
	
	private float temperature 	= 0.0f;
	private float humidity 		= 0.0f;
	private float light 		= 0.0f;
	
	public Environment(float temperature, float humidity, float light) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public float getHumidity() {
		return humidity;
	}
	
	public float getLight() {
		return light;
	}
	

}
