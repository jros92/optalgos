package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;

import algorithms.*;

import core.*;
import demo.Demo;

public class FormMain extends JFrame {

	private JPanel contentPane;
	private DialogAddInstance dialogAddInstance;
	private DefaultListModel<Instance> instanceListModel;
	private DefaultListModel<Algorithms> algosListModel;
	private DefaultListModel<Neighborhoods> neighborhoodListModel;
	private JList<Instance> listCurrentInstances;
	private FeasibleSolution feasibleSolution;
	private int dpi;
	private Font fontStandard;
	private Font fontLarger;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;

	private JList<Solver> listSolvers;
	private DefaultListModel<Solver> solverListModel;

	/* Parameters to be tuned */
//	long maxIterations = 100000L;
    long maxIterations = Integer.MAX_VALUE;
	//			long maxIterations = 30;
	int numberOfNeighbors = 500;
	int sleepDuration = 0;	// in ms

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO: Remove this after debug
		SimulatedAnnealingAlgorithm simAnnTest = (SimulatedAnnealingAlgorithm)Algorithm.generateInstance(Algorithms.SimulatedAnnealing);
		System.out.println(simAnnTest.getCoolingScheduleTextual());

		try {
            // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
			System.out.println("Error setting Look and Feel. Keeping default.");
		}
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		
		/* Start the Application */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/* Read System Resolution (DPI) and create Frame */
					int dpi = getDpi();
					FormMain frame = new FormMain(dpi);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static int getDpi() {
		int dpi = (int)java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
		if (dpi < 40) dpi = 40;
		return dpi;
	}


	/**
	 * Create the Main frame of the application.
	 * @param dpi The resolution of the screen, should be detected by caller
	 */
	public FormMain(int dpi) {

		this.dpi = dpi;
		
		float mainFormHeightFactor = 6f;
		int leftColWidth = (int)Math.round(dpi*3.8);
		int leftColHeight = (int)Math.round(dpi*1.1);
		
		int fontSize = (int) Math.round(dpi / 7 + 0.5);
		fontStandard = new Font("Tahoma", Font.PLAIN, fontSize-1);
		fontLarger = new Font("Tahoma", Font.PLAIN, fontSize);

		System.out.println("Started GUI");
		System.out.println("Detected display dpi: " + dpi);
		
		setTitle("Optimization Algorithms WS2017/18");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, (int)Math.round(dpi*5.5), (int)Math.round(dpi*mainFormHeightFactor));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/* Right and Left Panels */
		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(leftColWidth + 20, 30, (int)Math.round(dpi*1.3), (int)Math.round(dpi*(mainFormHeightFactor*0.9)));
		GridLayout buttonPanelLayout = new GridLayout(8, 1);
		buttonPanelLayout.setVgap((int)Math.round(dpi/20));
		rightPanel.setLayout(buttonPanelLayout);
		contentPane.add(rightPanel);
		
//		JPanel leftPanel = new JPanel();
//		leftPanel.setBounds((int)Math.round(dpi*3) + 20, 30, (int)Math.round(dpi*1.3), (int)Math.round(dpi*(mainFormHeightFactor*0.95)));
//		GridLayout leftPanelLayout = new GridLayout(7, 1);
//		leftPanelLayout.setVgap(2);
//		leftPanel.setLayout(leftPanelLayout);
//		contentPane.add(leftPanel);
		
		
		/* Fill upper area */
		
		JLabel lblCurrentInstances = new JLabel("1. Instances");
		lblCurrentInstances.setFont(fontLarger);
		lblCurrentInstances.setBounds(5, 5, 424, 24);
		contentPane.add(lblCurrentInstances);
		
		JScrollPane scrollPaneInstances = new JScrollPane();
		scrollPaneInstances.setBounds(5, 30, leftColWidth, 160);
		contentPane.add(scrollPaneInstances);
		
		listCurrentInstances = new JList<Instance>();
		listCurrentInstances.setFont(fontStandard);
		scrollPaneInstances.setViewportView(listCurrentInstances);
	
		/* Buttons for Instances */
		
