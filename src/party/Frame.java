package party;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Frame extends JFrame
{
	private static final long serialVersionUID = -4830574897210070284L;
	public static BufferedImage screen;
	int Framecount = 0;
	float animationSpeed = 3f;
	int maxFrames = 1000;
	static Particle[] p;
	
	
	public Frame()
	{
//		this.setExtendedState(Frame.MAXIMIZED_BOTH);
//		int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		int w = 500;
		int h = 500;
		this.setSize(w,h);
		Particle.generateNoise(animationSpeed);
		p = new Particle[5000];
		int radius = 200;
		for(int i = 0; i < p.length; i++)
		{
			int x, y;
			do
			{
				x = (int)(Math.random()*radius*2)-radius;
				y = (int)(Math.random()*radius*2)-radius;
			}while(Math.sqrt((x*x+y*y))>radius);
			
			p[i] = new Particle(w, h, x+w/2, y+h/2, radius);
		}
		screen = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void saveScreen()
	{
		try {
			ImageIO.write(screen, "png", new File("imgs/img"+Framecount+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics fin) 
	{
		Graphics2D g = screen.createGraphics();
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		
		g.setColor(new Color(10,10,10));
		g.fillRect(0,0,screen.getWidth(), screen.getHeight());
		
		for(Particle pa : p)
		{
			pa.draw(g, Framecount/(float)maxFrames);
		}
		Framecount++;
//		saveScreen();
//		if(Framecount == maxFrames)System.exit(13);
		
		fin.drawImage(screen, 0,0,null);
	}
}
