package rotating;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Particle
{
	float r, a;
	ArrayList<Float> rs;
	ArrayList<Float> as;

	public Particle(int r, int a)
	{
		this.r = r;
		this.a = a;
		rs = new ArrayList<Float>();
		as = new ArrayList<Float>();
	}

	public void draw(Graphics2D g, int off, float aoff)
	{

		float a = Frame.Framecount / (float) Frame.maxFrames;
		float r = 1 + Frame.noise.noise(a + aoff, off * Frame.offset) * Frame.noiseRatio;
		int dx = Frame.screen.getWidth() / 2 + (int) (Math.sin((aoff + a) * Math.PI * 2) * r * Frame.radius);
		int dy = Frame.screen.getHeight() / 2 + (int) (Math.cos((aoff + a) * Math.PI * 2) * r * Frame.radius);
		rs.add(r);
		as.add(a);
		g.fillOval(dx - 2, dy - 2, 5, 5);
		for (int i = 0; i < rs.size(); i++)
		{
			float angle = as.get(i);
			as.set(i, angle);
			float radius = rs.get(i);
			rs.set(i, radius * 0.999f);
			float alpha = (float) Math.pow(Math.max(0, radius - 0.3f), 2);
			if (radius < 0.3f)
			{
				rs.remove(i);
				as.remove(i);
			}

			int x = Frame.screen.getWidth() / 2
					+ (int) (Math.sin((aoff + angle) * Math.PI * 2) * radius * Frame.radius);
			int y = Frame.screen.getHeight() / 2
					+ (int) (Math.cos((aoff + angle) * Math.PI * 2) * radius * Frame.radius);
			Frame.screen.setRGB(x, y, new Color(1f, 1f, 1f, alpha).getRGB());
		}
	}
}
