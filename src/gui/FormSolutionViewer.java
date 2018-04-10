package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeListener;

import core.FeasibleSolution;

public class FormSolutionViewer extends JFrame {

	public SolutionDisplayPanel solutionDisplayPanel;
	private FeasibleSolution solution;

	private boolean updateWithWorseSolution;
	private boolean autoScaling;
	private boolean multiColored;
	
	int scaleFactor = 40;

	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	JCheckBoxMenuItem cbMenuItemShowWorseSolutions;
	JCheckBoxMenuItem cbAutoScaling;
	JScrollPane scrollPane;
	JSlider zoomSlider;
	
	/**
	 * Create a new window to display a solution
	 * @param solution
	 * @param dpi
	 */
	public FormSolutionViewer(FeasibleSolution solution, int dpi) {
		this.solution = solution;

//		double scaleFactorInitialValueQuotient = 0.3181 * Math.sqrt(solution.getRectangles().size()) - 1.2896;
		this.scaleFactor = (int) Math.ceil(dpi / 10);

		this.multiColored = true;
		this.autoScaling = true;

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1600, 600);
		setTitle("Solution of " + solution.getInstance().toString() + " | Using " + solution.getBoxCount() + " Boxes");
		
		this.setBackground(new Color(250, 250, 250));
		

		solutionDisplayPanel = new SolutionDisplayPanel(this.solution, this.scaleFactor, this.multiColored);		
		solutionDisplayPanel.updateBoxPanels();
		
		/* Add ScrollPane */
		scrollPane = new JScrollPane(solutionDisplayPanel,
	            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		

		this.setContentPane(scrollPane);
		
//		solutionDisplayPanel.setParentScrollPane(scrollPane);
		
		/* MENU BAR */
		createMenuBar();
		this.cbMenuItemShowWorseSolutions.setEnabled(false);
		this.cbMenuItemShowWorseSolutions.setSelected(false);
		this.updateWithWorseSolution = false;


		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				if (autoScaling) autoScale();
			}
		});
	}


	public void updateSolution(FeasibleSolution solution, boolean betterSolution) {

		// If the new solution is worse than the previous one, check if the user wants to display it or not, and do so
		if (!betterSolution & !updateWithWorseSolution) return;

		this.solution = solution;
		setTitle("Solution of " + solution.getInstance().toString() + " | Using " + solution.getBoxCount() + " Boxes");

		solutionDisplayPanel.setSolution(this.solution);
		solutionDisplayPanel.updateBoxPanels();

		if (autoScaling) autoScale();
	}
	
	/**
	 * Call revalidate() on solutionDisplayPanel, then validate() the Frame, then repaint() the panel
	 */
	@Override
	public void validate() {
		solutionDisplayPanel.revalidate();
		super.validate();
		solutionDisplayPanel.repaint();
	}
	

	/**
	 * Creates all the menu bar items and stuff
	 */
	private void createMenuBar() {
		//Create the menu bar
		menuBar = new JMenuBar();

		// Build the first menu
		menu = new JMenu("Display");
		menu.setMnemonic(KeyEvent.VK_D);
		menu.getAccessibleContext().setAccessibleDescription("Control displaying options");
		menuBar.add(menu);

		// Button to print solution to console
		menuItem = new JMenuItem("Print solution to console");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				FormSolutionViewer.this.solution.printToConsole();
			}
		});
		menuItem.setMnemonic(KeyEvent.VK_P);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
//		menuItem.setFont(fontStandard);
		menu.add(menuItem);

		menu.addSeparator();

		// Option to toggle auto scaling on / off
		cbAutoScaling = new JCheckBoxMenuItem("Auto Scaling");
		cbAutoScaling.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FormSolutionViewer.this.autoScaling =
						FormSolutionViewer.this.cbAutoScaling.isSelected();
				System.out.println("Auto Scaling enabled: " + FormSolutionViewer.this.cbAutoScaling.isSelected());
				if (FormSolutionViewer.this.autoScaling) autoScale();	// first computation
			}
		});
		cbAutoScaling.setMnemonic(KeyEvent.VK_A);
		cbAutoScaling.setToolTipText("Automatically set the zoom value");
		cbAutoScaling.setSelected(true);