		JButton btnAddInstance = new JButton("Add Instance");
		rightPanel.add(btnAddInstance);
		btnAddInstance.setMargin(new Insets(0, 5, 0, 5));
		btnAddInstance.setFont(fontStandard);
		
//		JButton btnRemoveInstance = new JButton("<html>Remove<br>Instance<html>");
		JButton btnRemoveInstance = new JButton("Remove Instance");
		rightPanel.add(btnRemoveInstance);
		btnRemoveInstance.setEnabled(false);
		btnRemoveInstance.setMargin(new Insets(0, 5, 0, 5));
		btnRemoveInstance.setFont(fontStandard);
		
		JButton btnViewInstance = new JButton("View Instance");
		rightPanel.add(btnViewInstance);
		btnViewInstance.setEnabled(false);
		btnViewInstance.setMargin(new Insets(0, 5, 0, 5));
		btnViewInstance.setFont(fontStandard);
		btnViewInstance.setToolTipText("Only enabled for Instances generated with Generator 2");
		
		JButton btnFillAndViewStart = new JButton("Initialize + View");
		rightPanel.add(btnFillAndViewStart);
		btnFillAndViewStart.setEnabled(false);
		btnFillAndViewStart.setMargin(new Insets(0, 5, 0, 5));
		btnFillAndViewStart.setFont(fontStandard);
		

		/* Algorithm Area */
		
		JLabel lblAlgorithms = new JLabel("2. Algorithms");
		lblAlgorithms.setFont(fontLarger);
		lblAlgorithms.setBounds(5, 198, 165, 24);
		contentPane.add(lblAlgorithms);
		
		JLabel lblNeighborhood = new JLabel("3. Neighborhoods");
		lblNeighborhood.setFont(fontLarger);
		lblNeighborhood.setBounds((leftColWidth-10)/2 + 15, 198, 165, 24);
		contentPane.add(lblNeighborhood);
		
//		JLabel lblGo = new JLabel("4. Solve");
//		lblGo.setFont(fontLarger);
////		lblGo.setBounds(350, 194, 100, 24);
//		rightPanel.add(lblGo);
		
		JScrollPane scrollPaneAlgorithms = new JScrollPane();
		scrollPaneAlgorithms.setBounds(5, 223, (leftColWidth-10)/2, leftColHeight);
		contentPane.add(scrollPaneAlgorithms);
		
		JList<Algorithms> listAlgorithms = new JList<>();
		scrollPaneAlgorithms.setViewportView(listAlgorithms);
		listAlgorithms.setEnabled(false);
		listAlgorithms.setFont(fontStandard);
		
		JScrollPane scrollPaneNeighborhoods = new JScrollPane();
		scrollPaneNeighborhoods.setBounds((leftColWidth-10)/2 + 15, 223, (leftColWidth-10)/2, leftColHeight);
		contentPane.add(scrollPaneNeighborhoods);
		
		JList<Neighborhoods> listNeighborhoods = new JList<Neighborhoods>();
		scrollPaneNeighborhoods.setViewportView(listNeighborhoods);
		listNeighborhoods.setEnabled(false);
		listNeighborhoods.setFont(fontStandard);
		
		JButton btnGo = new JButton("Solve!");
//		JButton btnGo = new JGradientButton("Solve!");
		btnGo.setEnabled(false);
		btnGo.setBounds(350, 223, 140, 86);
		btnGo.setFont(fontStandard);
		btnGo.setBackground(new Color(0, 220, 130));
//		btnGo.setForeground(new Color(0, 220, 130));
		rightPanel.add(btnGo);
		
		dialogAddInstance = new DialogAddInstance(this, this.dpi, fontStandard, fontLarger);
		dialogAddInstance.setLocation(150, 150);

		/* List of open solvers */
		JLabel lblSolvers = new JLabel("Solvers / Solutions");
		lblSolvers.setFont(fontLarger);
		lblSolvers.setBounds(5, 223+leftColHeight+5, leftColWidth, 24);
		contentPane.add(lblSolvers);

		JScrollPane scrollPaneSolvers = new JScrollPane();
		scrollPaneSolvers.setBounds(5, 223+leftColHeight+30, leftColWidth, this.getHeight() - (223+leftColHeight+30+100));
		contentPane.add(scrollPaneSolvers);

		listSolvers = new JList<>();
		solverListModel = new DefaultListModel<>();
		listSolvers.setModel(solverListModel);

