package party;

public class SimplexNoiseLoop {
	
	double xpos;
	double ypos;
	double strength;
	
	public SimplexNoiseLoop(double s)
	{
		xpos = (Math.random() * 100)-50;
		ypos = (Math.random() * 100)-50;
		strength = s;
	}
	
	double noise(float a)
	{
		double x = xpos + Math.cos(a*2*Math.PI) * strength;
		double y = ypos + Math.sin(a*2*Math.PI) * strength;
		
		return SimplexNoise.noise(x, y);
		
	}
	
}
