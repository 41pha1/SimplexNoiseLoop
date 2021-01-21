package code;

public class SimplexNoiseLoop {
	
	double xpos;
	double ypos;
	double min;
	double max;
	double strength;
	
	public SimplexNoiseLoop(double min, double max, double s)
	{
		this.min = min;
		this.max = max;
		xpos = (Math.random() * 100)-50;
		ypos = (Math.random() * 100)-50;
		strength = s;
	}
	
	double noise(float a)
	{
		double x = xpos + Math.cos(a*2*Math.PI) * strength;
		double y = ypos + Math.sin(a*2*Math.PI) * strength;
		
		return (((SimplexNoise.noise(x, y)+1)/2d)*(max-min))+min;
		
	}
	
}
