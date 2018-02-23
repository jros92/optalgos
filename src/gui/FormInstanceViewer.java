package gui;

import core.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Canvas;

public class FormInstanceViewer extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FormInstanceViewer(Instance instance, String title, int dpi) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		InstanceCanvas canvas = new InstanceCanvas(instance, dpi);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 30, 335, 140);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(canvas);
	
		
		
		
//		contentPane.add(canvas, BorderLayout.CENTER);
	}

	
}

