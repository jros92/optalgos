package demo;

/**
 * Helper class to run a set of demos
 * @author js
 *
 */
public class RunDemos {

	public static void main(String[] args) {
		System.out.println("Running small demo.");
		runSmallDemo(false);
	}

	public static void runSmallDemo(boolean showSolutions) {
		System.out.println("Running small demo.");
		
		Demo demo = new Demo(2, 1000, 1, 10, 10, showSolutions, false);
		demo.runDemo();
		
	}

	public static void runLargeDemo(boolean showSolutions) {
		System.out.println("Running large demo.");

		Demo demo = new Demo(10, 1000, 1, 9, 9, showSolutions, false);
		demo.runDemo();

	}


}
