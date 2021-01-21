package HD;

public class Main
{

	public static Frame f;
	static final int FrameCap = 60;

	public static void main(String[] args)
	{
		long lf = System.nanoTime();
		int NanosPerFrame = 1000000000 / FrameCap;

		f = new Frame();

		while (true)
		{
			if (f.isActive())
			{
				if (System.nanoTime() - lf > NanosPerFrame)
				{
					lf = System.nanoTime();
					f.drawFrame();
					f.repaint();
				}
			}
		}
	}

}
