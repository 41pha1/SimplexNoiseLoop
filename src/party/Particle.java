package party;

import java.awt.Color;
import java.awt.Graphics;

public class Particle {
	
	int x, y;
	static SimplexNoiseLoop xPos;
	static SimplexNoiseLoop yPos;
	double d;
//	SimplexNoiseLoop color;
	
	public static void generateNoise(float animationSpeed)
	{
		xPos = new SimplexNoiseLoop(1f*animationSpeed);
		yPos = new SimplexNoiseLoop(1f*animationSpeed);
	}
	
	public Particle(int w, int h, int x, int y, int r)
	{
		d = r-Math.sqrt(Math.pow(Math.abs(w/2-y),2) + Math.pow(Math.abs(h/2-x),2));
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics graphics, float a)
	{	
		graphics.setColor(Color.WHITE);
		
		int dx = (int)(((xPos.noise(a))* d))+x;
		int dy = (int)(((yPos.noise(a))* d))+y;
		graphics.fillOval(dx, dy, 1,1);
		
	}
}
