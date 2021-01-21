package rotating;

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
	static int Framecount = 0;
	static float noiseStrength = 1f;
	static float noiseRatio = 0.4f;
	static float animationSpeed = 0.3f;
	static int maxFrames = 4000;
	static int numPs = 8;
	static int radius = 400;
	static float offset = 0.2f;
	static int saved = 0;
	static SimplexNoiseLoop noise;
	static Particle[] ps;

	public Frame()
	{
		int w = 1000;
		int h = 1000;
		this.setSize(w, h);
		noise = new SimplexNoiseLoop(-0.5, 0.5, noiseStrength);
		screen = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setVisible(true);
		ps = new Particle[numPs * 8];
		for (int i = 0; i < numPs * 8; i++)
		{
			ps[i] = new Particle(0, 0);
		}
	}

	public void saveScreen()
	{
		try
		{
			BufferedImage toSave = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_RGB);
			toSave.getGraphics().drawImage(screen, 0, 0, null);
			ImageIO.write(toSave, "png", new File("imgs/img" + saved + ".png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		saved++;
	}

	@Override
	public void paint(Graphics fin)
	{
		Graphics2D g = screen.createGraphics();
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		g.setColor(Color.black);
		g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		for (int a = 0; a < 8; a++)
		{
			for (int i = 0; i < numPs; i++)
			{
				g.setColor(Color.WHITE);
				ps[i + a * 8].draw(g, i, a / 8f);
			}
		}

		System.out.println(Framecount);
		Framecount++;
		if (Framecount >= 1000 && Framecount % 5 == 0)
			saveScreen();
		if (Framecount == maxFrames + 500)
			System.exit(0);
		fin.drawImage(screen, 0, 0, null);
	}
}
