package test;

import algorithms.Algorithm;
import algorithms.Algorithms;
import algorithms.IOptimizationAlgorithm;
import algorithms.Solver;
import core.Instance;
import core.NeighborhoodRuleBased;
import core.Rectangle;
import core.Box;
import gui.FormSolutionViewer;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;


public class TestCases3x3 {

    @Test
    public void testCase6RectanglesLocalSearch() {
        System.out.println("Testing 6 Rectangles on 3x3 Boxes using Local Search and RuleBased NBH");

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        rectangles.add(new Rectangle(2, 2, 0));
        rectangles.add(new Rectangle(2, 2, 1));
        rectangles.add(new Rectangle(1, 1, 2));
        rectangles.add(new Rectangle(2, 1, 3));
        rectangles.add(new Rectangle(1, 3, 4));     // This rectangle should be the second in the list (index 1)
        rectangles.add(new Rectangle(1, 2, 5));

        Instance instance = new Instance(6, 3, 0, rectangles);

        IOptimizationAlgorithm algorithm = Algorithm.generateInstance(Algorithms.LocalSearch);

        Solver solver = new Solver(algorithm, new NeighborhoodRuleBased(), instance, 30, 50);
        solver.setSleepDuration(0);

        // Start the solver thread
        try {
            solver.solve();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create a solution viewer and pass it to the solver
        FormSolutionViewer solutionViewer = new FormSolutionViewer(solver.getSolution(), 100, 100, (int)java.awt.Toolkit.getDefaultToolkit().getScreenResolution());
        solver.setViewer(solutionViewer);
        solutionViewer.setVisible(true);


        // Evaluate solution
        boolean correctSolution = false;

        ArrayList<Box> boxes = solver.getSolution().getBoxes();
        Box box1 = boxes.get(0);
        Box box2 = boxes.get(1);
       // Box box3 = boxes.get(2);



        correctSolution = (
                (boxes.size() == 2) &&
                (box1.getRectangles().get(0).getPos().getX() == 0) &&
                (box1.getRectangles().get(0).getPos().getY() == 0) &&
                (box1.getRectangles().get(0).getWidth() == 2) &&
                (box1.getRectangles().get(0).getHeight() == 2) &&
                (box1.getRectangles().get(1).getPos().getX() == 2) &&
                (box1.getRectangles().get(1).getPos().getY() == 0) &&
                (box1.getRectangles().get(1).getWidth() == 1) &&
                (box1.getRectangles().get(1).getHeight() == 3)
        );

        assertTrue("Solution should have used 2 boxes, the first one with 2 rectangles (2x2 and 1x3), and the second with 4 rectangles (2x2 1x1 2x1 and 1x2).", correctSolution);
    }
}
