package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import core.Box;
import core.Rectangle;

/**
 * A BoxPanel holds one Box object and paints this box and all the rectangles it contains
 * @author Jörg R. Schmidt <jroschmidt@gmail.com>
 *
 */
public class BoxPanel extends JPanel {

	private Box box;
	private int scaleFactor;
	private boolean multiColored;
	//private Color[] colors;
	
	public BoxPanel(Box box, int scaleFactor, boolean multiColored) {
		super();
		this.box = box;
		this.scaleFactor = scaleFactor;
		this.multiColored = multiColored;
		this.setLayout(null);	// Use null layout for absolute positioning
		//colors = new Color[this.box.getRectangles().size()];
		
		this.setBackground(Color.white);

//		// Display no. of rectangles contained in tooltip
//		this.setToolTipText("Box contains " + this.box.getRectangles().size() + " rectangles");
//		
		// Draw border of box
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		
//		if (this.multiColored) setRandomColors();
		
//		addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent me) {
//            	
//            }
//        });
			
	}
	
//	public void setRandomColors() {
//		int i = 0;
//		for (Rectangle rect : box.getRectangles()) {
//			colors[i++] = ColorGenerator.randomTransparentRGBColor(); // Set color for rectangle filling
//		}
//	}

//	public int getNRectangles() {
//		return this.box.getRectangles().size();
//	}
//	
//	public double getPackingPercentage() {
//		return this.box.getPackingPercentage();
//	}
	
	public void setMultiColored(boolean value) {
		this.multiColored = value;
	}
	
	/**
	 * Need to override getPreferredSize() when using a FlowLayout
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.box.getLength() * this.scaleFactor, this.box.getLength() * this.scaleFactor);
	}
	
	/**
	 * Paint the box border, background and its rectangles
	 * @param g
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		/* Draw rectangles contained in box */
		int i = 0;
		for (Rectangle rect : box.getRectangles()) {

			if (multiColored)
				g.setColor(rect.getColor()); // Set color for rectangle filling
			else
				g.setColor(Color.orange); // Set color for rectangle filling
			
			
			g.fillRect(rect.getPos().getX() * this.scaleFactor, rect.getPos().getY() * this.scaleFactor, 
					rect.getWidth() * this.scaleFactor, rect.getHeight() * this.scaleFactor);
			
			
			g.setColor(Color.BLACK); // Set color for border
			g.drawRect(rect.getPos().getX() * this.scaleFactor, rect.getPos().getY() * this.scaleFactor, 
					rect.getWidth() * this.scaleFactor, rect.getHeight() * this.scaleFactor);
			

////			g.setColor(ColorGenerator.randomTransparentRGBColor()); // Set color for rectangle
////			g.fillRect(rect.getPos().getX() * this.scaleFactor, rect.getPos().getY() * this.scaleFactor, 
////					rect.getWidth() * this.scaleFactor, rect.getHeight() * this.scaleFactor);
//			
			i++;
		}
		
	}
}