//		menuItem.setFont(fontStandard);
		menu.add(cbAutoScaling);

		// Option to select if solutions that are worse will be shown
		// for Simulated Annealing and Taboo Search
		cbMenuItemShowWorseSolutions = new JCheckBoxMenuItem("Show only better solutions");
		cbMenuItemShowWorseSolutions.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FormSolutionViewer.this.updateWithWorseSolution =
						FormSolutionViewer.this.cbMenuItemShowWorseSolutions.isSelected();
				System.out.println("updateWithWorseSolution = "
						+ FormSolutionViewer.this.cbMenuItemShowWorseSolutions.isSelected());
			}
		});
		cbMenuItemShowWorseSolutions.setMnemonic(KeyEvent.VK_B);
		cbMenuItemShowWorseSolutions.setToolTipText("Only for Simulated Annealing and Taboo Search");
//		menuItem.setFont(fontStandard);
		menu.add(cbMenuItemShowWorseSolutions);

		// Group of radio buttons for changing the color scheme
		menu.addSeparator();
		JMenu submenuColors = new JMenu("Color Scheme");
//		menu.add(new JMenuItem("Color Scheme"));
		ButtonGroup groupColors = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Mono-colored");
		rbMenuItem.setSelected(!this.multiColored);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		rbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	System.out.println("Displaying rectangles in monochrome");
            	// Set the color of the boxPanels
            	FormSolutionViewer.this.multiColored = false;
            	FormSolutionViewer.this.solutionDisplayPanel.setMultiColored(false);
            	FormSolutionViewer.this.validate();
            }
        });
		groupColors.add(rbMenuItem);
		submenuColors.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Multi-colored");
		rbMenuItem.setSelected(this.multiColored);
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		rbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		rbMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	System.out.println("Displaying rectangles multi-colored");
            	// Set the color of the boxPanels
            	FormSolutionViewer.this.multiColored = true;
            	FormSolutionViewer.this.solutionDisplayPanel.setMultiColored(true);
            	FormSolutionViewer.this.validate();
            }
        });
		groupColors.add(rbMenuItem);
		submenuColors.add(rbMenuItem);
		menu.add(submenuColors);
		
		// Zoom slider
		zoomSlider = new JSlider(JSlider.HORIZONTAL, 1, (int) Math.round(this.scaleFactor * 3), this.scaleFactor);
		zoomSlider.setMaximumSize(new Dimension(600, (int) zoomSlider.getMaximumSize().getHeight() - 4));
        ChangeListener cl = e -> {
            JSlider sliderValue = (JSlider) e.getSource();
            System.out.println("Zoom value adjusted to " + sliderValue.getValue());
            FormSolutionViewer.this.scaleFactor =  sliderValue.getValue();
        	FormSolutionViewer.this.solutionDisplayPanel.setScaleFactor(FormSolutionViewer.this.scaleFactor);
        	FormSolutionViewer.this.validate();
        };
        zoomSlider.addChangeListener(cl);
		menuBar.add(new JMenuItem("Zoom: "));
        menuBar.add(zoomSlider);

        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Compute the current maximum scale factor that will fit all the boxes inside the window as it is currently sized
	 */
	public void autoScale() {
		// Compute zoom value
		// ScaleFactor = (sqrt(W*L / nBoxes)-spacing) / boxlength
		this.scaleFactor = (int)Math.floor(
				(Math.sqrt((double)scrollPane.getViewport().getHeight() * (double)scrollPane.getViewport().getWidth()
				  / (double)solution.getBoxCount()) - (double)solutionDisplayPanel.getSpacing())
						/ (double)solution.getBoxLength());

//		System.out.println("SDP Height: " + solutionDisplayPanel.getHeight()
//				+ ", ScrollPane Viewport Height: " + scrollPane.getViewport().getHeight()
//				+ ", ScrollPane Viewport Width: " + scrollPane.getViewport().getWidth());

		/* update graphics*/
		zoomSlider.setValue(this.scaleFactor);
		this.solutionDisplayPanel.setScaleFactor(this.scaleFactor);
		this.validate();
	}


	public void setCheckBoxShowWorseSolutions(boolean value) {
		this.cbMenuItemShowWorseSolutions.setEnabled(value);
		this.cbMenuItemShowWorseSolutions.setSelected(value);
		this.updateWithWorseSolution = value;
	}

	class MenuZoomSlider extends JSlider {
		MenuZoomSlider(int orientation, int minValue, int maxValue, int value) {
			super(orientation, minValue, maxValue, value);
		}

	}
}