		scrollPaneSolvers.setViewportView(listSolvers);
		listSolvers.setEnabled(true);
		listSolvers.setFont(fontStandard);

		/* Buttons for Solvers / Solutions */
		JButton btnShowSolution = new JButton("Show Solution");
		rightPanel.add(btnShowSolution);
		btnShowSolution.setEnabled(false);
		btnShowSolution.setMargin(new Insets(0, 5, 0, 5));
		btnShowSolution.setFont(fontStandard);

		JButton btnRemoveSolver = new JButton("Delete Solution");
		rightPanel.add(btnRemoveSolver);
		btnRemoveSolver.setEnabled(false);
		btnRemoveSolver.setMargin(new Insets(0, 5, 0, 5));
		btnRemoveSolver.setFont(fontStandard);

		/* Tooltips */
		String toolTipText = "Create and select instance first";
		String toolTipText2 = "Select instance, algorithm and neighborhood first";
		btnRemoveInstance.setEnabled(false);
		btnViewInstance.setEnabled(false);
		btnFillAndViewStart.setEnabled(false);
		btnFillAndViewStart.setToolTipText(toolTipText);
		listAlgorithms.setEnabled(false);
		listAlgorithms.setToolTipText(toolTipText);
		listNeighborhoods.setEnabled(false);
		listNeighborhoods.setToolTipText(toolTipText);
		btnGo.setEnabled(false);
		btnGo.setToolTipText(toolTipText2);


		/* Initialization Done */


		/* Fill models */
		
		// Fill Instance List
		instanceListModel = new DefaultListModel<Instance>();
		listCurrentInstances.setModel(instanceListModel);
		
		// Fill Algorithm List
		algosListModel = new DefaultListModel<>();
		algosListModel.addElement(Algorithms.LocalSearch);
		algosListModel.addElement(Algorithms.SimulatedAnnealing);
		algosListModel.addElement(Algorithms.TabooSearch);
		listAlgorithms.setModel(algosListModel);
		
		// Fill Neighborhood list
		neighborhoodListModel = new DefaultListModel<Neighborhoods>();
		neighborhoodListModel.addElement(Neighborhoods.Geometric);
		neighborhoodListModel.addElement(Neighborhoods.RuleBased);
		neighborhoodListModel.addElement(Neighborhoods.GeometricNew);
		listNeighborhoods.setModel(neighborhoodListModel);



		
		/* Add Action Listeners */
		
		// Enable and disable remove button and algorithms list according to instance selection from the list
		listCurrentInstances.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (listCurrentInstances.getSelectedIndex() > -1) {
					btnRemoveInstance.setEnabled(true);
					btnViewInstance.setEnabled(listCurrentInstances.getSelectedValue().getGeneratorType() == 2);
					btnFillAndViewStart.setEnabled(true);
					btnFillAndViewStart.setToolTipText("Show initial solution");
					listAlgorithms.setEnabled(true);
					listAlgorithms.setToolTipText("Select algorithm to use");
					listNeighborhoods.setEnabled(true);
					listNeighborhoods.setToolTipText("Select neighborhood to use");
					if (listAlgorithms.getSelectedIndex() > -1) {
						if (listNeighborhoods.getSelectedIndex() > -1) {
							btnGo.setEnabled(true);
							btnGo.setToolTipText("Solve the problem");
						}
					}
				} else {
					String toolTipText = "Create and select instance first";
					String toolTipText2 = "Select instance, algorithm and neighborhood first";
					btnRemoveInstance.setEnabled(false);
					btnViewInstance.setEnabled(false);
					btnFillAndViewStart.setEnabled(false);
					btnFillAndViewStart.setToolTipText(toolTipText);
					listAlgorithms.setEnabled(false);
					listAlgorithms.setToolTipText(toolTipText);
					listNeighborhoods.setEnabled(false);
					listNeighborhoods.setToolTipText(toolTipText);
					btnGo.setEnabled(false);
					btnGo.setToolTipText(toolTipText2);
				}
			}
		});
		
		// Enable and disable GO button according to algorithm selection from the list of algos
		listAlgorithms.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (listAlgorithms.getSelectedIndex() > -1) {
					if (listNeighborhoods.getSelectedIndex() > -1) {
						btnGo.setEnabled(true);
					}
				} else {
					btnGo.setEnabled(false);
				}
			}
		});
		
		// Enable and disable GO button according to algorithm selection from the list of algos
		listNeighborhoods.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (listNeighborhoods.getSelectedIndex() > -1) {
					if (listAlgorithms.getSelectedIndex() > -1) {
						btnGo.setEnabled(true);
					}
				} else {
					btnGo.setEnabled(false);
				}
			}
		});

		/* Button to view a generated instance */
		btnViewInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showUninitializedInstance();
			}
		});

		/* Button to initialize an instance */
		btnFillAndViewStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showInitializedInstance(listCurrentInstances.getSelectedValue());
			}
		});
		
		// Button to remove Instances
		btnRemoveInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<Instance> listModel = (DefaultListModel<Instance>) listCurrentInstances.getModel();
				listModel.removeElement(listCurrentInstances.getSelectedValue());
			}
		});
		
		// Button to add Instances
		btnAddInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Instance instance = dialogAddInstance.showDialog();
				dialogAddInstance.setVisible(false);
