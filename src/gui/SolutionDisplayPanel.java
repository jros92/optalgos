package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import core.Box;
import core.FeasibleSolution;

/**
 * A container (panel) that displays a solution.
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class SolutionDisplayPanel extends JPanel {

	private FeasibleSolution solution;
	private int scaleFactor, spacing;
	private int neededHeight;
	private boolean multiColored;
	
	public SolutionDisplayPanel(int scaleFactor) {
		this.setScaleFactor(scaleFactor);
		this.setLayoutAndBorder(spacing);
		this.multiColored = false;
	}
	
	public SolutionDisplayPanel(FeasibleSolution solution, int scaleFactor, boolean multiColored) {
		this(scaleFactor);
		this.setSolution(solution);
		this.multiColored = multiColored;
	}

	public int getSpacing() {
		return this.spacing;
	}

	public void setSolution (FeasibleSolution solution) {
		this.solution = solution;
	}

	private void setLayoutAndBorder(int spacing) {
		setPreferredSize(new Dimension(500, 400));
		
		this.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));

		this.spacing = spacing;
		
		FlowLayout layout = new FlowLayout(FlowLayout.LEADING, this.spacing, this.spacing);
		this.setLayout(layout);
	}

	/**
	 * Set the scaleFactor (zoom) and spacing between boxes accordingly and, if solution already exists, update drawing
	 * @param scaleFactor One length unit (LU) equals <code>scaleFactor</code> pixels on the screen
	 */
	public void setScaleFactor(int scaleFactor) {
		this.scaleFactor = scaleFactor;
		this.spacing = (int) Math.ceil(scaleFactor / 5) + 1;
		if (this.solution != null) this.updateBoxPanels();
	}
	
	/**
	 * Set the color scheme and update the drawing
	 * @param multiColored true for multi-colored with random colors, false for pure orange
	 */
	public void setMultiColored(boolean multiColored) {
		this.multiColored = multiColored;
		this.updateBoxPanels();
	}
	
	@Override
	public Dimension getPreferredSize() {

		// Find needed width (depending on Frame width)
		Dimension dim = this.getParent().getParent().getSize();
		dim.width -= 30; // TODO: Quick and dirty solution
		int widthAvailable = dim.width;
//		int widthAvailable = (int) this.getLayout().minimumLayoutSize(this).width;
		
		// Find needed Height
		int boxPanelSize = this.scaleFactor * this.solution.getBoxLength();
		int boxesPerRow = widthAvailable / (boxPanelSize + this.spacing);
		if (boxesPerRow <= 0) boxesPerRow = 1;
		
		int neededRows = (int) Math.ceil((float)this.solution.getBoxCount() / (float)boxesPerRow);
		
		this.neededHeight = neededRows * (boxPanelSize + this.spacing) + 2*this.spacing;
		
//		System.out.println("boxpanelsize = " + boxPanelSize + ", widthAvailable = "
// 					+ widthAvailable + ", BoxesPerRow: " + boxesPerRow + ", needed Rows = " + neededRows);
		
		Dimension result = new Dimension(widthAvailable, this.neededHeight);
		
		return result;

	}
	
	/**
	 * Update the painted boxes
	 */
	public void updateBoxPanels() {
		this.removeAll();
		FlowLayout layout = new FlowLayout(FlowLayout.LEADING, this.spacing, this.spacing);
		this.setLayout(layout);
		
		/* Paint one boxPanel for each box contained in the solution */
		for (Box box : solution.getBoxes()) {
			BoxPanel boxPanel = new BoxPanel(box, scaleFactor, this.multiColored);

			// Display no. of rectangles contained in tooltip
			boxPanel.setToolTipText(String.format("Box " + box.getId() + " contains " + box.getRectangles().size()
					+ " rectangles and uses %.2f%% of space.", box.getPackingPercentage()*100));
			this.add(boxPanel);
			boxPanel.setVisible(true);
		}
	}
}
