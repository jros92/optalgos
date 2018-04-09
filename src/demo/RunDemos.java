package demo;

/**
 * Helper class to run a set of demos
 * @author js
 *
 */
public class RunDemos {

	public static void main(String[] args) {
		System.out.println("Running demos.");
		
		Demo demo1 = new Demo(2, 5, 1, 3, 3, true);
		demo1.runDemo();
		
		Demo demo2 = new Demo(10, 10, 1, 9, 9, true);
		demo2.runDemo();
		
		
	}
	

}