//				dialogAddInstance.dispose();
				dialogAddInstance.disposeInstance();	// Needed so old instance are not stored, otherwise they will show up again on Cancel or Close next time the dialog is opened
				
				if (instance != null) {
					System.out.println("Created " + instance);
					instanceListModel.addElement(instance);
				}
				
			}
		});
		
		// Button to start solving
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startAndShowSolver(listAlgorithms.getSelectedValue(), listNeighborhoods.getSelectedValue());
			}
		});

		// Enable and disable remove button for solvers according to solver selection from the list
		listSolvers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (listSolvers.getSelectedIndex() > -1) {
					btnRemoveSolver.setEnabled(true);
					btnShowSolution.setEnabled(true);
				} else {
					btnRemoveSolver.setEnabled(false);
					btnShowSolution.setEnabled(false);
				}
			}
		});

		// Button to remove Solvers
		btnRemoveSolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<Solver> listModel = (DefaultListModel<Solver>) listSolvers.getModel();
				listModel.removeElement(listSolvers.getSelectedValue());
			}
		});

		// Button to Show Solution
		btnShowSolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Solver solver = listSolvers.getSelectedValue();
				if (solver != null)
				showSolution(solver.getSolution());

			}
		});

		/* Add a MENU BAR */

		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setFont(fontStandard);
		
		// File Menu
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
		        "Control the application");
		menuBar.add(menu);
		menu.setFont(fontStandard);

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	askToExitApplication();
            }
        });
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setFont(fontStandard);
		
		menu.add(menuItem);

		// Demo Menu
		menu = new JMenu("Batch");
		menu.setMnemonic(KeyEvent.VK_B);
		menu.getAccessibleContext().setAccessibleDescription(
				"Control Batch Processes");
		menuBar.add(menu);
		menu.setFont(fontStandard);

		JCheckBoxMenuItem menuItemCb = new JCheckBoxMenuItem("Display solutions graphically");
		menuItemCb.setState(false);
		menuItemCb.setFont(fontStandard);
		menu.add(menuItemCb);

		menu.addSeparator();

		// Button to run small demo
		menuItem = new JMenuItem("Run Small Demo (3 Instances, 1000 Rectangles)");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// TODO: Set nInstances back to 3 !!!
				Demo demo = new Demo(1, 1000, 1, 10, 10, menuItemCb.getState());
				// add instances to GUI list
				for (Instance inst : demo.getInstances()) {
					instanceListModel.addElement(inst);
				}
				// Run the demo
				demo.runDemo();
				// when done, add to list of solutions
//				for (FeasibleSolution solution : demo.getSolutions()) {
//					solverListModel.addElement(solution);
//				}
			}
		});
//		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setFont(fontStandard);
		menu.add(menuItem);

		// Button to run large demo
		menuItem = new JMenuItem("Run Large Demo (30 Instances, 1000 Rectangles)");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Demo demo = new Demo(30, 1000, 1, 10, 10, menuItemCb.getState());
				// add instances to GUI list
				for (Instance inst : demo.getInstances()) {
					instanceListModel.addElement(inst);
				}
				// Run the demo
				demo.runDemo();
				// when done, add to list of solutions
