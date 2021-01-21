package HD;

public class SimplexNoiseLoop
{

	double xpos;
	double ypos;
	double strength;
	double scale;

	public SimplexNoiseLoop(double s, double scale)
	{
		xpos = (Math.random() * 100) - 50;
		ypos = (Math.random() * 100) - 50;
		strength = s;
		this.scale = scale;
	}

	double noise(double x, double y, float t)
	{
		double tx = xpos + Math.cos(t * 2 * Math.PI) * strength;
		double ty = ypos + Math.sin(t * 2 * Math.PI) * strength;

		return SimplexNoise.noise(x * scale + xpos, y * scale + ypos, tx, ty);
	}
}
