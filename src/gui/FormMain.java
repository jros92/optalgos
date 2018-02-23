package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
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

public class FormMain extends JFrame {

	private JPanel contentPane;
	private DialogAddInstance dialogAddInstance;
	private DefaultListModel<Instance> instanceListModel;
	private DefaultListModel<Algorithms> algosListModel;
	private DefaultListModel<INeighborhood> neighborhoodListModel;
	private JList<Instance> listCurrentInstances;
	private FeasibleSolution feasibleSolution;
	private int dpi;
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws InterruptedException {
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
					int dpi = (int)java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
					FormMain frame = new FormMain(dpi);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the Main frame of the application.
	 * @param dpi The resolution of the screen, should be detected by caller
	 */
	public FormMain(int dpi) {
		
		if (dpi < 40) dpi = 40;
		this.dpi = dpi;
		
		float mainFormHeightFactor = 4.2f;
		int leftColWidth = (int)Math.round(dpi*3.8);
		int leftColHeight = (int)Math.round(dpi*1.1);
		
		int fontSize = (int) Math.round(dpi / 7 + 0.5);
		Font fontStandard = new Font("Tahoma", Font.PLAIN, fontSize-1);
		Font fontLarger = new Font("Tahoma", Font.PLAIN, fontSize);

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
		GridLayout buttonPanelLayout = new GridLayout(6, 1);
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
		
		JLabel lblCurrentInstances = new JLabel("1. Instance");
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
		
		JButton btnFillAndViewStart = new JButton("Initialize + View");
		rightPanel.add(btnFillAndViewStart);
		btnFillAndViewStart.setEnabled(false);
		btnFillAndViewStart.setMargin(new Insets(0, 5, 0, 5));
		btnFillAndViewStart.setFont(fontStandard);
		

		/* Algorithm Area */
		
		JLabel lblAlgorithms = new JLabel("2. Algorithm");
		lblAlgorithms.setFont(fontLarger);
		lblAlgorithms.setBounds(5, 194, 165, 24);
		contentPane.add(lblAlgorithms);
		
		JLabel lblNeighborhood = new JLabel("3. Neighborhood");
		lblNeighborhood.setFont(fontLarger);
		lblNeighborhood.setBounds(175, 194, 165, 24);
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
		scrollPaneNeighborhoods.setBounds((leftColWidth-10)/2 + 10, 223, (leftColWidth-10)/2, leftColHeight);
		contentPane.add(scrollPaneNeighborhoods);
		
		JList<INeighborhood> listNeighborhoods = new JList<INeighborhood>();
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
		
		dialogAddInstance = new DialogAddInstance(this);
		
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
		neighborhoodListModel = new DefaultListModel<INeighborhood>();
		neighborhoodListModel.addElement(new NeighborhoodGeometric());
		neighborhoodListModel.addElement(new NeighborhoodRuleBased());
		listNeighborhoods.setModel(neighborhoodListModel);
		
		
		/* Add Action Listeners */
		
		// Enable and disable remove button and algorithms list according to instance selection from the list
		listCurrentInstances.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (listCurrentInstances.getSelectedIndex() > -1) {
					btnRemoveInstance.setEnabled(true);
					btnViewInstance.setEnabled(true);
					btnFillAndViewStart.setEnabled(true);
					listAlgorithms.setEnabled(true);
					listNeighborhoods.setEnabled(true);
					if (listAlgorithms.getSelectedIndex() > -1) {
						if (listNeighborhoods.getSelectedIndex() > -1) {
							btnGo.setEnabled(true);
						}
					}
				} else {
					btnRemoveInstance.setEnabled(false);
					btnViewInstance.setEnabled(false);
					btnFillAndViewStart.setEnabled(false);
					listAlgorithms.setEnabled(false);
					listNeighborhoods.setEnabled(false);
					btnGo.setEnabled(false);
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
				showUnitializedInstance();
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
		
		
		/* MENU BAR */
		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setFont(fontStandard);
		
		//Build the first menu.
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
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Instantiate a solver with the chosen instance, algorithm and neighborhood
	 * @param algorithmChoice
	 * @param neighborhood
	 */
	public void startAndShowSolver(Algorithms algorithmChoice, INeighborhood neighborhood) {
		if (listCurrentInstances.getSelectedValue() != null) {
//			long maxIterations = 1000000L;
			long maxIterations = 30;
			int numberOfNeighbors = 10000;

			IOptimizationAlgorithm algorithm = Algorithm.generateInstance(algorithmChoice);

			Solver solver = new Solver(algorithm, neighborhood, new ObjectiveFunction(), listCurrentInstances.getSelectedValue(), maxIterations, numberOfNeighbors);
	
			// Start the solver thread
			Thread solverThread = new Thread(solver);
			solverThread.start();
			
			// Create a solution viewer and pass it to the solver
			FormSolutionViewer solutionViewer = new FormSolutionViewer(solver.getSolution(), (this.getX() + this.getWidth()), this.getY(), this.dpi);
			solver.setViewer(solutionViewer);
			solutionViewer.setVisible(true);
		} else {
			System.out.println("Please create and choose and instance first.");
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
	private void showUnitializedInstance() {
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
		
		FormSolutionViewer solutionViewer = new FormSolutionViewer(feasibleSolution, (this.getX() + this.getWidth()), this.getY(), this.dpi);
		solutionViewer.setVisible(true);
		feasibleSolution.printToConsole();
	}
	
}
