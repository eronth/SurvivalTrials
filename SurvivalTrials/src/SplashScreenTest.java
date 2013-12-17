import java.awt.*;
 
/** Splash Screen Demo (with a Progress Bar)
    Run with VM command-line option -splash:splashImageFilename */
public class SplashScreenTest {
 
	public static void main(String[] args) {
		SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			System.err.println("Splash Screen not available!");
		} else {
			// Okay, Splash screen created
			Dimension splashBounds=splash.getSize();
			Graphics2D g2d=splash.createGraphics();
	 
			// Simulate a progress bar
			for (int i = 0; i < 100; i += 5) {
				g2d.setColor(Color.green);
				g2d.fillRect(0, splashBounds.height / 2,
						splashBounds.width * i / 100, 20);
				splash.update();
				try {
					Thread.sleep(200); // Some delays
				} catch (Exception e) {}
			}
			invokeMain("mainClass",null);
			g2d.dispose();
			splash.close();
		}
	}
   
	public static void invokeMain(String className, String[] args) {
		try {
			Class.forName(className).getMethod("main", new Class[] {String[].class}).invoke(null, new Object[] {args});
		} catch (Exception e) {
			InternalError error=new InternalError("Failed to invoke main method");
			error.initCause(e);
			throw error;
		}
	}
}