package code;

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
	float animationSpeed = 0.3f;
	int maxFrames = 100;
	static Particle[] p;
	SimplexNoiseLoop background;
	
	
	public Frame()
	{
//		this.setExtendedState(Frame.MAXIMIZED_BOTH);
//		int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		int w = 1000;
		int h = 1000;
		this.setSize(w,h);
		
		p = new Particle[50];
		for(int i = 0; i < p.length; i++)
		{
			p[i] = new Particle(w, h, animationSpeed);
		}
		background = new SimplexNoiseLoop(0.1, 0.2, 0.1f);
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
		
		g.setColor(new Color(Color.HSBtoRGB((float)background.noise(Framecount/(float)maxFrames), 0.2f, 1f)));
		g.fillRect(0,0,screen.getWidth(), screen.getHeight());
		
		for(Particle pa : p)
		{
			pa.draw(g, Framecount/(float)maxFrames);
		}
		Framecount++;
		saveScreen();
		if(Framecount == maxFrames)System.exit(13);
		
		fin.drawImage(screen, 0,0,null);
	}
}
