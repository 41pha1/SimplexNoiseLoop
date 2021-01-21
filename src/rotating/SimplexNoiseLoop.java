package rotating;

public class SimplexNoiseLoop
{

	double xpos;
	double ypos;
	double min;
	double max;
	double strength;

	public SimplexNoiseLoop(double min, double max, double s)
	{
		this.min = min;
		this.max = max;
		xpos = (Math.random() * 100) - 50;
		ypos = (Math.random() * 100) - 50;
		strength = s;
	}

	float noise(float a, float off)
	{
		float x = (float) (xpos + Math.cos(a * 2 * Math.PI) * strength);
		float y = (float) (ypos + Math.sin(a * 2 * Math.PI) * strength);

		return (float) ((((SimplexNoise.noise(x, y, off) + 1) / 2d) * (max - min)) + min);
	}
}
