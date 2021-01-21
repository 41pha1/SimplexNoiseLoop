package HD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Frame extends JFrame
{
	private static final long serialVersionUID = -4830574897210070284L;
	static int res = 10000;
	static int maxFrames = 200;
	static int radius = 450;
	static float noiseScale = 0.009f;
	static float noiseItensity = 1f;
	static float noiseSpeed = 0.002f;
	static float opacity = 600;
	static SimplexNoiseLoop xnoise = new SimplexNoiseLoop(maxFrames * noiseSpeed, noiseScale);
	static SimplexNoiseLoop ynoise = new SimplexNoiseLoop(maxFrames * noiseSpeed, noiseScale);
	static float[][] distances;
	static float[] pxs;
	static float[] pys;
	static float[][] reds;
	static float[][] greens;
	static float[][] blues;
	static long lastFrame;
	static long startTime;
	public static BufferedImage screen;
	int Framecount;

	public Frame()
	{
		int w = 1000;
		int h = 1000;
		this.setSize(w, h);
		screen = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		prepareRender();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("NOICE NOISE");
		startTime = System.currentTimeMillis();
		lastFrame = System.currentTimeMillis();
	}

	public synchronized int getNextFrameToRender()
	{
		return Framecount++;
	}

	public void prepareRender()
	{
		distances = new float[res][res];
		pxs = new float[res];
		pys = new float[res];
		reds = new float[res][res];
		greens = new float[res][res];
		blues = new float[res][res];
		float alpha = opacity / (res * res);
		for (int x = 0; x < res; x++)
		{
			for (int y = 0; y < res; y++)
			{
				float px = (screen.getWidth() / 2f) - radius + ((x / (float) res) * radius * 2);
				float py = (screen.getHeight() / 2f) - radius + ((y / (float) res) * radius * 2);
				float dx = screen.getWidth() / 2f - px;
				float dy = screen.getHeight() / 2f - py;
				distances[x][y] = radius - ((float) Math.sqrt(((dx * dx) + (dy * dy))));
				pxs[x] = px;
				pys[y] = py;

				float c = distances[x][y] / Frame.radius;
				Color col = new Color(Color.HSBtoRGB(0.2f + c / 3f, 1, c * c));
				reds[x][y] += col.getRed() * alpha;
				greens[x][y] += col.getGreen() * alpha;
				blues[x][y] += col.getBlue() * alpha;
			}
		}
	}

	public void saveScreen()
	{
		try
		{
			ImageIO.write(screen, "png", new File("green/img" + Framecount + ".png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		long timeForThisFrame = System.currentTimeMillis() - lastFrame;
		System.out.println("Frame " + Framecount + " in " + timeForThisFrame / 1000f + " seconds, ETA: "
				+ ((int) ((((maxFrames - Framecount) * timeForThisFrame) / 1000f)) / 6) / 10f + " minutes, "
				+ (((System.currentTimeMillis() - startTime) / 1000) / 6) / 10f + " minutes Total ("
				+ (Framecount / (float) maxFrames) * 100 + "% Done)");
		lastFrame = System.currentTimeMillis();
	}

	public void drawFrame()
	{
		float[][][] image = new float[screen.getWidth()][screen.getHeight()][3];

		float t = Framecount / (float) maxFrames;

		for (int x = 0; x < res; x++)
		{
			for (int y = 0; y < res; y++)
			{
				float distance = distances[x][y];
				if (distance > 0)
				{

					float px = pxs[x];
					float py = pys[y];

					int dx = (int) (((Frame.xnoise.noise(px, py, t)) * distance * noiseItensity) + px);
					int dy = (int) (((Frame.ynoise.noise(px, py, t)) * distance * noiseItensity) + py);

					image[dx][dy][0] += reds[x][y];
					image[dx][dy][1] += greens[x][y];
					image[dx][dy][2] += blues[x][y];
				}
			}
		}
		for (int x = 0; x < screen.getWidth(); x++)
		{
			for (int y = 0; y < screen.getHeight(); y++)
			{
				screen.setRGB(x, y,
						new Color(Math.min(1, image[x][y][0]), Math.min(1, image[x][y][1]), Math.min(1, image[x][y][2]))
								.getRGB());
			}
		}
		Framecount++;
		saveScreen();
		if (Framecount == maxFrames)
			System.exit(13);
	}

	@Override
	public void paint(Graphics fin)
	{
		fin.drawImage(screen, 0, 0, null);
	}
}
