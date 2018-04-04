package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
	
	private boolean multiColored;
	
	int scaleFactor = 40;

	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	JScrollPane scrollPane;
	
	/**
	 * Create a new window to display a solution
	 * @param solution
	 * @param x position of 
	 * @param y
	 * @param dpi
	 */
	public FormSolutionViewer(FeasibleSolution solution, int x, int y, int dpi) {
		this.solution = solution;

//		double scaleFactorInitialValueQuotient = 0.3181 * Math.sqrt(solution.getRectangles().size()) - 1.2896;
		double scaleFactorInitialValueQuotient = 1;
		this.scaleFactor = (int) Math.ceil(dpi / (6 * scaleFactorInitialValueQuotient));


		this.multiColored = true;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(x, y, 800, 600);
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
		
	}


	public void updateSolution(FeasibleSolution solution) {
		this.solution = solution;
		setTitle("Solution of " + solution.getInstance().toString() + " | Using " + solution.getBoxCount() + " Boxes");
		
//		solutionDisplayPanel = new SolutionDisplayPanel(this, this.solution, this.scaleFactor, this.multiColored);	
//		this.scrollPane.setViewportView(solutionDisplayPanel);
		
		solutionDisplayPanel.setSolution(this.solution);
		solutionDisplayPanel.updateBoxPanels();
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
//
		menuItem = new JMenuItem("Print solution to console");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FormSolutionViewer.this.solution.printToConsole();
			}
		});
		menuItem.setMnemonic(KeyEvent.VK_P);
//		menuItem.setFont(fontStandard);

		menu.add(menuItem);

		// Group of radio buttons for changing the color scheme
		menu.addSeparator();
		menu.add(new JMenuItem("Colors"));
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
		menu.add(rbMenuItem);

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
		menu.add(rbMenuItem);
		
		// Zoom slider 
		menu.addSeparator();
		menu.add(new JMenuItem("Zoom"));
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, (int) Math.round(this.scaleFactor * 3), this.scaleFactor);
        ChangeListener cl = e -> {
            JSlider sliderValue = (JSlider) e.getSource();
            System.out.println("Zoom value adjusted to " + sliderValue.getValue());
            FormSolutionViewer.this.scaleFactor =  sliderValue.getValue();
        	FormSolutionViewer.this.solutionDisplayPanel.setScaleFactor(FormSolutionViewer.this.scaleFactor);
        	FormSolutionViewer.this.validate();
        };
        slider.addChangeListener(cl);
        menu.add(slider);

		this.setJMenuBar(menuBar);
	}

}
