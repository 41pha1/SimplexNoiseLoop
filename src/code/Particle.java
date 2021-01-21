package code;

import java.awt.Color;
import java.awt.Graphics;

public class Particle {
	
	SimplexNoiseLoop xPos;
	SimplexNoiseLoop yPos;
	SimplexNoiseLoop size;
	SimplexNoiseLoop color;
	
	public Particle(int w, int h, float animationSpeed)
	{
		xPos = new SimplexNoiseLoop(-w/2, w+w/2, 0.15f*animationSpeed);
		yPos = new SimplexNoiseLoop(-h/2, h+h/2, 0.15f*animationSpeed);
		color = new SimplexNoiseLoop(0.2, 0.6, 0.5f*animationSpeed);
		size = new SimplexNoiseLoop(20, 100, 1*animationSpeed);
	}
	
	public void draw(Graphics graphics, float a)
	{	
		Color c = new Color(Color.HSBtoRGB((float)color.noise(a), 0.8f, 0.8f));
		graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 128));
		int s = (int)size.noise(a);
		graphics.fillOval((int)xPos.noise(a), (int)yPos.noise(a), s, s);
	}
}
