package gui;

import core.*;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class DialogAddInstance extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textFieldBoxLength;
	private JTextField textFieldNrectangles;
	private JTextField textFieldLMin;
	private JTextField textFieldLMax;
	private int dpi;
	private Font fontStandard;
	private Font fontLarger;


	JRadioButton rdbtnGenerator1 = new JRadioButton("Generator 1");
	JRadioButton rdbtnGenerator2 = new JRadioButton("Generator 2");
	
	JButton okButton;
	JButton cancelButton;
	
	private Instance instance;
	private int GeneratorType = 1;

	/**
	 * Create the dialog.
	 */
	public DialogAddInstance(Window owner, int dpi, Font fontStandard, Font fontLarger) {
		this.dpi = dpi;
		this.fontStandard = fontStandard;
		this.fontLarger = fontLarger;
		this.setFont(this.fontStandard);


		// Create all the components
		initializeComponents();


		/* Action Listeners */
		
		rdbtnGenerator1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				enableTextFields();
			}
		});
		
		rdbtnGenerator2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (rdbtnGenerator2.isSelected()) {
					textFieldLMax.setEnabled(false);
					textFieldLMin.setEnabled(false);
					GeneratorType = 2;
				}
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instance = generateInstance();
//				System.out.println(instance);
//				instance.printRectangles();
				setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instance = null;
				setVisible(false);
				dispose();
			}
		});
		
		/* Pre-fill instance gen parameters for tests */
		this.textFieldBoxLength.setText("6");
		this.textFieldNrectangles.setText("120");
		this.textFieldLMin.setText("1");
		this.textFieldLMax.setText("6");

	}
	
	/*
	 * Add all the components
	 */
	private void initializeComponents() {
		setModal(true);
		setTitle("Generate New Instance");
//		setBounds(100, 100, 392, 236);
		setSize((int)Math.round(dpi*3.5), (int)Math.round(dpi*2.5));


//		getContentPane().setLayout(null);
//		contentPanel.setBounds(0, 0, 376, 197);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout());

		JPanel leftPanel = new JPanel();
		GridLayout layoutLeftPanel = new GridLayout(6,1);
		layoutLeftPanel.setVgap((int)Math.round(dpi/30));
		leftPanel.setLayout(layoutLeftPanel);
		contentPanel.add(leftPanel, BorderLayout.WEST);

		JPanel rightPanel = new JPanel();
		GridLayout layoutRightPanel = new GridLayout(6,1);
		layoutRightPanel.setVgap((int)Math.round(dpi/30));
		rightPanel.setLayout(layoutRightPanel);
		contentPanel.add(rightPanel, BorderLayout.EAST);

		{
			JLabel lblChooseGenerator = new JLabel("Choose Generator:");
			//lblChooseGenerator.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblChooseGenerator.setFont(this.fontStandard);
			lblChooseGenerator.setBounds(11, 14, 122, 14);
			leftPanel.add(lblChooseGenerator);
			leftPanel.add(new JLabel(""));
		}
		{
			
//			rdbtnGenerator1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			rdbtnGenerator1.setFont(this.fontStandard);
			rdbtnGenerator1.setSelected(true);
			rdbtnGenerator1.setBounds(139, 10, 97, 23);
			buttonGroup.add(rdbtnGenerator1);
			rightPanel.add(rdbtnGenerator1);
		}
		{
			
//			rdbtnGenerator2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			rdbtnGenerator2.setFont(this.fontStandard);
			rdbtnGenerator2.setBounds(238, 10, 106, 23);
			buttonGroup.add(rdbtnGenerator2);
			rightPanel.add(rdbtnGenerator2);
		}
		{
			JLabel lblBoxLength = new JLabel("Box Length");
//			lblBoxLength.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblBoxLength.setFont(this.fontStandard);
			lblBoxLength.setBounds(11, 49, 122, 14);
			leftPanel.add(lblBoxLength);
		}
		{
			JLabel lblNumberOfRectangles = new JLabel("Number of Rectangles");
//			lblNumberOfRectangles.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNumberOfRectangles.setFont(this.fontStandard);
			lblNumberOfRectangles.setBounds(11, 77, 122, 14);
			leftPanel.add(lblNumberOfRectangles);
		}
		
		textFieldBoxLength = new JTextField();
		textFieldBoxLength.setBounds(170, 46, 86, 20);
		textFieldBoxLength.setFont(this.fontStandard);
		rightPanel.add(textFieldBoxLength);
		textFieldBoxLength.setColumns(10);
		{
			textFieldNrectangles = new JTextField();
			textFieldNrectangles.setColumns(10);
			textFieldNrectangles.setBounds(170, 74, 86, 20);
			textFieldNrectangles.setFont(this.fontStandard);
			rightPanel.add(textFieldNrectangles);
		}
		{
			JLabel lblLmin = new JLabel("Rectangle Lmin");
//			lblLmin.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblLmin.setFont(this.fontStandard);
			lblLmin.setBounds(11, 106, 122, 14);
			leftPanel.add(lblLmin);
		}
		{
			textFieldLMin = new JTextField();
			textFieldLMin.setColumns(10);
			textFieldLMin.setBounds(170, 103, 86, 20);
			textFieldLMin.setFont(this.fontStandard);
			rightPanel.add(textFieldLMin);
		}
		{
			JLabel lblLmax = new JLabel("Rectangle Lmax");
//			lblLmax.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblLmax.setFont(this.fontStandard);
			lblLmax.setBounds(11, 134, 122, 14);
			leftPanel.add(lblLmax);
		}
		{
			textFieldLMax = new JTextField();
			textFieldLMax.setColumns(10);
			textFieldLMax.setBounds(170, 131, 86, 20);
			textFieldLMax.setFont(this.fontStandard);
			rightPanel.add(textFieldLMax);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 164, 376, 33);
			contentPanel.add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				okButton = new JButton("Generate");
				okButton.setActionCommand("OK");
				okButton.setFont(this.fontStandard);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.setFont(this.fontStandard);
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	/*
	 * Enable text fields if Generator 1 is selected
	 */
	private void enableTextFields() {
		if (rdbtnGenerator1.isSelected()) {
			textFieldLMax.setEnabled(true);
			textFieldLMin.setEnabled(true);
			GeneratorType = 1;
			
		}
	}
	
	public Instance showDialog() {
		setVisible(true);
		return instance;
	}
	
	public void disposeInstance() {
		instance = null;
	}
	
	/**
	 * Generate the instance
	 * @return the generated instance
	 */
	private Instance generateInstance() {
		Instance instance = null;
		
		int boxLength = -1;
		int nRectangles = -1;
		boolean generationSuccessful = false;
		
		while (boxLength < 1) {
			try {
				boxLength = Integer.parseInt(textFieldBoxLength.getText());
				if (boxLength < 1) {
					System.out.println("Box length has to be greater than 0.");
					boxLength = -1;
				}
			} catch (Exception e) {
				System.out.println("Box length has to be an integer between 1 and x");
			}
		}
		
		while (nRectangles < 1) {
			try {
				nRectangles = Integer.parseInt(textFieldNrectangles.getText());
				if (nRectangles < 1) {
					System.out.println("Number of rectangles has to be greater than 0.");
					nRectangles = -1;
				}
			} catch (Exception e) {
				System.out.println("Number of rectangles has to be an integer between 1 and x");
			}
		}
	
			
		// Check inputs for:
		
		// 0. All ints > 0
		// 1. LMax >= LMin
		// 2. LMax <= LBox
		
		if (GeneratorType == 1) {
			IInstanceGenerator iGen1 = new InstanceGenerator1();
			
			int lMin = Integer.parseInt(textFieldLMin.getText());
			int lMax = Integer.parseInt(textFieldLMax.getText());
			
			instance = iGen1.generate(nRectangles, lMin, lMax, boxLength);
		} else {
			instance = null;
			
			IInstanceGenerator iGen2 = new InstanceGenerator2();
			instance = iGen2.generate(nRectangles, -1, -1, boxLength);
		}
		
		
		return instance;
		
	}
}
