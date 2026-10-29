package gui.elements;

import java.awt.Graphics;

import javax.swing.JPanel;

import engine.process.managers.SimulationManager;
/**
 * <p>A class that initialize the display of the first page "Settings".</p>
 * <p>It uses the class "PaintStrategy" to draw the page with an external illustration schema added</p>
 * <p>of the rocket used during the simulation</p>
 * @version 2.0
 * @since 2026-03-15
 * 
 */
public class SchemaPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private String filePath;
	
	public SchemaPanel(String filePath) {
		this.filePath=filePath;
	}
	
	


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    g.drawImage(SimulationManager.readImage(filePath), 0, 0, getWidth(), getHeight(), null);

	}
	//GETTERS/SETTERS

	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