//				for (FeasibleSolution solution : demo.getSolutions()) {
//					solverListModel.addElement(solution);
//				}
			}
		});
//		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setFont(fontStandard);
		menu.add(menuItem);


		// Set the menuBar for the Frame
		this.setJMenuBar(menuBar);
	}

	/**
	 * Instantiate a solver with the chosen instance, algorithm and neighborhood
	 * @param algorithmChoice
	 * @param neighborhoodChoice
	 */
	public void startAndShowSolver(Algorithms algorithmChoice, Neighborhoods neighborhoodChoice) {
		if (listCurrentInstances.getSelectedValue() != null) {

			IOptimizationAlgorithm algorithm = Algorithm.generateInstance(algorithmChoice);
			INeighborhood neighborhood = Neighborhood.generateInstance(neighborhoodChoice);

			Solver solver = new Solver(algorithm, neighborhood, listCurrentInstances.getSelectedValue(), maxIterations, numberOfNeighbors);
			solver.setSleepDuration(sleepDuration);

			// add solver to list of solvers
			solverListModel.addElement(solver);

			// Start the solver thread
			Thread solverThread = new Thread(solver);
			solverThread.start();
			
			// Create a solution viewer and pass it to the solver
//			FormSolutionViewer solutionViewer = new FormSolutionViewer(solver.getSolution(), (this.getX() + this.getWidth()), this.getY(), this.dpi);
			FormSolutionViewer solutionViewer = new FormSolutionViewer(solver.getSolution(), this.dpi);
			solver.setViewer(solutionViewer);
			solutionViewer.setVisible(true);
			// Enable display option for Sim. Annealing and Taboo Search, disable for Local Search
			if (algorithmChoice == Algorithms.LocalSearch) solutionViewer.setCheckBoxShowWorseSolutions(false);
			else solutionViewer.setCheckBoxShowWorseSolutions(true);
		} else {
			System.out.println("Please create and choose an instance first.");
		}
	}

	/**
	 * Show a static solution - viewer will not be attached to a solver
	 */
	public void showSolution(FeasibleSolution solution) {
		if (solution != null) {
			// Create a solution viewer and pass it to the solver
//			FormSolutionViewer solutionViewer = new FormSolutionViewer(solution, (this.getX() + this.getWidth()), this.getY(), this.dpi);
			FormSolutionViewer solutionViewer = new FormSolutionViewer(solution, this.dpi);
			solutionViewer.setVisible(true);

			// Disable display option (for Sim. Annealing and Taboo Search)
			solutionViewer.setCheckBoxShowWorseSolutions(false);
		} else {
			System.out.println("ERROR Selected solution not valid.");
		}
	}

	
	/**
	 * Display a dialog asking the user if they want to exit the application.
	 */
	public void askToExitApplication() {
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?",
    	        "Quit Program", JOptionPane.OK_CANCEL_OPTION) == 0) {
    		System.out.println("Exiting...");
        	System.exit(0);
    	}
	}
	
	/**
	 * Show a raw generated instance (not initialized)
	 */
	private void showUninitializedInstance() {
		FormInstanceViewer instanceForm = new FormInstanceViewer(this.listCurrentInstances.getSelectedValue(), "View " + listCurrentInstances.getSelectedValue().toString(), this.dpi);
		instanceForm.setVisible(true);
	}

	/**
	 * Initialize an instance and display it
	 * @param instance
	 */
	private void showInitializedInstance(Instance instance) {
		// Clone the instance
//		IProblemInitializer problemInitializer = new SimplestInitializer();
//		IProblemInitializer problemInitializer = new SimplerInitializer();
		IProblemInitializer problemInitializer = new SimpleInitializer();
		feasibleSolution = problemInitializer.initialize(instance);
		
//		FormSolutionViewer solutionViewer = new FormSolutionViewer(feasibleSolution, (this.getX() + this.getWidth()), this.getY(), this.dpi);
		FormSolutionViewer solutionViewer = new FormSolutionViewer(feasibleSolution, this.dpi);
		solutionViewer.setVisible(true);
		feasibleSolution.printToConsole();
	}
	
}
