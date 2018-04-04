package test;

import algorithms.INeighborhood;
import core.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Test RuleBased Neighborhood on 6 Rectangles, only has to change order of the rectangles
 */
public class TestNeighborhoodRuleBased {

    @Test
    public void testGetNeighbor() {

        boolean correctNeighborsReturned = true;
        boolean initialSolutionUnchanged = true;

        System.out.println("Testing 6 Rectangles on 3x3 Boxes using Local Search and RuleBased NBH");

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        rectangles.add(new Rectangle(2, 2));
        rectangles.add(new Rectangle(2, 2));
        rectangles.add(new Rectangle(1, 1));
        rectangles.add(new Rectangle(2, 1));
        rectangles.add(new Rectangle(1, 3));     // This rectangle should be the second in the list (index 1)
        rectangles.add(new Rectangle(1, 2));

        Instance instance = new Instance(6, 3, 0, rectangles);

        /* Initialize a "bad" solution */
        IProblemInitializer problemInitializer = new SimpleInitializer();
        FeasibleSolution initialSolution = problemInitializer.initialize(instance);

        // Make sure initial solution was set up correct
        initialSolutionUnchanged &= (
                (initialSolution.getRectangles().get(0).getWidth() == 2) &&
                (initialSolution.getRectangles().get(0).getHeight() == 2) &&
                (initialSolution.getRectangles().get(1).getWidth() == 2) &&
                (initialSolution.getRectangles().get(1).getHeight() == 2) &&
                (initialSolution.getRectangles().get(2).getWidth() == 1) &&
                (initialSolution.getRectangles().get(2).getHeight() == 1) &&
                (initialSolution.getRectangles().get(3).getWidth() == 2) &&
                (initialSolution.getRectangles().get(3).getHeight() == 1) &&
                (initialSolution.getRectangles().get(4).getWidth() == 1) &&
                (initialSolution.getRectangles().get(4).getHeight() == 3) &&
                (initialSolution.getRectangles().get(5).getWidth() == 1) &&
                (initialSolution.getRectangles().get(5).getHeight() == 2)
        );

        /* Print initial solution */
        System.out.println("Initial Solution below\n======================");
        initialSolution.printToConsole();
        System.out.println("======================\nInitial Solution above\n");

        NeighborhoodRuleBased neighborhood = new NeighborhoodRuleBased();

        FeasibleSolution neighbor = neighborhood.getNeighbor(initialSolution).solution;  // neighbor 1
//        System.out.println("Neighbor 1 below\n======================");
//        neighbor.printToConsole();
//        System.out.println("======================\nNeighbor 1 above\n");
        correctNeighborsReturned &= (
                (neighbor.getRectangles().get(0).getWidth() == 1) &&
                (neighbor.getRectangles().get(0).getHeight() == 2) &&
                (neighbor.getRectangles().get(1).getWidth() == 2) &&
                (neighbor.getRectangles().get(1).getHeight() == 2) &&
                (neighbor.getRectangles().get(2).getWidth() == 1) &&
                (neighbor.getRectangles().get(2).getHeight() == 1) &&
                (neighbor.getRectangles().get(3).getWidth() == 2) &&
                (neighbor.getRectangles().get(3).getHeight() == 1) &&
                (neighbor.getRectangles().get(4).getWidth() == 1) &&
                (neighbor.getRectangles().get(4).getHeight() == 3) &&
                (neighbor.getRectangles().get(5).getWidth() == 2) &&
                (neighbor.getRectangles().get(5).getHeight() == 2)
        );

        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 1.", correctNeighborsReturned);

        neighbor = neighborhood.getNeighbor(initialSolution).solution;   // neighbor 2
//        System.out.println("Neighbor 2 below\n======================");
//        neighbor.printToConsole();
//        System.out.println("======================\nNeighbor 2 above\n");
        correctNeighborsReturned &= (
                (neighbor.getRectangles().get(0).getWidth() == 1) &&
                (neighbor.getRectangles().get(0).getHeight() == 3) &&
                (neighbor.getRectangles().get(1).getWidth() == 2) &&
                (neighbor.getRectangles().get(1).getHeight() == 2) &&
                (neighbor.getRectangles().get(2).getWidth() == 1) &&
                (neighbor.getRectangles().get(2).getHeight() == 1) &&
                (neighbor.getRectangles().get(3).getWidth() == 2) &&
                (neighbor.getRectangles().get(3).getHeight() == 1) &&
                (neighbor.getRectangles().get(4).getWidth() == 2) &&
                (neighbor.getRectangles().get(4).getHeight() == 2) &&
                (neighbor.getRectangles().get(5).getWidth() == 1) &&
                (neighbor.getRectangles().get(5).getHeight() == 2)
        );

        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 2.", correctNeighborsReturned);

        neighbor = neighborhood.getNeighbor(initialSolution).solution; // neighbor 3
//        System.out.println("Neighbor 3 below\n======================");
//        neighbor.printToConsole();
//        System.out.println("======================\nNeighbor 3 above\n");
        correctNeighborsReturned &= (
                (neighbor.getRectangles().get(0).getWidth() == 2) &&
                (neighbor.getRectangles().get(0).getHeight() == 1) &&
                (neighbor.getRectangles().get(1).getWidth() == 2) &&
                (neighbor.getRectangles().get(1).getHeight() == 2) &&
                (neighbor.getRectangles().get(2).getWidth() == 1) &&
                (neighbor.getRectangles().get(2).getHeight() == 1) &&
                (neighbor.getRectangles().get(3).getWidth() == 2) &&
                (neighbor.getRectangles().get(3).getHeight() == 2) &&
                (neighbor.getRectangles().get(4).getWidth() == 1) &&
                (neighbor.getRectangles().get(4).getHeight() == 3) &&
                (neighbor.getRectangles().get(5).getWidth() == 1) &&
                (neighbor.getRectangles().get(5).getHeight() == 2)
        );

        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 3.", correctNeighborsReturned);

        ArrayList<Rectangle> listOfRectangles = new ArrayList<>();
        for (Rectangle r : initialSolution.getRectangles())
            listOfRectangles.add(r.clone());

        neighborhood.reorderRectangles(listOfRectangles); // Neighbor 4
        correctNeighborsReturned &= (
                (listOfRectangles.get(0).getWidth() == 1) &&
                (listOfRectangles.get(0).getHeight() == 1) &&
                (listOfRectangles.get(1).getWidth() == 2) &&
                (listOfRectangles.get(1).getHeight() == 2) &&
                (listOfRectangles.get(2).getWidth() == 2) &&
                (listOfRectangles.get(2).getHeight() == 2) &&
                (listOfRectangles.get(3).getWidth() == 2) &&
                (listOfRectangles.get(3).getHeight() == 1) &&
                (listOfRectangles.get(4).getWidth() == 1) &&
                (listOfRectangles.get(4).getHeight() == 3) &&
                (listOfRectangles.get(5).getWidth() == 1) &&
                (listOfRectangles.get(5).getHeight() == 2)
        );
        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 4.", correctNeighborsReturned);

        neighbor = neighborhood.getNeighbor(initialSolution).solution; // Neighbor 5
        correctNeighborsReturned &= (
                (neighbor.getRectangles().get(0).getWidth() == 2) &&
                (neighbor.getRectangles().get(0).getHeight() == 2) &&
                (neighbor.getRectangles().get(1).getWidth() == 2) &&
                (neighbor.getRectangles().get(1).getHeight() == 2) &&
                (neighbor.getRectangles().get(2).getWidth() == 1) &&
                (neighbor.getRectangles().get(2).getHeight() == 1) &&
                (neighbor.getRectangles().get(3).getWidth() == 2) &&
                (neighbor.getRectangles().get(3).getHeight() == 1) &&
                (neighbor.getRectangles().get(4).getWidth() == 1) &&
                (neighbor.getRectangles().get(4).getHeight() == 3) &&
                (neighbor.getRectangles().get(5).getWidth() == 1) &&
                (neighbor.getRectangles().get(5).getHeight() == 2)
        );
        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 5.", correctNeighborsReturned);

        listOfRectangles = new ArrayList<>();
        for (Rectangle r : initialSolution.getRectangles())
            listOfRectangles.add(r.clone());

        neighborhood.reorderRectangles(listOfRectangles); // Neighbor 6
        correctNeighborsReturned &= (
                (listOfRectangles.get(0).getWidth() == 2) &&
                (listOfRectangles.get(0).getHeight() == 2) &&
                (listOfRectangles.get(1).getWidth() == 1) &&
                (listOfRectangles.get(1).getHeight() == 2) &&
                (listOfRectangles.get(2).getWidth() == 1) &&
                (listOfRectangles.get(2).getHeight() == 1) &&
                (listOfRectangles.get(3).getWidth() == 2) &&
                (listOfRectangles.get(3).getHeight() == 1) &&
                (listOfRectangles.get(4).getWidth() == 1) &&
                (listOfRectangles.get(4).getHeight() == 3) &&
                (listOfRectangles.get(5).getWidth() == 2) &&
                (listOfRectangles.get(5).getHeight() == 2)
        );
        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 6.", correctNeighborsReturned);

        listOfRectangles = new ArrayList<>();
        for (Rectangle r : initialSolution.getRectangles())
            listOfRectangles.add(r.clone());

        neighborhood.reorderRectangles(listOfRectangles); // Neighbor 7
        correctNeighborsReturned &= (
                (listOfRectangles.get(0).getWidth() == 2) &&
                (listOfRectangles.get(0).getHeight() == 2) &&
                (listOfRectangles.get(1).getWidth() == 1) &&
                (listOfRectangles.get(1).getHeight() == 3) &&
                (listOfRectangles.get(2).getWidth() == 1) &&
                (listOfRectangles.get(2).getHeight() == 1) &&
                (listOfRectangles.get(3).getWidth() == 2) &&
                (listOfRectangles.get(3).getHeight() == 1) &&
                (listOfRectangles.get(4).getWidth() == 2) &&
                (listOfRectangles.get(4).getHeight() == 2) &&
                (listOfRectangles.get(5).getWidth() == 1) &&
                (listOfRectangles.get(5).getHeight() == 2)
        );
        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 7.", correctNeighborsReturned);
        neighbor = neighborhood.getNeighbor(initialSolution).solution; // Neighbor 8
        neighbor = neighborhood.getNeighbor(initialSolution).solution; // Neighbor 9


        listOfRectangles = new ArrayList<>();
        for (Rectangle r : initialSolution.getRectangles())
            listOfRectangles.add(r.clone());

        neighborhood.reorderRectangles(listOfRectangles);   // Neighbor 10
        correctNeighborsReturned &= (
                (listOfRectangles.get(0).getWidth() == 2) &&
                (listOfRectangles.get(0).getHeight() == 2) &&
                (listOfRectangles.get(1).getWidth() == 2) &&
                (listOfRectangles.get(1).getHeight() == 2) &&
                (listOfRectangles.get(2).getWidth() == 1) &&
                (listOfRectangles.get(2).getHeight() == 2) &&
                (listOfRectangles.get(3).getWidth() == 2) &&
                (listOfRectangles.get(3).getHeight() == 1) &&
                (listOfRectangles.get(4).getWidth() == 1) &&
                (listOfRectangles.get(4).getHeight() == 3) &&
                (listOfRectangles.get(5).getWidth() == 1) &&
                (listOfRectangles.get(5).getHeight() == 1)
        );
        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 10.", correctNeighborsReturned);

        neighbor = neighborhood.getNeighbor(initialSolution).solution; // Neighbor 11
        neighbor = neighborhood.getNeighbor(initialSolution).solution; // Neighbor 12
        neighbor = neighborhood.getNeighbor(initialSolution).solution; // Neighbor 13
        neighbor = neighborhood.getNeighbor(initialSolution).solution; // Neighbor 14

        listOfRectangles = new ArrayList<>();
        for (Rectangle r : initialSolution.getRectangles())
            listOfRectangles.add(r.clone());

        neighborhood.reorderRectangles(listOfRectangles);   // Neighbor 15 - last neighbor
        correctNeighborsReturned &= (
                (listOfRectangles.get(0).getWidth() == 2) &&
                (listOfRectangles.get(0).getHeight() == 2) &&
                (listOfRectangles.get(1).getWidth() == 2) &&
                (listOfRectangles.get(1).getHeight() == 2) &&
                (listOfRectangles.get(2).getWidth() == 1) &&
                (listOfRectangles.get(2).getHeight() == 1) &&
                (listOfRectangles.get(3).getWidth() == 2) &&
                (listOfRectangles.get(3).getHeight() == 1) &&
                (listOfRectangles.get(4).getWidth() == 1) &&
                (listOfRectangles.get(4).getHeight() == 2) &&
                (listOfRectangles.get(5).getWidth() == 1) &&
                (listOfRectangles.get(5).getHeight() == 3)
        );
        assertTrue("Rule-Based Neighborhood did not return correct neighbor no. 15.", correctNeighborsReturned);


        // Make sure initial solution was not changed
        initialSolutionUnchanged &= (
                (initialSolution.getRectangles().get(0).getWidth() == 2) &&
                (initialSolution.getRectangles().get(0).getHeight() == 2) &&
                (initialSolution.getRectangles().get(1).getWidth() == 2) &&
                (initialSolution.getRectangles().get(1).getHeight() == 2) &&
                (initialSolution.getRectangles().get(2).getWidth() == 1) &&
                (initialSolution.getRectangles().get(2).getHeight() == 1) &&
                (initialSolution.getRectangles().get(3).getWidth() == 2) &&
                (initialSolution.getRectangles().get(3).getHeight() == 1) &&
                (initialSolution.getRectangles().get(4).getWidth() == 1) &&
                (initialSolution.getRectangles().get(4).getHeight() == 3) &&
                (initialSolution.getRectangles().get(5).getWidth() == 1) &&
                (initialSolution.getRectangles().get(5).getHeight() == 2)
        );

        assertTrue("Rule-Based Neighborhood changed initial solution when it shouldn't.", initialSolutionUnchanged);
        assertTrue("Rule-Based Neighborhood did not return correct neighbors.", correctNeighborsReturned);
    }
}
